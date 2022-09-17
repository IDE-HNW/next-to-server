package kr.hs.ide.nextto.global.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class Receiver {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        log.info("<== Received : {}", message);
        latch.countDown();
    }

    public CountDownLatch getLatch(){
        return latch;
    }
}
