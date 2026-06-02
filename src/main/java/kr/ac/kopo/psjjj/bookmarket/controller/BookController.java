package kr.ac.kopo.psjjj.bookmarket.controller;

import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kopo.psjjj.bookmarket.validator.UnitsInStockValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import kr.ac.kopo.psjjj.bookmarket.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
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

    @Autowired
    private UnitsInStockValidator unitsInStockValidator;

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
    public String requestBooksByFilter(@MatrixVariable(pathVar = "bookFilter") Map<String, List<String>> bookFilter, Model model){
        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);
        return "books";
    }

    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model){
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "book";
    }

    /**
     * ★ 수정된 핵심 부분 ★
     * 상단의 @RequestMapping("/books")와 결합하여 실제 수신 주소는 '/books/add'가 됩니다.
     * 타임리프(HTML) Form 태그의 action="/BookMarket/books/add"와 정확하게 매칭됩니다.
     */
    @PostMapping("/add")
    public String submitAddNewBook(@ModelAttribute Book book){

        MultipartFile bookImage = book.getBookImage();

        // 파일이 첨부되었는지 검증
        if (bookImage != null && !bookImage.isEmpty()){
            System.out.println("파일사이즈: " + bookImage.getSize());
            String saveName = bookImage.getOriginalFilename();
            File saveFile = new File(fileDir, saveName);

            try {
                bookImage.transferTo(saveFile);
                book.setFileName(saveName); // 업로드 성공 시 파일명 세팅
            } catch (IOException e) {
                throw new RuntimeException("이미지가 업로드 되지 않았습니다.", e);
            }
        } else {
            // 업로드한 이미지가 없을 때 기본 이미지 처리
            book.setFileName("default.png");
        }

        bookService.setNewBook(book);

        // context-path(/BookMarket)를 뺀 컨트롤러 매핑 주소 기준으로 리다이렉트 경로 지정
        return "redirect:/home";
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
        response.setHeader("Content-Disposition", "attachment;filename=\"" + paramKey + "\"");

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

    @InitBinder // <-- 이 어노테이션을 반드시 추가해야 합니다!
    public void initBinder(WebDataBinder binder){
        binder.setValidator(unitsInStockValidator);
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