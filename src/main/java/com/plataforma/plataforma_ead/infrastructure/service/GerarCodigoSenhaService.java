package com.plataforma.plataforma_ead.infrastructure.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.plataforma.plataforma_ead.domain.service.CodigoSenhaService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GerarCodigoSenhaService implements CodigoSenhaService {
	
	private final StringRedisTemplate redisTemplate;

	@Override
	public void salvarCodigo(String email, String code) {
		redisTemplate.opsForValue().set("password-reset:" + email, code, 5, TimeUnit.MINUTES);
	}

	@Override
	public String obterCodigo(String email) {
		return redisTemplate.opsForValue().get("password-reset:" + email);
	}

}
