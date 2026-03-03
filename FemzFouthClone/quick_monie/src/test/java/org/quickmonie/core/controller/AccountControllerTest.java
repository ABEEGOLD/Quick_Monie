package org.quickmonie.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;
import org.quickmonie.core.dto.request.UpdateAccountRequest;
import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper mapper;
    private final Logger LOG = LoggerFactory.getLogger(AccountControllerTest.class);




    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testCanUpdateAccount() throws Exception {
        InputStream kycDocumentStream = Files.newInputStream(Path
                .of("/Users/dee/Desktop/quickmonie/src/main/resources/static/osimehn.jpeg"));
            MultipartFile file = new MockMultipartFile("verificationDocument", "verificationDocument", "image/jpeg", kycDocumentStream);
            List<JsonPatchOperation> operations = List.of(
                    new ReplaceOperation(
                            new JsonPointer("/email"),
                            new TextNode("james@email.com")
                    )
            );
            JsonPatch patch = new JsonPatch(operations);
        String accountId = "0f88c24e-8f30-4903-9a14-0e5de7c8e756";
        mockMvc.perform(multipart("/api/v1/account/".concat(accountId))
                .file(file.getName(), file.getBytes())
                .part(new MockPart("patch", mapper.writeValueAsString(patch).getBytes()))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .with(r-> {
                            r.setMethod(HttpMethod.PATCH.name());
                            return r;
                        }))

                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
