package pl.kamilmazurek.example.beans.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logExistingUsers() {
        var users = userRepository.findAll().stream().map(UserEntity::getLogin).toList();
        log.info("Existing users: " + String.join(", ", users));
    }

}