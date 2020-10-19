package mk.ukim.finki.emt.consultations.professormanagement.application.service;

import mk.ukim.finki.emt.consultations.professormanagement.domain.model.*;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.dto.StudentConsultationSlotInfo;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.*;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;
import org.springframework.data.domain.Page;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ProfessorService {

    Page<Professor> getAllProfessors(int page, int pageSize);

    List<Professor> searchProfessors(String term);

    List<Professor> getProfessors(List<ProfessorId> professorIds);

    Professor getProfessor(ProfessorId professorId);

    Professor getProfessorByUsername(ProfessorUsername professorUsername);

    StudentConsultationSlotInfo getStudentConsultationSlotInfo(ProfessorId professorId,
                                                               ConsultationSlotId consultationSlotId,
                                                               SubjectId subjectId);

    void addSubject(ProfessorId professorId, SubjectId subjectId);

    void removeSubject(ProfessorId professorId, SubjectId subjectId);

    RegularConsultationSlot createRegularConsultationSlot(ProfessorId professorId, RoomId roomId, DayOfWeek dayOfWeek,
                                                          LocalTime from, LocalTime to);

    RegularConsultationSlot updateRegularConsultationSlot(ProfessorId professorId,
                                                          RegularConsultationSlotId regularConsultationSlotId,
                                                          RoomId roomId, DayOfWeek dayOfWeek, LocalTime from,
                                                          LocalTime to);

    void deleteRegularConsultationSlot(ProfessorId professorId, RegularConsultationSlotId regularConsultationSlotId);

    RegularConsultationSlot cancelConsultationSlot(ProfessorId professorId,
                                                   RegularConsultationSlotId regularConsultationSlotId,
                                                   ConsultationSlotId consultationSlotId);

    RegularConsultationSlot uncancelConsultationSlot(ProfessorId professorId,
                                                     RegularConsultationSlotId regularConsultationSlotId,
                                                     ConsultationSlotId consultationSlotId);

    void createConsultationSlot();

    ConsultationSlot createAdditionalConsultationSlot(ProfessorId professorId, RoomId roomId, LocalDate date,
                                                      LocalTime from, LocalTime to);

    ConsultationSlot updateAdditionalConsultationSlot(ProfessorId professorId,
                                                      ConsultationSlotId additionalConsultationSlotId, RoomId roomId,
                                                      LocalDate date, LocalTime from, LocalTime to);

    void deleteAdditionalConsultationSlot(ProfessorId professorId, ConsultationSlotId additionalConsultationSlotId);

    void deleteFinishedConsultationSlots();

    void addStudentToConsultationSlot(ProfessorId professorId, ConsultationSlotId consultationSlotId,
                                      StudentId studentId, Index index, FullName fullName, SubjectId subjectId,
                                      String note, LocalDateTime createdOn);

    void removeStudentFromConsultationSlot(ProfessorId professorId, ConsultationSlotId consultationSlotId,
                                           StudentId studentId);

}
