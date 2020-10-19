package mk.ukim.finki.emt.consultations.usermanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "role")
@Getter
public class Role extends AbstractEntity<RoleId> {

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    protected Role() {}

    public Role(String name) {
        super(DomainObjectId.randomId(RoleId.class));

        Objects.requireNonNull(name, "name must not be null");

        this.name = name;
    }

}
