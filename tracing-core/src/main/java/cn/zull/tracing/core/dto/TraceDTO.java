package cn.zull.tracing.core.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author zurun
 * @date 2018/9/17 23:51:10
 */
@Data
public class TraceDTO {
    private String traceId;
    private String spanId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
