package ir.maktab.onlineexam.model.repository;

import ir.maktab.onlineexam.model.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface RoleRepository extends Repository<Role, Integer>, JpaSpecificationExecutor<Integer> {
    void save(Role role);

    List<Role> findAll();

    @Query("select role.roleName From Role role where not role.roleName ='admin'")
    List<String> findUserRoles();

    Role findByRoleName(String roleName);
}
