package cbnu.io.cbnuswalrami;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloAPI {

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("hello5");
    }
}
