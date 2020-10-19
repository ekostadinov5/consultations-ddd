package mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class RoomId extends DomainObjectId {

    protected RoomId() {
        super(DomainObjectId.randomId(RoomId.class).getId());
    }

    public RoomId(String id) {
        super(id);
    }

}
