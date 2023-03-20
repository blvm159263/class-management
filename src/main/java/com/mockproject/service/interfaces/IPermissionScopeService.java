package com.mockproject.service.interfaces;

import com.mockproject.dto.PermissionScopeDTO;

import javax.swing.text.html.Option;
import java.util.List;

public interface IPermissionScopeService {
    public Long getPermissionScopeIdByPermissionScopeName(String name);

    public List<PermissionScopeDTO> getAll();

}
