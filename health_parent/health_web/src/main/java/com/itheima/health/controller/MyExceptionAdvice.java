package com.itheima.health.controller;
import com.itheima.exception.MyException;
import com.itheima.health.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//通过注解将异常处理进行ioc
@RestControllerAdvice
public class MyExceptionAdvice {
//    log为自定义的且无法更改的
    public static final Logger log=LoggerFactory.getLogger(MyExceptionAdvice.class);

//    设置异常,异常处理注解只能放在方法上
    @ExceptionHandler(MyException.class)
    public Result myexception(MyException e){
//        将异常进行处理,通过result并且以json形式返回
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result excep(Exception e){

        log.error("发生未知异常",e);
        return new Result(false,"发生未知异常,请联系系统管理员");

    }

}
