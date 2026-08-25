package kr.ac.kopo.psjjj.bookmarket.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonException {
    @ExceptionHandler(value = {BookIdException.class})
    public ModelAndView handleError(HttpServletRequest request, BookIdException exception){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL()+"?"+request.getQueryString());
        mav.setViewName("errorBookId");
        return mav;
    }
}
