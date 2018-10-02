package cn.zull.tracing.core.exception;

/**
 * @author zurun
 * @date 2018/10/3 00:33:58
 */
public class TracingInnerException extends TracingException {
    public TracingInnerException(String messsage) {
        super(messsage);
    }

    public TracingInnerException(Throwable cause) {
        super(cause);
    }
}
