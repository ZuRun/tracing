package cn.zull.tracing.mybatis;

import cn.zull.tracing.core.AbstractTraceContext;
import cn.zull.tracing.core.UnilateralTraceContext;
import cn.zull.tracing.core.dto.TraceDTO;
import org.springframework.stereotype.Component;

/**
 * @author zurun
 * @date 2018/10/19 00:16:51
 */
@Component
public class MybatisTraceContext extends AbstractTraceContext implements UnilateralTraceContext {
    @Override
    public TraceDTO provider() {
        return super.getContextAndSpanIdPlusOne(traceDTO -> {

        });
    }
}
