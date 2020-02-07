package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.RoleDTO;
import ru.mail.dimaushenko.service.model.AddRoleDTO;

public interface RoleService {

    void addRole(AddRoleDTO addRoleDTO);

    List<RoleDTO> getAllRoles();

}
