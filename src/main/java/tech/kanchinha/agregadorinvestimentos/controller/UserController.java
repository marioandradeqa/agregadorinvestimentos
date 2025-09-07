package tech.kanchinha.agregadorinvestimentos.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.kanchinha.agregadorinvestimentos.dto.CreateUserDto;
import tech.kanchinha.agregadorinvestimentos.dto.UpdateUserDto;
import tech.kanchinha.agregadorinvestimentos.entity.User;
import tech.kanchinha.agregadorinvestimentos.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createUser(@RequestBody CreateUserDto createUserDto){
        var userId = userService.createUser(createUserDto);
        //return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("userId",userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User>  getUserById(@PathVariable("userId") String userId){
        var user = userService.getUserById(userId);

        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>>  getAllUsers(){
        var users = userService.getUserAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId){
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, String>> upadateById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto){

        if (!userService.updateUserById(userId, updateUserDto)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Usuário não existe"));
        }
       return ResponseEntity.noContent().build();
    }

}
