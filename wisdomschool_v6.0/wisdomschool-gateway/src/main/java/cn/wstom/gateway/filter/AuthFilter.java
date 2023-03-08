package cn.wstom.gateway.filter;


import cn.wstom.gateway.constant.JwtConstant;
import cn.wstom.gateway.utils.JwtGateWayUtils;
import cn.wstom.gateway.utils.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConfigurationProperties(prefix = "auth.skip")
@Data
public class AuthFilter implements GlobalFilter, Ordered {

    private List<String> uris;

    private String loginIndex;

    private String examLogin;
    private List<String> examExpired;

    @Value("${jwt.blacklist.format}")
    private String jwtBlacklist;

    @Value("${jwt.token.format}")
    private String jwtToken;

    @Value("${jwt.expiredTimeUrl}")
    private String expiredTimeUrl;

    @Value("${jwt.monitorUrl}")
    private List<String> monitorUrl;

    @Autowired
    JwtGateWayUtils jwtGateWayUtils;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String Authorization = request.getQueryParams().getFirst("token");
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        String url=request.getURI().getScheme()+"://"+request.getURI().getHost()+":"+request.getURI().getPort()+path;
        //if (request.getMethod() == HttpMethod.POST) {
        //    // mediaType
        //
        //    MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
        //    // read & modify body
        //    ServerRequest serverRequest = new DefaultServerRequest(exchange);
        //    Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
        //            .flatMap(body -> {
        //                Authorization.set(StringUtil.getLastString(body, 292));
        //                return Mono.empty();
        //            });
        //    BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        //    HttpHeaders headers = new HttpHeaders();
        //    headers.putAll(exchange.getRequest().getHeaders());
        //    headers.remove(HttpHeaders.CONTENT_LENGTH);
        //    CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        //    ServerWebExchange finalExchange = exchange;
        //    return bodyInserter.insert(outputMessage, new BodyInserterContext())
        //            .then(Mono.defer(() -> {
        //                ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
        //                        finalExchange.getRequest()) {
        //
        //                    @Override
        //                    public Flux<DataBuffer> getBody() {
        //                        return outputMessage.getBody();
        //                    }
        //                };
        //                return chain.filter(finalExchange.mutate().request(decorator).build());
        //            }));
        //}

        //如果查询参数有token
        if (!"".equals(Authorization)&&!StringUtils.isEmpty(Authorization)){
            //向headers中放文件，记得build
            URI uri = exchange.getRequest().getURI();
            String oldUrl = uri.toString();
            String newUrl="";
            newUrl=oldUrl.replace("&token="+Authorization,"").trim();
            if(newUrl.equals(oldUrl)){
                newUrl=oldUrl.replace("?token="+Authorization,"").trim();
            }
            URIBuilder uriBuilder=null;
            URI newUri=null;
            try {
                 uriBuilder = new URIBuilder(newUrl);
                newUri=uriBuilder.build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            assert newUri != null;
            ServerHttpRequest host = exchange.getRequest().mutate().uri(newUri).header("Authorization", Authorization).build();
            //将现在的request 变成 change对象
            exchange = exchange.mutate().request(host).build();
        }

            //如果访问路径在定义过滤路径之中，直接放行
        if (StringUtil.containsAnyIgnoreCase(path, uris)) {
            log.info("当前路径 {}，是否放行 {}",  request.getURI(), true);
            return chain.filter(exchange);
        }



        String token = "";
        //得到请求头中Authorization的token值
        List<String> tokenHead = request.getHeaders().get(JwtConstant.tokenHeader);
        if (tokenHead != null) {
            token = tokenHead.get(0);
        }

        //验证token
        //没有token，没有权限
        if (StringUtils.isEmpty(token)) {
            log.error("当前路径 {}，是否放行 {},原因:没有token，没有权限",  request.getURI(), false);
            //50000: no token
            //if (StringUtil.containsAnyIgnoreCase(path, examExpired)){
                DataBuffer dataBuffer = createResponseBody(50009, "没有权限", response);
                return response.writeWith(Flux.just(dataBuffer));
            //}
            //response.getHeaders().set(HttpHeaders.LOCATION, loginIndex);
            //response.setStatusCode(HttpStatus.SEE_OTHER);
            //return response.setComplete();

        }

        //有token，token不合法
        Claims claim = jwtGateWayUtils.getClaimsFromToken(token);
        if (claim == null) {
            log.error("当前路径 {}，是否放行 {},原因:有token，token不合法",  request.getURI(), false);
            //50008: Illegal token
            DataBuffer dataBuffer = createResponseBody(50008, "非法token", response);
            return response.writeWith(Flux.just(dataBuffer));
        }

        String username = jwtGateWayUtils.getUserNameFromToken(token);
        String id = jwtGateWayUtils.getUserIdFromToken(token);
        String group = jwtGateWayUtils.getGroupFromToken(token);
        //没有有效载荷，token定义为非法
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(id) || StringUtils.isEmpty(group)) {
            log.error("当前路径 {}，是否放行 {},原因:没有有效载荷，token定义为非法",  request.getURI(), false);
            DataBuffer dataBuffer = createResponseBody(50008, "非法token", response);
            return response.writeWith(Flux.just(dataBuffer));
        }

        ////token可用性判断后 才可以刷新和重新登录
        //boolean checkUri = StringUtil.containsAnyIgnoreCase(path, checktoken);
        //if (checkUri) {
        //    return chain.filter(exchange);
        //}
        //log.error("验证token后放行路径{},当前路径 {}，是否放行 {}", Arrays.asList(checktoken), path, checkUri);


        //有token，但已被加入黑名单,只能选择再登录
        String key = String.format(jwtBlacklist, group);
        String blackToken = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(blackToken)) {
            log.error("当前路径 {}，是否放行 {},原因:有token，但已被加入黑名单",  request.getURI(), false);
            if (StringUtil.containsAnyIgnoreCase(path, examExpired)){
                DataBuffer dataBuffer = createResponseBody(50009, "非法token", response);
                return response.writeWith(Flux.just(dataBuffer));
            }
                response.getHeaders().set(HttpHeaders.LOCATION, loginIndex);
                response.setStatusCode(HttpStatus.SEE_OTHER);
                return response.setComplete();
        }

        // redis中id对应的token不存在
        // 或者请求中的token和redis中活跃的token不匹配，只能选择再登录
        String redisToken = (String) redisTemplate.opsForHash().get(jwtToken, id);
        if (StringUtils.isEmpty(redisToken) || !redisToken.equals(token)) {
            log.error("当前路径 {}，是否放行 {},原因: redis中id对应的token不存在或者请求中的token和redis中活跃的token不匹配,重定向到首页",  request.getURI(), false);
            if (StringUtil.containsAnyIgnoreCase(path, examExpired)){
                DataBuffer dataBuffer = createResponseBody(50009, "非法token", response);
                return response.writeWith(Flux.just(dataBuffer));
            }
            response.getHeaders().set(HttpHeaders.LOCATION, loginIndex);
            response.setStatusCode(HttpStatus.SEE_OTHER);
            return response.setComplete();
        }

        //有身份，过免登录时间
        if (!jwtGateWayUtils.isHoldTime(token)) {
            log.error("当前路径 {}，是否放行 {},原因:有身份，过免登录时间,重定向到首页",  request.getURI(), false);
            if (StringUtil.containsAnyIgnoreCase(path, examExpired)){
                DataBuffer dataBuffer = createResponseBody(50009, "非法token", response);
                return response.writeWith(Flux.just(dataBuffer));
            }
            //50014: Token expired;
            response.getHeaders().set(HttpHeaders.LOCATION, loginIndex);
            //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
            response.setStatusCode(HttpStatus.SEE_OTHER);
            return response.setComplete();
        }

        //token有效期内，可以进行登出
        if (expiredTimeUrl.equals(path)) {
            log.error("当前路径 {}，是否放行 {} ,原因：token有效期内，可以进行登出",  request.getURI(), true);
            return chain.filter(exchange);
        }


        //token 失效
        if (jwtGateWayUtils.canRefresh(token)) {
            String refreshToken = jwtGateWayUtils.refreshToken(token);
            //更新请求头
            ServerHttpRequest httpRequest = request.mutate().header(JwtConstant.tokenHeader, refreshToken).build();
            ServerWebExchange webExchange = exchange.mutate().request(httpRequest).build();
            log.info("当前路径 {}，是否放行 {},token已经刷新",  request.getURI(), true);
            return chain.filter(webExchange);
        }
        log.info("当前路径 {}，是否放行 {}",  request.getURI(), true);
        return chain.filter(exchange);

    }


    private DataBuffer createResponseBody(int code,String message,ServerHttpResponse response){

        response.getHeaders().add("Content-Type","application/json;charset=utf-8");
        cn.wstom.gateway.result.Data result = cn.wstom.gateway.result.Data.error(code, message);

        ObjectMapper objectMapper = new ObjectMapper();
        String str="";
        try {
            str=objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
           log.error("json转换错误 {}",e.getLocalizedMessage());
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(str.getBytes());
        return dataBuffer;
    }

    /**
     * 获取请求体中的字符串内容
     * @param serverHttpRequest
     * @return
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest){
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        StringBuilder sb = new StringBuilder();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return sb.toString();
    }


        private Map<String, Object> decodeBody(String body) {
            return Arrays.stream(body.split("&")).map(s -> s.split("="))
                    .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
        }

        private String encodeBody(Map<String, Object> map) {
            return map.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
        }




    @Override
    public int getOrder() {
        return 0;
    }
}
