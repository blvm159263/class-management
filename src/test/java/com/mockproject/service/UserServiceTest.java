package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.Level;
import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private LevelRepository levelRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private TrainingClassRepository trainingClassRepository;
    @MockBean
    private TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;
    @MockBean
    private TrainingClassAdminRepository trainingClassAdminRepository;
    @MockBean
    private AttendeeRepository attendeeRepository;

    @Autowired
    private UserService userService;

    Role role1 = new Role(1L, "Super Admin", true, null, null);
    Role role2 = new Role(2L, "Class Admin", true, null, null);

    User user1 = new User(1L, "user1@gmail.com", "123", "Tui la user 1", "user1.png",
            1, LocalDate.now(), "0123456789", true, true, role2, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null);
    User user2 = new User(2L, "user2@gmail.com", "123", "Tui la user 2", "user1.png",
            1, LocalDate.now(), "0123456789", true, true, role1, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null);
    User user3 = new User(3L, "user3@gmail.com", "123", "Tui la user 3", "user1.png",
            1, LocalDate.now(), "0123456789", true, true, role2, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null);

    Level level1 = new Level(1l, "AA","hihi", true, null);

    /**
     * Method under test: {@link UserService#listClassAdminTrue()}
     */
    @Test
    void canListUserHaveRoleClassAdminWithStatusTrue() {
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        Role role = new Role();
        role.setId(2L);

        when(userRepository.findByRoleAndStatus(role, true)).thenReturn(list.stream().filter(p -> p.getRole().getId() == 2L).toList());
        List<UserDTO> result = userService.listClassAdminTrue();

        assertEquals(2, result.size());
        assertEquals("user3@gmail.com", result.get(1).getEmail());
        verify(userRepository).findByRoleAndStatus((Role) any(), anyBoolean());
    }

    /**
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    void canGetUserById() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        UserDTO actualUser = userService.getUserById(2L);
        assertTrue(actualUser.isGender() && user2.isGender());
        assertTrue(user2.isStatus() && actualUser.isStatus());
        assertEquals(user2.getState(), actualUser.getState());
        assertEquals(user2.getRole().getId(), actualUser.getRoleId());
        assertEquals(user2.getPhone(), actualUser.getPhone());
        assertEquals(2L, actualUser.getId());
        assertEquals(user2.getImage(), actualUser.getImage());
        assertEquals(user2.getFullName(), actualUser.getFullName());
        assertEquals(user2.getEmail(), actualUser.getEmail());
        assertEquals(user2.getDob(), actualUser.getDob());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void canUpdateStatus(){
        //updatestatus to false to delete user

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);

        Boolean result = userService.updateStatus(1L);
        assertEquals(true, result);
        assertEquals(user1.isStatus(), false);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void canUpdateStateToFalse(){
        //update state to 0 to De-active user

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);

        int result = userService.updateStateToFalse(1L);
        assertEquals(0, result);
        assertEquals(user1.getState(), 0);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void canUpdateStateToTrue(){
        //update state to 1 to Active user

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);

        int result = userService.updateStateToTrue(1L);
        assertEquals(1, result);
        assertEquals(user1.getState(), 1);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void canChangeRole(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(roleRepository.getRoleById(1l)).thenReturn(Optional.of(role1));
        when(userRepository.save(user1)).thenReturn(user1);

        boolean result = userService.changeRole(1l, 1l);

        assertEquals(true, result );
        assertEquals(role1, user1.getRole());

        verify(userRepository, times(1)).findById(1l);
        verify(userRepository, times(1)).save(user1);
        verify(roleRepository,times(1)).getRoleById(1l);
    }

    @Test
    void canEditUser(){
        when(userRepository.findById(1l)).thenReturn(Optional.of(user1));
        when(levelRepository.getLevelById(1l)).thenReturn(Optional.of(level1));
        when(userRepository.save(user1)).thenReturn(user1);

        UserDTO userDTO = UserMapper.INSTANCE.toDTO(user1);
        userDTO.setFullName("Test");
        userDTO.setLevelId(1l);
        userDTO.setGender(true);
        LocalDate dob;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        dob = LocalDate.parse("28/10/2002", formatter);

        userDTO.setDob(dob);

        boolean result = userService.editUser(userDTO);

        assertEquals(true, result);
        assertEquals("Test", user1.getFullName());
        assertEquals(dob, user1.getDob());
        assertEquals(true, user1.isGender());

        verify(userRepository, times(1)).findById(1l);
        verify(levelRepository,times(1)).getLevelById(1l);
        verify(userRepository, times(1)).save(user1);
    }


}

