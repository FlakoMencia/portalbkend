package com.personal.portalbkend.repository;

import com.personal.portalbkend.domain.SecUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecUserRepository extends JpaRepository<SecUser, Long> {

    Optional<SecUser> findSecUsersByStEmail(String stEmail);

    @Query("SELECT u FROM SecUser u left join u.secPhoneBook p  WHERE "
            + "(  :userEmail IS NULL OR u.stEmail LIKE %:userEmail% ) AND"
            + "(  :name IS NULL OR  u.stName LIKE %:name% ) AND"
            + "(  :number IS NULL OR  CAST(p.nuNumber as string) = :number ) AND"
            + "(  u.isActive = :isActive)"
    )
    List<SecUser> findByUserFilters(@Param("userEmail")String userEmail,
                                    @Param("name")String name,
                                    @Param("number")String number,
                                    @Param("isActive")Boolean isActive);
}
