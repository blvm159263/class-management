package com.mockproject.service;


import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.RolePermissionScope;
import com.mockproject.entity.User;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.ICustomUserDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class CustomUserDetailsService implements ICustomUserDetailsService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolePermissionScopeRepository rolePermissionScopeRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionScopeRepository permissionScopeRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.findByEmail(username).isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User user = userRepository.findByEmail(username).get();
        Set<GrantedAuthority> authoritySet;
        authoritySet = new HashSet<>();
        String role = user.getRole().getRoleName();
        List<String> listAuth = generateAuthoriessByRoleId(user.getRole().getListRolePermissionScope());

        for (String s: listAuth) {
            authoritySet.add(new SimpleGrantedAuthority(s));
        }
        return new CustomUserDetails(user, authoritySet, role);
    }

    public UserDetails loadUserById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new Exception();
        }
        User result = user.get();
        String role = result.getRole().getRoleName();
        Set<GrantedAuthority> authoritySet;
        authoritySet = new HashSet<>();
        List<String> listAuth = generateAuthoriessByRoleId(result.getRole().getListRolePermissionScope());

        for (String s: listAuth) {
            authoritySet.add(new SimpleGrantedAuthority(s));
        }
        return new CustomUserDetails(result, authoritySet, role);
    }

    public List<String> generateAuthoriessByRoleId(List<RolePermissionScope> rolePermissionScopeEntityList){
        List<String> result = new ArrayList<>();
        for (RolePermissionScope item: rolePermissionScopeEntityList) {
            String s = "";
            s = "ROLE_"+  item.getPermission().getPermissionName() + "_"+item.getPermissionScope().getScopeName() ;
            result.add(s);
        }
        return result;
    }
}
