package mk.ukim.finki.emt.consultations.studentmanagement.domain.repository;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.StudentId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, StudentId> {

    Optional<Student> findByIndex(Index index);

}
