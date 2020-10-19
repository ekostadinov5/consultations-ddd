package mk.ukim.finki.emt.consultations.roommanagement.domain.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Building {

    AF("Анекс на ФЕИТ"),
    B("Бараки"),
    D("Деканат на ФИНКИ"),
    F("ФИНКИ"),
    MF("МФ / ФЕИТ"),
    T("ТМФ");

    private final String description;

}
