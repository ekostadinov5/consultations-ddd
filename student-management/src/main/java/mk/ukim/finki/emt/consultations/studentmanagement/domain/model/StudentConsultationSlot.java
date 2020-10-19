package mk.ukim.finki.emt.consultations.studentmanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ConsultationSlotId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ProfessorId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.SubjectId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "student_consultation_slot")
@Getter
public class StudentConsultationSlot extends AbstractEntity<StudentConsultationSlotId> {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "professor_id", nullable = false))
    private ProfessorId professorId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "consultation_slot_id", nullable = false))
    private ConsultationSlotId consultationSlotId;

    @Column(name = "consultation_slot_start", nullable = false)
    private LocalDateTime consultationSlotStart;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "room_id", nullable = false))
    private RoomId roomId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "subject_id", nullable = false))
    private SubjectId subjectId;

    @Column(name = "note", nullable = false, length = 3000)
    private String note;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    protected StudentConsultationSlot() {}

    public StudentConsultationSlot(ProfessorId professorId, ConsultationSlotId consultationSlotId,
                                   LocalDateTime consultationSlotStart, RoomId roomId, SubjectId subjectId,
                                   String note) {
        super(DomainObjectId.randomId(StudentConsultationSlotId.class));

        Objects.requireNonNull(professorId, "professorId must not be null");
        Objects.requireNonNull(consultationSlotId, "consultationSlotId must not be null");
        Objects.requireNonNull(consultationSlotStart, "consultationSlotStart must not be null");
        Objects.requireNonNull(roomId, "roomId must not be null");
        Objects.requireNonNull(subjectId, "subjectId must not be null");
        Objects.requireNonNull(note, "note must not be null");

        this.professorId = professorId;
        this.consultationSlotId = consultationSlotId;
        this.consultationSlotStart = consultationSlotStart;
        this.roomId = roomId;
        this.subjectId = subjectId;
        this.note = note;
        this.createdOn = LocalDateTime.now();
    }

}
