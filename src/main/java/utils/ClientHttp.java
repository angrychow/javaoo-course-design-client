package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class ClientHttp {
    public static HashMap<String, Object> Get(String url, HashMap<String, Object> params) {
        try {
            var httpClient = HttpClients.createDefault();
            var uri = new URIBuilder(url);
            if (params != null) {
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    uri.setParameter(param.getKey(), param.getValue().toString());
                }
            }
            var httpGet = new org.apache.http.client.methods.HttpGet(uri.build());
            var response = httpClient.execute(httpGet);

            // status code
            System.out.println(response.getStatusLine().getStatusCode());
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("statusCode", response.getStatusLine().getStatusCode());

            // body
            var entity = response.getEntity();
            var objectMapper = new ObjectMapper();
            resultMap.put("body", objectMapper.readValue(EntityUtils.toString(entity), HashMap.class));
            response.close();
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HashMap<String, Object> Post(String url, HashMap<String, Object> JSON) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头，将 Content-Type 设置为 application/json
            httpPost.setHeader("Content-Type", "application/json");
            // 设置 POST 请求参数，将 JSON 数据转换为 String 类型，并将其放入 HttpEntity 中
            var objectMapper = new ObjectMapper();
            String JSONString = objectMapper.writeValueAsString(JSON);
            HttpEntity entity = new StringEntity(JSONString);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            // status code
            System.out.println(response.getStatusLine().getStatusCode());
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("statusCode", response.getStatusLine().getStatusCode());

            // body
            var respEntity = response.getEntity();
            resultMap.put("body", objectMapper.readValue(EntityUtils.toString(respEntity), HashMap.class));
            response.close();
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
