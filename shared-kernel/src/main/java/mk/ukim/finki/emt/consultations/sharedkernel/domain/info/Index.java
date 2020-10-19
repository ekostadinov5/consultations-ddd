package mk.ukim.finki.emt.consultations.sharedkernel.domain.info;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Index implements ValueObject {

    private String index;

    protected Index() {}

    public Index(String index) {
        Objects.requireNonNull(index, "index must not be null");

        if (index.isEmpty()) {
            throw new IllegalArgumentException("The index must not be empty");
        }

        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index1 = (Index) o;
        return Objects.equals(index, index1.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return "Index{" +
                "index='" + index + '\'' +
                '}';
    }

}
