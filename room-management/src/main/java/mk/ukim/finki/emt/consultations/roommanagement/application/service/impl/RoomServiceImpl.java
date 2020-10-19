package mk.ukim.finki.emt.consultations.roommanagement.application.service.impl;

import mk.ukim.finki.emt.consultations.roommanagement.application.service.RoomService;
import mk.ukim.finki.emt.consultations.roommanagement.domain.model.Room;
import mk.ukim.finki.emt.consultations.roommanagement.domain.model.exception.InvalidRoomIdException;
import mk.ukim.finki.emt.consultations.roommanagement.domain.repository.RoomRepository;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return this.roomRepository.findAll();
    }

    @Override
    public List<Room> searchRooms(String term) {
        return this.roomRepository.searchProfessors(term);
    }

    @Override
    public Room getRoom(RoomId roomId) {
        return this.roomRepository.findById(roomId).orElseThrow(InvalidRoomIdException::new);
    }

}
