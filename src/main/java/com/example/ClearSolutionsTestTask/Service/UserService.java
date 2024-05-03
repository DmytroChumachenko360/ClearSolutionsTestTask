package com.example.ClearSolutionsTestTask.Service;

import com.example.ClearSolutionsTestTask.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Value("${user.min.age}")
    private int minAge;

    private static Integer idIterator = 0;

    private Map<Integer, User> users;

    public UserService(Map<Integer, User> newUsers) {
        users = newUsers;
    }

    public UserService() {
        users = new HashMap<>();

        User user1 = new User(
                idIterator += 1,
                "oskar_laczm@gmail.com",
                "Oskar",
                "Laczman",
                LocalDate.of(2002, 1, 8),
                "Popowskiego",
                "0000000000"
        );
        User user2 = new User(
                idIterator += 1,
                "norbert_lipka@gmail.com",
                "Norbert",
                "Lipka",
                LocalDate.of(2005, 2, 1),
                "Popowskiego",
                "0000000000"
        );
        User user3 = new User(
                idIterator += 1,
                "lili_bond@gmail.com",
                "Lili",
                "Bond",
                LocalDate.of(2009, 2, 1),
                "Popowskiego",
                "0000000000"
        );

        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);
        users.put(user3.getId(), user3);
    }

    public Map<Integer, User> getUsersMap() {
        return users;
    }

    public ResponseEntity<User> getUserById(@RequestParam Integer id) {
        if (users.containsKey(id)) {
            return new ResponseEntity(users.get(id), HttpStatus.OK);
        }
        return new ResponseEntity("Such user does not exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<User> createUser(@Validated @RequestBody User user) {
        LocalDate eighteenYearsAgo = LocalDate.now().minusYears(minAge);
        if (user.getBirthDate().isAfter(LocalDate.now())){
            return new ResponseEntity(" Value must be earlier than current date", HttpStatus.BAD_REQUEST);
        } else if (user.getBirthDate().isAfter(eighteenYearsAgo)) {
            return new ResponseEntity("You must be 18 years old to register", HttpStatus.BAD_REQUEST);
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return new ResponseEntity("You need to enter email", HttpStatus.BAD_REQUEST);
        } else if (!pattern.matcher(user.getEmail()).matches()) {
            return new ResponseEntity("Invalid email", HttpStatus.BAD_REQUEST);
        }

        idIterator += 1;
        user.setId(idIterator);
        users.put(user.getId(), user);

        return new ResponseEntity("Successfully created a user", HttpStatus.CREATED);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for (Map.Entry<Integer, User> user : users.entrySet()) {
            allUsers.add(user.getValue());
        }
        return allUsers;
    }

    public ResponseEntity<List<User>> updateOneOrSomeUsersById(List<User> newUsers) {
        for (User newUser : newUsers) {
            if (users.containsKey(newUser.getId())) {
                users.put(newUser.getId(), newUser);
            } else {
                return new ResponseEntity("You need to enter existing id number to identificate a user", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Successfully updated", HttpStatus.OK);
    }

    public ResponseEntity<List<User>> updateAllUsersById(List<User> newUsers) {
        for (User newUser : newUsers) {
            if (newUsers.size() < users.size()) {
                return new ResponseEntity("You need to enter all users", HttpStatus.BAD_REQUEST);
            }
            if (users.containsKey(newUser.getId())) {
                users.put(newUser.getId(), newUser);
            } else {
                return new ResponseEntity("You need to enter existing id number to identificate a user", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Successfully updated all users", HttpStatus.OK);
    }

    public List<User> getUsersByBirthDateRange(LocalDate from, LocalDate to) {
        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getBirthDate().isAfter(from) && user.getBirthDate().isBefore(to)) {
                result.add(user);
            }
        }
        return result;
    }

    public ResponseEntity<User> deleteUserById(Integer id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return new ResponseEntity("User is successfully deleted", HttpStatus.OK);
        }
        return new ResponseEntity("Such user does not exist", HttpStatus.BAD_REQUEST);
    }
}