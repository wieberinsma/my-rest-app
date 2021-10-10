package nl.han.resttest.core.repositories;

import nl.han.resttest.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsernameAndPassword(String username, String password);

}
