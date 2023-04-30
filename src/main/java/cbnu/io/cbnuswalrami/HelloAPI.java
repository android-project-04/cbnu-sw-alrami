package cbnu.io.cbnuswalrami;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloAPI {

    @GetMapping("/")
    public String hello() {
        return "hello3";
    }
}
