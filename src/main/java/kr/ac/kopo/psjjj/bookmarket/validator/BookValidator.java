package kr.ac.kopo.psjjj.bookmarket.validator;
// 테스트
import kr.ac.kopo.psjjj.bookmarket.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashSet;
import java.util.Set;

@Component
public class BookValidator implements Validator {
    @Autowired
    private jakarta.validation.Validator beanValidator;
    public Set<Validator> springValidators;

    public BookValidator() {
        springValidators=new HashSet<Validator>();
    }

    @Override
    public boolean supports(Class<?> clazz){
        return Book.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){

    }
}
