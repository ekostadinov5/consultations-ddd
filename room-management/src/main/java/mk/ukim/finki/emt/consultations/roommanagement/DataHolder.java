package mk.ukim.finki.emt.consultations.roommanagement;

import mk.ukim.finki.emt.consultations.roommanagement.domain.model.Room;
import mk.ukim.finki.emt.consultations.roommanagement.domain.model.enumeration.Building;
import mk.ukim.finki.emt.consultations.roommanagement.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    private final RoomRepository roomRepository;

    public static final List<Room> rooms = new ArrayList<>();

    public DataHolder(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PostConstruct
    public void init() {
        rooms.add(new Room("Б2.1", " ", Building.B));
        rooms.add(new Room("Б2.2", " ", Building.B));
        rooms.add(new Room("Б3.1", " ", Building.B));
        rooms.add(new Room("Б3.2", " ", Building.B));

        rooms.add(new Room("223", " ", Building.MF));
        rooms.add(new Room("225", " ", Building.MF));

        rooms.add(new Room("117", " ", Building.T));
        rooms.add(new Room("138", " ", Building.T));
        rooms.add(new Room("200АБ", " ", Building.T));
        rooms.add(new Room("200В", " ", Building.T));
        rooms.add(new Room("215", " ", Building.T));

        if (this.roomRepository.count() == 0) {
            this.roomRepository.saveAll(rooms);
        }
    }

}
