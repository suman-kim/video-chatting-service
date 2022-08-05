package com.webrtc.videoChattingService.advice;


import com.webrtc.videoChattingService.advice.exception.EmptyException;
import com.webrtc.videoChattingService.advice.exception.NotFoundException;
import com.webrtc.videoChattingService.response.CommonResult;
import com.webrtc.videoChattingService.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    private MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, NotFoundException e) {
        return responseService.getFailDefaultResult(-1,e.getMessage());
    }


    @ExceptionHandler(EmptyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult EmptyException(HttpServletRequest request , EmptyException e){
        return responseService.getFailDefaultResult(-1,"데이터가 없습니다.");
    }

    // code정보에 해당하는 메세지를 조회합니다.
    private String getMessage(String code){
        return getMessage(code, null);
    }
    private String getMessage(String code,Object[] args){
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }


}