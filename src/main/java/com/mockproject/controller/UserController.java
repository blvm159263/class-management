package com.mockproject.controller;


import com.mockproject.dto.*;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.RolePermissionScope;
import com.mockproject.jwt.JwtTokenProvider;
import com.mockproject.mapper.RoleMapper;
import com.mockproject.mapper.UserMapper;
import com.mockproject.service.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API")
@RequestMapping("/api/user")
@SecurityRequirement(name = "Authorization")
public class UserController {

    public static final String VIEW = "ROLE_View_ User";
    public static final String MODIFY = "ROLE_Modify_User";
    public static final String CREATE = "ROLE_Create_User";
    public static final String FULL_ACCESS = "ROLE_Full access_User";

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final IUserService userService;

    private final IRoleService roleService;

    private final IRolePermissionScopeService rolePermissionScopeService;

    private final IPermissionService permissionService;

    private final IPermissionScopeService permissionScopeService;

    private final ILevelService levelService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginFormDTO loginFormDTO) {
        String email = loginFormDTO.getEmail();
        String pass = loginFormDTO.getPassword();

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing email");
        }

        if (pass == null || pass.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing password");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginFormDTO.getEmail(), loginFormDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(user);
            UserDTO userDTO = UserMapper.INSTANCE.toDTO(user.getUser());
            return ResponseEntity.ok(new JwtResponseDTO(userDTO, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    @GetMapping("/getAll")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAll() {
        List<UserDTO> userDTOList = userService.getAll();
        if (userDTOList.isEmpty()) {
            return ResponseEntity.badRequest().body("List is empty");
        } else {
            return ResponseEntity.ok(userDTOList);
        }
    }

    @GetMapping("/getListUser")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getListUser(@RequestParam(value = "page", required = false, defaultValue = "1") Long page, @RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") Long rowsPerPage) {
        List<UserDTO> userDTOList = userService.getAllByPageAndRowPerPage(page, rowsPerPage);
        if (userDTOList.isEmpty()) {
            return ResponseEntity.badRequest().body("List is empty");
        } else {
            return ResponseEntity.ok(userDTOList);
        }
    }


    @GetMapping("/getAllPermissionName")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAllPermission() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    @GetMapping("/getAllRole")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAllRole() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/getAllRoleDetail")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAllRoleDetail() {
        List<FormRoleDTO> list = new ArrayList<>();
        for (RoleDTO role : roleService.getAll()) {
            FormRoleDTO roleDTO = new FormRoleDTO();
            List<RolePermissionScope> listRolePermissionScope = rolePermissionScopeService.findAllByRoleId(role.getId());
            roleDTO.setId(role.getId());
            roleDTO.setRoleName(role.getRoleName());

            for (RolePermissionScope rpc : listRolePermissionScope) {
                switch (rpc.getPermissionScope().getScopeName()) {
                    case "Syllabus":
                        roleDTO.setSyllabusPermission(rpc.getPermission().getPermissionName());
                        break;
                    case "Training program":
                        roleDTO.setTraningProgramPermission(rpc.getPermission().getPermissionName());
                        break;
                    case "Class":
                        roleDTO.setClassPermission(rpc.getPermission().getPermissionName());
                        break;
                    case "Learning material":
                        roleDTO.setLeaningMaterialPermission(rpc.getPermission().getPermissionName());
                        break;
                    case "User":
                        roleDTO.setUserPermission(rpc.getPermission().getPermissionName());
                        break;
                }
            }
            list.add(roleDTO);


        }
        return ResponseEntity.ok(list);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any User"),
            @ApiResponse(responseCode = "200", description = "When get list admin successfully!",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(summary = "Get all User have role CLASS_ADMIN")
    @GetMapping("/class-admin")
    public ResponseEntity<?> listClassAdmin() {
        List<UserDTO> list = userService.listClassAdminTrue();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any User (Class Admin)!");
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any User"),
            @ApiResponse(responseCode = "200", description = "When get list trainer successfully!",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(summary = "Get all User have role TRAINER")
    @GetMapping("/trainer")
    public ResponseEntity<?> listTrainer() {
        List<UserDTO> list = userService.listTrainerTrue();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any User (Trainer)!");
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any User"),
            @ApiResponse(responseCode = "200", description = "When get user successfully!",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(summary = "Get User by given {ID}")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@Parameter(description = "User's ID") @PathVariable("id") Long id) {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any User have ID = " + id);
        }
    }

    @GetMapping("/list")
    @Operation(
            summary = "Get user list"
    )
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser(true));
    }

    @GetMapping("/getRoleById")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getRoleById (@RequestParam(value = "id")Long id){
        RoleDTO role = roleService.getRoleById(id);
        if (role != null){
            return ResponseEntity.ok(role);
        }
        return ResponseEntity.badRequest().body("Role ot found!");
    }

    @PutMapping("/updateRole")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity updateAllRole(@RequestBody List<FormRoleDTO> formRoleDTOList) {

        for (FormRoleDTO fdto : formRoleDTOList) {
            if (roleService.checkDuplicatedByRoleName(fdto.getRoleName()))
                return ResponseEntity.badRequest().body("Role " + fdto.getRoleName() + " is duplicated!");
            if (fdto.getId() != 0) {
                roleService.save(new RoleDTO(fdto.getId(), fdto.getRoleName(), true));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getClassPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Class"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getSyllabusPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Syllabus"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getLeaningMaterialPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Learning material"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getTraningProgramPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Training program"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getUserPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("User"));

            } else {
                RoleDTO roleSave = RoleMapper.INSTANCE.toDTO(roleService.save(new RoleDTO(fdto.getRoleName(), true)));
                for (PermissionScopeDTO permissionScopeDTO : permissionScopeService.getAll()) {
                    rolePermissionScopeService.save(new RolePermissionScopeDTO(true, roleSave.getId(), permissionService.getPermissionIdByName("Access denied"), permissionScopeDTO.getId()));
                }
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getClassPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Class"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getSyllabusPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Syllabus"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getLeaningMaterialPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Learning material"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getTraningProgramPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Training program"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getUserPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("User"));
            }
        }
        return ResponseEntity.ok("Successfull");
    }

    @PostMapping("/searchByFilter")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity searchByFilter(@RequestParam(value = "Id", required = false) Long id,
                                          @RequestParam(value = "Dob", required = false) LocalDate dob,
                                          @RequestParam(value = "Email", required = false) String email,
                                          @RequestParam(value = "FullName", required = false) String fullName,
                                          @RequestParam(value = "Gender", required = false) Boolean gender,
                                          @RequestParam(value = "Phone", required = false) String phone,
                                          @RequestParam(value = "StateId", required = false, defaultValue = "") List<Integer> stateId,
                                          @RequestParam(value = "AtendeeId", required = false, defaultValue = "") List<Long> atendeeId,
                                          @RequestParam(value = "LevelId", required = false, defaultValue = "") List<Long> levelId,
                                          @RequestParam(value = "RoleId", required = false, defaultValue = "") List<Long> roleId,
                                          @RequestParam(value = "Page", required = false) Optional<Integer> page,
                                          @RequestParam(value = "Size", required = false) Optional<Integer> size,
                                          @RequestParam(value = "Order", required = false) List<String> order

    ) {
        Page<UserDTO> result;
        try {
            result = userService.searchByFilter(id, dob, email, fullName, gender, phone, stateId, atendeeId, levelId, roleId, page, size, order);
        } catch (InvalidDataAccessApiUsageException e) {
            return ResponseEntity.badRequest().body("==============================================\nCOULD NOT FOUND ATTRIBUTE ORDER" + "\nExample: " + "id-asc\n" + "email-asc\n" + "fullname-asc\n" + "state-asc\n" + "dob-asc\n" + "phone-asc\n" + "attendee-asc\n" + "level-asc\n" + "role-asc\n" + "NOTE:::::::: asc = ascending; desc = descending");
        } catch (ArrayIndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().body("==============================================\nFORMAT ORDER LIST INVALID" + "\nExample: " + "id-asc\n" + "email-asc\n" + "fullname-asc\n" + "state-asc\n" + "dob-asc\n" + "phone-asc\n" + "attendee-asc\n" + "level-asc\n" + "role-asc\n" + "NOTE:::::::: asc = ascending; desc = descending");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Parameter invalid");
        }

        if (result != null && !result.isEmpty()) return ResponseEntity.ok(result);
        else return ResponseEntity.badRequest().body("Not found user!");
    }

    @GetMapping("/getRoleByName")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getRoleByName(@RequestParam(value = "roleName")String rolename){
        Long roleId = roleService.getRoleByRoleName(rolename);
        return ResponseEntity.ok(roleId);
    }

    @GetMapping("/getLevel")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getLevelById (@RequestParam(value = "id")Long id){
        LevelDTO level = levelService.getLevelById(id);
        if (level != null){
            return ResponseEntity.ok(level);
        } else return ResponseEntity.badRequest().body("Not found level!");

    }

    @PutMapping("/de-activateUser")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity deactivateUser (@RequestParam(value = "id")Long id){
        int state = userService.updateStateToFalse(id);
        if (state == 0) return ResponseEntity.ok("De-activate user successfully");
        return ResponseEntity.badRequest().body("User not found");
    }

    @PutMapping("/activateUser")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity activateUser (@RequestParam(value = "id")Long id){
        int state = userService.updateStateToTrue(id);
        if (state == 1) return ResponseEntity.ok("Activate user successfully");
        return ResponseEntity.badRequest().body("User not found");
    }

    @DeleteMapping("/deleteUser")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity deleteUser (@RequestParam(value = "id")Long id){
        boolean delete = userService.updateStatus(id);
        if (!delete) return ResponseEntity.badRequest().body("Delete failed");
        return ResponseEntity.ok("Delete successfully");
    }

    @PutMapping("/changeRole")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity changeRole (@RequestParam(value = "id")Long id,@RequestParam(value = "roleName")String roleName){
        if (roleService.getRoleByRoleName(roleName) == null){
            return ResponseEntity.badRequest().body("Role not found!");
        }
        boolean change = userService.changeRole(id,roleService.getRoleByRoleName(roleName));
        if (!change) return ResponseEntity.badRequest().body("Change failed");
        return ResponseEntity.ok(roleName);
    }

    @PutMapping("/editName")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editFullName (@RequestParam(value = "id")Long id, @RequestParam(value = "fullname")String fullname){
        boolean editName = userService.editName(id,fullname);
        if (editName)
            return ResponseEntity.ok("Successfully");
        else return ResponseEntity.badRequest().body("Could not change!");
    }

    @PutMapping("/editDoB")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editDoB (@RequestParam(value = "id")Long id, @RequestParam(value = "dob")LocalDate date){
            boolean editDoB = userService.editDoB(id, date);
            if (editDoB)
                return ResponseEntity.ok("Successfully");
            else return ResponseEntity.badRequest().body("Could not change!");
    }


    @PutMapping("/editGender")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editGender (@RequestParam(value = "id")Long id, @RequestParam(value = "gender")boolean gender){
        boolean editGender = userService.editGender(id,gender);
        return ResponseEntity.ok(editGender);
    }

    @PutMapping("/editLevel")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editLevel (@RequestParam(value = "id")Long id, @RequestParam(value = "levelCode")String levelCode){
        boolean editLevel = userService.editLevel(id,levelCode);
        return ResponseEntity.ok(editLevel);
    }

    @PutMapping("/editUser")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editUser (@RequestBody UserDTO user){
        boolean editUser = userService.editUser(user);
        if (editUser)
            return ResponseEntity.ok("Successfully");
        else return ResponseEntity.badRequest().body("Could not change!");
    }





    @Operation(summary = "Get all class's Trainers by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/all-class-trainers")
    public ResponseEntity<?> getAllTrainers(@Parameter(description = "TrainingClass id", example = "1") @Param("id") Long id) {
        try{
            return ResponseEntity.ok(userService.getAllTrainersByTrainingClassId(id));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }



    @Operation(summary = "Get class's Admins by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/all-class-admins")
    public ResponseEntity<?> getAllAdmins(@Parameter(description = "TrainingClass id", example = "1") @Param("id") Long id) {
        try{
            return ResponseEntity.ok(userService.getAllAdminsByTrainingClassId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }



    @Operation(summary = "Get class's Creator by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/class-creator")
    public ResponseEntity<?> getCreator(@Parameter(description = "TrainingClass id", example = "1") @Param("id") Long id) {
        try {
            return ResponseEntity.ok(userService.getCreatorByTrainingClassId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }



    @Operation(
            summary = "Get all class's Trainers for day-nth of total days of the class schedule",
            description = "Get list of Trainers in a date clicked in the class schedule table by the user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Day [-] of Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/class-trainers-for-a-date")
    public ResponseEntity<?> getAllTrainersForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") Long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        try{
            return ResponseEntity.ok(userService.getAllTrainersForADateByTrainingClassId(id, dayNth));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day [" + dayNth + "] of Training class id[" + id + "] not found!!!");
        }
    }
}