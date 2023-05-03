package nl.han.resttest.domain.user.spec.repository;

import nl.han.resttest.database.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsernameAndPassword(String username, String password);

    UserEntity findByUsername(String username);

}
