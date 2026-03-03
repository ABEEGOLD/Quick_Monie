package org.quickmonie.core.proxy.cloud;

import org.junit.jupiter.api.Test;
import org.quickmonie.core.dto.response.CloudUploadResponse;
import org.quickmonie.core.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CloudServiceTest {
    @Autowired
    private CloudService cloudService;

    @Test
    public void testCanUploadFile() {
        Path path = Paths.get("/Users/dee/Desktop/quickmonie/src/main/resources/static/osimehn.jpeg");
        try(InputStream inputStream = Files.newInputStream(path);) {
            MultipartFile file = new MockMultipartFile("file", inputStream);
            CloudUploadResponse cloudUploadResponse = cloudService.upload(file);
            assertThat(cloudUploadResponse).isNotNull();
            assertThat(cloudUploadResponse.getUrl()).isNotNull();
            assertThat(cloudUploadResponse.getUrl()).containsAnyOf("cloudinary", "aws", "walrus", "gcp", "res");
        }catch (IOException | FileUploadException exception){
            exception.printStackTrace();
            assertThat(exception).isNull();
        }
    }
}
