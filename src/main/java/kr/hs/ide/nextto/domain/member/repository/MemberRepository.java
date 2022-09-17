package kr.hs.ide.nextto.domain.member.repository;

import kr.hs.ide.nextto.domain.member.entity.Member;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {

    Mono<Member> findByUsername(String username);

    Flux<Member> findAllByRenewalDateBefore(LocalDate renewalDate);

    @Query("select member_detail.emergency_contact from member join member_detail " +
            "on member.detail_id = member_detail.detail_id where member.member_id = :id")
    Mono<String> findTelephoneById(long id);
    
}
