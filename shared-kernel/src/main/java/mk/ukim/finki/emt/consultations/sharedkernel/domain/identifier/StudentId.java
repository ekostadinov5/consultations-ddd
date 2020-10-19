package mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class StudentId extends DomainObjectId {

    protected StudentId() {
        super(DomainObjectId.randomId(StudentId.class).getId());
    }

    public StudentId(String id) {
        super(id);
    }

}
