package ir.maktab.onlineexam.model.repository;

import ir.maktab.onlineexam.model.entity.ConfirmationToken;
import ir.maktab.onlineexam.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ConfirmationTokenRepository extends Repository<ConfirmationToken, Integer>, JpaSpecificationExecutor<ConfirmationToken> {

    Optional<ConfirmationToken> findByToken(String token);

    void save(ConfirmationToken token);

    Optional<ConfirmationToken> findByUser(User user);

    /*   @Transactional
       @Modifying*/
    /*  @Query("delete from ConfirmationToken c where c.user.email=:email")*/
    void deleteByUser(User user);

    void deleteByToken(String token);
}