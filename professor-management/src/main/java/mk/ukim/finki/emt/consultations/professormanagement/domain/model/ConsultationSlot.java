package mk.ukim.finki.emt.consultations.professormanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.ConsultationSlotStartTimeAfterEndTimeException;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.ConsultationSlotStartTimeInThePastException;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ConsultationSlotId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.StudentId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.SubjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "consultation_slot")
@Getter
public class ConsultationSlot extends AbstractEntity<ConsultationSlotId> {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "room_id"))
    private RoomId roomId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time_from", nullable = false)
    private LocalTime from;

    @Column(name = "time_to", nullable = false)
    private LocalTime to;

    @Column(name = "canceled", nullable = false)
    private Boolean canceled;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "consultation_slot_id")
    private Set<ConsultationSlotStudent> students;

    protected ConsultationSlot() {}

    public ConsultationSlot(RoomId roomId, LocalDate date, LocalTime from, LocalTime to) {
        super(DomainObjectId.randomId(ConsultationSlotId.class));

        setFields(roomId, date, from, to);

        this.students = new HashSet<>();
    }

    public void setFields(RoomId roomId, LocalDate date, LocalTime from, LocalTime to) {
        Objects.requireNonNull(roomId, "roomId must not be null");
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");

        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        if (from.isAfter(to)) {
            throw new ConsultationSlotStartTimeAfterEndTimeException();
        }
        if (date.isBefore(dateNow) || date.isEqual(dateNow) && from.isBefore(timeNow)) {
            throw new ConsultationSlotStartTimeInThePastException();
        }

        this.roomId = roomId;
        this.date = date;
        this.from = from;
        this.to = to;
        this.canceled = false;
    }

    public Boolean isInProgress() {
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        return this.date.isEqual(dateNow) && this.from.isBefore(timeNow) && this.to.isAfter(timeNow);
    }

    public Boolean isFinished() {
        LocalDateTime dateTime = LocalDateTime.of(this.getDate(), this.getTo());
        return dateTime.isBefore(LocalDateTime.now());
    }

    public void cancel() {
        this.canceled = true;
    }

    public void uncancel() {
        this.canceled = false;
    }

    public void addStudent(StudentId studentId, Index index, FullName fullName,
                           SubjectId subjectId, String note, LocalDateTime createdOn) {
        ConsultationSlotStudent consultationSlotStudent = new ConsultationSlotStudent(studentId, index, fullName,
                subjectId, note, createdOn);
        this.students.add(consultationSlotStudent);
    }

    public void removeStudent(StudentId studentId) {
        this.students.removeIf(s -> s.getStudentId().equals(studentId));
    }

}
