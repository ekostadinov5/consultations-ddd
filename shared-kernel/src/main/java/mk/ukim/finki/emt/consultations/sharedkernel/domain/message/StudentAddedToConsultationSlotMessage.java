package mk.ukim.finki.emt.consultations.sharedkernel.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObject;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ConsultationSlotId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ProfessorId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.StudentId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.SubjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentAddedToConsultationSlotMessage implements DomainObject {

    private StudentId studentId;

    private Index index;

    private FullName fullName;

    private SubjectId subjectId;

    private String note;

    private LocalDateTime createdOn;

    private ProfessorId professorId;

    private ConsultationSlotId consultationSlotId;

}
