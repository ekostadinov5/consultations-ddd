package mk.ukim.finki.emt.consultations.sharedkernel.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObject;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ConsultationSlotId;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConsultationSlotDeletedOrCanceledMessage implements DomainObject {

    private ConsultationSlotId consultationSlotId;

    private String professorTitleAndFullName;

    private LocalDate date;

    private LocalTime start;

    private Boolean notify;

}
