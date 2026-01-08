package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTicketRepository extends JpaRepository<TicketEntity, UUID> {
	List<TicketEntity> findByUserId(UUID userId);
}
