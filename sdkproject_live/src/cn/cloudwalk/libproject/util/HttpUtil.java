package cn.cloudwalk.libproject.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class HttpUtil {

	private static final int NET_TIME_OUT = 100000;

	public static boolean networkIsAvailable(Activity act) {
		ConnectivityManager manager = (ConnectivityManager) act.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	public static String getFromUrl(String url) {
		String strResult = "";
		HttpClient httpclient = null;
		HttpResponse httpResponse;
		try {
			httpclient = getHttpClient();
			LogUtil.logE("get:url=" + url);
	
			HttpGet httpRequest = new HttpGet(url);
			httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			LogUtil.logE("get:strResult=" + strResult);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		
		return strResult;
	}

	public static String postToUrl(String url, String data) {
		String strResult = "";
		HttpClient httpclient = null;
		try {
			LogUtil.logE("post:url=" + url);
			LogUtil.logE("post:data=" + data.toString());
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setEntity(new StringEntity(data, HTTP.UTF_8));
			httpclient = getHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			LogUtil.logE("post:strResult=" + strResult);
		} catch (Exception e) {
			LogUtil.logE("post:strResult" + e);
			return e.toString();
		}
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return strResult;
	}

	public static String postToUrl(String url, List<NameValuePair> parameters) {
		String strResult = "";
		HttpClient httpclient = null;
		try {
			LogUtil.logE("post:url=" + url);
			HttpPost httpRequest = new HttpPost(url);
			LogUtil.logE("post:parameters=" + parameters);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
			httpRequest.setEntity(entity);
			httpclient = getHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}
			LogUtil.logE("post:strResult=" + strResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return strResult;
	}

	private static DefaultHttpClient getHttpClient() {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, NET_TIME_OUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, NET_TIME_OUT);
		return client;
	}



}
