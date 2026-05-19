package kr.ac.kopo.psjjj.bookmarket.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import kr.ac.kopo.psjjj.bookmarket.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    @PostMapping("/BookMarket/books/add") // 1. URL 경로를 HTML action과 일치시킴
    public String submitAddNewBook(@ModelAttribute Book book){

        MultipartFile bookImage = book.getBookImage();

        // 2. 반드시 null 및 비어있는지 체크를 먼저 진행
        if (bookImage != null && !bookImage.isEmpty()){
            // 체크를 통과한 안전한 상태에서만 파일 정보 추출
            System.out.println("파일사이즈: " + bookImage.getSize());
            String saveName = bookImage.getOriginalFilename();
            File saveFile = new File(fileDir, saveName);

            try {
                bookImage.transferTo(saveFile);
                book.setFileName(saveName); // 파일 저장이 성공했을 때만 DB에 파일명 저장
            } catch (IOException e) {
                throw new RuntimeException("이미지가 업로드 되지 않았습니다.", e);
            }
        } else {
            // 이미지가 없을 때 기본 이미지 명을 넣거나, null 처리를 해줍니다.
            book.setFileName("default.png");
        }

        bookService.setNewBook(book);
        return "redirect:/BookMarket/home"; // 성공 후 이동할 HTML 경로에 맞게 리다이렉트 변경
    }
    @ModelAttribute
    public void addAddtributes(Model model){
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @GetMapping("/download")
    public void downloadBookImage(@RequestParam("file") String paramKey, HttpServletResponse response){
        File imgFile = new File(fileDir + paramKey);

        response.setContentType("application/download");
        response.setContentLength((int)imgFile.length());
        response.setHeader("Content-Disposition", "attachment:fimename=\"" + paramKey + "\"");

        try {
            OutputStream out = response.getOutputStream();
            FileInputStream fileIn = new FileInputStream(imgFile);
            FileCopyUtils.copy(fileIn, out);
            fileIn.close();
            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
