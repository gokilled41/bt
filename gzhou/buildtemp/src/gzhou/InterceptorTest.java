package gzhou;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InterceptorTest {

    public static void main(String[] args) throws Exception {
        Interceptor interceptor = new Interceptor(new A());
        AI a = (AI) Proxy.newProxyInstance(A.class.getClassLoader(), new Class[] { AI.class }, interceptor);
        a.test();
        a.test2();
    }

}

class A implements AI {
    public void test() {
        System.out.println("A.test()");
    }

    public void test2() {
        System.out.println("A.test2()");
    }
}

interface AI {
    public void test();

    public void test2();
}

class Interceptor implements InvocationHandler {
    AI ai_;

    public Interceptor(AI ai) {
        ai_ = ai;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().endsWith("2"))
            System.out.println("Interceptor.invoke()");
        return method.invoke(ai_, args);
    }
}
