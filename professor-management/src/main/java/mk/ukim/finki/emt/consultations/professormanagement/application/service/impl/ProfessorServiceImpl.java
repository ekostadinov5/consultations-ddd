package mk.ukim.finki.emt.consultations.professormanagement.application.service.impl;

import mk.ukim.finki.emt.consultations.professormanagement.application.kafka.senders.ConsultationSlotDeletedOrCanceledSender;
import mk.ukim.finki.emt.consultations.professormanagement.application.service.ProfessorService;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.*;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.dto.StudentConsultationSlotInfo;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.InvalidProfessorIdException;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.InvalidProfessorUsernameException;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.InvalidSubjectIdException;
import mk.ukim.finki.emt.consultations.professormanagement.domain.repository.ProfessorRepository;
import mk.ukim.finki.emt.consultations.professormanagement.domain.repository.SubjectRepository;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.*;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.ConsultationSlotDeletedOrCanceledMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final ConsultationSlotDeletedOrCanceledSender consultationSlotDeletedOrCanceledSender;

    public ProfessorServiceImpl(ProfessorRepository professorRepository, SubjectRepository subjectRepository,
                                ConsultationSlotDeletedOrCanceledSender consultationSlotDeletedOrCanceledSender) {
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.consultationSlotDeletedOrCanceledSender = consultationSlotDeletedOrCanceledSender;
    }

    @Override
    public Page<Professor> getAllProfessors(int page, int pageSize) {
        return this.professorRepository
                .findAll(PageRequest.of(page, pageSize,
                        Sort.by("fullName.lastName").and(Sort.by("fullName.firstName"))));
    }

    @Override
    public List<Professor> searchProfessors(String term) {
        return this.professorRepository.searchProfessors(term);
    }

    @Override
    public List<Professor> getProfessors(List<ProfessorId> professorIds) {
        return this.professorRepository.findAllById(professorIds);
    }

    @Override
    public Professor getProfessor(ProfessorId professorId) {
        return this.professorRepository.findById(professorId).orElseThrow(InvalidProfessorIdException::new);
    }

    @Override
    public Professor getProfessorByUsername(ProfessorUsername professorUsername) {
        return this.professorRepository.findByProfessorUsername(professorUsername)
                .orElseThrow(InvalidProfessorUsernameException::new);
    }

    @Override
    public StudentConsultationSlotInfo getStudentConsultationSlotInfo(ProfessorId professorId,
                                                                      ConsultationSlotId consultationSlotId,
                                                                      SubjectId subjectId) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        ConsultationSlot consultationSlot = professor.findConsultationSlotById(consultationSlotId);
        if (!subjectId.getId().equals("0")) {
            Subject subject = professor.findSubjectById(subjectId);
            return new StudentConsultationSlotInfo(professor.getTitle(), professor.getFullName(),
                    consultationSlot.getDate(), consultationSlot.getFrom(), consultationSlot.getTo(),
                    subject.getName());
        }
        return new StudentConsultationSlotInfo(professor.getTitle(), professor.getFullName(),
                consultationSlot.getDate(), consultationSlot.getFrom(), consultationSlot.getTo(),
                "Останато");
    }

    public void addSubject(ProfessorId professorId, SubjectId subjectId) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        Subject subject = this.subjectRepository.findById(subjectId).orElseThrow(InvalidSubjectIdException::new);
        professor.addSubject(subject);
        this.professorRepository.saveAndFlush(professor);
    }

    public void removeSubject(ProfessorId professorId, SubjectId subjectId) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        professor.removeSubject(subjectId);
        this.professorRepository.saveAndFlush(professor);
    }

    @Override
    public RegularConsultationSlot createRegularConsultationSlot(ProfessorId professorId, RoomId roomId,
                                                                 DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        RegularConsultationSlot regularConsultationSlot = professor
                .createRegularConsultationSlot(roomId, dayOfWeek, from, to);
        this.professorRepository.saveAndFlush(professor);
        return regularConsultationSlot;
    }

    @Override
    public RegularConsultationSlot updateRegularConsultationSlot(ProfessorId professorId,
                                                                 RegularConsultationSlotId regularConsultationSlotId,
                                                                 RoomId roomId, DayOfWeek dayOfWeek, LocalTime from,
                                                                 LocalTime to) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        RegularConsultationSlot regularConsultationSlot = professor
                .updateRegularConsultationSlot(regularConsultationSlotId, roomId, dayOfWeek, from, to);
        this.professorRepository.saveAndFlush(professor);
        return regularConsultationSlot;
    }

    @Override
    public void deleteRegularConsultationSlot(ProfessorId professorId,
                                              RegularConsultationSlotId regularConsultationSlotId) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        RegularConsultationSlot regularConsultationSlot =
                professor.deleteRegularConsultationSlot(regularConsultationSlotId);
        regularConsultationSlot.getConsultationSlots().forEach(cs ->
                this.consultationSlotDeletedOrCanceledSender
                        .sendMessage("consultation-slot-delete-or-cancel",
                                new ConsultationSlotDeletedOrCanceledMessage(cs.id(), professor.getTitleAndFullName(),
                                        cs.getDate(), cs.getFrom(), true)));
        this.professorRepository.saveAndFlush(professor);
    }

    @Override
    public RegularConsultationSlot cancelConsultationSlot(ProfessorId professorId,
                                                          RegularConsultationSlotId regularConsultationSlotId,
                                                          ConsultationSlotId consultationSlotId) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        RegularConsultationSlot regularConsultationSlot = professor
                .cancelConsultationSlot(regularConsultationSlotId, consultationSlotId);
        regularConsultationSlot.getConsultationSlots().forEach(cs ->
                this.consultationSlotDeletedOrCanceledSender
                        .sendMessage("consultation-slot-delete-or-cancel",
                                new ConsultationSlotDeletedOrCanceledMessage(cs.id(), professor.getTitleAndFullName(),
                                        cs.getDate(), cs.getFrom(), true)));
        this.professorRepository.saveAndFlush(professor);
        return regularConsultationSlot;
    }

    @Override
    public RegularConsultationSlot uncancelConsultationSlot(ProfessorId professorId,
                                                            RegularConsultationSlotId regularConsultationSlotId,
                                                            ConsultationSlotId consultationSlotId) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        RegularConsultationSlot regularConsultationSlot = professor
                .uncancelConsultationSlot(regularConsultationSlotId, consultationSlotId);
        this.professorRepository.saveAndFlush(professor);
        return regularConsultationSlot;
    }

    @Override
    public void createConsultationSlot() {
        this.professorRepository.findAll().forEach(Professor::createConsultationSlot);
    }

    @Override
    public ConsultationSlot createAdditionalConsultationSlot(ProfessorId professorId, RoomId roomId, LocalDate date,
                                                             LocalTime from, LocalTime to) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        ConsultationSlot consultationSlot = professor.createAdditionalConsultationSlot(roomId, date, from, to);
        this.professorRepository.saveAndFlush(professor);
        return consultationSlot;
    }

    @Override
    public ConsultationSlot updateAdditionalConsultationSlot(ProfessorId professorId,
                                                             ConsultationSlotId additionalConsultationSlotId,
                                                             RoomId roomId, LocalDate date, LocalTime from,
                                                             LocalTime to) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        ConsultationSlot consultationSlot = professor.updateAdditionalConsultationSlot(additionalConsultationSlotId,
                roomId, date, from, to);
        this.professorRepository.saveAndFlush(professor);
        return consultationSlot;
    }

    @Override
    public void deleteAdditionalConsultationSlot(ProfessorId professorId,
                                                 ConsultationSlotId additionalConsultationSlotId) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        ConsultationSlot additionalConsultationSlot =
                professor.deleteAdditionalConsultationSlot(additionalConsultationSlotId);
        this.consultationSlotDeletedOrCanceledSender.sendMessage("consultation-slot-delete-or-cancel",
                        new ConsultationSlotDeletedOrCanceledMessage(additionalConsultationSlot.id(),
                                professor.getTitleAndFullName(), additionalConsultationSlot.getDate(),
                                additionalConsultationSlot.getFrom(), true));
        this.professorRepository.saveAndFlush(professor);
    }

    @Override
    public void deleteFinishedConsultationSlots() {
        List<Professor> professors = this.professorRepository.findAll();
        professors.forEach(p -> {
            p.deleteFinishedConsultationSlots().forEach(cs-> this.consultationSlotDeletedOrCanceledSender
                    .sendMessage("consultation-slot-delete-or-cancel",
                            new ConsultationSlotDeletedOrCanceledMessage(cs.id(), null, null,
                                    null, false)));
        });
        this.professorRepository.saveAll(professors);
    }

    @Override
    public void addStudentToConsultationSlot(ProfessorId professorId, ConsultationSlotId consultationSlotId,
                                             StudentId studentId, Index index, FullName fullName, SubjectId subjectId,
                                             String note, LocalDateTime createdOn) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        professor.addStudentToConsultationSlot(consultationSlotId, studentId, index, fullName, subjectId, note,
                createdOn);
        this.professorRepository.saveAndFlush(professor);
    }

    @Override
    public void removeStudentFromConsultationSlot(ProfessorId professorId, ConsultationSlotId consultationSlotId,
                                                  StudentId studentId) {
        Professor professor = this.professorRepository.findById(professorId)
                .orElseThrow(InvalidProfessorIdException::new);
        professor.removeStudentFromConsultationSlot(consultationSlotId, studentId);
        this.professorRepository.saveAndFlush(professor);
    }

}
