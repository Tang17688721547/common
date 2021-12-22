package cn.yuexiu.manage.common.util;

import cn.yuexiu.manage.common.exception.JeecgBootException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * HTTP请求封装
 *
 * @Author wei
 * @Date 2021/2/22 20:50
 **/
@Slf4j
public class HttpUtils {


    /**
     * get
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doGet(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys)
            throws Exception {

        // Assert.notNull(host, "host not null!");
        host = host.replace(" ", "");
        CloseableHttpClient httpClient = wrapClient(host);

        HttpGet httpGet = new HttpGet(buildUrl(host, path, querys));
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Entry<String, String> e : headers.entrySet()) {
                httpGet.addHeader(e.getKey(), e.getValue().replace(" ", ""));
            }
        }
        try {
            log.info("###-> 调用3方接口 httpGet Obj: {}", JSONObject.toJSONString(httpGet));
            return httpClient.execute(httpGet);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * post form
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param bodys
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doPost(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys,
            Map<String, String> bodys)
            throws Exception {
        CloseableHttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        try {
            return httpClient.execute(request);
        } catch (Exception e) {
            return null;
        }
    }

    public static void shutDown() {
        try {
            HttpClientFactory.getHttpClients().getConnectionManager().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * post form
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param bodys
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doPost2(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys,
            Map<String, Object> bodys)
            throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
//        if (bodys != null) {
//            stringBuilder.append("{");
//            int i = 0;
//            for (String key : bodys.keySet()) {
//                if (i == 0) {
//                    i = 1;
//                } else {
//                    stringBuilder.append(",");
//                }
//                stringBuilder.append("\"").append(key).append("\"").append(":")
//                        .append("\"").append(bodys.get(key)).append("\"");
//            }
//            stringBuilder.append("}");
//            //根据需要发送的数据做相应替换
//        }
        return doPost(host, path, method, headers, querys,JSONObject.toJSON(bodys).toString());
    }


    /**
     * @Title: zhujianjuClient
     * @Description: 住建局数据上报
     * @Params: [host, path, method, headers, querys, bodys]
     * @Return: org.apache.http.client.methods.CloseableHttpResponse
     * @Author: z.h.c
     * @Date: 2021/7/22 10:29
     */
    public static CloseableHttpResponse zhujianjuClient(String host, String path,
            String method,
            Map<String, String> headers,
            Map<String, String> querys,
            Map<String, Object> bodys)
            throws Exception {
//        StringBuffer sb = new StringBuffer();
//        if (bodys != null) {
//            sb.append("{");
//            int i = 0;
//            for (String key : bodys.keySet()) {
//                if (i == 0) {
//                    i = 1;
//                } else {
//                    sb.append(",");
//                }
//                sb.append("\"").append(key).append("\"").append(":")
//                        .append("\"").append(bodys.get(key)).append("\"");
//            }
//            sb.append("}");
//            //根据需要发送的数据做相应替换
//        }
        return doPost3(host, path, method, headers, querys,JSONObject.toJSON(bodys).toString());
    }

    private static CloseableHttpResponse doPost3(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys,
            String body)
            throws Exception {
        CloseableHttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }
        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, HTTP.UTF_8));
        }
//        try {
        return httpClient.execute(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    /**
     * Post String
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doPost(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys,
            String body)
            throws Exception {
        CloseableHttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }
        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, HTTP.UTF_8));
        }
        try {
            return httpClient.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Post stream
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doPost(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys,
            byte[] body)
            throws Exception {
        CloseableHttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Put String
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doPut(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys,
            String body)
            throws Exception {
        CloseableHttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }

        }
        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * Put stream
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doPut(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys,
            byte[] body)
            throws Exception {
        CloseableHttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Delete
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doDelete(String host, String path, String method,
            Map<String, String> headers,
            Map<String, String> querys)
            throws Exception {
        CloseableHttpClient httpClient = wrapClient(host);

        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        return httpClient.execute(request);
    }

    private static String buildUrl(String host, String path, Map<String, String> querys)
            throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            path = path.replace(" ", "");
            sbUrl.append(path);
        }
        if (null != querys) {

            StringBuilder sbQuery = new StringBuilder();
            for (Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                if (host.indexOf("?") != -1) {
                    sbUrl.append("&").append(sbQuery);
                } else {
                    sbUrl.append("?").append(sbQuery);
                }
            }
        }

        return sbUrl.toString();
    }

    private static CloseableHttpClient wrapClient(String host) {
        CloseableHttpClient httpClient = HttpClientFactory.getHttpClient(host);
//        if (host.startsWith("https://")) {
//            sslClient(httpClient);
//        }
        return httpClient;
    }

    private static void sslClient(CloseableHttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * 读取返回结果
     */
    public static String read(InputStream in) throws IOException {
        return read(in, "utf-8");
    }

    /**
     * 读取返回结果
     *
     * @param is
     * @param charset
     * @return
     * @throws IOException
     */
    public static String read(InputStream is, String charset) throws IOException {
        BufferedReader br = null;
        try {
            StringBuffer sb = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                line = new String(line.getBytes(), charset);
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            log.info("读取返回结果异常 ,{}", e.getMessage());
            throw new JeecgBootException("读取返回结果异常",e);
           //// return "";
        } finally {
            if (br != null) {
                br.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 关闭输入流,用于并发情况下流没有关闭导入网关超时
     *
     * @param entity
     * @throws IOException
     */
    public static void consume(final HttpEntity entity) throws IOException {
        if (entity == null) {
            return;
        }
        if (entity.isStreaming()) {
            final InputStream instream = entity.getContent();
            if (instream != null) {
                instream.close();
            }
        }
    }

    /**
     * 关闭CloseableHttpResponse
     *
     * @param response
     * @throws Exception
     */
    public static void closeResponse(CloseableHttpResponse response) throws Exception {
        // 关闭CloseableHttpResponse
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }


}
