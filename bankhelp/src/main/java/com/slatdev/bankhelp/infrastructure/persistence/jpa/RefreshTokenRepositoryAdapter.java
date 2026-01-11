package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.domain.repository.RefreshTokenRepository;
import com.slatdev.bankhelp.infrastructure.persistence.jpa.mapper.RefreshTokenMapper;

@Repository
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

	private final Logger log = LoggerFactory.getLogger(RefreshTokenRepositoryAdapter.class);
	private final SpringDataRefreshTokenRepository jpaRepo;

	public RefreshTokenRepositoryAdapter(SpringDataRefreshTokenRepository jpaRepo) {
		this.jpaRepo = jpaRepo;
	}

	@Override
	public Optional<RefreshToken> findById(UUID id) {
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][FIND_BY_ID] Inicio");
		Optional<RefreshToken> refreshToken = jpaRepo.findById(id).map(RefreshTokenMapper::toDomain);
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][FIND_BY_ID] Fin | resultado=OK");
		return refreshToken;
	}

	@Override
	public Optional<RefreshToken> findByUserId(UUID userId) {
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][FIND_BY_USERID] Inicio");
		Optional<RefreshToken> refreshToken = jpaRepo.findByUserId(userId).map(RefreshTokenMapper::toDomain);
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][FIND_BY_USERID] Fin | resultado=OK");
		return refreshToken;
	}

	@Override
	public RefreshToken save(RefreshToken refreshToken) {
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][SAVE] Inicio");
		RefreshTokenEntity newRefreshTokenEntity = jpaRepo.save(RefreshTokenMapper.toEntity(refreshToken));
		RefreshToken newRefreshToken = RefreshTokenMapper.toDomain(newRefreshTokenEntity);
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][SAVE] Fin | resultado=OK");
		return newRefreshToken;
	}

	@Override
	public void update(RefreshToken refreshToken) {
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][SAVE] Inicio");
		jpaRepo.save(RefreshTokenMapper.toEntity(refreshToken));
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][SAVE] Fin | resultado=OK");
	}

	@Override
	public void delete(RefreshToken refreshToken) {
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][DELETE_BY_USER_ID] Inicio");
		jpaRepo.delete(RefreshTokenMapper.toEntity(refreshToken));
		log.info("[REFRESH_TOKEN_REPOSITOR_ADAPTER][DELETE_BY_USER_ID] Fin | resultado=OK");
	}

}
