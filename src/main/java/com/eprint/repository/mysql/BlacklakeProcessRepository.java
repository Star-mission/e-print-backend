package com.eprint.repository.mysql;

import com.eprint.entity.BlacklakeProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklakeProcessRepository extends JpaRepository<BlacklakeProcess, Long> {

    Optional<BlacklakeProcess> findByBlacklakeId(Long blacklakeId);
}
