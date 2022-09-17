package kr.hs.ide.nextto.domain.member.service;

import kr.hs.ide.nextto.domain.detail.repository.MemberDetailRepository;
import kr.hs.ide.nextto.domain.member.entity.Member;
import kr.hs.ide.nextto.domain.member.repository.MemberRepository;
import kr.hs.ide.nextto.domain.member.ro.MemberRO;
import kr.hs.ide.nextto.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberDetailRepository detailRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public Mono<MemberRO> findInfoById(final long id) {
        return memberRepository.findById(id)
                .switchIfEmpty(Mono.error(Member.NotExistsException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(member -> detailRepository.findById(member.getDetailId())
                        .subscribeOn(Schedulers.boundedElastic())
                        .map(MemberRO::new));
    }

    @Transactional
    public Mono<Void> renewal(final String token) {
        String username = jwtUtil.extractUsernameFromToken(token, "access");
        LocalDate localDate = LocalDate.now();
        return memberRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(Member.NotExistsException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(member -> {
                    if (member.getRenewalDate() == null || !localDate.isEqual(member.getRenewalDate())) {
                        member.setRenewalDate(localDate);
                        return memberRepository.save(member);
                    }

                    return Mono.just(member);
                }).then().subscribeOn(Schedulers.boundedElastic());
    }
}
