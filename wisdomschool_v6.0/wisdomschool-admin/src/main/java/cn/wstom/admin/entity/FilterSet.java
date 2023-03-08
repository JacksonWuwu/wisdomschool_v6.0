package cn.wstom.admin.entity;

/**
 * @author dws
 * @date 2019/03/07
 */
public class FilterSet {
    private final long[] elements;


    public FilterSet() {
        elements = new long[1 + (65535 >>> 6)];
    }

    public void add(final int no) {
        elements[no >>> 6] |= (1L << (no & 63));
    }

    public void add(final int... no) {
        for (int currNo : no) {
            elements[currNo >>> 6] |= (1L << (currNo & 63));
        }
    }

    public void remove(final int no) {
        elements[no >>> 6] &= ~(1L << (no & 63));
    }

    /**
     * @param no
     * @return true:添加成功	false:原已包含
     */
    public boolean addAndNotify(final int no) {
        int eWordNum = no >>> 6;
        long oldElements = elements[eWordNum];
        elements[eWordNum] |= (1L << (no & 63));
        //		if (result)
//			size++;
        return elements[eWordNum] != oldElements;
    }

    /**
     * @param no
     * @return true:移除成功	false:原本就不包含
     */
    public boolean removeAndNotify(final int no) {
        int eWordNum = no >>> 6;
        long oldElements = elements[eWordNum];
        elements[eWordNum] &= ~(1L << (no & 63));
        return elements[eWordNum] != oldElements;
    }

    public boolean contains(final int no) {
        return (elements[no >>> 6] & (1L << (no & 63))) != 0;
    }

    public boolean containsAll(final int... no) {
        if (no.length == 0) {
            return true;
        }
        for (int currNo : no) {
            if ((elements[currNo >>> 6] & (1L << (currNo & 63))) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 不如直接循环调用contains
     *
     * @param no
     * @return
     */
    public boolean containsallUeslessway(final int... no) {
        long[] elements = new long[this.elements.length];
        for (int currNo : no) {
            elements[currNo >>> 6] |= (1L << (currNo & 63));
        }//这一步执行完跟循环调用contains差不多了

        for (int i = 0; i < elements.length; i++) {
            if ((elements[i] & ~this.elements[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 目前没有去维护size，每次都是去计算size
     *
     * @return
     */
    public int size() {
        int size = 0;
        for (long element : elements) {
            size += Long.bitCount(element);
        }
        return size;
    }
}
