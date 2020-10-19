package mk.ukim.finki.emt.consultations.professormanagement.domain.repository;

import mk.ukim.finki.emt.consultations.professormanagement.domain.model.Professor;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.ProfessorUsername;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ProfessorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, ProfessorId> {

    @Query("SELECT p " +
            "FROM Professor p " +
            "WHERE p.fullName.firstName LIKE %:term% OR p.fullName.lastName LIKE %:term% " +
            "ORDER BY p.fullName.lastName, p.fullName.lastName")
    List<Professor> searchProfessors(@Param("term") String term);

    Optional<Professor> findByProfessorUsername(ProfessorUsername professorUsername);

}
