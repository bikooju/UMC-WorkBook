package umc.spring.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewRequestDTO {
    @NotNull
    private Long storeId;

    @NotNull
    private Long userId;

    @NotBlank
    private String title;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Float score;

    @NotBlank
    private String content;
}
