package cn.zull.tracing.core.exception;

/**
 * @author zurun
 * @date 2018/10/2 20:39:23
 */
public class TracingException extends RuntimeException {
    public TracingException(String message) {
        super(message);
    }

    public TracingException(Throwable cause) {
        super(cause);
    }
}
