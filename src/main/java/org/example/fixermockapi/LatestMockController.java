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

    @Value("classpath:missing-key.json")
    private Resource missingKey;

    @Value("classpath:invalid-key.json")
    private Resource invalidKey;

    @GetMapping
    public ResponseEntity<?> getLatest(@RequestParam(name = "access_key", required = false) String apiKey) {
        System.out.println("Processing Request");
        try {
            if (apiKey == null || apiKey.isEmpty()) {
                return ResponseEntity.status(401).body(readFileContent(missingKey));
            } else if (apiKey.equals(this.apiKey)) {
                return ResponseEntity.ok(readFileContent(standardAnswer));
            } else {
                return ResponseEntity.status(401).body(readFileContent(invalidKey));
            }
        } catch(IOException e) {
            System.err.printf("Could not retrieve contents of one of the files: %s%n", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private String readFileContent(Resource file) throws IOException {
        return new String(file.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
