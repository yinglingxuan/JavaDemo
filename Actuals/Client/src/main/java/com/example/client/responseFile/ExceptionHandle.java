//package com.example.client.responseFile;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.xml.transform.Result;
//
///**
// * @author jiashubing
// * @since 2018/10/29
// */
//@ControllerAdvice
//public class ExceptionHandle {
//    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
//    /**
//     * 异常处理
//     * @param e 异常信息
//     * @return 返回类是我自定义的接口返回类，参数是返回码和返回结果，异常的返回结果为空字符串
//     */
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public BizResponse handle(Exception e) {
//       return BizResponse.of("400","请查看参数");
//    }
//}