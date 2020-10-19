package mk.ukim.finki.emt.consultations.roommanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.roommanagement.domain.model.enumeration.Building;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "room")
@Getter
public class Room extends AbstractEntity<RoomId> {

    @Version
    private Long version;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "building", nullable = false)
    @Enumerated(EnumType.STRING)
    private Building building;

    protected Room() {}

    public Room(@NonNull String name, @NonNull String description, @NonNull Building building) {
        super(DomainObjectId.randomId(RoomId.class));

        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(description, "description must not be null");
        Objects.requireNonNull(building, "building must not be null");

        if (name.isEmpty()) {
            throw new IllegalArgumentException("The name must not be empty");
        }
        if (description.isEmpty()) {
            throw new IllegalArgumentException("The description must not be empty");
        }

        this.name = name;
        this.description = description;
        this.building = building;
    }

}
