package cn.zull.tracing.core.resttemplate;

import org.springframework.http.client.ClientHttpResponse;

/**
 * @author zurun
 * @date 2019/7/2 17:26:03
 */
public interface TracingClientHttpResponse extends ClientHttpResponse {

    byte[] getBodyBytes()  ;
}
