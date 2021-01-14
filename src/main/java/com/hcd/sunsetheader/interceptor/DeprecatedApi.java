package com.hcd.sunsetheader.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeprecatedApi {
	
	String since() default "";
	
	String alternate() default "";
	
	String policy() default "";
	
	String sunset() default "";
}
