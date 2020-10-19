package mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class SubjectId extends DomainObjectId {

    protected SubjectId() {
        super(DomainObjectId.randomId(SubjectId.class).getId());
    }

    public SubjectId(String id) {
        super(id);
    }

}
