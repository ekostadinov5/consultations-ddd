package mk.ukim.finki.emt.consultations.professormanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.enumeration.Title;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.InvalidConsultationSlotIdException;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.InvalidRegularConsultationSlotIdException;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.exception.InvalidSubjectIdException;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.*;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Email;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "professor")
@Getter
public class Professor extends AbstractEntity<ProfessorId> {

    @Version
    private Long version;

    @Embedded
    @AttributeOverride(name = "username",
            column = @Column(name = "professor_username", nullable = false, unique = true))
    private ProfessorUsername professorUsername;

    @Column(name = "title", nullable = false)
    @Enumerated(EnumType.STRING)
    private Title title;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    })
    private FullName fullName;

    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @ManyToMany(/* cascade = CascadeType.ALL, */ fetch = FetchType.EAGER)
    private Set<Subject> subjects;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id")
    private Set<RegularConsultationSlot> regularConsultationSlots;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id")
    private Set<ConsultationSlot> additionalConsultationSlots;

    protected Professor() {}

    public Professor(ProfessorUsername professorUsername, Title title, FullName fullName, Email email) {
        super(DomainObjectId.randomId(ProfessorId.class));

        Objects.requireNonNull(professorUsername, "professorUsername must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        Objects.requireNonNull(email, "email must not be null");

        this.professorUsername = professorUsername;
        this.title = title;
        this.fullName = fullName;
        this.email = email;

        subjects = new HashSet<>();
        regularConsultationSlots = new HashSet<>();
        additionalConsultationSlots = new HashSet<>();
    }

    public String getTitleAndFullName() {
        return this.getTitle().getTitle() + " " + this.getFullName().getFirstName() + " "
                + this.getFullName().getLastName();
    }

    public Subject findSubjectById(SubjectId subjectId) {
        return this.subjects.stream()
                .filter(s -> s.id().equals(subjectId))
                .findFirst()
                .orElseThrow(InvalidSubjectIdException::new);
    }

    public Set<ConsultationSlot> getAllConsultationSlots() {
        Set<ConsultationSlot> result = new HashSet<>(this.additionalConsultationSlots);
        result.addAll(this.regularConsultationSlots.stream()
                .map(RegularConsultationSlot::getConsultationSlots)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
        return result;
    }

    public ConsultationSlot findConsultationSlotById(ConsultationSlotId consultationSlotId) {
        Set<ConsultationSlot> allConsultationSlots = getAllConsultationSlots();
        return allConsultationSlots.stream()
                .filter(cs -> cs.getId().equals(consultationSlotId))
                .findFirst()
                .orElseThrow(InvalidConsultationSlotIdException::new);
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public void removeSubject(SubjectId subjectId) {
        Subject subject = this.subjects.stream()
                .filter(s -> s.id().equals(subjectId))
                .findFirst()
                .orElseThrow(InvalidSubjectIdException::new);
        this.subjects.remove(subject);
    }

    public RegularConsultationSlot createRegularConsultationSlot(RoomId roomId, DayOfWeek dayOfWeek, LocalTime from,
                                                                 LocalTime to) {
        RegularConsultationSlot regularConsultationSlot = new RegularConsultationSlot(roomId, dayOfWeek, from, to);
        this.regularConsultationSlots.add(regularConsultationSlot);
        return regularConsultationSlot;
    }

    public RegularConsultationSlot updateRegularConsultationSlot(RegularConsultationSlotId regularConsultationSlotId,
                                                                 RoomId roomId, DayOfWeek dayOfWeek, LocalTime from,
                                                                 LocalTime to) {
        RegularConsultationSlot regularConsultationSlot = this.getRegularConsultationSlots().stream()
                .filter(rcs -> rcs.id().equals(regularConsultationSlotId))
                .findFirst()
                .orElseThrow(InvalidRegularConsultationSlotIdException::new);
        regularConsultationSlot.setFields(roomId, dayOfWeek, from, to);
        regularConsultationSlot.updateConsultationSlots(roomId, dayOfWeek, from, to);
        return regularConsultationSlot;
    }

    public RegularConsultationSlot deleteRegularConsultationSlot(RegularConsultationSlotId regularConsultationSlotId) {
        RegularConsultationSlot regularConsultationSlot = this.regularConsultationSlots.stream()
                .filter(rcs -> rcs.id().equals(regularConsultationSlotId))
                .findFirst()
                .orElseThrow(InvalidRegularConsultationSlotIdException::new);
        this.getRegularConsultationSlots().remove(regularConsultationSlot);
        return regularConsultationSlot;
    }

    public RegularConsultationSlot cancelConsultationSlot(RegularConsultationSlotId regularConsultationSlotId,
                                                          ConsultationSlotId consultationSlotId) {
        RegularConsultationSlot regularConsultationSlot = this.regularConsultationSlots.stream()
                .filter(rcs -> rcs.id().equals(regularConsultationSlotId))
                .findFirst()
                .orElseThrow(InvalidRegularConsultationSlotIdException::new);
        regularConsultationSlot.cancelConsultationSlot(consultationSlotId);
        return regularConsultationSlot;
    }

    public RegularConsultationSlot uncancelConsultationSlot(RegularConsultationSlotId regularConsultationSlotId,
                                                            ConsultationSlotId consultationSlotId) {
        RegularConsultationSlot regularConsultationSlot = this.regularConsultationSlots.stream()
                .filter(rcs -> rcs.id().equals(regularConsultationSlotId))
                .findFirst()
                .orElseThrow(InvalidRegularConsultationSlotIdException::new);
        regularConsultationSlot.uncancelConsultationSlot(consultationSlotId);
        return regularConsultationSlot;
    }

    public void createConsultationSlot() {
        this.regularConsultationSlots.forEach(RegularConsultationSlot::createConsultationSlot);
    }

    public ConsultationSlot createAdditionalConsultationSlot(RoomId roomId, LocalDate date, LocalTime from,
                                                             LocalTime to) {
        ConsultationSlot additionalConsultationSlot = new ConsultationSlot(roomId, date, from, to);
        this.additionalConsultationSlots.add(additionalConsultationSlot);
        return additionalConsultationSlot;
    }

    public ConsultationSlot updateAdditionalConsultationSlot(ConsultationSlotId consultationSlotId, RoomId roomId,
                                                             LocalDate date, LocalTime from, LocalTime to) {
        ConsultationSlot additionalConsultationSlot = this.additionalConsultationSlots.stream()
                .filter(acs -> acs.id().equals(consultationSlotId))
                .findFirst()
                .orElseThrow(InvalidConsultationSlotIdException::new);
        additionalConsultationSlot.setFields(roomId, date, from, to);
        return additionalConsultationSlot;
    }

    public ConsultationSlot deleteAdditionalConsultationSlot(ConsultationSlotId additionalConsultationSlotId) {
        ConsultationSlot additionalConsultationSlot = this.additionalConsultationSlots.stream()
                .filter(acs -> acs.id().equals(additionalConsultationSlotId))
                .findFirst()
                .orElseThrow(InvalidConsultationSlotIdException::new);
        this.additionalConsultationSlots.remove(additionalConsultationSlot);
        return additionalConsultationSlot;
    }

    public List<ConsultationSlot> deleteFinishedConsultationSlots() {
        List<ConsultationSlot> deletedConsultationSlots = new ArrayList<>();
        this.getRegularConsultationSlots().forEach(rcs ->
                deletedConsultationSlots.addAll(rcs.deleteFinishedConsultationSlots()));
        List<ConsultationSlot> additionalConsultationSlots =
                this.getAdditionalConsultationSlots().stream()
                        .filter(ConsultationSlot::isFinished)
                        .collect(Collectors.toList());
        this.getAdditionalConsultationSlots().removeAll(additionalConsultationSlots);
        deletedConsultationSlots.addAll(additionalConsultationSlots);
        return deletedConsultationSlots;
    }

    public void addStudentToConsultationSlot(ConsultationSlotId consultationSlotId, StudentId studentId, Index index,
                                             FullName fullName, SubjectId subjectId, String note,
                                             LocalDateTime createdOn) {
        ConsultationSlot consultationSlot = this.findConsultationSlotById(consultationSlotId);
        consultationSlot.addStudent(studentId, index, fullName, subjectId, note, createdOn);
    }

    public void removeStudentFromConsultationSlot(ConsultationSlotId consultationSlotId, StudentId studentId) {
        ConsultationSlot consultationSlot = this.findConsultationSlotById(consultationSlotId);
        consultationSlot.removeStudent(studentId);
    }

}
