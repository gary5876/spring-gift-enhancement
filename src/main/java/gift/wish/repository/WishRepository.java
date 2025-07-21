package gift.wish.repository;

import gift.global.exception.ProductNotFoundException;
import gift.wish.entity.Wish;
import gift.member.entity.Member;
import gift.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByMember(Member member);

    Page<Wish> findByMember(Member member, Pageable pageable);

    boolean existsByMemberAndProduct(Member member, Product product);

    void deleteByMemberAndProduct(Member member, Product product);
}
