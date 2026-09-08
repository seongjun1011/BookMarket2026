package kr.ac.kopo.psjjj.bookmarket.repository;

import kr.ac.kopo.psjjj.bookmarket.domain.Cart;

public interface CartRepository {
    Cart create(Cart cart);
    Cart read(String cartId);
    void update(String cartId, Cart cart);
    void delete(String cartId);
}