package kr.hs.ide.nextto.domain.member.controller;

import kr.hs.ide.nextto.domain.member.dto.ReissueDto;
import kr.hs.ide.nextto.domain.member.dto.SignInDto;
import kr.hs.ide.nextto.domain.member.dto.SignUpDto;
import kr.hs.ide.nextto.domain.member.ro.LoginRO;
import kr.hs.ide.nextto.domain.member.ro.RegistryRO;
import kr.hs.ide.nextto.domain.member.ro.ReissueRO;
import kr.hs.ide.nextto.domain.member.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;

    @PostMapping("/registry")
    public Mono<RegistryRO> registry(@RequestBody @Valid SignUpDto signUpDto) {
        return signService.registry(signUpDto);
    }

    @PostMapping("/login")
    public Mono<LoginRO> login(@RequestBody @Valid SignInDto signInDto) {
        return signService.login(signInDto);
    }

    @PostMapping("/reissue")
    public Mono<ReissueRO> reissueToken(@RequestBody @Valid ReissueDto reissueDto) {
        return signService.reissueToken(reissueDto);
    }
}
