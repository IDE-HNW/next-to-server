package kr.hs.ide.nextto.domain.member.service;

import kr.hs.ide.nextto.domain.detail.entity.MemberDetail;
import kr.hs.ide.nextto.domain.detail.repository.MemberDetailRepository;
import kr.hs.ide.nextto.domain.member.dto.sign.ReissueDto;
import kr.hs.ide.nextto.domain.member.dto.sign.SignInDto;
import kr.hs.ide.nextto.domain.member.dto.sign.SignUpDto;
import kr.hs.ide.nextto.domain.member.entity.Member;
import kr.hs.ide.nextto.domain.member.repository.MemberRepository;
import kr.hs.ide.nextto.domain.member.ro.LoginRO;
import kr.hs.ide.nextto.domain.member.ro.RegistryRO;
import kr.hs.ide.nextto.domain.member.ro.ReissueRO;
import kr.hs.ide.nextto.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {

    private final MemberDetailRepository detailRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Mono<RegistryRO> registry(final SignUpDto signUpDto) {
        Mono<MemberDetail> roMono = detailRepository.save(signUpDto.toDetail())
                .subscribeOn(Schedulers.boundedElastic());

        return roMono.flatMap(memberDetail -> memberRepository
                .save(signUpDto.toEntity(memberDetail, passwordEncoder))
                .subscribeOn(Schedulers.boundedElastic())
                .map(RegistryRO::new));
    }

    @Transactional(readOnly = true)
    public Mono<LoginRO> login(final SignInDto signInDto) {
        final String username = signInDto.getUsername();
        Mono<Member> memberMono = memberRepository
                .findByUsername(username)
                .switchIfEmpty(Mono.error(new Member.NotExistsException()))
                .subscribeOn(Schedulers.boundedElastic());
        return memberMono.flatMap(member -> {
            if (!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())) {
                return Mono.error(Member.InvalidPasswordException::new);
            }

            return Mono.just(new LoginRO(
                    jwtUtil.generateAccessToken(username),
                    jwtUtil.generateRefreshToken(username)
            ));
        });
    }

    @Transactional(readOnly = true)
    public Mono<ReissueRO> reissueToken(final ReissueDto reissueDto) {
        String username = jwtUtil.extractUsernameFromToken(
                reissueDto.getRefreshToken(), "refresh"
        );
        return memberRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(Member.NotExistsException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(member -> Mono.just(new ReissueRO(jwtUtil.generateAccessToken(username))));
    }
}
