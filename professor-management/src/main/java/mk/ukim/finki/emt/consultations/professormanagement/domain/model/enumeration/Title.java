package mk.ukim.finki.emt.consultations.professormanagement.domain.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Title {
    A("Академик д-р"),
    D("д-р"),
    M("м-р"),
    S("спец.");

    private final String title;

}
