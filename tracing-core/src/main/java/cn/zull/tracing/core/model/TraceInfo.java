package cn.zull.tracing.core.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author zurun
 * @date 2018/9/24 18:10:14
 */
@Data
public class TraceInfo {
    private transient TraceDTO traceDTO;

    private String traceId;
    private String spanId;
    /**
     * 链路创建时间(时间戳,ms)
     */
    private Long ctm;
    /**
     * 链路时长
     */
    private Integer cost;
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


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
