package com.github.windmill312.gateway.aop;

import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.exception.util.GatewayExceptionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GatewayServiceAdvice {

    @Around(value = "@within(gatewayService)", argNames = "pjp, gatewayService")
    public Object anyServiceMethod(
            ProceedingJoinPoint pjp,
            GatewayService gatewayService)
            throws Throwable {
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Exception e) {
            throw GatewayExceptionUtil.convert(gatewayService.serviceName(), e);
        }
    }
}
