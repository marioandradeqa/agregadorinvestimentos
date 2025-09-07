package tech.kanchinha.agregadorinvestimentos.service;


import org.springframework.stereotype.Service;
import tech.kanchinha.agregadorinvestimentos.dto.CreateUserDto;
import tech.kanchinha.agregadorinvestimentos.entity.User;
import tech.kanchinha.agregadorinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){

       User entity = new User();
       entity.setUsername(createUserDto.username());
       entity.setEmail(createUserDto.email());
       entity.setPassword(createUserDto.password());
       entity.setCreationTimestamp(Instant.now());

       var userSaved = userRepository.save(entity);

       return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }
}
