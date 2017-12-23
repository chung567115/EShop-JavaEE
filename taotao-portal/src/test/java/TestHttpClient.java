import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * @author Charle Chung created on 2017年12月6日
 */
public class TestHttpClient {
	@Test
	public void testDoGet() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet get = new HttpGet("http://www.baidu.com");

		CloseableHttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);

		HttpEntity entity = response.getEntity();
		String entityString = EntityUtils.toString(entity, "UTF-8");
		System.out.println(entityString);

		response.close();
		httpClient.close();
	}

	@Test
	public void testDoGetWithParams() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		URIBuilder uriBuilder = new URIBuilder("http://www.baidu.com/s");
		uriBuilder.addParameter("wd", "猎场");

		HttpGet get = new HttpGet(uriBuilder.build());

		CloseableHttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);

		HttpEntity entity = response.getEntity();
		String entityString = EntityUtils.toString(entity, "UTF-8");
		System.out.println(entityString);

		response.close();
		httpClient.close();
	}

	@Test
	public void testDoPost() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.html");

		CloseableHttpResponse response = httpClient.execute(post);

		HttpEntity entity = response.getEntity();

		System.out.println(EntityUtils.toString(entity, "UTF-8"));

		response.close();
		httpClient.close();
	}

	@Test
	public void testDoPostWithParams() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.html");

		// 模拟表单
		List<NameValuePair> list = new ArrayList<>();
		list.add(new BasicNameValuePair("username", "张冲"));
		list.add(new BasicNameValuePair("password", "123456"));

		// 封装Entity对象
		StringEntity requestEntity = new UrlEncodedFormEntity(list, "UTF-8");
		post.setEntity(requestEntity);

		CloseableHttpResponse response = httpClient.execute(post);
		HttpEntity responseEntity = response.getEntity();
		System.out.println(EntityUtils.toString(responseEntity));

		response.close();
		httpClient.close();
	}

}
