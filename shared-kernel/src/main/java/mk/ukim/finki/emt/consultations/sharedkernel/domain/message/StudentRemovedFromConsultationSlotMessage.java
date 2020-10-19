package mk.ukim.finki.emt.consultations.sharedkernel.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObject;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ConsultationSlotId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ProfessorId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.StudentId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentRemovedFromConsultationSlotMessage implements DomainObject {

    private StudentId studentId;

    private ProfessorId professorId;

    private ConsultationSlotId consultationSlotId;

}
