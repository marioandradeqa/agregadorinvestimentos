package tech.kanchinha.agregadorinvestimentos.service;


import org.springframework.stereotype.Service;
import tech.kanchinha.agregadorinvestimentos.dto.CreateUserDto;
import tech.kanchinha.agregadorinvestimentos.dto.UpdateUserDto;
import tech.kanchinha.agregadorinvestimentos.entity.User;
import tech.kanchinha.agregadorinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.List;
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

    public List<User> getUserAll(){
        return userRepository.findAll();
    }

    public void deleteUserById(String userId){
        var id = UUID.fromString(userId);
        var userExist = userRepository.existsById(id);
        if (userExist){
            userRepository.deleteById(id);
        }
    }

    public Boolean updateUserById(String userId, UpdateUserDto updateUserDto){

        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()){
            var user = userEntity.get();
            if (updateUserDto.username() != null){
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null){
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
            return true;
        }

        return false;
    }

}
