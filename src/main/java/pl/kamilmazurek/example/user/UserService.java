package pl.kamilmazurek.example.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logExistingUsers() {
        var users = userRepository.findAll().stream().map(UserEntity::getName).toList();
        log.info("Existing users: " + String.join(", ", users));
    }

}