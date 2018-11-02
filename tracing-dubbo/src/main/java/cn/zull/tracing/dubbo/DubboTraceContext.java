package cn.zull.tracing.dubbo;

import cn.zull.tracing.core.AbstractTraceContext;
import cn.zull.tracing.core.dto.TraceDTO;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2018/9/23 00:16:46
 */
@Component
public class DubboTraceContext extends AbstractTraceContext implements RpcTraceContext {

    @Override
    public TraceDTO consumer(@NotNull Consumer<TraceDTO> traceDTOConsumer) {
        TraceDTO traceDTO = getTraceDTOByRpcContext().spanIdAddLevel();
        traceDTOConsumer.accept(traceDTO);

        printTraceLog(traceDTO);

        setContext(traceDTO);
        return traceDTO;
    }

    @Override
    public TraceDTO product() {
        return super.getContextAndSpanIdPlusOne(traceDTO -> RpcContext.getContext().setAttachments(traceDto2Map(traceDTO)));
    }


    /**
     * 从rpcContext读取 隐式参数
     *
     * @return
     */
    private TraceDTO getTraceDTOByRpcContext() {
        Map<String, String> map = RpcContext.getContext().getAttachments();
        return map2TraceDto(map);
    }

    protected Map<String, String> traceDto2Map(TraceDTO traceDTO) {
        Map<String, String> map = new HashMap<>(3);
        map.put("traceId", traceDTO.getTraceId());
        map.put("spanId", traceDTO.getSpanId());
        map.put("ctm", traceDTO.getCtm());
        map.put("properties", JSON.toJSONString(traceDTO.getProperties()));
        return map;
    }

    protected TraceDTO map2TraceDto(Map<String, String> map) {
        TraceDTO traceDTO = TraceDTO.getInstance();
        traceDTO.setTraceId(map.get("traceId"));
        traceDTO.setSpanId(map.get("spanId"));
        traceDTO.setCtm(map.get("ctm"));
        traceDTO.setProperties(JSON.parseObject(map.get("properties")));
        return traceDTO;
    }

    @Override
    public void addRpcContext() {

    }
}