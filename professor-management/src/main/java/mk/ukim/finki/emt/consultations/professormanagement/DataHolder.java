package mk.ukim.finki.emt.consultations.professormanagement;

import mk.ukim.finki.emt.consultations.professormanagement.domain.model.Professor;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.ProfessorUsername;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.Subject;
import mk.ukim.finki.emt.consultations.professormanagement.domain.model.enumeration.Title;
import mk.ukim.finki.emt.consultations.professormanagement.domain.repository.ProfessorRepository;
import mk.ukim.finki.emt.consultations.professormanagement.domain.repository.SubjectRepository;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Email;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;

    public static final List<Professor> professors = new ArrayList<>();
    public static final List<Subject> subjects = new ArrayList<>();

    public DataHolder(ProfessorRepository professorRepository, SubjectRepository subjectRepository) {
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostConstruct
    public void init() {
        Subject kmb = new Subject("Компјутерски мрежи и безбедност");
        Subject vp = new Subject("Веб програмирање");
        Subject emt = new Subject("Електронска и мобилна трговија");

        subjects.add(kmb);
        subjects.add(vp);
        subjects.add(emt);

        Professor dt = new Professor(new ProfessorUsername("dimitar.trajanov"), Title.D,
                new FullName("Димитар", "Трајанов"), new Email("dimitar.trajanov@finki.ukim.mk"));
        Professor sg = new Professor(new ProfessorUsername("sasho.gramatikov"), Title.D,
                new FullName("Сашо", "Граматиков"), new Email("sasho.gramatikov@finki.ukim.mk"));
        Professor rs = new Professor(new ProfessorUsername("riste.stojanov"), Title.D,
                new FullName("Ристе", "Стојанов"), new Email("riste.stojanov@finki.ukim.mk"));
        Professor km = new Professor(new ProfessorUsername("kostadin.mishev"), Title.M,
                new FullName("Костадин", "Мишев"), new Email("kostadin.mishev@finki.ukim.mk"));
        Professor jd = new Professor(new ProfessorUsername("jovan.davchev"), Title.S,
                new FullName("Јован", "Давчев"), new Email("jovan.davchev@finki.ukim.mk"));

        dt.addSubject(vp);
        dt.addSubject(emt);
        sg.addSubject(vp);
        sg.addSubject(emt);
        rs.addSubject(kmb);
        rs.addSubject(vp);
        rs.addSubject(emt);
        km.addSubject(kmb);
        km.addSubject(vp);
        km.addSubject(emt);
        jd.addSubject(emt);

        professors.add(dt);
        professors.add(sg);
        professors.add(rs);
        professors.add(km);
        professors.add(jd);
        professors.add(new Professor(new ProfessorUsername("ljupcho.kocarev"), Title.A,
                new FullName("Љупчо", "Коцарев"), new Email("ljupcho.kocarev@finki.ukim.mk")));
        professors.add(new Professor(new ProfessorUsername("nevena.ackovska"), Title.D,
                new FullName("Невена", "Ацковска"), new Email("nevena.ackovska@finki.ukim.mk")));
        professors.add(new Professor(new ProfessorUsername("aleksandra.popovska.mitrovikj"), Title.D,
                new FullName("Александра", "Поповска Митровиќ"),
                new Email("aleksandra.popovska.mitrovikj@finki.ukim.mk")));
        professors.add(new Professor(new ProfessorUsername("stefan.andonov"), Title.S,
                new FullName("Стефан", "Андонов"), new Email("stefan.andonov@finki.ukim.mk")));

        if (this.professorRepository.count() == 0) {
            this.subjectRepository.saveAll(subjects);
            this.professorRepository.saveAll(professors);
        }
    }

}
