package foodconverter.repository;

import foodconverter.entity.PasswordResetToken;
import foodconverter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by Iliya on 26.12.2016 г..
 */
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken,Long>{


    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

}
