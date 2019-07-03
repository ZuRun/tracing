package cn.zull.tracing.core.resttemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zurun
 * @date 2019/7/2 16:55:43
 */
public class ClientHttpResponseWrapper implements TracingClientHttpResponse {
    private ClientHttpResponse clientHttpResponse;
    private byte[] bytes;

    public ClientHttpResponseWrapper(ClientHttpResponse clientHttpResponse) {
        this.clientHttpResponse = clientHttpResponse;
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return clientHttpResponse.getStatusCode();
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return clientHttpResponse.getRawStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return clientHttpResponse.getStatusText();
    }

    @Override
    public void close() {
        clientHttpResponse.close();
    }


    @Override
    public InputStream getBody() throws IOException {
        checkBytes();
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public HttpHeaders getHeaders() {
        return clientHttpResponse.getHeaders();
    }

    @Override
    public byte[] getBodyBytes()   {
        try {
            checkBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private void checkBytes() throws IOException {
        synchronized (this) {
            if (bytes == null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                InputStream inputStream = clientHttpResponse.getBody();
                try {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) > -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    outputStream.flush();
                    this.bytes = outputStream.toByteArray();
                } finally {
                    inputStream.close();
                    outputStream.close();
                }

            }
        }
    }

}
