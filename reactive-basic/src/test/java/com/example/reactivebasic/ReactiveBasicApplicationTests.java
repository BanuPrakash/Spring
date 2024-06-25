package com.example.reactivebasic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
class ReactiveBasicApplicationTests {

    @Test
    void testChunks() {
        Flux request = Flux.range(1, 50);
    }

}
