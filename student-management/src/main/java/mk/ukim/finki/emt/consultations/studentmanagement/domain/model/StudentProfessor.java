package mk.ukim.finki.emt.consultations.studentmanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ProfessorId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "student_professor")
@Getter
public class StudentProfessor extends AbstractEntity<StudentProfessorId> {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "professor_id", nullable = false))
    private ProfessorId professorId;

    protected StudentProfessor() {}

    public StudentProfessor(ProfessorId professorId) {
        super(DomainObjectId.randomId(StudentProfessorId.class));

        Objects.requireNonNull(professorId, "professorId must not be null");

        this.professorId = professorId;
    }

}
