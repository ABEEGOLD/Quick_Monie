package org.quickmonie.core.proxy.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.quickmonie.core.dto.response.CloudUploadResponse;
import org.quickmonie.core.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryCloudService implements CloudService {
    @Value("${cloud.api.name}")
    private String cloudName;
    @Value("${cloud.api.key}")
    private String cloudKey;
    @Value("${cloud.api.secret}")
    private String cloudSecret;

    @Override
    public CloudUploadResponse upload(MultipartFile file) throws FileUploadException {
        try {
            Cloudinary cloudinary = new Cloudinary(
                    ObjectUtils.asMap(
                            "cloud_name", cloudName,
                            "api_key", cloudKey,
                            "api_secret", cloudSecret
                    )
            );
            Map<?,?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            log.info("Upload result:: {}", result);
            String url = result.get("secure_url").toString();
            CloudUploadResponse response = new CloudUploadResponse();
            response.setUrl(url);
            return response;
        }catch (IOException exception){
            throw new FileUploadException(exception.getMessage());
        }
    }
}
