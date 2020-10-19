package mk.ukim.finki.emt.consultations.professormanagement.domain.repository;

import mk.ukim.finki.emt.consultations.professormanagement.domain.model.Subject;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.SubjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, SubjectId> {
}
