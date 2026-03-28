package com.eprint.repository.mysql;

import com.eprint.entity.ExternalToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalTokenRepository extends JpaRepository<ExternalToken, Long> {

    Optional<ExternalToken> findByProvider(String provider);
}
