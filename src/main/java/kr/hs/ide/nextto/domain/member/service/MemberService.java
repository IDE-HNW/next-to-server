package kr.hs.ide.nextto.domain.member.service;

import kr.hs.ide.nextto.domain.detail.repository.MemberDetailRepository;
import kr.hs.ide.nextto.domain.member.entity.Member;
import kr.hs.ide.nextto.domain.member.repository.MemberRepository;
import kr.hs.ide.nextto.domain.member.ro.MemberRO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberDetailRepository detailRepository;
    private final MemberRepository memberRepository;

    public Mono<MemberRO> findInfoById(final long id) {
        return memberRepository.findById(id)
                .switchIfEmpty(Mono.error(Member.NotExistsException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(member -> detailRepository.findById(member.getDetailId())
                        .subscribeOn(Schedulers.boundedElastic())
                        .map(MemberRO::new));
    }
}
