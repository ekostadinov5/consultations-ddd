package mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidProfessorIdException extends RuntimeException {
}