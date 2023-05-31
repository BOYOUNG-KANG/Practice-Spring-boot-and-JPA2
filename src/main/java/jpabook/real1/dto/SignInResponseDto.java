package jpabook.real1.dto;

import jpabook.real1.domain.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponseDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    @NotBlank
    private String accessToken;
    @NotBlank
    private String tokenType;
    @NotBlank
    private int expiresIn;

    public SignInResponseDto(Member member, String accessToken, String tokenType, int expiresIn) {
        this.id = member.getId();
        //email
        this.name = member.getUsername();
        //phonenumber
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }
}
