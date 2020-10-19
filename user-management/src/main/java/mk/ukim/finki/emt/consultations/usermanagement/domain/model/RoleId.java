package mk.ukim.finki.emt.consultations.usermanagement.domain.model;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class RoleId extends DomainObjectId {

    protected RoleId() {
        super(DomainObjectId.randomId(RoleId.class).getId());
    }

    public RoleId(String id) {
        super(id);
    }

}
