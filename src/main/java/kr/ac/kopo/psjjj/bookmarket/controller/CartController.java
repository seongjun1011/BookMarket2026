package kr.ac.kopo.psjjj.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import kr.ac.kopo.psjjj.bookmarket.domain.Cart;
import kr.ac.kopo.psjjj.bookmarket.domain.CartItem;
import kr.ac.kopo.psjjj.bookmarket.exception.BookIdException;
import kr.ac.kopo.psjjj.bookmarket.service.BookService;
import kr.ac.kopo.psjjj.bookmarket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String requestCartId(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return "redirect:/cart/" + sessionId;
    }

    @PostMapping
    public @ResponseBody Cart create(@RequestBody Cart cart) {
        return cartService.create(cart);
    }

    @GetMapping("/{cartId}")
    public String requestCartList(@PathVariable(value = "cartId") String cartId, Model model) {
        Cart cart = cartService.read(cartId);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PutMapping("/{cartId}")
    public @ResponseBody Cart read(@PathVariable(value = "cartId") String cartId) {
        return cartService.read(cartId);
    }

    @PutMapping("/book/{bookId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addCartByNewItem(@PathVariable("bookId") String bookId, HttpServletRequest request) {
        String sessionId = request.getSession(true).getId();

        Cart cart = cartService.read(sessionId);

        if (cart == null)
            cart = cartService.create(new Cart(sessionId));

        Book book = bookService.getBookById(bookId);

        if(book == null)
            throw new IllegalArgumentException(new BookIdException(bookId));

        cart.addCartItem(new CartItem(book));

        cartService.update(sessionId, cart);
    }
}
