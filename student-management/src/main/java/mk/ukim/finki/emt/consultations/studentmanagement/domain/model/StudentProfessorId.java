package mk.ukim.finki.emt.consultations.studentmanagement.domain.model;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class StudentProfessorId extends DomainObjectId {

    protected StudentProfessorId() {
        super(DomainObjectId.randomId(StudentProfessorId.class).getId());
    }

    public StudentProfessorId(String id) {
        super(id);
    }

}
