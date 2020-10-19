package mk.ukim.finki.emt.consultations.professormanagement.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.enumeration.Title;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObject;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class StudentConsultationSlotInfo implements DomainObject {

    private final Title professorTitle;

    private final FullName professorFullName;

    private final LocalDate date;

    private final LocalTime from;

    private final LocalTime to;

    private final String subjectName;

}
