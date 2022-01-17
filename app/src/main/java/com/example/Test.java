package com.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Des
 *
 * @author WangJian on 2021/2/26 14
 */
public class Test {

    static class B {
        public void run(int i) throws ZeroException {
            if (i < 0) {
                throw new ZeroException("参数不能小于零！");
            }
            System.out.println("参数：" + i);

        }
    }

    static class ZeroException extends Exception {
        private static final long serialVersionUID = 1L;

        private String detailMessage;

        public ZeroException(String detailMessage) {
            this.detailMessage = detailMessage;
        }

        public String getMessage() {
            return detailMessage;
        }
    }

    public static void main(String[] args){
        try{
            Class<?> clazz = Class.forName("com.example.Reflect");
            Method method = clazz.getMethod("run", int.class);
            method.invoke(clazz.newInstance(), -1);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.getTargetException().printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
