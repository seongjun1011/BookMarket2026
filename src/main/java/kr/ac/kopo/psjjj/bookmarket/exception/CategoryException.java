package kr.ac.kopo.psjjj.bookmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "요청한 도서 카테고리를 찾을 수 없습니다.")
public class CategoryException extends RuntimeException {

    private String errorMessage;

    public CategoryException() {
        this.errorMessage = "요청한 도서 카테고리를 찾을 수 없습니다.";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}