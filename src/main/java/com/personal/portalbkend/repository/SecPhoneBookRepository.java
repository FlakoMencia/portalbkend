package com.personal.portalbkend.repository;

import com.personal.portalbkend.domain.SecPhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecPhoneBookRepository  extends JpaRepository<SecPhoneBook, Long> {


}
