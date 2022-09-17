package kr.hs.ide.nextto.domain.member.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hs.ide.nextto.domain.detail.entity.MemberDetail;
import lombok.Getter;

@Getter
public class MemberRO {

    private final short age;

    @JsonProperty("emergency_contact")
    private final String emergencyContact;

    @JsonProperty("road_address")
    private final String roadAddress;

    @JsonProperty("detail_address")
    private final String detailAddress;

    public MemberRO(MemberDetail memberDetail) {
        this.age = memberDetail.getAge();
        this.emergencyContact = memberDetail.getEmergencyContact();
        this.roadAddress = memberDetail.getRoadAddress();
        this.detailAddress = memberDetail.getDetailAddress();
    }
}
