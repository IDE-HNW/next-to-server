package kr.hs.ide.nextto.domain.member.entity;

import kr.hs.ide.nextto.domain.detail.entity.MemberDetail;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private Long memberId;

    private LocalDate renewalDate;

    private MemberDetail memberDetail;
}
