package mk.ukim.finki.emt.consultations.sharedkernel.domain.info;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Email implements ValueObject {

    private String email;

    protected Email() {}

    public Email(@NonNull String email) {
        Objects.requireNonNull(email, "email must not be null");

        if (!Email.isValid(email)) {
            throw new IllegalArgumentException("Invalid e-mail address");
        }

        this.email = email;
    }

    public static Boolean isValid(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";  // Simple e-mail validation
        return email.matches(regex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                '}';
    }

}
