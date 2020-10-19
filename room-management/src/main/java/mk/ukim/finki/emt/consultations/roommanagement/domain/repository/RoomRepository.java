package mk.ukim.finki.emt.consultations.roommanagement.domain.repository;

import mk.ukim.finki.emt.consultations.roommanagement.domain.model.Room;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, RoomId> {

    @Query("SELECT r " +
            "FROM Room r " +
            "WHERE r.name LIKE %:term% OR r.description LIKE %:term%")
    List<Room> searchProfessors(@Param("term") String term);

}
