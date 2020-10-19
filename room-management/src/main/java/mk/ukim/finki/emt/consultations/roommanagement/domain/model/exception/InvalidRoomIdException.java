package mk.ukim.finki.emt.consultations.roommanagement.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidRoomIdException extends RuntimeException {
}
