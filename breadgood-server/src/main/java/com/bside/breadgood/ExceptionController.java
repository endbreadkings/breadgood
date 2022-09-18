package com.bside.breadgood;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping("throw-exception")
    public void throwException() {
        throw new IllegalArgumentException("EXCEPTION");
    }
}
