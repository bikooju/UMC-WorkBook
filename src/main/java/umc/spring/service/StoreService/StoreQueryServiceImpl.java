package umc.spring.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.ReviewRepository;
import umc.spring.repository.StoreRepository.StoreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreQueryServiceImpl implements StoreQueryService{

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;


    // 리뷰 저장 메서드
    @Override
    public Review saveReview(String title, String body, Float score, Long storeId, Long memberId) {
        // Store 객체 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 아이디 존재하지 않음"));

        //Member 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 아이디 존재하지 않음"));

        // 새로운 리뷰 객체 생성
        Review review = Review.builder()
                .title(title)
                .body(body)
                .score(score)
                .store(store)
                .member(member)
                .build();

        // 리뷰 저장
        return reviewRepository.save(review); // 저장과 동시에 createdAt이 자동 설정됨
    }

    @Override
    public Optional<Store> findStore(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public List<Store> findStoresByNameAndScore(String name, Float score) {
        List<Store> filteredStores = storeRepository.dynamicQueryWithBooleanBuilder(name, score);

        filteredStores.forEach(store -> System.out.println("Store = " + store));

        return filteredStores;
    }

    @Override
    public Page<Review> getReviewList(Long storeId, Integer page) {
        Store store = storeRepository.findById(storeId).get();

        Page<Review> StorePage = reviewRepository.findALlByStore(store, PageRequest.of(page, 10));
        return StorePage;

    }
}
