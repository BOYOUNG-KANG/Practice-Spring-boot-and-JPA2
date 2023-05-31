package jpabook.real1.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String password;



}
