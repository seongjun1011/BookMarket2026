package kr.ac.kopo.psjjj.bookmarket.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class CartItem {
    private Book book; //도서정보
    private int quantity; //수량
    private BigDecimal totalPrice; //단위가격*수량

    public CartItem(Book book) {
        this.book = book;
        quantity = 1;
        totalPrice = book.getUnitPrice();
    }

    public void setBook(Book bookd) {
        this.book = book;
    }

    public void setQuentity(int quantity) {
        this.quantity = quantity;
        updateTotalPrice();
    }

    public void updateTotalPrice(){
        totalPrice = book.getUnitPrice().multiply(new BigDecimal(quantity));
    }
}
