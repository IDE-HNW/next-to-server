package kr.hs.ide.nextto.domain.member.dto.sign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class ReissueDto {

    @JsonProperty("refresh_token")
    private String refreshToken;
}