package kr.hs.ide.nextto.domain.member.controller;

import kr.hs.ide.nextto.domain.member.dto.SignUpDto;
import kr.hs.ide.nextto.domain.member.ro.RegistryRO;
import kr.hs.ide.nextto.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/registry")
    public Mono<RegistryRO> registry(@RequestBody SignUpDto signUpDto) {
        return memberService.registry(signUpDto);
    }
}
