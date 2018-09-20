package cn.zull.tracing.dubbo;

import cn.zull.tracing.core.AbstractTraceContext;
import cn.zull.tracing.core.dto.TraceDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author zurun
 * @date 2018/9/17 10:21:45
 */
public abstract class AbstractRpcTraceContext extends AbstractTraceContext implements RpcTraceContext {

    @Override
    public TraceDTO getTraceDto() {
        return Optional.ofNullable(super.getTraceDto())
                .orElseGet(this::getTraceBoByRpcContext);
    }


    /**
     * RpcContext values
     *
     * @return
     */
    public abstract Map<String, String> rpcValues();

    /**
     * 从rpcContext读取
     *
     * @return
     */
    private TraceDTO getTraceBoByRpcContext() {

        TraceDTO traceDTO = map2TraceDto(rpcValues());
        if (traceDTO != null) {
            context.set(traceDTO);
        }
        return context.get();
    }

    protected Map<String, String> traceBo2Map(TraceDTO traceBO) {
        Map<String, String> map = new HashMap(3);
        map.put("traceId", traceBO.getTraceId());
        map.put("spanId", traceBO.getSpanId());
        return map;
    }

    protected TraceDTO map2TraceDto(Map<String, String> map) {
        TraceDTO traceBO = new TraceDTO();
        traceBO.setTraceId(map.get("traceId"));
        traceBO.setSpanId(map.get("spanId"));
        return traceBO;
    }

}
