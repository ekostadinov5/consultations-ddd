package mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ProfessorId extends DomainObjectId {

    protected ProfessorId() {
        super(DomainObjectId.randomId(ProfessorId.class).getId());
    }

    public ProfessorId(String id) {
        super(id);
    }

}
