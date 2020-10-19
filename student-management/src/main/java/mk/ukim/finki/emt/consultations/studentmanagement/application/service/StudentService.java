package mk.ukim.finki.emt.consultations.studentmanagement.application.service;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.*;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface StudentService {

    Student getStudent(StudentId studentId);

    Student getStudentByIndex(Index index);

    StudentProfessor followProfessor(StudentId studentId, ProfessorId professorId);

    StudentProfessor unfollowProfessor(StudentId studentId, ProfessorId professorId);

    StudentConsultationSlot addToConsultationSlot(StudentId studentId, ProfessorId professorId,
                                                  ConsultationSlotId consultationSlotId,
                                                  LocalDateTime consultationSlotStart, RoomId roomId,
                                                  SubjectId subjectId, String note);

    StudentConsultationSlot removeFromConsultationSlot(StudentId studentId, ProfessorId professorId,
                                                       ConsultationSlotId consultationSlotId);

    void removeFromDeletedOrCanceledConsultationSlot(ConsultationSlotId consultationSlotId,
                                                     String professorTitleAndFullName, LocalDate date, LocalTime start,
                                                     Boolean notify);

}
