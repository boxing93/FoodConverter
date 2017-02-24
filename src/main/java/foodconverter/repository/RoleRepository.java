package foodconverter.repository;

import foodconverter.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Iliya on 15.12.2016 г..
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
