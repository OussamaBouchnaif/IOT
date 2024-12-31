package org.ehei.iot.Repository;

import org.ehei.iot.Entities.Role;
import org.ehei.iot.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findByRole(Role role);
}
