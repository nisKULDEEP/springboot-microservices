package com.microservices.accounts.repositories;

import com.microservices.accounts.models.Accounts;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Long> {
    @Override
    Optional<Accounts> findById(Long aLong);

    Optional<Accounts> findByCustomerId(Long aLong);

    @Transactional
    @Modifying
    void deleteByCustomerId(Long aLong);

    //    Optional<Accounts> findByMobileNumber(String mobileNumber);
}
