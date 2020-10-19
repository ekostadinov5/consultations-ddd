package mk.ukim.finki.emt.consultations.roommanagement.application.service;

import mk.ukim.finki.emt.consultations.roommanagement.domain.model.Room;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();

    List<Room> searchRooms(String term);

    Room getRoom(RoomId roomId);

}
