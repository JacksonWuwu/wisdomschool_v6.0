package cn.wstom.common.reflection;

import cn.wstom.common.utils.OgnlUtils;
import ognl.OgnlException;
import ognl.OgnlRuntime;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * @author dws
 * @date 2019/02/06
 */
public class SimpleReflectionProvider implements ReflectionProvider {

    private OgnlUtils ognlUtils = new OgnlUtils();

    public void setOgnlUtils(OgnlUtils ognlUtils) {
        this.ognlUtils = ognlUtils;
    }

    @Override
    public Method getGetMethod(Class clazz, String methodName) throws IntrospectionException, Exception {
        try {
            return OgnlRuntime.getGetMethod(null, clazz, methodName);
        } catch (OgnlException e) {
            throw new Exception(e);
        }
    }

    @Override
    public Method getSetMethod(Class clazz, String methodName) throws IntrospectionException, Exception {
        try {
            return OgnlRuntime.getSetMethod(null, clazz, methodName);
        } catch (OgnlException e) {
            throw new Exception(e);
        }
    }

    @Override
    public Field getField(Class clazz, String fieldName) {
        return OgnlRuntime.getField(clazz, fieldName);
    }

    @Override
    public void setProperties(Map<String, String> props, Object target, Map<String, Object> context) {
        this.ognlUtils.setProperties(props, target, context);
    }

    @Override
    public void setProperties(Map<String, String> props, Object target, Map<String, Object> context, boolean throwPropertyExceptions) throws Exception {
        this.ognlUtils.setProperties(props, target, context, throwPropertyExceptions);
    }

    @Override
    public void setProperties(Map<String, Object> props, Object target) {
        this.ognlUtils.setProperties(props, target);
    }

    @Override
    public PropertyDescriptor getPropertyDescriptor(Class targetClass, String propertyName) throws IntrospectionException, Exception {
        try {
            return OgnlRuntime.getPropertyDescriptor(targetClass, propertyName);
        } catch (OgnlException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void copy(Object source, Object target, Map<String, Object> context, Collection<String> exclusions, Collection<String> inclusions) {
        this.ognlUtils.copy(source, target, context, exclusions, inclusions);
    }

    @Override
    public Object getRealTarget(String property, Map<String, Object> context, Object root) throws Exception {
        try {
            return this.ognlUtils.getRealTarget(property, context, root);
        } catch (OgnlException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void setProperty(String name, Object value, Object target, Map<String, Object> context, boolean throwPropertyExceptions) {
        this.ognlUtils.setProperty(name, value, target, context, throwPropertyExceptions);
    }

    @Override
    public void setProperty(String name, Object value, Object target, Map<String, Object> context) {
        this.ognlUtils.setProperty(name, value, target, context);
    }

    @Override
    public Map getBeanMap(Object source) throws IntrospectionException, Exception {
        try {
            return this.ognlUtils.getBeanMap(source);
        } catch (OgnlException e) {
            throw new Exception(e);
        }
    }

    @Override
    public Object getValue(String expression, Map<String, Object> context, Object root) throws Exception {
        try {
            return this.ognlUtils.getValue(expression, context, root);
        } catch (OgnlException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void setValue(String expression, Map<String, Object> context, Object root, Object value) throws Exception {
        try {
            this.ognlUtils.setValue(expression, context, root, value);
        } catch (OgnlException e) {
            throw new Exception(e);
        }
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors(Object source) throws IntrospectionException {
        return this.ognlUtils.getPropertyDescriptors(source);
    }

    public static Class classForName(String name) throws ClassNotFoundException {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                return classLoader.loadClass(name);
            }
        } catch (Throwable e) {
        }
        return Class.forName(name);
    }
}
