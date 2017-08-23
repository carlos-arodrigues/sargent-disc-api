package com.sargentdisc.rodrigues.carlos.domain.userfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sargentdisc.domain.model.userfile.UserFile;

@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Long> {

}
