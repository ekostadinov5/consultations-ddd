package mk.ukim.finki.emt.consultations.professormanagement.port.rest;

import mk.ukim.finki.emt.consultations.professormanagement.application.service.ProfessorService;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.*;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.dto.StudentConsultationSlotInfo;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ConsultationSlotId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.ProfessorId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.RoomId;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.identifier.SubjectId;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/professors", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class ProfessorApi {

    private final ProfessorService professorService;

    public ProfessorApi(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public Page<Professor> getAllProfessors(
            @RequestHeader(name = "page", defaultValue = "0", required = false) int page,
            @RequestHeader(name = "pageSize", defaultValue = "18", required = false) int pageSize) {
        return this.professorService.getAllProfessors(page, pageSize);
    }

    @GetMapping(params = "term")
    public List<Professor> searchProfessors(@RequestParam String term) {
        return this.professorService.searchProfessors(term);
    }

    @GetMapping("/listByIds")
    public List<Professor> getProfessors(@RequestHeader(name = "professorsIds") List<ProfessorId> professorIds) {
        return professorService.getProfessors(professorIds);
    }

    @GetMapping("/{professorId}")
    public Professor getProfessor(@PathVariable ProfessorId professorId) {
        return this.professorService.getProfessor(professorId);
    }

    @GetMapping("/byUsername/{professorUsername}")
    public Professor getProfessor(@PathVariable ProfessorUsername professorUsername) {
        return this.professorService.getProfessorByUsername(professorUsername);
    }

    @GetMapping("/studentConsultationSlotInfo/{professorId}")
    public StudentConsultationSlotInfo getStudentConsultationSlotInfo(@PathVariable ProfessorId professorId,
                                                                      @RequestHeader ConsultationSlotId
                                                                              consultationSlotId,
                                                                      @RequestHeader SubjectId subjectId) {
        return this.professorService.getStudentConsultationSlotInfo(professorId, consultationSlotId, subjectId);
    }

    @PostMapping("/regularConsultationSlots")
    @ResponseStatus(HttpStatus.CREATED)
    public RegularConsultationSlot createRegularConsultationSlot(@RequestParam ProfessorId professorId,
                                                                 @RequestParam RoomId roomId,
                                                                 @RequestParam String dayOfWeek,
                                                                 @RequestParam
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                         LocalTime from,
                                                                 @RequestParam
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                         LocalTime to) {
        return this.professorService
                .createRegularConsultationSlot(professorId, roomId, DayOfWeek.valueOf(dayOfWeek), from, to);
    }

    @PatchMapping("/regularConsultationSlots/{professorId}")
    public RegularConsultationSlot updateRegularConsultationSlot(@PathVariable ProfessorId professorId,
                                                                 @RequestParam RegularConsultationSlotId
                                                                         regularConsultationSlotId,
                                                                 @RequestParam RoomId roomId,
                                                                 @RequestParam String dayOfWeek,
                                                                 @RequestParam
                                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                             LocalTime from,
                                                                 @RequestParam
                                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                             LocalTime to) {
        return this.professorService.updateRegularConsultationSlot(professorId, regularConsultationSlotId, roomId,
                DayOfWeek.valueOf(dayOfWeek), from, to);
    }

    @DeleteMapping("/regularConsultationSlots/{professorId}/{regularConsultationSlotId}")
    public void deleteRegularConsultationSlot(@PathVariable ProfessorId professorId,
                                              @PathVariable RegularConsultationSlotId regularConsultationSlotId) {
        this.professorService.deleteRegularConsultationSlot(professorId, regularConsultationSlotId);
    }

    @PatchMapping("/regularConsultationSlots/cancel/{professorId}")
    public RegularConsultationSlot cancelConsultationSlot(@PathVariable ProfessorId professorId,
                                                          @RequestParam ConsultationSlotId consultationSlotId,
                                                          @RequestParam RegularConsultationSlotId
                                                                      regularConsultationSlotId) {
        return this.professorService.cancelConsultationSlot(professorId, regularConsultationSlotId, consultationSlotId);
    }

    @PatchMapping("/regularConsultationSlots/uncancel/{professorId}")
    public RegularConsultationSlot uncancelConsultationSlot(@PathVariable ProfessorId professorId,
                                                            @RequestParam RegularConsultationSlotId
                                                                    regularConsultationSlotId,
                                                            @RequestParam ConsultationSlotId consultationSlotId) {
        return this.professorService.
                uncancelConsultationSlot(professorId, regularConsultationSlotId, consultationSlotId);
    }

    @PostMapping("/additionalConsultationSlots")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultationSlot createAdditionalConsultationSlot(@RequestParam ProfessorId professorId,
                                                             @RequestParam RoomId roomId,
                                                             @RequestParam String date,
                                                             @RequestParam
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                         LocalTime from,
                                                             @RequestParam
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                         LocalTime to) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return this.professorService.createAdditionalConsultationSlot(professorId, roomId, localDate, from, to);
    }

    @PatchMapping("/additionalConsultationSlots/{professorId}")
    public ConsultationSlot updateAdditionalConsultationSlot(@PathVariable ProfessorId professorId,
                                                             @RequestParam ConsultationSlotId
                                                                            additionalConsultationSlotId,
                                                             @RequestParam RoomId roomId,
                                                             @RequestParam String date,
                                                             @RequestParam
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                         LocalTime from,
                                                             @RequestParam
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                                                         LocalTime to) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return this.professorService.updateAdditionalConsultationSlot(professorId, additionalConsultationSlotId, roomId,
                localDate, from, to);
    }

    @DeleteMapping("/additionalConsultationSlots/{professorId}/{additionalConsultationSlotId}")
    public void deleteAdditionalConsultationSlot(@PathVariable ProfessorId professorId,
                                                 @PathVariable ConsultationSlotId additionalConsultationSlotId) {
        this.professorService.deleteAdditionalConsultationSlot(professorId, additionalConsultationSlotId);
    }

}
