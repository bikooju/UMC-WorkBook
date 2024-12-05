package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Review;
import umc.spring.service.StoreService.StoreQueryService;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.web.dto.StoreResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreQueryService storeQueryService;

    @PostMapping("/{storeId}/reviews")
    @Operation(summary = "새로운 리뷰 작성 API", description = "특정 가게에 새로운 리뷰를 작성하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청"),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게 아이디, path variable입니다."),
            @Parameter(name = "title", description = "리뷰 제목"),
            @Parameter(name = "body", description = "리뷰 본문 내용"),
            @Parameter(name = "score", description = "리뷰 점수"),
            @Parameter(name = "memberId", description = "리뷰 작성자 아이디")
    })
    public ApiResponse<StoreResponseDTO.ReviewPreviewDTO> createReview(
            @PathVariable("storeId") Long storeId,
            @RequestParam("title") String title,
            @RequestParam("body") String body,
            @RequestParam("score") Float score,
            @RequestParam("memberId") Long memberId) {

        // 리뷰 저장
        Review savedReview = storeQueryService.saveReview(title, body, score, storeId, memberId);

        // 저장된 리뷰를 DTO로 변환하여 반환
        StoreResponseDTO.ReviewPreviewDTO reviewPreviewDTO = StoreConverter.reviewPreviewDTO(savedReview);
        return ApiResponse.onSuccess(reviewPreviewDTO);
    }


    @GetMapping("/{storeId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API",description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다." +
            "query String으로 page 번호를 주세요")
    @ApiResponses({ //API의 응답, content = 를 통해서 에러상황에 대해서 알려줌
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003",description = "access 토근을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004",description = "access 토근 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006",description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({ //프론트엔드에서 넘겨줘야 할 정보
            @Parameter(name = "storeId",description = "가게의 아이디, path variable 입니다!")
    })
    public ApiResponse<StoreResponseDTO.ReviewPreviewListDTO> getReviewList(@ExistStore @PathVariable(name="storeId") Long storeId,
                                                                            @RequestParam(name="page") Integer page) {
        Page<Review> reviewList = storeQueryService.getReviewList(storeId, page);
        return ApiResponse.onSuccess(StoreConverter.reviewPreviewListDTO(reviewList));

    }
}
