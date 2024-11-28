package umc.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {

    private Long reviewId;
    private Long storeId;
    private String title;
    private Long userId;
    private Float score;
    private String content;
    private LocalDateTime createdAt;
}
