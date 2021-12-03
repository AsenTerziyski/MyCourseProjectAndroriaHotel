package com.example.myproject.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnProperty(name = "androria.andraop.enabled", havingValue = "true")
public class AndroriaAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AndroriaAspect.class);


    @Pointcut("execution(* com.example.myproject.web.AndroriaUserBrowserListener.*(..))")
    public void testTrack() {
    }

    @Pointcut("execution(* com.example.myproject.service.impl.BookingServiceImpl.saveNewBooking(..))")
    public void beforeSavingBookingByGuest() {
    }

    @Before("testTrack()")
    public void beforeAnyMethodInAndroriaListener(JoinPoint joinPoint) {
        LOGGER.info("Before calling get signature: {}", joinPoint.getSignature());
    }

    @Before("beforeSavingBookingByGuest()")
    public void beforeSavingBooking(JoinPoint joinPoint) {
        LOGGER.info("Before saving a booking {}", joinPoint.getSignature());
    }

//    @Pointcut("execution(* com.example.myproject.web.UserController.deleteUser(..))")
//    public void trackUserControllerDeleteUserExceptions() {
//    }
//
//    @AfterThrowing(pointcut = "trackUserControllerDeleteUserExceptions()", throwing = "error")
//    public void trackUserControllerExceptions(Throwable error) {
//        LOGGER.info("USER CONTROLLER THREW EXCEPTION");
//    }


}
