package mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConsultationSlotStartTimeAfterEndTimeException extends RuntimeException {
}
