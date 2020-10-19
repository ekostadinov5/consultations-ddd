package mk.ukim.finki.emt.consultations.roommanagement.port.rest;

import mk.ukim.finki.emt.consultations.roommanagement.application.service.RoomService;
import mk.ukim.finki.emt.consultations.roommanagement.domain.model.Room;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/rooms", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RoomApi {

    private final RoomService roomService;

    public RoomApi(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return this.roomService.getAllRooms();
    }

    @GetMapping(params = "term")
    public List<Room> searchRooms(@RequestParam String term) {
        return this.roomService.searchRooms(term);
    }

    @GetMapping("/{roomId}")
    public Room getRoom(@PathVariable RoomId roomId) {
        return this.roomService.getRoom(roomId);
    }

}
