package cn.yuexiu.manage.common.util;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.HttpClientConnectionManager;

/**
 * 线程用来清理 http连接池无效的链接
 *
 * @Author wei
 * @Date 2021/8/7 10:21
 **/
@Slf4j
public class HttpClientConnectionMonitorThread extends Thread {

    private final HttpClientConnectionManager connManager;
    private volatile boolean shutdown;

    public HttpClientConnectionMonitorThread(HttpClientConnectionManager connManager) {
        super();
        this.setName("http-connection-monitor");
        this.setDaemon(true);
        this.connManager = connManager;
        log.info(this.getName() + "-启动");
        this.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000); // 等待5秒
                    // 关闭过期的链接
                    connManager.closeExpiredConnections();
                    // 选择关闭 空闲30秒的链接
                    connManager.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
        }
    }


    /**
     * * 方法描述: 停止 管理器 清理无效链接  (该方法当前暂时关闭)
     * * @author andy 2017年8月28日 下午1:45:18
     */
    @Deprecated
    public void shutDownMonitor() {
        synchronized (this) {
            shutdown = true;
            notifyAll();
        }
    }

}
