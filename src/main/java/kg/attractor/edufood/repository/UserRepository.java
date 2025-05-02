package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.avatar = :avatar where u.id = :userId")
    void updateUserAvatar(@Param("userId") Long userId, @Param("avatar") String avatar);

    boolean existsByEmail(String email);
}
