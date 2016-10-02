package ru.consort.sensor.handlers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by DaH4uk on 06.07.2016.
 * Implements the functionality of the exception handler and writes the stack trace to the log.
 * https://konsort.planfix.ru/task/32615
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    public static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        System.out.println("Spring MVC ExceptionHandler handling");
        logger.error("ErrorLog: ", e);
        return new ModelAndView("error/error", "exceptionMsg", "ExceptionHandler msg: " + e.toString());
    }
}
