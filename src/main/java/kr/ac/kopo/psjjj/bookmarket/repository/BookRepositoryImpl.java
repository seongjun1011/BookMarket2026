package kr.ac.kopo.psjjj.bookmarket.repository;

import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class BookRepositoryImpl implements BookRepository{
    private List<Book> listOfBooks = new ArrayList<Book>();

    public BookRepositoryImpl() {
        Book book1 = new Book();
        book1.setBookId("isbn11");
        book1.setName("데미안");
        book1.setUnitPrice(new BigDecimal(7200));
        book1.setAuthor("헤르만 헤세");
        book1.setDescription("『데미안』은 주인공 싱클레어와 데미안의 우정을 바탕으로, 성장 과정에서 겪는 시련과 그 시련의 극복, 깨달음을 통해 완전한 자아에 이르는 과정을 성찰한다. 이 작품은 헤세 자신에게도 재출발을 의미했으며, 소년기의 심리, 엄격한 구도성, 문명 비판, 만물의 근원으로서의 어머니라는 관념 등 헤세의 전, 후기 작품 특징이 고루 나타나 있다.");
        book1.setPublisher("민음사");
        book1.setCategory("문학");
        book1.setUnitsInStock(1000);
        book1.setReleaseDate("2000 / 12 / 20");

        Book book2 = new Book();
        book2.setBookId("isbn22");
        book2.setName("코스모스");
        book2.setUnitPrice(new BigDecimal(19800));
        book2.setAuthor("칼 세이건");
        book2.setDescription("칼 세이건의 <코스모스> 특별판이 세이건의 서거 10주기를 기념하여 출간되었다. 이 특별판은 지난 2004년 12월에 출간된 <코스모스>(양장본)의 텍스트 전문과 도판 일부를 사용하고 판형을 휴대하기 쉬운 신국판으로 바꿔 출간한 책으로, 독자들이 좀 더 쉽게 칼 세이건의 메시지를 만날 수 있도록 배려한 책이다.");
        book2.setPublisher("사이언스북스");
        book2.setCategory("우주과학");
        book2.setUnitsInStock(1000);
        book2.setReleaseDate("2006 / 12 / 20");

        Book book3 = new Book();
        book3.setBookId("isbn33");
        book3.setName("논어");
        book3.setUnitPrice(new BigDecimal(10800));
        book3.setAuthor("공자");
        book3.setDescription("자유子游가 효를 묻자, 공자가 대답했다. “지금의 효는 잘 봉양하는 것을 말한다. 그러나 견마犬馬에게도 모두 서로 길러줌이 있으니, 공경함이 없다면 무엇이 다르겠는가?”서");
        book3.setPublisher("현대지성");
        book3.setCategory("동양철학");
        book3.setUnitsInStock(1000);
        book3.setReleaseDate("2018 / 10 / 01");

        listOfBooks.add(book1);
        listOfBooks.add(book2);
        listOfBooks.add(book3);
    }

    @Override
    public List<Book> getAllBookList(){
        return listOfBooks;
    }

    @Override
    public Book getBookById(String bookId) {
        Book book = null;
        for(Book searchBook: listOfBooks){
            if (searchBook != null && searchBook.getBookId() != null && searchBook.getBookId().equals(bookId)){
                book = searchBook;
                break;
            }
        }

        if(book == null){
            throw new IllegalArgumentException("도서ID가 " + bookId + "인 도서를 찾을 수 없습니다.");
        }

        return book;
    }

    @Override
    public List<Book> getBookListByCategory(String category) {
        List<Book> booksByCategory = new ArrayList<Book>();
        for(Book searchBook : listOfBooks) {
            if (category.equalsIgnoreCase(searchBook.getCategory()))
                booksByCategory.add(searchBook);

        }
        return booksByCategory;
    }

    @Override
    public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
        Set<Book> booksByCategory = new HashSet<Book>();
        Set<Book> booksByPublisher = new HashSet<Book>();
        Set<String> filterKeys = filter.keySet();


        if (filterKeys.contains("publisher")) {
            for (String publisherName : filter.get("publisher")) {
                for (Book searchBook : listOfBooks) {
                    if (publisherName.equalsIgnoreCase(searchBook.getPublisher())) {
                        booksByPublisher.add(searchBook);
                    }
                }
            }
        }

        // 2. 카테고리(category) 필터링
        if (filterKeys.contains("category")) {
            for (String category : filter.get("category")) {
                List<Book> list = getBookListByCategory(category);
                booksByCategory.addAll(list);
            }
        }



        booksByCategory.retainAll(booksByPublisher);

        return booksByCategory;
    }
}

