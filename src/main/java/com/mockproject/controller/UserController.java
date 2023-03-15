package com.mockproject.controller;


import com.mockproject.Jwt.JwtTokenProvider;
import com.mockproject.dto.*;
import com.mockproject.entity.*;
import com.mockproject.mapper.RoleMapper;
import com.mockproject.dto.JwtResponseDTO;
import com.mockproject.dto.LoginFormDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.service.*;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.LevelService;
import com.mockproject.service.RoleService;
import com.mockproject.service.UserService;
import com.mockproject.utils.CSVUtils;
import com.mockproject.service.interfaces.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.query.SemanticException;
import org.hibernate.query.sqm.InterpretationException;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/User")
@SecurityRequirement(name = "Authorization")
public class UserController {

    public static final String VIEW = "ROLE_View_User";
    public static final String MODIFY = "ROLE_Modify_User";
    public static final String CREATE = "ROLE_Create_User";
    public static final String FULL_ACCESS = "ROLE_Full access_User";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    RolePermissionScopeService rolePermissionScopeService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    PermissionScopeService permissionScopeService;

    @Autowired
    LevelService levelService;

    @PostMapping("/Login")
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

    @GetMapping("/GetAll")
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
    public ResponseEntity getListUser(@RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") int rowsPerPage) {
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
            List<RolePermissionScope> listRolePermissionScope = rolePermissionScopeService.findAllByRole_Id(role.getId());
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

    @GetMapping("/GetRoleById")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getRoleById (@RequestParam(value = "id")long id){
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

    @PostMapping("/searchByFillet")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity searchByFillter(@RequestParam(value = "Id", required = false) Long id,
                                          @RequestParam(value = "Dob", required = false) LocalDate dob,
                                          @RequestParam(value = "Email", required = false) String email,
                                          @RequestParam(value = "FullName", required = false) String fullName,
                                          @RequestParam(value = "Gender", required = false) Boolean gender,
                                          @RequestParam(value = "Phone", required = false) String phone,
                                          @RequestParam(value = "StateId", required = false, defaultValue = "") List<Integer> stateId,
                                          @RequestParam(value = "AtendeeId", required = false, defaultValue = "") List<Long> atendeeId,
                                          @RequestParam(value = "LevelId", required = false, defaultValue = "") List<Long> levelId,
                                          @RequestParam(value = "RoleId", required = false, defaultValue = "") List<Long> role_id,
                                          @RequestParam(value = "Page", required = false) Optional<Integer> page,
                                          @RequestParam(value = "Size", required = false) Optional<Integer> size,
                                          @RequestParam(value = "Order", required = false) List<String> order

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
    public ResponseEntity encodePassword() {
        userService.encodePassword();
        return ResponseEntity.ok("Oke nha hihi");
    }

    @GetMapping("/GetRoleByName")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getRoleByName(@RequestParam(value = "roleName")String rolename){
        long roleId = roleService.getRoleByRoleName(rolename);
        return ResponseEntity.ok(roleId);
    }

    @GetMapping("/GetLevel")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getLevelById (@RequestParam(value = "id")long id){
        LevelDTO level = levelService.getLevelById(id);
        if (level != null){
            return ResponseEntity.ok(level);
        } else return ResponseEntity.badRequest().body("Not found level!");

    }

    @PutMapping("/De-activateUser")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity deactivateUser (@RequestParam(value = "id")long id){
        int state = userService.updateStateToFalse(id);
        if (state == 0) return ResponseEntity.ok("De-activate user successfully");
        return ResponseEntity.badRequest().body("User not found");
    }

    @PutMapping("/ActivateUser")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity activateUser (@RequestParam(value = "id")long id){
        int state = userService.updateStateToTrue(id);
        if (state == 1) return ResponseEntity.ok("Activate user successfully");
        return ResponseEntity.badRequest().body("User not found");
    }

    @DeleteMapping("/DeleteUser")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity deleteUser (@RequestParam(value = "id")long id){
        boolean delete = userService.updateStatus(id);
        if (!delete) return ResponseEntity.badRequest().body("Delete failed");
        return ResponseEntity.ok("Delete successfully");
    }

    @PutMapping("/ChangeRole")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity changeRole (@RequestParam(value = "id")long id,@RequestParam(value = "roleName")String roleName){
        if (roleService.getRoleByRoleName(roleName) == null){
            return ResponseEntity.badRequest().body("Role not found!");
        }
        boolean change = userService.changeRole(id,roleService.getRoleByRoleName(roleName));
        if (!change) return ResponseEntity.badRequest().body("Change failed");
        return ResponseEntity.ok(roleName);
    }

    @PutMapping("/EditName")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity editFullName (@RequestParam(value = "id")long id, @RequestParam(value = "fullname")String fullname){
        boolean editName = userService.editName(id,fullname);
        if (editName)
            return ResponseEntity.ok("Successfully");
        else return ResponseEntity.badRequest().body("Could not change!");
    }

    @PutMapping("/EditDoB")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity editDoB (@RequestParam(value = "id")long id, @RequestParam(value = "dob")LocalDate date){
            boolean editDoB = userService.editDoB(id, date);
            if (editDoB)
                return ResponseEntity.ok("Successfully");
            else return ResponseEntity.badRequest().body("Could not change!");
    }

    @PutMapping("/EditGender")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity editGender (@RequestParam(value = "id")long id, @RequestParam(value = "gender")boolean gender){
        boolean editGender = userService.editGender(id,gender);
        return ResponseEntity.ok(editGender);
    }

    @PutMapping("/EditLevel")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity editLevel (@RequestParam(value = "id")long id, @RequestParam(value = "levelCode")String levelCode){
        boolean editLevel = userService.editLevel(id,levelCode);
        return ResponseEntity.ok(editLevel);
    }

    @PutMapping("/EditUser")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity editUser (@RequestBody UserDTO user){
        boolean editUser = userService.editUser(user);
        if (editUser)
            return ResponseEntity.ok("Successfully");
        else return ResponseEntity.badRequest().body("Could not change!");
    }

    @GetMapping("/DownloadCSVUserFile")
    public ResponseEntity downloadCSV(){
        String filename = "Import_User_Template.csv";
        InputStreamResource file = new InputStreamResource(CSVUtils.importUserExampleCSVFile());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

}

