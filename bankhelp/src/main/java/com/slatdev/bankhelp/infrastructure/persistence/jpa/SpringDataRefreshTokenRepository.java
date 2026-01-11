package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
	Optional<RefreshTokenEntity> findByUserId(UUID userId);
}
