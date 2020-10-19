package mk.ukim.finki.emt.consultations.professormanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class ProfessorUsername implements ValueObject {

    private String username;

    protected ProfessorUsername() {}

    public ProfessorUsername(String username) {
        Objects.requireNonNull(username, "username must not be null");

        if (username.isEmpty()) {
            throw new IllegalArgumentException("The username must not be empty");
        }

        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorUsername that = (ProfessorUsername) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "ProfessorUsername{" +
                "username='" + username + '\'' +
                '}';
    }

}
