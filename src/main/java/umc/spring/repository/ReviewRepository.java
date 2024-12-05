package umc.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Review;
import umc.spring.domain.Store;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findALlByStore(Store store, PageRequest pageRequest);
    //Spring Data JPA에서 메서드 이름만으로 SQL을 만들어주는 기능 활용
    //PageRequest는 페이징과 관련된 옵션이 포함됌
}
