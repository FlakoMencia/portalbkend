package com.personal.portalbkend.repository;

import com.personal.portalbkend.domain.SecUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecUserRoleRepository extends JpaRepository<SecUserRole, Long> {


}
