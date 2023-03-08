package cn.wstom.exam.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * 反射提供者接口
 *
 * @author dws
 * @date 2019/02/06
 */
public interface ReflectionProvider {

    /**
     * 获取指定的get方法
     *
     * @param clazz      目标类
     * @param methodName 方法名 如：获取getStudent methodName为student
     * @return get方法
     * @throws IntrospectionException
     * @throws Exception
     */
    Method getGetMethod(Class clazz, String methodName) throws IntrospectionException, Exception;

    /**
     * 获取指定的set方法
     *
     * @param clazz      目标类
     * @param methodName 方法名 如：获取setStudent methodName为student
     * @return set方法
     * @throws IntrospectionException
     * @throws Exception
     */
    Method getSetMethod(Class clazz, String methodName) throws IntrospectionException, Exception;

    /**
     * 获取字段
     *
     * @param clazz     目标类
     * @param fieldName 字段名
     * @return 指定的字段
     */
    Field getField(Class clazz, String fieldName);

    /**
     * 设置属性值
     *
     * @param props   属性值
     * @param target  目标对象
     * @param context context
     */
    void setProperties(Map<String, String> props, Object target, Map<String, Object> context);

    /**
     * 设置属性值
     *
     * @param props                   属性值
     * @param target                  目标对象
     * @param context                 context
     * @param throwPropertyExceptions 设置属性值异常是否抛出
     * @throws Exception
     */
    void setProperties(Map<String, String> props, Object target, Map<String, Object> context, boolean throwPropertyExceptions) throws Exception;

    /**
     * 设置属性值
     *
     * @param props  属性值
     * @param target 目标对象
     */
    void setProperties(Map<String, Object> props, Object target);

    /**
     * 获取属性信息
     *
     * @param targetClass  目标类
     * @param propertyName 属性名
     * @return
     * @throws IntrospectionException
     * @throws Exception
     */
    PropertyDescriptor getPropertyDescriptor(Class targetClass, String propertyName) throws IntrospectionException, Exception;

    /**
     * 对象拷贝
     *
     * @param source     原对象
     * @param target     目标对象
     * @param context    context
     * @param exclusions 排除的属性
     * @param inclusions 包含的属性
     */
    void copy(Object source, Object target, Map<String, Object> context, Collection<String> exclusions, Collection<String> inclusions);

    /**
     * @param property 属性
     * @param context  context
     * @param root
     * @return
     * @throws Exception
     */
    Object getRealTarget(String property, Map<String, Object> context, Object root) throws Exception;

    /**
     * 设置属性值
     *
     * @param name                    属性名
     * @param value                   属性值
     * @param target                  目标对象
     * @param context                 context
     * @param throwPropertyExceptions 设置属性值异常是否抛出
     */
    void setProperty(String name, Object value, Object target, Map<String, Object> context, boolean throwPropertyExceptions);

    /**
     * 设置属性值
     *
     * @param name    属性名
     * @param value   属性值
     * @param target  目标对象
     * @param context context
     */
    void setProperty(String name, Object value, Object target, Map<String, Object> context);

    /**
     * 获取bean的所有属性
     *
     * @param source 目标对象
     * @return bean的所有属性
     * @throws IntrospectionException
     * @throws Exception
     */
    Map getBeanMap(Object source) throws IntrospectionException, Exception;

    /**
     * 根据表达式获取值
     *
     * @param expression 表达式
     * @param context    context
     * @param root
     * @return
     * @throws Exception
     */
    Object getValue(String expression, Map<String, Object> context, Object root) throws Exception;

    /**
     * 根据表达式设置值
     *
     * @param expression 表达式
     * @param context    context
     * @param root
     * @param value
     * @throws Exception
     */
    void setValue(String expression, Map<String, Object> context, Object root, Object value) throws Exception;

    /**
     * 获取属性信息
     *
     * @param source 目标对象
     * @return
     * @throws IntrospectionException
     */
    PropertyDescriptor[] getPropertyDescriptors(Object source) throws IntrospectionException;
}
