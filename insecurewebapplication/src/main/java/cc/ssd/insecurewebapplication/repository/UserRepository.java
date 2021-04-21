package cc.ssd.insecurewebapplication.repository;

import cc.ssd.insecurewebapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findByName(String name);

    //Boolean existsByUsername(String username);

    //Boolean existsByEmail(String email);

    List<User> findAllByNameContaining(String name);

    //@Query("SELECT u FROM User u WHERE u.id = 1")
    //List<User> findAllUsers();
    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findAllUsers(Integer id);

}