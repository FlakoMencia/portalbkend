package com.personal.portalbkend.repository;

import com.personal.portalbkend.domain.SecUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecUserRepository extends JpaRepository<SecUser, Long> {

    Optional<SecUser> findSecUsersByStEmail(String stEmail);

}
