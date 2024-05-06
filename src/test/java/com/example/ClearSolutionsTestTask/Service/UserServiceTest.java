package com.example.ClearSolutionsTestTask.Service;

import com.example.ClearSolutionsTestTask.Entity.User;
import com.example.ClearSolutionsTestTask.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    private UserService userService;
    private Map<Integer, User> users;

    @BeforeEach
    public void setUp() {
        users = new HashMap<>();
        User user1 = new User(1, "oskar_laczm@gmail.com", "Oskar", "Laczman", LocalDate.of(2002, 1, 8), "Popowskiego", "0000000000");
        User user2 = new User(2, "norbert_lipka@gmail.com", "Norbert", "Lipka", LocalDate.of(2005, 2, 1), "Popowskiego", "0000000000");
        User user3 = new User(3, "lili_bond@gmail.com", "Lili", "Bond", LocalDate.of(2009, 2, 1), "Popowskiego", "0000000000");

        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);
        users.put(user3.getId(), user3);

        userService = new UserService(users);
    }

    @Test
    public void testGetAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        assertEquals(3, allUsers.size());
        assertEquals(users.get(1), allUsers.get(0));
        assertEquals(users.get(2), allUsers.get(1));
        assertEquals(users.get(3), allUsers.get(2));
    }
    @Test
    public void testGetUserById() {
        ResponseEntity<User> response = userService.getUserById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users.get(1), response.getBody());

        response = userService.getUserById(100);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Such user does not exist", response.getBody());
    }

    @Test
    public void testCreateUser() {
        User newUser = new User(5, "test@gmail.com", "Test", "User", LocalDate.of(2000, 1, 1), "Test Street", "1234567890");
        ResponseEntity<User> response = userService.createUser(newUser);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Successfully created a user", response.getBody());
        assertNotNull(users.get(newUser.getId()));
    }

    @Test
    public void testUpdateOneOrSomeUsersById() {
        User updatedUser = new User(1, "updated@gmail.com", "Updated", "User", LocalDate.of(2000, 1, 1), "Updated Street", "1234567890");
        ResponseEntity<List<User>> response = userService.updateOneOrSomeUsersById(List.of(updatedUser));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated", response.getBody());
        assertEquals(updatedUser, users.get(1));
    }

    @Test
    public void testUpdateAllUsersById() {
        User updatedUser = new User(1, "updated@gmail.com", "Updated", "User", LocalDate.of(2000, 1, 1), "Updated Street", "1234567890");
        ResponseEntity<List<User>> response = userService.updateOneOrSomeUsersById(List.of(updatedUser));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated", response.getBody());
        assertEquals(updatedUser, users.get(1));
    }

    @Test
    public void testGetUsersByBirthDateRange() {
        List<User> result = userService.getUsersByBirthDateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2010, 1, 1));
        assertEquals(3, result.size());
        assertTrue(result.contains(users.get(1)));
        assertTrue(result.contains(users.get(2)));
        assertTrue(result.contains(users.get(3)));
    }

    @Test
    public void testDeleteUserById() {
        ResponseEntity<User> response = userService.deleteUserById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User is successfully deleted", response.getBody());
        assertNull(users.get(1));

        response = userService.deleteUserById(100);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Such user does not exist", response.getBody());
    }
}


//package com.example.ClearSolutionsTestTask.Service;
//
//import com.example.ClearSolutionsTestTask.Entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDate;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private Map<Integer, User> users;
//
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//
//        users = new HashMap<>();
//
//        User user1 = new User(
//                1,
//                "test@gmail.com",
//                "Example",
//                "Example",
//                LocalDate.of(2000, 1, 1),
//                "Street",
//                "0000000000"
//        );
//
//        users.put(user1.getId(), user1);
//    }
//
//    @Test
//    public void testGetUserById() {
//        UserService userService = mock(UserService.class);
//        User user = new User(1, "test@gmail.com", "Test", "User", LocalDate.of(2000, 1, 1), "Test Street", "0000000000");
//
//        ResponseEntity<User> responseEntity = ResponseEntity.ok(user);
//
//        when(userService.getUserById(1)).thenReturn(responseEntity);
//
//        ResponseEntity<User> response = userService.getUserById(1);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user, response.getBody());
//    }
//
//    @Test
//    public void testCreateUser() {
//        Map<Integer, User> users = mock(Map.class);
//        User user = new User(1, "test@gmail.com", "Example", "Example", LocalDate.of(2000, 1, 1), "Street", "0000000000");
//
//        when(users.put(anyInt(), any(User.class))).thenReturn(null);
//
//        UserService userService = new UserService(users);
//
//        ResponseEntity<User> response = userService.createUser(user);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(users, times(1)).put(anyInt(), any(User.class));
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        List<User> userList = mock(List.class);
//        UserService userService = mock(UserService.class);
//
//        userList = userService.getAllUsers();
//
//        //userList.add(user1);
//        //userList.add(user2);
//
//        assertNotNull(userList);
//        assertEquals(2, userList.size());
//        assertTrue(userList.contains(user1));
//        assertTrue(userList.contains(user2));
//    }
//
//
//    @Test
//    public void testUpdateOneOrSomeUsersById() {
//        User user1 = new User(1, "test1@gmail.com", "Test1", "User1", LocalDate.of(2000, 1, 1), "Test Street", "0000000000");
//        User user2 = new User(2, "test2@gmail.com", "Test2", "User2", LocalDate.of(2000, 1, 1), "Test Street", "0000000000");
//        List<User> newUsers = Arrays.asList(user1, user2);
//
//        users = mock(Map.class);
//
//        when(users.containsKey(1)).thenReturn(true);
//        when(users.containsKey(2)).thenReturn(true);
//
//        users.put(user1.getId(), user1);
//        users.put(user2.getId(), user2);
//
//        ResponseEntity<List<User>> response = userService.updateOneOrSomeUsersById(newUsers);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(users, times(1)).put(1, user1);
//        verify(users, times(1)).put(2, user2);
//    }
//
//    @Test
//    public void testUpdateAllUsersById() {
//        User user1 = new User(1, "test1@gmail.com", "Test1", "User1", LocalDate.of(2000, 1, 1), "Test Street", "0000000000");
//        User user2 = new User(2, "test2@gmail.com", "Test2", "User2", LocalDate.of(2000, 1, 1), "Test Street", "0000000000");
//        List<User> newUsers = Arrays.asList(user1, user2);
//
//        when(users.size()).thenReturn(2);
//        when(users.containsKey(1)).thenReturn(true);
//        when(users.containsKey(2)).thenReturn(true);
//
//        ResponseEntity<List<User>> response = userService.updateAllUsersById(newUsers);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(users, times(1)).put(1, user1);
//        verify(users, times(1)).put(2, user2);
//    }
//
//    @Test
//    public void testGetUsersByBirthDateRange() {
//        User user1 = new User(1, "test1@gmail.com", "Test1", "User1", LocalDate.of(2000, 1, 1), "Test Street", "0000000000");
//        User user2 = new User(2, "test2@gmail.com", "Test2", "User2", LocalDate.of(2002, 1, 1), "Test Street", "0000000000");
//        User user3 = new User(3, "test3@gmail.com", "Test3", "User3", LocalDate.of(2004, 1, 1), "Test Street", "0000000000");
//
//        when(users.values()).thenReturn(Arrays.asList(user1, user2, user3));
//
//        List<User> result = userService.getUsersByBirthDateRange(LocalDate.of(2001, 1, 1), LocalDate.of(2003, 1, 1));
//
//        assertEquals(1, result.size());
//        assertEquals(user2, result.get(0));
//    }
//
//    @Test
//    public void testDeleteUserById() {
//        when(users.containsKey(1)).thenReturn(true);
//
//        ResponseEntity<User> response = userService.deleteUserById(1);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(users, times(1)).remove(1);
//    }
//
//}
//
