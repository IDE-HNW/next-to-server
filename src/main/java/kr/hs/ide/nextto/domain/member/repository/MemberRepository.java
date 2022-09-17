package kr.hs.ide.nextto.domain.member.repository;

import kr.hs.ide.nextto.domain.member.entity.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {

}