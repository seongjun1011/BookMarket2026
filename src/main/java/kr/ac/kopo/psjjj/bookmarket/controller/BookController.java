package kr.ac.kopo.psjjj.bookmarket.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import kr.ac.kopo.psjjj.bookmarket.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Value("${file.uploadDir}")
    String fileDir;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String requestBookList(Model model){
        List<Book> listOfBooks = bookService.getAllBookList();
        model.addAttribute("bookList", listOfBooks);
        return "books";
    }


    @GetMapping("/{category}")
    public String requestBooksByCategory(@PathVariable("category") String category, Model model){
        List<Book> booksByCategory = bookService.getBookListByCategory(category);
        model.addAttribute("bookList", booksByCategory);
        return "books";
    }

    @GetMapping("/filter/{bookFilter}")
    public String requestBooksByFilter(@MatrixVariable(pathVar = "bookFilter")Map<String, List<String>> bookFilter, Model model){
        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);

        return"books";
    }

    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model){
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "book";
    }

    @PostMapping("/add")
    public String submitAddNewBook(@ModelAttribute("book") Book book) {
        MultipartFile bookImage = book.getBookImage(); // Book 객체 내부의 MultipartFile 필드 호출

        if (bookImage != null && !bookImage.isEmpty()) {
            String saveName = bookImage.getOriginalFilename();
            File saveFile = new File(fileDir, saveName);

            try {
                bookImage.transferTo(saveFile); // 실제 D드라이브에 저장
                book.setFileName(saveName);    // DB에 저장될 파일명 필드에 세팅
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bookService.setNewBook(book);
        return "redirect:/books";
    }
    @ModelAttribute
    public void addAddtributes(Model model){
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @GetMapping("/all")
    public ModelAndView requestAllBooks() {
        ModelAndView modelAndView = new ModelAndView();
        List<Book> list = bookService.getAllBookList();
        modelAndView.addObject("bookList", list);
        modelAndView.setViewName("books");
        return modelAndView;
    }



    @GetMapping("/add")
    public String requestAddBookForm() {
        return "addBook";
    }
}
