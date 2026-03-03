package org.quickmonie.core.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class UpdateAccountRequest {
    private String patch;
    private MultipartFile verificationDocument;

    public JsonPatch getPatch() {
        try {
            return new ObjectMapper().readValue(patch, JsonPatch.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
