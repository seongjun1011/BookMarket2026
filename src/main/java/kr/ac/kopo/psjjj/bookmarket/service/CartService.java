package kr.ac.kopo.psjjj.bookmarket.service;

import kr.ac.kopo.psjjj.bookmarket.domain.Cart;

public interface CartService {
    Cart create(Cart cart);
    Cart read(String cartId);
    void update(String cartId, Cart cart);
    void delete(String cartId);
}