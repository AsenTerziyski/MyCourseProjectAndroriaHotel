//package com.example.myproject.aop;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@ConditionalOnProperty(name = "androria.andrmodifying.enabled", havingValue = "true")
//public class AndroriaModifyingAspect {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AndroriaModifyingAspect.class);
//
//    @Pointcut("execution(* com.example.myproject.web.AndroriaListener.modifyName(..))")
//    public void modifyNamePointcut() {
//    }
//
//    @Around("modifyNamePointcut() && args(userName)")
//    public Object modifyNameAspect(ProceedingJoinPoint pjp, String userName) throws Throwable {
//        String modified = "JUST USER";
//        Object result = pjp.proceed(new Object[]{
//                modified
//        });
//
//        LOGGER.info("result is {}", pjp.toString());
//
//        return "=>" + result + "<=";
//    }
//}
