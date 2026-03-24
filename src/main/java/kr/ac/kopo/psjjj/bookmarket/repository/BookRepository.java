package kr.ac.kopo.psjjj.bookmarket.repository;

import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import java.util.List;

public interface BookRepository {
    List<Book> getAllBookList();
}
