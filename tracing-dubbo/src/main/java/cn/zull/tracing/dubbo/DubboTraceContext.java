package cn.zull.tracing.dubbo;

import cn.zull.tracing.core.AbstractTraceContext;
import cn.zull.tracing.core.dto.TraceDTO;
import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2018/9/23 00:16:46
 */
@Component
public class DubboTraceContext extends AbstractTraceContext implements RpcTraceContext {

    @Override
    public TraceDTO getTraceDto() {
        return Optional.ofNullable(super.getTraceDto())
                .orElseGet(this::getTraceDTOByRpcContext);
    }

    @Override
    public void product(@NotNull Consumer<TraceDTO> traceDTOConsumer) {
        TraceDTO traceDTO = getTraceDTOByRpcContext();
        traceDTOConsumer.accept(traceDTO);
        setContext(traceDTO);
    }

    @Override
    public TraceDTO consumer() {
        TraceDTO traceDTO = super.getTraceDto();
        if (traceDTO == null) {
            return null;
        }
        RpcContext.getContext().setAttachments(traceDto2Map(traceDTO));
        return traceDTO;
    }


    /**
     * 从rpcContext读取
     *
     * @return
     */
    private TraceDTO getTraceDTOByRpcContext() {
        Map map = RpcContext.getContext().getAttachments();
        TraceDTO traceDTO = map2TraceDto(map);
        if (traceDTO != null) {
            setContext(traceDTO);
        }
        return getContext();
    }

    protected Map<String, String> traceDto2Map(TraceDTO traceDTO) {
        Map<String, String> map = new HashMap(3);
        map.put("traceId", traceDTO.getTraceId());
        map.put("spanId", traceDTO.getSpanId());
        return map;
    }

    protected TraceDTO map2TraceDto(Map<String, String> map) {
        TraceDTO traceDTO = new TraceDTO();
        traceDTO.setTraceId(map.get("traceId"));
        traceDTO.setSpanId(map.get("spanId"));
        return traceDTO;
    }

    @Override
    public void addRpcContext() {

    }
}