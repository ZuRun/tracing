package cn.zull.tracing.core.model;

import cn.zull.tracing.core.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zurun
 * @date 2018/9/24 18:10:14
 */
@Data
@Accessors(chain = true)
public class TraceInfo {
    private transient TraceDTO traceDTO;
    private transient Long startTime;

    private String traceId;
    private String spanId;
    /**
     * 链路创建时间(时间戳,ms)
     */
    private String ctm;
    /**
     * 链路时长
     */
    private String cost;
    /**
     * 开始时间 2018-09-24 18:12:13
     */
    private String stm;
    /**
     * 结束时间
     */
    private String etm;
    /**
     * 链路是否成功
     */
    private TraceStatusEnum status = TraceStatusEnum.SUCCESS;


    public TraceInfo(TraceDTO traceDTO) {
        this.start().setCtm(traceDTO.getCtm())
                .setTraceId(traceDTO.getTraceId())
                .setSpanId(traceDTO.getSpanId());
    }

    public TraceInfo start() {
        setStartTime(System.currentTimeMillis());
        setStm(DateUtils.dateTimeFormat(getStartTime()));
        return this;
    }

    public TraceInfo stop() {
        Long time = System.currentTimeMillis();
        setEtm(DateUtils.dateTimeFormat(time));
        setCost(String.valueOf(time - getStartTime()));
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
