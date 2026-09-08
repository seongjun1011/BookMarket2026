package kr.ac.kopo.psjjj.bookmarket.repository;

import kr.ac.kopo.psjjj.bookmarket.domain.Cart;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository // ★ 이 어노테이션이 있어야 @Autowired가 동작합니다!
public class CartRepositoryImpl implements CartRepository {

    // 장바구니 데이터를 메모리에 임시 저장하기 위한 Map
    private Map<String, Cart> listOfCarts = new HashMap<>();

    @Override
    public Cart create(Cart cart) {
        if (listOfCarts.containsKey(cart.getCartId())) {
            throw new IllegalArgumentException(
                    String.format("장바구니를 생성할 수 없습니다. 장바구니 ID(%s)가 이미 존재합니다.", cart.getCartId())
            );
        }
        listOfCarts.put(cart.getCartId(), cart);
        return cart;
    }

    @Override
    public Cart read(String cartId) {
        return listOfCarts.get(cartId);
    }

    @Override
    public void update(String cartId, Cart cart) {
        // 장바구니 업데이트 로직 (필요시)
        listOfCarts.put(cartId, cart);
    }

    @Override
    public void delete(String cartId) {
        if (!listOfCarts.containsKey(cartId)) {
            throw new IllegalArgumentException(
                    String.format("장바구니를 삭제할 수 없습니다. 장바구니 ID(%s)가 존재하지 않습니다.", cartId)
            );
        }
        listOfCarts.remove(cartId);
    }
}