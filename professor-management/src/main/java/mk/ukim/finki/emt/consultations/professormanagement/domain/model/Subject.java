package mk.ukim.finki.emt.consultations.professormanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.SubjectId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Objects;

@Entity
@Table(name = "subject")
@Getter
public class Subject extends AbstractEntity<SubjectId> {

    @Version
    private Long version;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    protected Subject() {}

    public Subject(String name) {
        super(DomainObjectId.randomId(SubjectId.class));

        Objects.requireNonNull(name, "name must not be null");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("The name must not be empty");
        }

        this.name = name;
    }

}
