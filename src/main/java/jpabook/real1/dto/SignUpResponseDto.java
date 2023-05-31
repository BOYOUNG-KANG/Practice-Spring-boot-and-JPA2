package jpabook.real1.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class SignUpResponseDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private Long id;
    @NotBlank
    private String token;
    @NotBlank
    private String name;

    @Builder
    public SignUpResponseDto(Long id, String token, String name) {
        this.id = id;
        this.token = token;
        this.name = name;
    }
}
