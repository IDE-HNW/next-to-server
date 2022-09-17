package kr.hs.ide.nextto.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class SignInDto {

    private String username;

    private String password;

}
