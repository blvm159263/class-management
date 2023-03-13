package com.mockproject.controller;


import com.mockproject.Jwt.JwtTokenProvider;
import com.mockproject.dto.JwtResponseDTO;
import com.mockproject.dto.LoginFormDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.LevelService;
import com.mockproject.service.RoleService;
import com.mockproject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/Test")
    @Secured(FULL_ACCESS)
    public String register(){
        return "test oke";
    }

    //@GetMapping("/GetAllUsers")

    @GetMapping ("/GetUserByFullName")
    //@Secured({VIEW,MODIFY,CREATE,FULL_ACCESS})
    public ResponseEntity getUserByFullName (@RequestParam(value = "fullName")String fullName){
        UserDTO user = userService.findByFullNameContains(fullName);
        if (user == null) return ResponseEntity.badRequest().body("Not found");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/GetRoleById")
    public ResponseEntity getRoleById (@RequestParam(value = "id")long id){
        String role = roleService.getRoleNameById(id);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/GetRoleByName")
    public ResponseEntity getRoleByName(@RequestParam(value = "roleName")String rolename){
        long roleId = roleService.getRoleByRoleName(rolename);
        return ResponseEntity.ok(roleId);
    }

    @GetMapping("/GetLevel")
    public ResponseEntity getLevelCodeById (@RequestParam(value = "id")long id){
        String levelcode = levelService.getLevelCodeById(id);
        return ResponseEntity.ok(levelcode);
    }

    @PutMapping("/De-activateUser")
    public ResponseEntity deactivateUser (@RequestParam(value = "id")long id){
        int state = userService.updateStateToFalse(id);
        if (state == 0) return ResponseEntity.ok("De-activate user successfully");
        return ResponseEntity.badRequest().body("User not found");
    }

    @PutMapping("/ActivateUser")
    public ResponseEntity activateUser (@RequestParam(value = "id")long id){
        int state = userService.updateStateToTrue(id);
        if (state == 1) return ResponseEntity.ok("Activate user successfully");
        return ResponseEntity.badRequest().body("User not found");
    }

    @PutMapping("/DeleteUser")
    public ResponseEntity deleteUser (@RequestParam(value = "id")long id){
        boolean delete = userService.updateStatus(id);
        if (!delete) return ResponseEntity.badRequest().body("Delete failed");
        return ResponseEntity.ok("Delete successfully");
    }

    @PutMapping("/ChangeRole")
    public ResponseEntity changeRole (@RequestParam(value = "id")long id,@RequestParam(value = "roleName")String roleName){
        boolean change = userService.changeRole(id,roleService.getRoleByRoleName(roleName));
        if (!change) return ResponseEntity.badRequest().body("Change failed");
        return ResponseEntity.ok(roleName);
    }

//    @PutMapping("/EditUser")
//    public ResponseEntity editUser (@RequestParam(value = "id")long id, @RequestParam(value = "fullname")String fullname,
//                                    @RequestParam(value = "dob")LocalDate dob,@RequestParam(value = "gender")boolean gender,
//                                    @RequestParam(value = "level")String level){
//        boolean edit = false;
//        return ResponseEntity.ok(edit);
//    }

    @PutMapping("/EditName")
    public ResponseEntity editFullName (@RequestParam(value = "id")long id, @RequestParam(value = "fullname")String fullname){
        boolean editName = userService.editName(id,fullname);
        return ResponseEntity.ok(editName);
    }

    @PutMapping("/EditDoB")
    public ResponseEntity editDoB (@RequestParam(value = "id")long id, @RequestParam(value = "dob")LocalDate date){
        boolean editDoB = userService.editDoB(id,date);
        return ResponseEntity.ok(editDoB);
    }

    @PutMapping("/EditGender")
    public ResponseEntity editGender (@RequestParam(value = "id")long id, @RequestParam(value = "gender")boolean gender){
        boolean editGender = userService.editGender(id,gender);
        return ResponseEntity.ok(editGender);
    }

    @PutMapping("/EditLevel")
    public ResponseEntity editLevel (@RequestParam(value = "id")long id, @RequestParam(value = "levelCode")String levelCode){
        boolean editLevel = userService.editLevel(id,levelCode);
        return ResponseEntity.ok(editLevel);
    }
}

