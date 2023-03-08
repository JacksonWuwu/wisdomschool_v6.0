package cn.wstom.storage.exception;

import java.util.Map;

/**
 * @author dws
 * @date 2019/02/16
 */
public class ApplicationException extends Exception {
    private static final long serialVersionUID = 4978166323215054417L;
    private Map<String, Object> data;

    public ApplicationException(String msg) {
        this(msg, (Exception) null);
    }

    public ApplicationException(String msg, Map data) {
        this(msg, (Exception) null);
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public ApplicationException(String msg, Exception e) {
        super(msg, e);
    }
}
