package mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ConsultationSlotId extends DomainObjectId {

    protected ConsultationSlotId() {
        super(DomainObjectId.randomId(ConsultationSlotId.class).getId());
    }

    public ConsultationSlotId(String id) {
        super(id);
    }

}
