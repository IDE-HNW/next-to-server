package kr.hs.ide.nextto.domain.member.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hs.ide.nextto.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class RegistryRO {

    @JsonProperty("member_id")
    private final long memberId;

    public RegistryRO(Member member) {
        this.memberId = member.getMemberId();
    }
}
