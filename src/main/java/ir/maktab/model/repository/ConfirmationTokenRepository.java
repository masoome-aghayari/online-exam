package ir.maktab.model.repository;

import ir.maktab.model.entity.ConfirmationToken;
import ir.maktab.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ConfirmationTokenRepository extends Repository<ConfirmationToken, Integer>, JpaSpecificationExecutor<ConfirmationToken> {

    Optional<ConfirmationToken> findByToken(String token);

    void save(ConfirmationToken token);

    Optional<ConfirmationToken> findByUser(User user);
    @Query("delete from ConfirmationToken  ct where ct.user.email=:email")
    void deleteByUser(@Param("email") String email);

    void deleteByToken(String token);
}