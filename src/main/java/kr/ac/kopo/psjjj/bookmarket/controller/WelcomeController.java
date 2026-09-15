package kr.ac.kopo.psjjj.bookmarket.controller;

import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import kr.ac.kopo.psjjj.bookmarket.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WelcomeController {

    private final BookService bookService;

    @Autowired
    public WelcomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String requestMethod(Model model) {
        // 책 목록 전체 조회 후 Model에 담기
        List<Book> books = bookService.getAllBookList();
        model.addAttribute("books", books);
        return "welcome";
    }
}