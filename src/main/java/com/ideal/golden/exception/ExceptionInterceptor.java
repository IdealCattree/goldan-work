package com.ideal.golden.exception;

import com.ideal.golden.enhance.JSONAble;
import com.ideal.golden.utils.Rs;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionInterceptor implements JSONAble {

    @ExceptionHandler(Throwable.class)
    public void handle(Throwable throwable,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        response.setStatus(400);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(Rs.error(throwable).toJSON());
        throwable.printStackTrace();
    }
}
