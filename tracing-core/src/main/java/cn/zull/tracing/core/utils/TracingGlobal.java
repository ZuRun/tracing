package cn.zull.tracing.core.utils;

import cn.zull.tracing.core.configuration.TracingProperties;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zurun
 * @date 2018/10/2 19:45:01
 */
public class TracingGlobal {

    private HostInfo hostInfo;

    private static class TracingGlobalInstance {
        private static final TracingGlobal INSTANCE = new TracingGlobal();
    }

    private TracingGlobal() {
    }

    public static TracingGlobal getInstance() {
        return TracingGlobalInstance.INSTANCE;
    }

    public HostInfo getHostInfo() {
        if (hostInfo == null) {
            synchronized (TracingGlobal.class) {
                if (hostInfo == null) {
                    hostInfo = new HostInfo();
                    hostInfo.setPort(SpringApplicationContext.getBean(TracingProperties.class).getServerPort());
                    try {
                        hostInfo.setHost(InetAddress.getLocalHost().getHostAddress());
                        hostInfo.setEndPoint(hostInfo.getHost() + ":" + hostInfo.getPort());
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return hostInfo;
    }

    public static class HostInfo {
        private String endPoint;
        private String host;
        private String port;

        public String getEndPoint() {
            return endPoint;
        }

        public HostInfo setEndPoint(String endPoint) {
            this.endPoint = endPoint;
            return this;
        }

        public String getHost() {
            return host;
        }

        public HostInfo setHost(String host) {
            this.host = host;
            return this;
        }

        public String getPort() {
            return port;
        }

        public HostInfo setPort(String port) {
            this.port = port;
            return this;
        }
    }
}
