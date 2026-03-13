package org.example.fixermockapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/latest")
public class LatestMockController {

    @Value("${mock.api.key}")
    private String apiKey;

    @Value("classpath:standard-answer.json")
    private Resource standardAnswer;

    @Value("classpath:error-answer.json")
    private Resource errorAnswer;

    @GetMapping
    public ResponseEntity<?> getLatest(@RequestParam(name = "access_key", required = false) String apiKey) {
        System.out.println("Processing Request");
        try {
            String body = this.apiKey.equals(apiKey) ? readFileContent(standardAnswer) : readFileContent(errorAnswer);
            return ResponseEntity.ok(body);
        } catch(IOException e) {
            System.err.printf("Could not retrieve contents of one of the files: %s%n", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private String readFileContent(Resource file) throws IOException {
        return new String(file.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
