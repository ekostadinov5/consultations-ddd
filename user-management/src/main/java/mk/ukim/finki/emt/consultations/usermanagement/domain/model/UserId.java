package mk.ukim.finki.emt.consultations.usermanagement.domain.model;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class UserId extends DomainObjectId {

    protected UserId() {
        super(DomainObjectId.randomId(UserId.class).getId());
    }

    public UserId(String id) {
        super(id);
    }

}
