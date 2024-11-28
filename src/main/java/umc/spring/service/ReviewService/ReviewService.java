package umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.ReviewRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    /**
     * 리뷰 작성 메서드
     *
     * @param requestDTO 리뷰 작성 요청 데이터
     * @return 생성된 리뷰 정보
     */
    public ReviewResponseDTO addReview(ReviewRequestDTO requestDTO) {
        //Member 엔티티 조회
        Member member = memberRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("멤버 아이디 존재하지 않음"));

        //Store 엔티티 조회
        Store store = storeRepository.findById(requestDTO.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("가게 아이디 존재하지 않음"));

        //Review 엔티티 생성 및 저장
        Review review = Review.builder()
                .title(requestDTO.getTitle())
                .score(requestDTO.getScore())
                .member(member)
                .store(store)
                .createdAt(LocalDateTime.now())
                .build();

        //Review 엔티티를 데이터베이스에 저장하기 위해서 (영속화)
        Review savedReview = reviewRepository.save(review);

        //저장된 리뷰를 DTO로 변환하여 반환
        return new ReviewResponseDTO(
                savedReview.getId(),
                savedReview.getStore().getId(),
                savedReview.getTitle(),
                savedReview.getMember().getId(),
                savedReview.getScore(),
                savedReview.getContent(),
                savedReview.getCreatedAt()
        );

    }

}
