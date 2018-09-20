package cn.zull.tracing.core;

/**
 * @author zurun
 * @date 2018/9/17 10:20:06
 */
public interface RpcTraceContext extends TraceContext {

    /**
     * 调用远程rpc接口,将threadLocal中TraceBO赋值给rpcContext
     */
    void addRpcContext();


}
