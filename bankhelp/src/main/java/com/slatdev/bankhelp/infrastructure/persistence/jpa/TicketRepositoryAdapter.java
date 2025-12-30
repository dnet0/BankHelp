package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;

@Repository
public class TicketRepositoryAdapter implements TicketRepository {

	@Autowired
	private SpringDataTicketRepository jpaRepo;

	@Override
	public Ticket save(Ticket ticket) {
		TicketEntity entity = jpaRepo.save(TicketMapper.toEntity(ticket));
		return TicketMapper.toDomain(entity);
	}

	@Override
	public List<Ticket> findByUserId(UUID userId) {
		List<TicketEntity> ticketsEntity = jpaRepo.findByUserId(userId);
		return ticketsEntity.stream().map(TicketMapper::toDomain).toList();
	}

}
