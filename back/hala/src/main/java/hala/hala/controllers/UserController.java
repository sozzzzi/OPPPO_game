package hala.hala.controllers;

import hala.hala.models.UserEntity;
import hala.hala.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Управление пользователями")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Создать нового пользователя")
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@Parameter(description = "Объект User для создания") @RequestBody UserEntity userEntity) {
        System.out.println("asdasd");
        UserEntity createdUserEntity = userService.createUser(userEntity);
        return ResponseEntity.ok(createdUserEntity);
    }

    @Operation(summary = "Удалить пользователя по ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID пользователя для удаления") @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Найти пользователя по имени")
    @GetMapping("/{userName}")
    public ResponseEntity<UserEntity> findUserByName(@Parameter(description = "Имя пользователя для поиска") @PathVariable String userName) {
        Optional<UserEntity> user = userService.findUserByName(userName);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}