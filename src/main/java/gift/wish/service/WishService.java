package gift.wish.service;

import gift.member.entity.Member;
import gift.member.repository.MemberRepository;
import gift.product.dto.ProductResponse;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.wish.entity.Wish;
import gift.wish.repository.WishRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository,
                       MemberRepository memberRepository,
                       ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllWishes(Member member) {
        List<Wish> wishes = wishRepository.findByMember(member);
        return wishes.stream()
                .map(Wish::getProduct)
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImgUrl()))
                .toList();
    }

    @Transactional
    public void addWish(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (wishRepository.existsByMemberAndProduct(member, product)) {
            throw new IllegalStateException("이미 찜한 상품입니다.");
        }

        wishRepository.save(new Wish(member, product));
    }

    @Transactional
    public void deleteWish(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        wishRepository.deleteByMemberAndProduct(member, product);
    }
}