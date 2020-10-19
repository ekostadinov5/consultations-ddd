package mk.ukim.finki.emt.consultations.studentmanagement.port.rest;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.*;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;
import mk.ukim.finki.emt.consultations.studentmanagement.application.service.StudentService;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/students", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class StudentApi {

    private final StudentService studentService;

    public StudentApi(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable StudentId studentId) {
        return this.studentService.getStudent(studentId);
    }

    @GetMapping("/index/{studentIndex}")
    public Student getStudentByIndex(@PathVariable Index studentIndex) {
        return this.studentService.getStudentByIndex(studentIndex);
    }

    @PostMapping("/follow")
    public StudentProfessor followProfessor(@RequestParam StudentId studentId,
                                            @RequestParam ProfessorId professorId) {
        return this.studentService.followProfessor(studentId, professorId);
    }

    @PostMapping("/unfollow")
    public StudentProfessor unfollowProfessor(@RequestParam StudentId studentId,
                                              @RequestParam ProfessorId professorId) {
        return this.studentService.unfollowProfessor(studentId, professorId);
    }

    @PostMapping("/add")
    public StudentConsultationSlot addToConsultationSlot(@RequestParam StudentId studentId,
                                                         @RequestParam ProfessorId professorId,
                                                         @RequestParam ConsultationSlotId consultationSlotId,
                                                         @RequestParam String date,
                                                         @RequestParam
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                     LocalTime from,
                                                         @RequestParam RoomId roomId,
                                                         @RequestParam SubjectId subjectId,
                                                         @RequestParam String note) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDateTime consultationSlotStart = LocalDateTime.of(localDate, from);
        return this.studentService.addToConsultationSlot(studentId, professorId, consultationSlotId,
                consultationSlotStart, roomId, subjectId, note);
    }

    @PostMapping("/remove")
    public StudentConsultationSlot removeFromConsultationSlot(@RequestParam StudentId studentId,
                                                              @RequestParam ProfessorId professorId,
                                                              @RequestParam ConsultationSlotId consultationSlotId) {
        return this.studentService.removeFromConsultationSlot(studentId, professorId, consultationSlotId);
    }

}
