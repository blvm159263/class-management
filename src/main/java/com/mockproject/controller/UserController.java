package com.mockproject.controller;


import com.mockproject.Jwt.JwtTokenProvider;
import com.mockproject.dto.*;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.RolePermissionScope;
import com.mockproject.entity.User;
import com.mockproject.mapper.RoleMapper;
import com.mockproject.mapper.UserMapper;
import com.mockproject.service.interfaces.*;
import com.mockproject.utils.CSVUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.service.ResponseMessage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API")
@RequestMapping("/api/user")
@SecurityRequirement(name = "Authorization")
@Slf4j
public class UserController {

    public static final String VIEW = "ROLE_View_User";
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
    @Operation(summary = "Get user list by page and rows per page")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getListUser(@RequestParam(value = "page", required = false, defaultValue = "1") @Parameter(description = "page number to display") Long page, @RequestParam(value = "rowsPerPage", required = false, defaultValue = "10")@Parameter(description = "number of row per page to display") Long rowsPerPage) {
        List<UserDTOCustom> userDTOList = userService.getAllByPageAndRowPerPage(page, rowsPerPage);
        if (userDTOList.isEmpty()) {
            return ResponseEntity.badRequest().body("List is empty");
        } else {
            return ResponseEntity.ok(userDTOList);
        }
    }


    @GetMapping("/getAllPermissionName")
    @Operation(summary = "Get all permission name of system")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAllPermission() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    @GetMapping("/getAllRole")
    @Operation(summary = "Get all Role")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAllRole() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/getAllRoleDetail")
    @Operation(summary = "Get all role detail")
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
    @Operation(summary = "Get a role by role id")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getRoleById(@RequestParam(value = "id") @Parameter(description = "Role id") long id) {
        RoleDTO role = roleService.getRoleById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        }
        return ResponseEntity.badRequest().body("Role ot found!");
    }

    @PutMapping("/updateRole")
    @Operation(summary = "Update role by list FormRoleDTO")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity updateAllRole(@RequestBody List<FormRoleDTO> formRoleDTOList) {

        for (FormRoleDTO fdto : formRoleDTOList) {
            if (roleService.checkDuplicatedByRoleIdAndRoleName(fdto.getId(),fdto.getRoleName()))
                return ResponseEntity.badRequest().body("Role " + fdto.getRoleName() + " is duplicated!");
            if (fdto.getSyllabusPermission().equals("Access denied")) { fdto.setLeaningMaterialPermission("Access denied"); }
            if (fdto.getId() != 0) {
                roleService.save(new RoleDTO(fdto.getId(), fdto.getRoleName(), true));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getClassPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Class"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getSyllabusPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Syllabus"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getLeaningMaterialPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Learning material"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getTraningProgramPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Training program"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getUserPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("User"));

            }
        }
        return ResponseEntity.ok("Successfull");
    }

    @PostMapping("/createRole")
    @Operation(summary = "Create Roles by list FormRoleDTO", description = "Set id of new Role = 0 to create")
    @Secured({FULL_ACCESS, CREATE})
    public ResponseEntity cretaeRole(@RequestBody List<FormRoleDTO> formRoleDTOList) {
        for (FormRoleDTO fdto : formRoleDTOList) {
            if (roleService.checkDuplicatedByRoleIdAndRoleName(fdto.getId(), fdto.getRoleName())) return ResponseEntity.badRequest().body("Role name: "+ fdto.getRoleName() +"is exits!");
            if (fdto.getSyllabusPermission().equals("Access denied")) { fdto.setLeaningMaterialPermission("Access denied"); }
            if (fdto.getId() == 0) {
                RoleDTO roleSave = RoleMapper.INSTANCE.toDTO(roleService.save(new RoleDTO(fdto.getRoleName(), true)));
                for (PermissionScopeDTO permissionScopeDTO : permissionScopeService.getAll()) {
                    rolePermissionScopeService.save(new RolePermissionScopeDTO  (true, roleSave.getId(), permissionService.getPermissionIdByName("Access denied"), permissionScopeDTO.getId()));
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

    @GetMapping("/searchByFilter")
    @Operation(summary = "Search User by filter and order")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity searchByFilter(@RequestParam(value = "Id", required = false) @Parameter(description = "User id") Long id,
                                         @RequestParam(value = "Dob", required = false) @Parameter(description = "Date of birth(yyyy/mm/dd)") LocalDate dob,
                                         @RequestParam(value = "Email", required = false) String email,
                                         @RequestParam(value = "FullName", required = false) String fullName,
                                         @RequestParam(value = "Gender", required = false) @Parameter(description = "true = Male, false = Female") Boolean gender,
                                         @RequestParam(value = "Phone", required = false) String phone,
                                         @RequestParam(value = "StateId", required = false, defaultValue = "") List<Integer> stateId,
                                         @RequestParam(value = "AtendeeId", required = false, defaultValue = "") List<Long> atendeeId,
                                         @RequestParam(value = "LevelId", required = false, defaultValue = "") List<Long> levelId,
                                         @RequestParam(value = "RoleId", required = false, defaultValue = "") List<Long> role_id,
                                         @RequestParam(value = "Page", required = false) Optional<Integer> page,
                                         @RequestParam(value = "Size", required = false) Optional<Integer> size,
                                         @RequestParam(value = "Order", required = false) @Parameter(description = "Order by attribute", example = "Example: " + "id-asc\n" + "email-asc\n" + "fullname-asc\n" + "state-asc\n" + "dob-asc\n" + "phone-asc\n" + "attendee-asc\n" + "level-asc\n" + "role-asc\n" + "NOTE:::::::: asc = ascending; desc = descending") List<String> order

    ) {
        Page<UserDTO> result;
        try {
            result = userService.searchByFilter(id, dob, email, fullName, gender, phone, stateId, atendeeId, levelId, role_id, page, size, order);
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

    @PostMapping("/encodePassword")
    @Operation(summary = "If the password in your Database is not encode,use this function is only 1 time!!!")
    public ResponseEntity encodePassword() {
        userService.encodePassword();
        return ResponseEntity.ok("Encode sucessfully");
    }

    @GetMapping("/getRoleByName")
    @Operation(summary = "Get roleDTO by role name")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getRoleByName(@RequestParam(value = "roleName") String rolename) {
        long roleId = roleService.getRoleByRoleName(rolename);
        return ResponseEntity.ok(roleId);
    }

    @GetMapping("/getLevel")
    @Operation(summary = "Get level by level_id")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getLevelById(@RequestParam(value = "id") @Parameter(description = "Level id") long id) {
        LevelDTO level = levelService.getLevelById(id);
        if (level != null) {
            return ResponseEntity.ok(level);
        } else return ResponseEntity.badRequest().body("Not found level!");

    }

    @PutMapping("/de-activateUser")
    @Operation(summary = "De-activate user by user id", description = "set state as de-active")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity deactivateUser(@RequestParam(value = "id") @Parameter(description = "User id") long id) {
        int state = userService.updateStateToFalse(id);
        if (state == 0) return ResponseEntity.ok("De-activate user successfully");
        return ResponseEntity.badRequest().body("User not found");
    }

    @PutMapping("/activateUser")
    @Operation(summary = "Activate user by user id", description = "Set state as active")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity activateUser(@RequestParam(value = "id") @Parameter(description = "User id") long id) {
        int state = userService.updateStateToTrue(id);
        if (state == 1) return ResponseEntity.ok("Activate user successfully");
        return ResponseEntity.badRequest().body("User not found");
    }

    @PutMapping("/deleteUser")
    @Operation(summary = "Delete user")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity deleteUser(@RequestParam(value = "id") @Parameter(description = "User id") long id) {
        boolean delete = userService.updateStatus(id);
        if (!delete) return ResponseEntity.badRequest().body("Delete failed");
        return ResponseEntity.ok("Delete successfully");
    }

    @PutMapping("/changeRole")
    @Operation(summary = "Edit user role by user id and role name")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity changeRole(@RequestParam(value = "id") @Parameter(description = "User id") long id, @RequestParam(value = "roleName") String roleName) {
        if (roleService.getRoleByRoleName(roleName) == null) {
            return ResponseEntity.badRequest().body("Role not found!");
        }
        boolean change = userService.changeRole(id, roleService.getRoleByRoleName(roleName));
        if (!change) return ResponseEntity.badRequest().body("Change failed");
        return ResponseEntity.ok(roleName);
    }

    @PutMapping("/editName")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editFullName(@RequestParam(value = "id") long id, @RequestParam(value = "fullname") String fullname) {
        boolean editName = userService.editName(id, fullname);
        if (editName)
            return ResponseEntity.ok("Successfully");
        else return ResponseEntity.badRequest().body("Could not change!");
    }

    @PutMapping("/editDoB")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editDoB(@RequestParam(value = "id") long id, @RequestParam(value = "dob") LocalDate date) {
        boolean editDoB = userService.editDoB(id, date);
        if (editDoB)
            return ResponseEntity.ok("Successfully");
        else return ResponseEntity.badRequest().body("Could not change!");
    }

    @PutMapping("/editGender")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editGender(@RequestParam(value = "id") long id, @RequestParam(value = "gender") boolean gender) {
        boolean editGender = userService.editGender(id, gender);
        return ResponseEntity.ok(editGender);
    }

    @PutMapping("/editLevel")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editLevel(@RequestParam(value = "id") long id, @RequestParam(value = "levelCode") String levelCode) {
        boolean editLevel = userService.editLevel(id, levelCode);
        return ResponseEntity.ok(editLevel);
    }

    @PutMapping("/editUser")
    @Operation(summary = "Edit user by UserDTO")
    @Secured({MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity editUser(@RequestBody UserDTO user) {
        boolean editUser = userService.editUser(user);
        if (editUser)
            return ResponseEntity.ok("Successfully");
        else return ResponseEntity.badRequest().body("Could not change!");
    }

    @GetMapping("/downloadCSVUser")
    @Operation(summary = "Download file UserCSVExample")
    public ResponseEntity<InputStreamResource> getFile() {
        String filename = "User_import.csv";
        InputStreamResource file = new InputStreamResource(userService.getCSVUserFileExample());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload csv file to import user")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file,
                                     @RequestParam("replace")Boolean replace,
                                     @RequestParam("skip") Boolean skip) throws IOException {
        String message = "";
        if (CSVUtils.hasCSVFormat(file)) {
                List<User> result = userService.csvToUsers(file.getInputStream(), replace, skip);
                userService.storeListUser(result);
                message = "Uploaded the file successfully: " + file.getOriginalFilename()  ;
                return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}

