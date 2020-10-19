package mk.ukim.finki.emt.consultations.studentmanagement.domain.model;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class StudentConsultationSlotId extends DomainObjectId {

    protected StudentConsultationSlotId() {
        super(DomainObjectId.randomId(StudentConsultationSlotId.class).getId());
    }

    public StudentConsultationSlotId(String id) {
        super(id);
    }

}
