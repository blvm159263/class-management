package com.mockproject.controller;


import com.mockproject.Jwt.JwtTokenProvider;
import com.mockproject.dto.*;
import com.mockproject.entity.*;
import com.mockproject.mapper.RoleMapper;
import com.mockproject.mapper.UserMapper;
import com.mockproject.service.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/User")
@Api(tags = "Users Rest Controller")
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
    RoleService roleService;

    @Autowired
    LevelService levelService;

    @Autowired
    RolePermissionScopeService rolePermissionScopeService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    PermissionScopeService permissionScopeService;

    @PostMapping("/Login")
    public ResponseEntity login(@RequestBody LoginFormDTO loginFormDTO){
        String email = loginFormDTO.getEmail();
        String pass = loginFormDTO.getPassword();

        if (email == null || email.isEmpty()){
            return ResponseEntity.badRequest().body("Missing email");
        }

        if (pass == null || pass.isEmpty()){
            return ResponseEntity.badRequest().body("Missing password");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginFormDTO.getEmail(), loginFormDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            String token= jwtTokenProvider.generateToken(user);
            UserDTO userDTO = UserMapper.INSTANCE.toDTO(user.getUser());

            return ResponseEntity.ok(new JwtResponseDTO(userDTO, token));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }
    @GetMapping("/GetAll")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAll(){
        List<UserDTO> userDTOList = userService.getAll();
        if(userDTOList.isEmpty()){
            return ResponseEntity.badRequest().body("List is empty");
        } else {
            return ResponseEntity.ok(userDTOList);
        }
    }

    @GetMapping("/getListUser")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getListUser(@RequestParam(value = "page", required = false, defaultValue = "1") int page,@RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") int rowsPerPage ){
        List<UserDTO> userDTOList = userService.getAllByPageAndRowPerPage(page, rowsPerPage);
        if (userDTOList.isEmpty()){
            return ResponseEntity.badRequest().body("List is empty");
        } else {
            return ResponseEntity.ok(userDTOList);
        }
    }



    @GetMapping("/getMaxPage")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getMaxPage(@RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") int rowsPerPage){
        long numberOfUser =  userService.countAllBy();
        long maxPage = numberOfUser/rowsPerPage;
        if ( maxPage == 0  ) maxPage =1;
        return ResponseEntity.ok(maxPage);
    }

    @GetMapping("/getAllPermissionName")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAllPermission(){
        return ResponseEntity.ok(permissionService.getAll());
    }

    @GetMapping("/getAllRole")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getAllRole(){
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/getAllLevels")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity getAllLevels(){
        return ResponseEntity.ok(levelService.getAll());
    }

    @GetMapping("/getAllRoleDetail")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity getSyllabusPermissionById(){
        List<FormRoleDTO> list = new ArrayList<>();


        for (RoleDTO role: roleService.getAll()) {
            FormRoleDTO roleDTO = new FormRoleDTO();
            List<RolePermissionScope> listRolePermissionScope = rolePermissionScopeService.findAllByRole_Id(role.getId());
            roleDTO.setId(role.getId());
            roleDTO.setRoleName(role.getRoleName());

            for (RolePermissionScope rpc: listRolePermissionScope) {
                switch (rpc.getPermissionScope().getScopeName()){
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

    @PostMapping("/updateRole")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity updateAllRole(@RequestBody List<FormRoleDTO> formRoleDTOList){

        for (FormRoleDTO fdto: formRoleDTOList) {
            if (roleService.checkDuplicatedByRoleName(fdto.getRoleName())) return ResponseEntity.badRequest().body("Role " + fdto.getRoleName() + " is duplicated!");
            if(fdto.getId() != 0){
                //listRoleDTOS.add(new RoleDTO(fdto.getId(), fdto.getRoleName(), true));
                roleService.save(new RoleDTO(fdto.getId(), fdto.getRoleName(), true));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getClassPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Class"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getSyllabusPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Syllabus"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getLeaningMaterialPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Learning material"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getTraningProgramPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Training program"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getUserPermission(), fdto.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("User"));

            } else {
                RoleDTO roleSave = RoleMapper.INSTANCE.toDTO(roleService.save(new RoleDTO(fdto.getRoleName(), true)));
                for (PermissionScopeDTO permissionScopeDTO: permissionScopeService.getAll() ) {
                    rolePermissionScopeService.save(new RolePermissionScopeDTO(true,roleSave.getId(), permissionService.getPermissionIdByName("Access denied") ,permissionScopeDTO.getId()));
                }
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getClassPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Class"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getSyllabusPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Syllabus"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getLeaningMaterialPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Learning material"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getTraningProgramPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("Training program"));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getUserPermission(), roleSave.getId(), permissionScopeService.getPermissionScopeIdByPermissionScopeName("User"));
            }
        }
        return ResponseEntity.ok("hihi");
    }

    @PostMapping("/searchByFilter")
    public ResponseEntity searchByFilter(@RequestBody SearchUserFillerDTO searchUserFillerDTO){
        List<UserDTO> result = userService.searchByFillter(searchUserFillerDTO);
        if (result != null && !result.isEmpty())
            return ResponseEntity.ok(result);
        else
            return ResponseEntity.badRequest().body("Not found user!");
    }

    @PutMapping("/saveUser")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity saveUser(@RequestParam(value = "id") long id, @RequestBody UserFormDTO userForm)
    {
        UserDTO user = userService.getByID(id);

        user.setImage(userForm.getImage());
        user.setEmail(userForm.getEmail());
        user.setFullName(userForm.getFullName());
        user.setDob(userForm.getDob());
        user.setGender(userForm.isGender());
        user.setPhone(userForm.getPhone());
        user.setRoleId(userForm.getRoleId());
        user.setLevelId(userForm.getLevelId());
        user.setState(userForm.getState());
        user.setStatus(userForm.isStatus());

        UserDTO result = userService.saveUser(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/details")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity getUserDetails(@RequestParam(value = "id") long id, @RequestParam(value = "page") int page, @RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") int rowsPerPage)
    {
        UserDTO user = userService.getByID(id);

        if(user != null)
        {
            UserFormDTO userForm = new UserFormDTO(user);
            EntityModel<?> em = EntityModel.of(userForm, linkTo(methodOn(UserController.class).getAllLevels()).withRel("levels"), linkTo(methodOn(UserController.class).getAllRole()).withRel("roles"), linkTo(methodOn(UserController.class).getListUser(page, rowsPerPage)).withRel("back"));
            return ResponseEntity.ok().body(em);
        }
        else return ResponseEntity.badRequest().body("User not found!");
    }
}