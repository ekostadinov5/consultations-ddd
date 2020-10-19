package mk.ukim.finki.emt.consultations.professormanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.StudentId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.SubjectId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "consultation_slot_student")
@Getter
public class ConsultationSlotStudent extends AbstractEntity<ConsultationSlotStudentId> {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "student_id", nullable = false))
    private StudentId studentId;

    @Embedded
    @AttributeOverride(name = "index", column = @Column(name = "student_index", nullable = false))
    private Index studentIndex;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "student_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "student_last_name"))
    })
    private FullName studentFullName;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "subject_id", nullable = false))
    private SubjectId subjectId;

    @Column(name = "note", nullable = false, length = 3000)
    private String note;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    protected ConsultationSlotStudent() {}

    public ConsultationSlotStudent(StudentId studentId, Index studentIndex, FullName studentFullName,
                                   SubjectId subjectId, String note, LocalDateTime createdOn) {
        super(DomainObjectId.randomId(ConsultationSlotStudentId.class));

        Objects.requireNonNull(studentId, "studentId must not be null");
        Objects.requireNonNull(studentIndex, "studentIndex must not be null");
        Objects.requireNonNull(studentFullName, "studentFullName must not be null");
        Objects.requireNonNull(subjectId, "subjectId must not be null");
        Objects.requireNonNull(note, "note must not be null");
        Objects.requireNonNull(createdOn, "createdOn must not be null");

        this.studentId = studentId;
        this.studentIndex = studentIndex;
        this.studentFullName = studentFullName;
        this.subjectId = subjectId;
        this.note = note;
        this.createdOn = createdOn;
    }

}
