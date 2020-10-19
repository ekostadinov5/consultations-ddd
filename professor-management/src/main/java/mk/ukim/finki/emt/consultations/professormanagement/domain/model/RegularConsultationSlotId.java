package mk.ukim.finki.emt.consultations.professormanagement.domain.model;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class RegularConsultationSlotId extends DomainObjectId {

    protected RegularConsultationSlotId() {
        super(DomainObjectId.randomId(RegularConsultationSlotId.class).getId());
    }

    public RegularConsultationSlotId(String id) {
        super(id);
    }

}
