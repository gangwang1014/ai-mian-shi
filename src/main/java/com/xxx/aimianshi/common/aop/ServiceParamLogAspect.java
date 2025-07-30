package com.xxx.aimianshi.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.stream.IntStream;

@Slf4j
@Aspect
@Component
public class ServiceParamLogAspect {
    @Pointcut("execution(* com.xxx.aimianshi..service.impl..*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logServiceParams(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = method.getName();

        StringBuilder sb = new StringBuilder();
        sb.append(">>> [Service] ")
                .append(className)
                .append(".")
                .append(methodName)
                .append("(");
        IntStream.range(0, args.length).forEach(i -> {
            sb.append(parameterNames[i]).append("=").append(args[i]);
            if (i < args.length - 1) sb.append(", ");
        });
        sb.append(")");

        log.info(sb.toString());
    }
}
