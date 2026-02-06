package com.plataforma.plataforma_ead.core.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.plataforma.plataforma_ead.core.storage.StorageProperties.TipoStorage;
import com.plataforma.plataforma_ead.domain.service.FotoStorageService;
import com.plataforma.plataforma_ead.infrastructure.storageaws.LocalFotoStorageService;
import com.plataforma.plataforma_ead.infrastructure.storageaws.S3FotoStorageService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

	private final StorageProperties storageProperties;
	
	@Bean
	//@ConditionalOnProperty(name = "ead.storage.tipo", havingValue = "s3")
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(
				storageProperties.getS3().getIdChaveAcesso(), 
				storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	public FotoStorageService fotoStorageService(AmazonS3 amazonS3) {
		if (TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService(amazonS3, storageProperties);
		} else {
			return new LocalFotoStorageService(storageProperties);
		}
	}
	
}