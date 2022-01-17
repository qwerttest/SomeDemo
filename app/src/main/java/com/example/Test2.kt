package com.example

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.sql.SQLException

/**
 * Des
 * @author WangJian on 2021/2/26 14
 * */
class Test2 {

    fun addition_isCorrect() {
        val service: IService = ServiceImpl()
        val serviceProxy = Proxy.newProxyInstance(service.javaClass.classLoader,
                service.javaClass.interfaces, IServiceProxy(service)) as IService
        try {
            serviceProxy.foo()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // 接口定义
    interface IService {
        @Throws(SQLException::class)
        fun foo()
    }

    class ServiceImpl : IService {
        @Throws(SQLException::class)
        override fun foo() {
            throw SQLException("I test throw an checked Exception")
        }
    }

    // 动态代理
    class IServiceProxy internal constructor(private val target: Any) : InvocationHandler {

        override operator fun invoke(proxy: Any?, method: Method, args: Array<Any?>?): Any {
            return method.invoke(target, args)
        }
    }
}