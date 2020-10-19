package mk.ukim.finki.emt.consultations.usermanagement.domain.repository;

import mk.ukim.finki.emt.consultations.usermanagement.domain.model.Role;
import mk.ukim.finki.emt.consultations.usermanagement.domain.model.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, RoleId> {
}
