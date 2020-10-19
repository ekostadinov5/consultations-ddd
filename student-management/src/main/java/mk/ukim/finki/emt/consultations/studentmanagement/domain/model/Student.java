package mk.ukim.finki.emt.consultations.studentmanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.*;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Email;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.exception.ConsultationSlotInProgressException;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.exception.InvalidConsultationSlotIdException;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.exception.InvalidProfessorIdException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "student")
@Getter
public class Student extends AbstractEntity<StudentId> {

    @Version
    private Long version;

    @Embedded
    @AttributeOverride(name = "index", column = @Column(name = "student_index", nullable = false, unique = true))
    private Index index;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    })
    private FullName fullName;

    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Set<StudentProfessor> professorsFollowed;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Set<StudentConsultationSlot> consultationSlots;

    protected Student() {}

    public Student(Index index, FullName fullName, Email email) {
        super(DomainObjectId.randomId(StudentId.class));

        Objects.requireNonNull(index, "index must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        Objects.requireNonNull(email, "email must not be null");

        this.index = index;
        this.fullName = fullName;
        this.email = email;

        this.professorsFollowed = new HashSet<>();
        this.consultationSlots = new HashSet<>();
    }

    public StudentProfessor followProfessor(ProfessorId professorId) {
        StudentProfessor studentProfessor = new StudentProfessor(professorId);
        this.professorsFollowed.add(studentProfessor);
        return studentProfessor;
    }

    public StudentProfessor unfollowProfessor(ProfessorId professorId) {
        StudentProfessor studentProfessor = this.professorsFollowed.stream()
                .filter(pf -> pf.getProfessorId().equals(professorId))
                .findFirst()
                .orElseThrow(InvalidProfessorIdException::new);
        this.professorsFollowed.remove(studentProfessor);
        return studentProfessor;
    }

    public StudentConsultationSlot addConsultationSlot(ProfessorId professorId, ConsultationSlotId consultationSlotId,
                                                       LocalDateTime consultationSlotStart, RoomId roomId,
                                                       SubjectId subjectId, String note) {
        if (consultationSlotStart.isBefore(LocalDateTime.now())) {
            throw new ConsultationSlotInProgressException();
        }

        StudentConsultationSlot studentConsultationSlot = new StudentConsultationSlot(professorId, consultationSlotId,
                consultationSlotStart, roomId, subjectId, note);
        this.consultationSlots.add(studentConsultationSlot);
        return studentConsultationSlot;
    }

    public StudentConsultationSlot removeConsultationSlot(ConsultationSlotId consultationSlotId) {
        StudentConsultationSlot studentConsultationSlot = this.consultationSlots.stream()
                .filter(cs -> cs.getConsultationSlotId().equals(consultationSlotId))
                .findFirst()
                .orElseThrow(InvalidConsultationSlotIdException::new);

        if (studentConsultationSlot.getConsultationSlotStart().isBefore(LocalDateTime.now())) {
            throw new ConsultationSlotInProgressException();
        }

        this.consultationSlots.remove(studentConsultationSlot);
        return studentConsultationSlot;
    }

    public Boolean removeFromDeletedOrCanceledConsultationSlot(ConsultationSlotId consultationSlotId) {
        List<StudentConsultationSlot> slots = this.getConsultationSlots().stream()
                .filter(scs -> scs.getConsultationSlotId().equals(consultationSlotId))
                .collect(Collectors.toList());
        this.getConsultationSlots().removeAll(slots);
        return slots.size() > 0;
    }

}
