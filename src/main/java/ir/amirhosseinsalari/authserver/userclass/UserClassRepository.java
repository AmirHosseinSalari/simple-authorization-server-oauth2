package ir.amirhosseinsalari.authserver.userclass;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserClassRepository extends JpaRepository<UserClassEntity,Long>{

    @Query(value = "SELECT u FROM  UserClassEntity u WHERE u.username = ?1")
    UserClassEntity findByUsername(String username);

    @Query(value = "SELECT u FROM  UserClassEntity u WHERE u.username = ?1 and  u.password = ?2")
    UserClassEntity findByUsernameAndPassword(String username,String Password);
}
