package com.github.windmill312.gateway.annotation;

import com.github.windmill312.gateway.exception.model.Service;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.stereotype.Service
public @interface GatewayService {

    String value() default "";

    Service serviceName() default Service.GATEWAY;

}
