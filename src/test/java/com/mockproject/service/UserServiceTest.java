package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.*;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.IUnitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
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
    private IUnitService unitService;


    @Autowired
    private UserService userService;

    //Create Role
    Role role1 = new Role(1L, "Super Admin", true, null, null);
    Role role2 = new Role(2L, "Class Admin", true, null, null);
    Role role3 = new Role(3L, "Trainer", true, null, null);

    //Create User
    User user1 = new User(1L, "user1@gmail.com", "123", "Tui la user 1", "user1.png",
            1, LocalDate.now(), "0123456789", true, true, role1, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null);
    User user2 = new User(2L, "user2@gmail.com", "123", "Tui la user 2", "user1.png",
            1, LocalDate.now(), "0123456789", true, true, role2, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null);
    User user3 = new User(3L, "user3@gmail.com", "123", "Tui la user 3", "user1.png",
            1, LocalDate.now(), "0123456789", true, true, role3, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null);

    //Create training class
    TrainingClass tc1 = new TrainingClass(1L, "Class Name 1", "TC1", null, null,
            null, null, 12, 30, 30, 25, "Planning", null,
            null, null, null, 1, true, null, null,
            null, null, null, user2, null, user1, user1,
            null, null, null);

    //Create training class admin
    TrainingClassAdmin admin1 = new TrainingClassAdmin(1L, true, user1, tc1);
    TrainingClassAdmin admin2 = new TrainingClassAdmin(2L, true, user2, tc1);

    // Create Unit
    Unit unit1 = new Unit(1L, "Unit title 123", 1, BigDecimal.TEN, true, null, null, null);
    Unit unit2 = new Unit(2L, "Unit title 234", 2, BigDecimal.TEN, true, null, null, null);

    //Create training class unit information
    TrainingClassUnitInformation ui1 = new TrainingClassUnitInformation(1L, true, user3, unit1, tc1, null);
    TrainingClassUnitInformation ui2 = new TrainingClassUnitInformation(2L, true, user1, unit2, tc1, null);

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

    /**
     * Method under test: {@link UserService#getAdminByClassId(long)}
     */
    @Test
    void canGetAdminByTrainingClassId() {
        //Create list training class admin
        List<TrainingClassAdmin> adminList = new ArrayList<>();
        adminList.add(admin1);
        adminList.add(admin2);

        //Create training class Id
        Long trainingClassId = 1L;
        tc1.setListTrainingClassAdmins(adminList);

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(tc1));

        adminList.stream().filter(TrainingClassAdmin::isStatus).toList();
        when(trainingClassAdminRepository.findByTrainingClassAndStatus(tc1, true))
                .thenReturn(adminList);
        adminList.forEach(a ->
                when(userRepository.findByIdAndStatus(a.getAdmin().getId(), true))
                        .thenReturn(Optional.of(adminList.get(adminList.indexOf(a)).getAdmin())));

        List<UserDTO> admin = userService.getAdminByClassId(trainingClassId);

        assertEquals(2, admin.size());
        assertEquals(1L, admin.get(0).getId());
        assertEquals("user1@gmail.com", admin.get(0).getEmail());
        assertEquals("Tui la user 1", admin.get(0).getFullName());
        assertEquals("user2@gmail.com", admin.get(1).getEmail());
        assertEquals("Tui la user 2", admin.get(1).getFullName());
        assertTrue(admin.stream().filter(a -> !a.isStatus()).toList().isEmpty());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
        verify(trainingClassAdminRepository).findByTrainingClassAndStatus(tc1, true);
        adminList.forEach(a ->
                verify(userRepository).findByIdAndStatus(a.getAdmin().getId(), true));
    }

    @Test
    void itShouldThrowExceptionWhenTrainingClassAdminNotFound() {
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getAdminByClassId(1L));
        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }

    /**
     * Method under test: {@link UserService#getTrainerByClassId(Long)}
     */
    @Test
    void canGetTrainerByClassId() {
        //Create training class unit information
        List<TrainingClassUnitInformation> trainingClassUnitInformation =
                new ArrayList<>();
        trainingClassUnitInformation.add(ui1);
        trainingClassUnitInformation.add(ui2);

        //Create training class
        Long trainingClassId = 1L;
        tc1.setListTrainingClassUnitInformations(trainingClassUnitInformation);

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(tc1));
        when(trainingClassUnitInformationRepository.findByTrainingClassAndStatus(tc1, true))
                .thenReturn(Optional.of(trainingClassUnitInformation));
        trainingClassUnitInformation.forEach(t ->
                when(userRepository.findByIdAndStatus(t.getTrainer().getId(), true))
                        .thenReturn(Optional.of(trainingClassUnitInformation
                                .get(trainingClassUnitInformation.indexOf(t)).getTrainer())));

        List<UserDTO> trainersList = userService.getTrainerByClassId(trainingClassId);
        assertEquals(2, trainersList.size());
        assertEquals(3L, trainersList.get(0).getId());
        assertEquals("user3@gmail.com", trainersList.get(0).getEmail());
        assertEquals(1L, trainersList.get(1).getId());
        assertEquals("Tui la user 1", trainersList.get(1).getFullName());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
        verify(trainingClassUnitInformationRepository).findByTrainingClassAndStatus(tc1, true);
        trainingClassUnitInformation.forEach(t ->
                verify(userRepository).findByIdAndStatus(t.getTrainer().getId(), true));
    }

    @Test
    void itShouldThrowExceptionWhenTrainingClassTrainerNotFound() {
        //Create training class
        TrainingClass trainingClass = new TrainingClass();
        trainingClass.setStatus(true);

        //Case 1: Not found training class
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getTrainerByClassId(1L));

        //Case 2: Not found training class unit information
        when(trainingClassUnitInformationRepository.findByTrainingClassAndStatus(trainingClass, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getTrainerByClassId(2L));

        //Case 3: Not found trainer
        when(userRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getTrainerByClassId(3L));

        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }


    /**
     * Method under test: {@link UserService#getTrainerOnThisDayById(Long, int)}
     */
    @Test
    void canGetTrainerOnThisDayById() {
        //Create training class id and day number
        Long trainingClassId = 1L;
        int dayNth = 1;

        //Create id
        AtomicReference<Long> id = new AtomicReference<>(1L);

        //Create list unit
        List<Unit> unitList = new ArrayList<>();
        unitList.add(unit1);
        unitList.add(unit2);

        //Assign value for each unit
        unitList.forEach(i -> {
            i.setId(id.get());
            id.getAndSet(id.get() + 1);
            i.setListTrainingClassUnitInformations(new ArrayList<>
                    (List.of(new TrainingClassUnitInformation())));
            i.setStatus(true);
        });

        //Reset id
        id.getAndSet(1L);

        //Create list training class unit infor
        List<TrainingClassUnitInformation> trainingClassUnitInfor = new ArrayList<>();

        //Assign value for training class unit infor
        unitList.forEach(p -> {
            TrainingClassUnitInformation tr = p.getListTrainingClassUnitInformations().get(0);
            tr.setId(id.get());
            id.getAndSet(id.get() + 1);
            tr.setTrainingClass(tc1);
            tr.setUnit(p);
            tr.setTrainer(p.getId() % 2 == 0 ? user1 : user3);
            tr.setTower(null);
            tr.setStatus(true);
            trainingClassUnitInfor.add(tr);
        });

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(tc1));
        when(unitService.getListUnitsInASessionByTrainingClassId(trainingClassId, dayNth))
                .thenReturn(unitList);
        unitList.forEach(p -> when(trainingClassUnitInformationRepository
                .findByUnitAndTrainingClassAndStatus(p, tc1, true))
                .thenReturn(Optional.of(p.getListTrainingClassUnitInformations().get(0))));
        trainingClassUnitInfor.forEach(t -> when(userRepository
                .findByIdAndStatus(t.getTrainer().getId(), true))
                .thenReturn(Optional.of(trainingClassUnitInfor
                        .get(trainingClassUnitInfor.indexOf(t)).getTrainer())));

        List<UserDTO> trainerOnDay = userService.getTrainerOnThisDayById(trainingClassId, dayNth);
        assertEquals(2, trainerOnDay.size());
        assertEquals(3L, trainerOnDay.get(0).getId());
        assertEquals("user3@gmail.com", trainerOnDay.get(0).getEmail());
        assertEquals("Tui la user 3", trainerOnDay.get(0).getFullName());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
        verify(unitService).getListUnitsInASessionByTrainingClassId(trainingClassId, dayNth);
        unitList.forEach(p -> verify(trainingClassUnitInformationRepository).
                findByUnitAndTrainingClassAndStatus(p, tc1, true));
        trainingClassUnitInfor.forEach(t -> userRepository
                .findByIdAndStatus(t.getTrainer().getId(), true));
    }

    @Test
    void itShouldThrowExceptionWhenTrainingClassTrainerOnDateNotFound() {
        //Create training class
        TrainingClass trainingClass = new TrainingClass();
        trainingClass.setStatus(true);

        //Create unit
        Unit unit = new Unit();
        unit.setStatus(true);

        //Case 1: Not found training class
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getTrainerOnThisDayById(1L, 1));

        //Case 2: Not found training class unit information
        when(trainingClassUnitInformationRepository
                .findByUnitAndTrainingClassAndStatus(unit, trainingClass, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getTrainerOnThisDayById(2L, 2));

        //Case 3: Not found trainer
        when(userRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getTrainerOnThisDayById(3L, 3));

        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }

    /**
     * Method under test: {@link UserService#getCreatorByClassId(Long)}
     */
    @Test
    void canGetCreatorByClassId() {
        Long trainingClassId = 1L;

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(tc1));

        UserDTO creator = userService.getCreatorByClassId(trainingClassId);
        assertEquals(2L, creator.getId());
        assertEquals("user2@gmail.com", creator.getEmail());
        assertEquals("Tui la user 2", creator.getFullName());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
    }

    @Test
    void itShouldThrowExceptionWhenTrainingClassCreatorNotFound() {
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getCreatorByClassId(1L));
        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }

    /**
     * Method under test: {@link UserService#getReviewerByClassId(Long)}
     */
    @Test
    void canGetReviewerByClassId() {
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.of(tc1));

        UserDTO reviewer = userService.getReviewerByClassId(1L);
        assertEquals(1L, reviewer.getId());
        assertEquals("user1@gmail.com", reviewer.getEmail());
        assertEquals("Tui la user 1", reviewer.getFullName());

        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }

    @Test
    void itShouldThrowExceptionWhenTrainingClassReviewerNotFound() {
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getReviewerByClassId(1L));
        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }

    /**
     * Method under test: {@link UserService#getApproverByClassId(Long)}
     */
    @Test
    void canGetApproverByClassId() {
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.of(tc1));

        UserDTO approver = userService.getApproverByClassId(1L);
        assertEquals(1L, approver.getId());
        assertEquals("user1@gmail.com", approver.getEmail());
        assertEquals("Tui la user 1", approver.getFullName());

        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }

    @Test
    void itShouldThrowExceptionWhenTrainingClassApproverNotFound() {
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> userService.getApproverByClassId(1L));
        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }


}

