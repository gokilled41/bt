package com.hqjl.crm.aop;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class AuthorityAspect {

    /*	@Autowired
    	APPROVALSERVICEervice approvalServiceervice;*/

    public void doAfter(JoinPoint jp) {
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //check if login
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Annotation[] anns = sig.getMethod().getAnnotations();
        for (Annotation a : anns) {
            if (a.annotationType().equals(LoginCheck.class)) {
                Object[] objs = pjp.getArgs();
                for (Object obj : objs) {
                    if (obj instanceof HttpServletRequest) {
                        HttpServletRequest request = (HttpServletRequest) obj;
                        /*User u = SessionUtil.getLoginUser(request);
                        if (u == null) {
                        	//return not login message
                        	for (Object o : objs) {
                                if (o instanceof HttpServletResponse) {
                                    ((HttpServletResponse)o).getWriter().print(JsonUtil.notLoginResponse().toString());
                                    return null;
                                }
                            }
                        } else {
                        	return pjp.proceed();
                        }
                        break;*/
                    }
                }
            }
        }
        return pjp.proceed();
    }

    public void doBefore(JoinPoint jp) {

    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        System.out.println("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName()
                + " throw exception");
        ex.printStackTrace();
    }
}
