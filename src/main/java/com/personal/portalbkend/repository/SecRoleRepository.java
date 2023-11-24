package com.personal.portalbkend.repository;

import com.personal.portalbkend.domain.SecRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SecRoleRepository extends JpaRepository<SecRole, Long> {


}
