package umc.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.service.ReviewService.ReviewService;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성 API
     *
     * 파라미터 : requestDTO 리뷰 작성 요청 데이터
     * @return 작성된 리뷰 데이터
     */
    @PostMapping("/")
    public ApiResponse<ReviewResponseDTO> addReview(@RequestBody @Valid ReviewRequestDTO requestDTO) {
        ReviewResponseDTO responseDTO = reviewService.addReview(requestDTO);
        return ApiResponse.onSuccess(responseDTO);

    }


}
