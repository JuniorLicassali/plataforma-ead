package com.plataforma.plataforma_ead.infrastructure.cloudinary;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryStorageService {

	private final Cloudinary cloudinary;
	
	public String fazerUpload(MultipartFile arquivoVideo) throws Exception {
        Map uploadResult = cloudinary.uploader().upload(arquivoVideo.getBytes(),
                ObjectUtils.asMap("resource_type", "video"));

        return (String) uploadResult.get("secure_url");
    }

}
