package com.example.ClearSolutionsTestTask.Controller;

import com.example.ClearSolutionsTestTask.Entity.User;
import com.example.ClearSolutionsTestTask.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserById(Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/updateOneOrSome")
    public ResponseEntity<List<User>> updateOneOrSomeUsersById(@RequestBody List<User> newUsers) {
        return userService.updateOneOrSomeUsersById(newUsers);
    }

    @PutMapping("/updateAll")
    public ResponseEntity<List<User>> updateAllUsersById(@RequestBody List<User> newUsers) {
        return userService.updateAllUsersById(newUsers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/getUsersByBirthDate")
    public List<User> getUsersByBirthDateRange(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return userService.getUsersByBirthDateRange(from, to);
    }
}
