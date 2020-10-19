package mk.ukim.finki.emt.consultations.professormanagement.domain.model;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ConsultationSlotStudentId extends DomainObjectId {

    protected ConsultationSlotStudentId() {
        super(DomainObjectId.randomId(ConsultationSlotStudentId.class).getId());
    }

    public ConsultationSlotStudentId(String id) {
        super(id);
    }

}
