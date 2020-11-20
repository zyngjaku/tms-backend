package io.github.zyngjaku.tmsbackend.dao;

import io.github.zyngjaku.tmsbackend.dao.entity.Company;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByMail(String mail);
    User findUserByToken(String token);

    List<User> findUsersByCompanyAndMailNotContains(Company company, String mail);
    List<User> findUsersByCompanyAndLocalizationIsNotNullAndMailNotContains(Company company, String mail);
}
