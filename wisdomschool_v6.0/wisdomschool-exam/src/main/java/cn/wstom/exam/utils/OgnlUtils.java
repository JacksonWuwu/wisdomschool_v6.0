package cn.wstom.exam.utils;



import com.opensymphony.xwork2.util.CompoundRoot;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.OgnlRuntime;
import org.apache.ibatis.reflection.ReflectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dws
 * @date 2019/02/06
 */
public class OgnlUtils {

    private static final Logger LOG = LoggerFactory.getLogger(OgnlUtils.class);
    /**
     * 缓存表达式
     */
    private static boolean enableExpressionCache = true;
    private ConcurrentHashMap<String, Object> expressions = new ConcurrentHashMap<>();
    /**
     * 缓存beanInfo
     */
    private final ConcurrentHashMap<Class, BeanInfo> beanInfoCache = new ConcurrentHashMap<>();
    /**
     * 开发模式
     */
    private static boolean devMode = false;

    private final String PROPERTY_ROOT_KEY = "top";

    public OgnlUtils() {
    }

    /**
     * 设置对象属性
     *
     * @param props   属性值
     * @param target  目标对象
     * @param context 上下文环境
     */
    public void setProperties(Map<String, ?> props, Object target, Map<String, Object> context) {
        this.setProperties(props, target, context, false);
    }

    /**
     * 设置对象属性
     *
     * @param props                   属性值
     * @param target                  目标对象
     * @param context                 上下文环境
     * @param throwPropertyExceptions 是否抛出属性异常信息

     */
    public void setProperties(Map<String, ?> props, Object target, Map<String, Object> context, boolean throwPropertyExceptions) throws ReflectionException {
        if (props != null) {
            Object oldRoot = Ognl.getRoot(context);
            Ognl.setRoot(context, target);

            props.forEach((k, v) -> this.internalSetProperty(k, v, target, context, throwPropertyExceptions));

            Ognl.setRoot(context, oldRoot);
        }
    }

    /**
     * 设置对象属性
     *
     * @param props  属性值
     * @param target 目标对象
     */
    public void setProperties(Map<String, ?> props, Object target) {
        this.setProperties(props, target, false);
    }

    /**
     * 设置对象属性
     *
     * @param properties              属性值
     * @param target                  目标对象
     * @param throwPropertyExceptions 是否抛出属性异常信息
     */
    public void setProperties(Map<String, ?> properties, Object target, boolean throwPropertyExceptions) {
        Map context = Ognl.createDefaultContext(target);
        this.setProperties(properties, target, context, throwPropertyExceptions);
    }

    /**
     * 设置对象属性
     *
     * @param name    属性名
     * @param value   属性值
     * @param target  目标对象
     * @param context 上下文环境
     */
    public void setProperty(String name, Object value, Object target, Map<String, Object> context) {
        this.setProperty(name, value, target, context, false);
    }

    /**
     * 设置对象属性
     *
     * @param name                    属性名
     * @param value                   属性值
     * @param target                  目标对象
     * @param context                 上下文环境
     * @param throwPropertyExceptions 是否抛出属性异常信息
     */
    public void setProperty(String name, Object value, Object target, Map<String, Object> context, boolean throwPropertyExceptions) {
        Object oldRoot = Ognl.getRoot(context);
        Ognl.setRoot(context, target);
        this.internalSetProperty(name, value, target, context, throwPropertyExceptions);
        Ognl.setRoot(context, oldRoot);
    }

    /**
     * 获取属性所在的对象
     *
     * @param property 属性值
     * @param context  上下文环境
     * @param root
     * @return
     * @throws OgnlException
     */
    public Object getRealTarget(String property, Map<String, Object> context, Object root) throws OgnlException {
        if (PROPERTY_ROOT_KEY.equals(property)) {
            return root;
        } else if (root instanceof CompoundRoot) {
            CompoundRoot compoundRoot = (CompoundRoot) root;

            try {
                Object target;
                Iterator it = compoundRoot.iterator();

                do {
                    if (!it.hasNext()) {
                        return null;
                    }

                    target = it.next();
                } while (!OgnlRuntime.hasSetProperty((OgnlContext) context, target, property)
                        && !OgnlRuntime.hasGetProperty((OgnlContext) context, target, property)
                        && OgnlRuntime.getIndexedPropertyType((OgnlContext) context, target.getClass(), property) == OgnlRuntime.INDEXED_PROPERTY_NONE);

                return target;
            } catch (IntrospectionException e) {
                throw new ReflectionException("Cannot figure out real target class", e);
            }
        } else {
            return root;
        }
    }

    /**
     * 设置属性值
     *
     * @param name
     * @param context
     * @param root
     * @param value
     * @throws OgnlException
     */
    public void setValue(String name, Map<String, Object> context, Object root, Object value) throws OgnlException {
        Ognl.setValue(this.compile(name), context, root, value);
    }

    /**
     * 获取属性值
     *
     * @param name
     * @param context
     * @param root
     * @return
     * @throws OgnlException
     */
    public Object getValue(String name, Map<String, Object> context, Object root) throws OgnlException {
        return getValue(name, context, root, null);
    }

    /**
     * 获取属性值
     *
     * @param name
     * @param context
     * @param root
     * @param resultType
     * @return
     * @throws OgnlException
     */
    public Object getValue(String name, Map<String, Object> context, Object root, Class resultType) throws OgnlException {
        return Ognl.getValue(this.compile(name), context, root, resultType);
    }

    /**
     * 解析ognl表达式
     *
     * @param expression
     * @return
     * @throws OgnlException
     */
    public Object compile(String expression) throws OgnlException {
        if (enableExpressionCache) {
            Object o = this.expressions.get(expression);
            if (o == null) {
                o = Ognl.parseExpression(expression);
                this.expressions.put(expression, o);
            }

            return o;
        } else {
            return Ognl.parseExpression(expression);
        }
    }

    /**
     * 复制属性到目标对象
     *
     * @param from       原对象
     * @param target     目标对象
     * @param context    上下文
     * @param exclusions 忽略属性
     * @param inclusions
     */
    public void copy(Object from, Object target, Map<String, Object> context, Collection<String> exclusions, Collection<String> inclusions) {
        if (from != null && target != null) {
            Map contextFrom = Ognl.createDefaultContext(from);
            Map contextTarget = Ognl.createDefaultContext(target);

            PropertyDescriptor[] fromPds;
            PropertyDescriptor[] targetPds;
            try {
                //获取对象属性
                fromPds = this.getPropertyDescriptors(from);
                targetPds = this.getPropertyDescriptors(target);
            } catch (IntrospectionException ie) {
                LOG.error("An error occured", ie);
                return;
            }

            //转为map结构
            Map<String, PropertyDescriptor> targetPdMap = new HashMap<>(targetPds.length);
            for (PropertyDescriptor pd : targetPds) {
                targetPdMap.put(pd.getName(), pd);
            }

            for (PropertyDescriptor pd : fromPds) {
                //获取可读方法
                if (pd.getReadMethod() != null) {
                    boolean copy = true;
                    if (exclusions != null && exclusions.contains(pd.getName())) {
                        copy = false;
                    } else if (inclusions != null && !inclusions.contains(pd.getName())) {
                        copy = false;
                    }

                    if (copy) {
                        PropertyDescriptor targetPd = targetPdMap.get(pd.getName());
                        if (targetPd != null && targetPd.getWriteMethod() != null) {
                            try {
                                Object expr = this.compile(pd.getName());
                                Object value = Ognl.getValue(expr, contextFrom, from);
                                Ognl.setValue(expr, contextTarget, target, value);
                            } catch (OgnlException oe) {

                            }
                        }
                    }
                }
            }
        } else {
            LOG.warn("Attempting to copy from to target, but from or target a null source. This is illegal and is skipped. This may be due to an error in an OGNL expression, action chaining, or some other event.", new String[0]);
        }
    }

    /**
     * 复制属性到目标对象
     *
     * @param from    原对象
     * @param target  目标对象
     * @param context 上下文
     */
    public void copy(Object from, Object target, Map<String, Object> context) {
        this.copy(from, target, context, null, null);
    }

    /**
     * 获取属性信息
     *
     * @param source 目标对象
     * @return 属性信息集合
     * @throws IntrospectionException
     */
    public PropertyDescriptor[] getPropertyDescriptors(Object source) throws IntrospectionException {
        return this.getBeanInfo(source).getPropertyDescriptors();
    }

    /**
     * 获取属性信息
     *
     * @param clazz 目标类型
     * @return 属性信息集合
     * @throws IntrospectionException
     */
    public PropertyDescriptor[] getPropertyDescriptors(Class clazz) throws IntrospectionException {
        return this.getBeanInfo(clazz).getPropertyDescriptors();
    }

    /**
     * 获取bean集合
     *
     * @param source 目标对象
     * @return bean集合
     * @throws IntrospectionException
     * @throws OgnlException
     */
    public Map getBeanMap(Object source) throws IntrospectionException, OgnlException {
        Map<String, Object> beanMap = new HashMap<>();
        Map context = Ognl.createDefaultContext(source);
        PropertyDescriptor[] propertyDescriptors = this.getPropertyDescriptors(source);

        for (PropertyDescriptor pd : propertyDescriptors) {
            String propertyName = pd.getDisplayName();
            if (pd.getReadMethod() != null) {
                Object expr = this.compile(propertyName);
                Object value = Ognl.getValue(expr, context, source);
                beanMap.put(propertyName, value);
            } else {
                beanMap.put(propertyName, "There is no read method for " + propertyName);
            }
        }

        return beanMap;
    }

    /**
     * 获取beanInfo
     *
     * @param source 目标对象
     * @return beanInfo
     * @throws IntrospectionException
     */
    public BeanInfo getBeanInfo(Object source) throws IntrospectionException {
        return this.getBeanInfo(source.getClass());
    }

    /**
     * 获取beanInfo
     *
     * @param clazz 目标类型
     * @return beanInfo
     * @throws IntrospectionException
     */
    public BeanInfo getBeanInfo(Class clazz) throws IntrospectionException {
        synchronized (this.beanInfoCache) {
            BeanInfo beanInfo = this.beanInfoCache.get(clazz);
            if (beanInfo == null) {
                beanInfo = Introspector.getBeanInfo(clazz, Object.class);
                this.beanInfoCache.put(clazz, beanInfo);
            }

            return beanInfo;
        }
    }

    private void internalSetProperty(String name, Object value, Object target, Map<String, Object> context, boolean throwPropertyExceptions) throws ReflectionException {
        try {
            this.setValue(name, context, target, value);
        } catch (OgnlException e) {
            Throwable reason = e.getReason();
            String msg = "Caught OgnlException while setting property '" + name + "' on type '" + target.getClass().getName() + "'.";
            Throwable exception = reason == null ? e : reason;
            if (throwPropertyExceptions) {
                throw new ReflectionException(msg, exception);
            }

            if (devMode) {
                LOG.warn(msg, exception);
            }
        }

    }
}
