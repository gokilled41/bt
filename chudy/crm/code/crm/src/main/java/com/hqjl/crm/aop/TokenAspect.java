package com.hqjl.crm.aop;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class TokenAspect {

    /*@Autowired
    APPROVALSERVICEervice approvalServiceervice;*/

    public void doAfter(JoinPoint jp) {
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    public void doBefore(JoinPoint jp) throws IOException {
        Object[] objs = jp.getArgs();
        for (Object obj : objs) {
            if (obj instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) obj;
            }
        }
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        System.out.println("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName()
                + " throw exception");
        ex.printStackTrace();
    }
}
