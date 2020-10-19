package mk.ukim.finki.emt.consultations.professormanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.ConsultationSlotStartTimeAfterEndTimeException;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.InvalidConsultationSlotIdException;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ConsultationSlotId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "regular_consultation_slot")
@Getter
public class RegularConsultationSlot extends AbstractEntity<RegularConsultationSlotId> {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "room_id"))
    private RoomId roomId;

    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "time_from", nullable = false)
    private LocalTime from;

    @Column(name = "time_to", nullable = false)
    private LocalTime to;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "regular_consultation_slot_id")
    private Set<ConsultationSlot> consultationSlots;

    protected RegularConsultationSlot() {}

    public RegularConsultationSlot(RoomId roomId, DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        super(DomainObjectId.randomId(RegularConsultationSlotId.class));

        setFields(roomId, dayOfWeek, from, to);

        createConsultationSlots();
    }

    public void setFields(RoomId roomId, DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        Objects.requireNonNull(roomId, "roomId must not be null");
        Objects.requireNonNull(dayOfWeek, "dayOfWeek must not be null");
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");

        if (from.isAfter(to)) {
            throw new ConsultationSlotStartTimeAfterEndTimeException();
        }

        this.roomId = roomId;
        this.dayOfWeek = dayOfWeek;
        this.from = from;
        this.to = to;
    }

    public void createConsultationSlots() {
        this.consultationSlots = new HashSet<>();
        LocalDate date = LocalDate.now().plusDays(1);
        while (!date.getDayOfWeek().equals(dayOfWeek)) {
            date = date.plusDays(1);
        }
        for (int i = 0; i < 3; i++, date = date.plusDays(7)) {
            ConsultationSlot consultationSlot = new ConsultationSlot(this.roomId, date, this.from, this.to);
            this.consultationSlots.add(consultationSlot);
        }
    }

    public void updateConsultationSlots(RoomId roomId, DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        LocalDate date = LocalDate.now().plusDays(1);
        while (!date.getDayOfWeek().equals(dayOfWeek)) {
            date = date.plusDays(1);
        }
        List<ConsultationSlot> consultationSlots = this.consultationSlots.stream()
                .sorted(Comparator.comparing(ConsultationSlot::getDate))
                .collect(Collectors.toList());
        for (int i = 0; i < 3; i++, date = date.plusDays(7)) {
            consultationSlots.get(i).setFields(roomId, date, from, to);
        }
    }

    public void cancelConsultationSlot(ConsultationSlotId consultationSlotId) {
        ConsultationSlot consultationSlot = this.consultationSlots.stream()
                .filter(cs -> cs.id().equals(consultationSlotId))
                .findFirst()
                .orElseThrow(InvalidConsultationSlotIdException::new);
        consultationSlot.getStudents().clear();
        consultationSlot.cancel();
    }

    public void uncancelConsultationSlot(ConsultationSlotId consultationSlotId) {
        ConsultationSlot consultationSlot = this.consultationSlots.stream()
                .filter(cs -> cs.id().equals(consultationSlotId))
                .findFirst()
                .orElseThrow(InvalidConsultationSlotIdException::new);
        consultationSlot.uncancel();
    }

    public void createConsultationSlot() {
        if (this.consultationSlots.size() == 2) {
            List<ConsultationSlot> consultationSlots = this.consultationSlots.stream()
                    .sorted(Comparator.comparing(ConsultationSlot::getDate))
                    .collect(Collectors.toList());
            LocalDate date = consultationSlots.get(1).getDate().plusDays(7);
            this.consultationSlots.add(new ConsultationSlot(this.roomId, date, this.from, this.to));
        }
    }

    public List<ConsultationSlot> deleteFinishedConsultationSlots() {
        List<ConsultationSlot> consultationSlots =
                this.getConsultationSlots().stream().filter(ConsultationSlot::isFinished).collect(Collectors.toList());
        this.getConsultationSlots().removeAll(consultationSlots);
        return consultationSlots;
    }

}
