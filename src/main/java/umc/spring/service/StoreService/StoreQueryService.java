package umc.spring.service.StoreService;

import org.springframework.data.domain.Page;
import umc.spring.domain.Review;
import umc.spring.domain.Store;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StoreQueryService {

    Review saveReview(String title, String body, Float score, Long storeId, Long memberId);
    Optional<Store> findStore(Long id);
    List<Store> findStoresByNameAndScore(String name, Float score);
    Page<Review> getReviewList(Long storeId, Integer page); //Spring Data Jpa에서 제공하는 Paging을 위한 추상화
}
