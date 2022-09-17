package kr.hs.ide.nextto.domain.detail.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail {

    @Id
    private Long detailId;

    private short age;

    private String emergencyContact;

    private String roadAddress;

    private String detailAddress;
}
