package org.quickmonie.core.proxy.cloud;

import org.quickmonie.core.dto.response.CloudUploadResponse;
import org.quickmonie.core.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    CloudUploadResponse upload(MultipartFile file) throws FileUploadException;
}
