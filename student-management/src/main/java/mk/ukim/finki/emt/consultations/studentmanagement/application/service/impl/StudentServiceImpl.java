package mk.ukim.finki.emt.consultations.studentmanagement.application.service.impl;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.*;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentAddedToConsultationSlotMessage;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentRemovedFromConsultationSlotMessage;
import mk.ukim.finki.emt.consultations.studentmanagement.application.kafka.senders.StudentAddedToConsultationSlotSender;
import mk.ukim.finki.emt.consultations.studentmanagement.application.kafka.senders.StudentRemovedFromConsultationSlotSender;
import mk.ukim.finki.emt.consultations.studentmanagement.application.mail.EmailService;
import mk.ukim.finki.emt.consultations.studentmanagement.application.service.StudentService;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.*;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.exception.InvalidStudentIdException;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.exception.InvalidStudentIndexException;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentAddedToConsultationSlotSender studentAddedToConsultationSlotSender;
    private final StudentRemovedFromConsultationSlotSender studentRemovedFromConsultationSlotSender;
    private final EmailService emailService;

    public StudentServiceImpl(StudentRepository studentRepository,
                              StudentAddedToConsultationSlotSender studentAddedToConsultationSlotSender,
                              StudentRemovedFromConsultationSlotSender studentRemovedFromConsultationSlotSender,
                              EmailService emailService) {
        this.studentRepository = studentRepository;
        this.studentAddedToConsultationSlotSender = studentAddedToConsultationSlotSender;
        this.studentRemovedFromConsultationSlotSender = studentRemovedFromConsultationSlotSender;
        this.emailService = emailService;
    }

    @Override
    public Student getStudent(StudentId studentId) {
        return this.studentRepository.findById(studentId).orElseThrow(InvalidStudentIdException::new);
    }

    @Override
    public Student getStudentByIndex(Index index) {
        return this.studentRepository.findByIndex(index).orElseThrow(InvalidStudentIndexException::new);
    }

    @Override
    public StudentProfessor followProfessor(StudentId studentId, ProfessorId professorId) {
        Student student = this.studentRepository.findById(studentId).orElseThrow(InvalidStudentIdException::new);
        StudentProfessor studentProfessor = student.followProfessor(professorId);
        this.studentRepository.saveAndFlush(student);
        return studentProfessor;
    }

    @Override
    public StudentProfessor unfollowProfessor(StudentId studentId, ProfessorId professorId) {
        Student student = this.studentRepository.findById(studentId).orElseThrow(InvalidStudentIdException::new);
        StudentProfessor studentProfessor = student.unfollowProfessor(professorId);
        this.studentRepository.saveAndFlush(student);
        return studentProfessor;
    }

    @Override
    public StudentConsultationSlot addToConsultationSlot(StudentId studentId, ProfessorId professorId,
                                                         ConsultationSlotId consultationSlotId,
                                                         LocalDateTime consultationSlotStart, RoomId roomId,
                                                         SubjectId subjectId, String note) {
        Student student = this.studentRepository.findById(studentId).orElseThrow(InvalidStudentIdException::new);
        StudentConsultationSlot studentConsultationSlot = student.addConsultationSlot(professorId, consultationSlotId,
                consultationSlotStart, roomId, subjectId, note);
        this.studentRepository.saveAndFlush(student);
        this.studentAddedToConsultationSlotSender.sendMessage("student-consultation-slot-add",
                new StudentAddedToConsultationSlotMessage(studentId, student.getIndex(), student.getFullName(),
                        subjectId, note, studentConsultationSlot.getCreatedOn(), professorId, consultationSlotId));
        return studentConsultationSlot;
    }

    @Override
    public StudentConsultationSlot removeFromConsultationSlot(StudentId studentId, ProfessorId professorId,
                                                              ConsultationSlotId consultationSlotId) {
        Student student = this.studentRepository.findById(studentId).orElseThrow(InvalidStudentIdException::new);
        StudentConsultationSlot studentConsultationSlot = student.removeConsultationSlot(consultationSlotId);
        this.studentRepository.saveAndFlush(student);
        this.studentRemovedFromConsultationSlotSender.sendMessage("student-consultation-slot-remove",
                new StudentRemovedFromConsultationSlotMessage(studentId, professorId, consultationSlotId));
        return studentConsultationSlot;
    }

    @Override
    public void removeFromDeletedOrCanceledConsultationSlot(ConsultationSlotId consultationSlotId,
                                                            String professorTitleAndFullName, LocalDate date,
                                                            LocalTime start, Boolean notify) {
        List<Student> students = this.studentRepository.findAll();
        students.forEach(s -> {
            Boolean removed = s.removeFromDeletedOrCanceledConsultationSlot(consultationSlotId);
            if (removed) {
                if (notify) {
                    emailService.sendMail(s.getEmail().getEmail(),
                            "Откажување на консултациски термин",
                            "Консултацискиот термин на " + professorTitleAndFullName + ", на датум " +
                                    date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " со почеток во " +
                                    start.format(DateTimeFormatter.ofPattern("HH:mm")) + " часот, за кој што се " +
                                    "имате пријавено, е откажан.");
                }
            }
        });
        this.studentRepository.saveAll(students);
    }

}
