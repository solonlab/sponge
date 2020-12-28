package zm.data.dobbin.bull.dso;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.noear.snack.ONode;
import org.noear.water.utils.EncryptUtils;
import org.noear.water.utils.HttpUtils;


import java.io.IOException;
import java.util.*;


public class TestClient {

//    private static final String C_ID = "11019";
//    private static final String V_ID = "103";

    private static Map<String, String> buildQueryParam(String cId, String vId, String key, String cmd, String json) throws Exception {
        json = ONode.loadStr(json).toJson();

        Map<String, String> query = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append(cmd).append("#").append(json).append("#").append(key);
        String sign = EncryptUtils.sha256(sb.toString());
        String p64 = EncryptUtils.aesEncrypt(json, key, null);

        String p65 = EncryptUtils.aesDecrypt(p64, key, null);

        query.put("p", p64);
        query.put("k", cId + "." + vId + "." + sign);
        return query;
    }

    //测试用发送请求数据, 不加密, 用于API接口
    public static ONode doGet(String url, Map<String, String> input) throws Exception {
        Iterator<Map.Entry<String, String>> entries = input.entrySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key + "=" + value + "&");
        }
        String result = HttpUtils.getString(url + "?" + stringBuilder.toString());
        System.err.println(url + "接口返回: " + result);
        return ONode.load(result);
    }

    //测试用发送请求数据, 自动加密, 用于测试CMD接口
    public static ONode doPost(String cId, String vId, String key, String url, String cmd, String json) throws Exception {
        Map<String, String> map = buildQueryParam(cId, vId, key, cmd, json);
        String result = "";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        RequestConfig defaultRequestConfig = RequestConfig.custom().build();
        client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

        URIBuilder uriBuilder = new URIBuilder(url);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Charset", "UTF-8");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        List<NameValuePair> params = new ArrayList();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            params.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        try {
            long begin = System.currentTimeMillis();
            response = client.execute(httpPost);
            long end = System.currentTimeMillis();
            System.err.println(url + "接口调用时间: " + (end - begin) + " ms");
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                    System.err.println(url + "接口返回: " + result);
                }
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException("创建连接失败" + e);
        } catch (IOException e) {
            throw new RuntimeException("创建连接失败" + e);
        }

        return ONode.load(result);
    }
}