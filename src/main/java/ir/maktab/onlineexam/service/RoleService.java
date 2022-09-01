package ir.maktab.onlineexam.service;

import ir.maktab.onlineexam.model.entity.Role;
import ir.maktab.onlineexam.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public List<String> getUserRoles() {
        return roleRepository.findUserRoles();
    }

    @Transactional
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}