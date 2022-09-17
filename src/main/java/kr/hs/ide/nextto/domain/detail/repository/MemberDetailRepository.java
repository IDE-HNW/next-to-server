package kr.hs.ide.nextto.domain.detail.repository;

import kr.hs.ide.nextto.domain.detail.entity.MemberDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDetailRepository
        extends ReactiveCrudRepository<MemberDetail, Long> {
}
