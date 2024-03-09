package com.yc.exception;

import com.yc.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 统一异常处理@ExceptionHandler,主要用于Exception
 * @author HuChaoJie
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> error(HttpServletRequest request, Exception e) {
        log.error("异常信息：", e);
        if (e instanceof DuplicateKeyException) {
            return Result.error(-1, "请勿添加重复数据");
        }else if(e instanceof OLE2NotOfficeXmlFileException || e instanceof OfficeXmlFileException || e instanceof NotOLE2FileException){
            return Result.error(-1, "Excel文件有问题请检查后再上传");
        }else if(e instanceof  MyExcelException){
            return Result.error(-1, "Excel文件有问题,重新下载模板上传 或 另存为(.xlsx)上传,如还有问题请联系管理员");
        }else if(e instanceof MultipartException){
            return Result.error(-1, "当前网络环境较差,请切换网络!");
        }
        return Result.error(-1, "网络走丢了,请稍后再试!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> notValidExceptionError(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();

        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            errorMsg.append(fieldErrors.get(fieldErrors.size() - 1).getDefaultMessage()).append("!");
        }
        return Result.error(-1, errorMsg.toString());
    }


    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<?> customError(HttpServletRequest request, CustomException e) {
        return Result.error(Integer.valueOf(e.getCode()), e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
    }

}
