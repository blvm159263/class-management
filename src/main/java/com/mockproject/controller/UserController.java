package com.mockproject.controller;


import com.mockproject.Jwt.JwtTokenProvider;
import com.mockproject.dto.*;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.PermissionScope;
import com.mockproject.entity.Role;
import com.mockproject.entity.RolePermissionScope;
import com.mockproject.mapper.RoleMapper;
import com.mockproject.mapper.UserMapper;
import com.mockproject.service.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        List<RoleDTO> listRoleDTOS = new ArrayList<>();
        List<RolePermissionScopeDTO> rolePermissionScopeDTOList = new ArrayList<>();
        for (FormRoleDTO fdto: formRoleDTOList) {
            if(fdto.getId() != 0){
                //listRoleDTOS.add(new RoleDTO(fdto.getId(), fdto.getRoleName(), true));
                roleService.save(new RoleDTO(fdto.getId(), fdto.getRoleName(), true));
                rolePermissionScopeService.updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(fdto.getRoleName());

            } else {
               // listRoleDTOS.add(new RoleDTO(fdto.getRoleName(), true));
                RoleDTO roleSave = RoleMapper.INSTANCE.toDTO(roleService.save(new RoleDTO(fdto.getRoleName(), true)));

                for (PermissionScopeDTO permissionScopeDTO: permissionScopeService.getAll() ) {
                    rolePermissionScopeService.save(new RolePermissionScopeDTO(true,roleSave.getId(), permissionService.getPermissionIdByName("Access denied") ,permissionScopeDTO.getId()));
                }
            }
        }
        return ResponseEntity.ok("hihi");
    }


}
