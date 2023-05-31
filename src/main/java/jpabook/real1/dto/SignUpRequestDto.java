package jpabook.real1.dto;

import jpabook.real1.domain.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String id;
    @NotBlank
    @Size(min = 3, max = 100)
    private String password;
    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$")
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private int phoneNumber;

    //dto ->entity
//    public Member toEntity() {
//        return Member.builder()
//                .userId(id)
//                .name(name)
//                .email(email)
//                .phoneNumber(phoneNumber)
//                .build();
//    }

}
