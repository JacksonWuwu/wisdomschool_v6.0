package cn.wstom.gateway.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @className FilterHeadersUtil
 * @Description 过滤器请求/响应工具类
 * @date 2021/7/28
 */
@Slf4j
public final class FilterRequestResponseUtil {

     /**
     * 可以获取json类型或者x-www-form-urlencoded类型的请求参数
     *
     * @param exchange
     * @return
     */
    public static JSONObject getPostParam(ServerWebExchange exchange) {
        JSONObject jsonObject = new JSONObject();
        ServerHttpRequest httpRequest = exchange.getRequest();
        Flux<DataBuffer> dataBufferFlux = httpRequest.getBody();
        //获取body中的数据
        String body = FilterRequestResponseUtil.resolveBodyFromRequest(dataBufferFlux);
        if (StringUtils.isEmpty(body)) {
            return jsonObject;
        }
        if (isJson(body)) {
            return JSONObject.parseObject(body);
        }

        Arrays.stream(body.split("&")).forEach(c -> {
            String[] p = c.split("=");
            if (p.length == 0) {
                return;
            }
            try {
                String valStr = URLDecoder.decode(p[1], "UTF-8");
                jsonObject.put(p[0], valStr);
            } catch (UnsupportedEncodingException e) {
                log.error("gateway 转码错误:{}", body);
                return;
            }
        });
        return jsonObject;


    }

    /**
     * spring cloud gateway 获取post请求的body体
     *
     * @param body
     * @return
     */
    public static String resolveBodyFromRequest(Flux<DataBuffer> body) {
        AtomicReference<String> bodyRef = new AtomicReference<>();
        // 缓存读取的request body信息
        body.subscribe(dataBuffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
            DataBufferUtils.release(dataBuffer);
            bodyRef.set(charBuffer.toString());
        });
        //获取request body
        return bodyRef.get();
    }

    /**
     * 读取body内容
     *
     * @param body
     * @return
     */
    public static String resolveBodyFromRequest2(Flux<DataBuffer> body) {
        StringBuilder sb = new StringBuilder();

        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return formatStr(sb.toString());
    }

    /**
     * 去掉空格,换行和制表符
     *
     * @param str
     * @return
     */
    public static String formatStr(String str) {
        if (str != null && str.length() > 0) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        }
        return str;
    }

    public static boolean isJson(String string) {
        try {
            JSONObject.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
