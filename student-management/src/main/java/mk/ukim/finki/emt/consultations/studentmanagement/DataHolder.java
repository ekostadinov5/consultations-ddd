package mk.ukim.finki.emt.consultations.studentmanagement;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Email;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.FullName;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.info.Index;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.model.*;
import mk.ukim.finki.emt.consultations.studentmanagement.domain.repository.StudentRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    private final StudentRepository studentRepository;

    public static final List<Student> students = new ArrayList<>();

    public DataHolder(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void init() {

        Student ek = new Student(new Index("171242"), new FullName("Евгениј", "Костадинов"),
                new Email("evgenij.kostadinov@students.finki.ukim.mk"));
        Student tm = new Student(new Index("24545/17"), new FullName("Теодора", "Миткова"),
                new Email("tea_mitkova@yahoo.com"));
        Student am = new Student(new Index("171175"), new FullName("Ана", "Мангаровска"),
                new Email("ana.mangarovska@students.finki.ukim.mk"));
        Student zz = new Student(new Index("47400"), new FullName("Зоран", "Здравковски"),
                new Email("zoranzdravkovski99@yahoo.com"));
        Student vp = new Student(new Index("173098"), new FullName("Виктор", "Прентовски"),
                new Email("viktor.prentovski@students.finki.ukim.mk"));

        students.add(ek);
        students.add(tm);
        students.add(am);
        students.add(zz);
        students.add(vp);

        if (this.studentRepository.count() == 0) {
            this.studentRepository.saveAll(students);
        }
    }

}
