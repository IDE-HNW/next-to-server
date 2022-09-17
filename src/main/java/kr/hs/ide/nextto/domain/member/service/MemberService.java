package kr.hs.ide.nextto.domain.member.service;

import kr.hs.ide.nextto.domain.detail.entity.MemberDetail;
import kr.hs.ide.nextto.domain.detail.repository.MemberDetailRepository;
import kr.hs.ide.nextto.domain.member.dto.SignUpDto;
import kr.hs.ide.nextto.domain.member.repository.MemberRepository;
import kr.hs.ide.nextto.domain.member.ro.RegistryRO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberDetailRepository detailRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<RegistryRO> registry(final SignUpDto signUpDto) {
        Mono<MemberDetail> roMono = detailRepository.save(signUpDto.toDetail())
                .subscribeOn(Schedulers.boundedElastic());

        return roMono.flatMap(memberDetail -> memberRepository
                .save(signUpDto.toEntity(memberDetail, passwordEncoder))
                .subscribeOn(Schedulers.boundedElastic())
                .log()
                .map(RegistryRO::new));
    }
}
