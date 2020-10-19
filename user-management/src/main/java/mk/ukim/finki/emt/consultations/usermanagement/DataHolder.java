package mk.ukim.finki.emt.consultations.usermanagement;

import mk.ukim.finki.emt.consultations.usermanagement.domain.model.Role;
import mk.ukim.finki.emt.consultations.usermanagement.domain.model.User;
import mk.ukim.finki.emt.consultations.usermanagement.domain.repository.RoleRepository;
import mk.ukim.finki.emt.consultations.usermanagement.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public static final List<User> users = new ArrayList<>();
    public static final List<Role> roles = new ArrayList<>();

    public DataHolder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Role admin = new Role("ADMIN");
        Role professor = new Role("PROFESSOR");
        Role student = new Role("STUDENT");

        roles.add(admin);
        roles.add(professor);
        roles.add(student);

        users.add(new User("dimitar.trajanov", passwordEncoder.encode("dimitar.trajanov"),
                professor));
        users.add(new User("sasho.gramatikov", passwordEncoder.encode("sasho.gramatikov"),
                professor));
        users.add(new User("riste.stojanov", passwordEncoder.encode("riste.stojanov"),
                professor));
        users.add(new User("kostadin.mishev", passwordEncoder.encode("kostadin.mishev"),
                professor));
        users.add(new User("jovan.davchev", passwordEncoder.encode("jovan.davchev"),
                professor));
        users.add(new User("ljupcho.kocarev", passwordEncoder.encode("ljupcho.kocarev"),
                professor));
        users.add(new User("nevena.ackovska", passwordEncoder.encode("nevena.ackovska"),
                professor));
        users.add(new User("aleksandra.popovska.mitrovikj",
                passwordEncoder.encode("aleksandra.popovska.mitrovikj"), professor));
        users.add(new User("stefan.andonov", passwordEncoder.encode("stefan.andonov"),
                professor));

        users.add(new User("171242", passwordEncoder.encode("171242"), student));
        users.add(new User("24545/17", passwordEncoder.encode("24545/17"), student));
        users.add(new User("171175", passwordEncoder.encode("171175"), student));
        users.add(new User("47400", passwordEncoder.encode("47400"), student));
        users.add(new User("173098", passwordEncoder.encode("173098"), student));

        if (this.userRepository.count() == 0) {
            this.roleRepository.saveAll(roles);
            this.userRepository.saveAll(users);
        }
    }

}
