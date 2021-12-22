package cn.yuexiu.manage.common.util;

/**
 * http client获取类
 */

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HttpClientFactory {

    static final int timeOut = 20 * 60 * 1000;

    private static CloseableHttpClient httpClient = null;

    private final static Object syncLock = new Object();

    private static HttpClientConnectionMonitorThread thread;  //HTTP链接管理器线程

    public static HttpClientConnectionMonitorThread getThread() {
        return thread;
    }

    public static void setThread(HttpClientConnectionMonitorThread thread) {
        HttpClientFactory.thread = thread;
    }

    private static void config(HttpRequestBase httpRequestBase) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
        httpRequestBase.setConfig(requestConfig);
    }


    /**
     * 获取HttpClient对象
     *
     * @return
     * @author wei
     */
    public static CloseableHttpClient getHttpClient(String url) {
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }
//        if (httpClient == null) {
//            synchronized (syncLock) {
//                if (httpClient == null) {
//                    httpClient = createHttpClient(200, 40, 100, hostname, port);
//                }
//            }
//        }

        if (httpClient == null) {
            httpClient = createHttpClient(200, 40, 100, hostname, port);
        }
        return httpClient;
    }

    /**
     * 创建HttpClient对象
     *
     * @return
     * @author wei
     */
    public static CloseableHttpClient createHttpClient(int maxTotal,
            int maxPerRoute, int maxRoute, String hostname, int port) {
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        //设置连接参数
        cm.setMaxTotal(maxTotal); // 最大连接数
        cm.setDefaultMaxPerRoute(maxRoute); // 路由最大连接数

        HttpHost httpHost = new HttpHost(hostname, port);
        cm.setMaxPerRoute(new HttpRoute(httpHost), maxPerRoute);
        HttpClientFactory.thread = new HttpClientConnectionMonitorThread(
                cm); //管理 http连接池
        //请求失败时,进行请求重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > 3) {
                    //重试超过3次,放弃请求
                    log.info("retry has more than 3 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException) {
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    log.info("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException) {
                    // SSL握手异常
                    log.info("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException) {
                    //超时
                    log.info("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException) {
                    // 服务器不可达
                    log.info("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException) {
                    // 连接超时
                    log.info("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException) {
                    log.info("SSLException");
                    return false;
                }

                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    //如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            }
        };

        CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(cm)
                .setConnectionManagerShared(true).setRetryHandler(handler)
                .build();
        if (cm != null && cm.getTotalStats() != null) { //打印连接池的状态
            //log.info("当前连接池中情况/now client pool {}", cm.getTotalStats().toString());
        }
        return httpClient;
    }


    public static CloseableHttpClient getHttpClients() {
        return httpClient;
    }


}
