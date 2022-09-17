package kr.hs.ide.nextto.global.scheduled;

import kr.hs.ide.nextto.domain.member.repository.MemberRepository;
import kr.hs.ide.nextto.global.config.rabbitmq.RabbitMQConfig;
import kr.hs.ide.nextto.global.receiver.Receiver;
import kr.hs.ide.nextto.global.sms.CoolSMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    private final MemberRepository memberRepository;
    private final CoolSMSService coolSMSService;

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void task() {
        log.info("send");
        findUnRenewal(LocalDate.now().minusDays(1));
    }

    public void findUnRenewal(LocalDate localDate) {
        memberRepository.findAllByRenewalDateBefore(localDate)
                .parallel()
                .runOn(Schedulers.parallel())
                .doOnNext(member -> {
                    LocalDate date = member.getRenewalDate();
                    if (date == null) {
                        sendMessage(-99);
                    }
                    else {
                        Period period = Period.between(date, localDate);
                        if (period.getDays() < 7) {
                            sendMessage(period.getDays());
                        }
                        else {
                            memberRepository.findTelephoneById(member.getMemberId())
                                    .map(telephone -> {
                                        try {
                                            coolSMSService.certifiedPhoneNumber(telephone, "일주일 간 핸드폰 사용량이 없습니다. 확인해주세요!");
                                            return Mono.empty();
                                        } catch (CoolsmsException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).subscribeOn(Schedulers.boundedElastic())
                                    .subscribe();
                        }
                    }
                }).subscribe();
    }

    public void sendMessage(int date) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "next.to.aa", String.valueOf(date));
        try {
            //noinspection ResultOfMethodCallIgnored
            receiver.getLatch().await(1000, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
