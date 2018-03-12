package com.example.humencheckworkattendance.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 日志拦截器
 * 功能：
 * 1.打印网络请求和响应日志
 * 2.切换BaseUrl
 */
public class LogInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Log.e("http", "请求方式:" + request.method() + ":" + getFullRequest(request));
        Response response = chain.proceed(request);
        Log.e("http", "请求方式:" + request.method() + "\n网络请求:" + getFullRequest(request) + "\n返回数据:" + getFullResponse(response));
        return response;
    }

    /**
     * 获取到完整的请求(地址+参数)
     *
     * @param request
     * @return
     */
    private String getFullRequest(Request request) throws IOException {
        RequestBody requestBody = request.body();
        String body = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }
        return request.url() + "&" + body;
    }

    /**
     * 获取到完整的响应数据(服务器返回数据)
     *
     * @param response
     * @return
     */
    private String getFullResponse(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        String reBody = null;
        if (response.body() != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            reBody = buffer.clone().readString(charset);
        }
        return reBody;
    }
}
