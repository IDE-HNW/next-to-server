package kr.hs.ide.nextto.domain.member.controller;

import kr.hs.ide.nextto.domain.member.ro.MemberRO;
import kr.hs.ide.nextto.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("{id}")
    public Mono<MemberRO> findInfoById(Mono<Authentication> authenticationMono,
                                       @PathVariable("id") long id) {
        return authenticationMono
                .map(authentication -> authentication.getCredentials().toString())
                .publishOn(Schedulers.boundedElastic())
                .flatMap(token -> memberService.findInfoById(id));
    }
}
