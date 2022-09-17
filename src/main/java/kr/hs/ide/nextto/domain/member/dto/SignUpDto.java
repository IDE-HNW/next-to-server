package kr.hs.ide.nextto.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hs.ide.nextto.domain.detail.entity.MemberDetail;
import kr.hs.ide.nextto.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class SignUpDto {

    private String username;
    private String password;
    private short age;

    @JsonProperty("emergency_contact")
    private String emergencyContact;

    @JsonProperty("road_address")
    private String roadAddress;

    @JsonProperty("detail_address")
    private String detail_address;

    public MemberDetail toDetail() {
        return MemberDetail.builder()
                .age(age)
                .emergencyContact(emergencyContact)
                .roadAddress(roadAddress)
                .detailAddress(detail_address)
                .build();
    }

    public Member toEntity(MemberDetail memberDetail,
                           PasswordEncoder passwordEncoder) {
        return Member.builder()
                .detailId(memberDetail.getDetailId())
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
