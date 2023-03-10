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

        //?????????????????????token
        if (!"".equals(Authorization)&&!StringUtils.isEmpty(Authorization)){
            //???headers?????????????????????build
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
            //????????????request ?????? change??????
            exchange = exchange.mutate().request(host).build();
        }

            //????????????????????????????????????????????????????????????
        if (StringUtil.containsAnyIgnoreCase(path, uris)) {
            log.info("???????????? {}??????????????? {}",  request.getURI(), true);
            return chain.filter(exchange);
        }



        String token = "";
        //??????????????????Authorization???token???
        List<String> tokenHead = request.getHeaders().get(JwtConstant.tokenHeader);
        if (tokenHead != null) {
            token = tokenHead.get(0);
        }

        //??????token
        //??????token???????????????
        if (StringUtils.isEmpty(token)) {
            log.error("???????????? {}??????????????? {},??????:??????token???????????????",  request.getURI(), false);
            //50000: no token
            //if (StringUtil.containsAnyIgnoreCase(path, examExpired)){
                DataBuffer dataBuffer = createResponseBody(50009, "????????????", response);
                return response.writeWith(Flux.just(dataBuffer));
            //}
            //response.getHeaders().set(HttpHeaders.LOCATION, loginIndex);
            //response.setStatusCode(HttpStatus.SEE_OTHER);
            //return response.setComplete();

        }

        //???token???token?????????
        Claims claim = jwtGateWayUtils.getClaimsFromToken(token);
        if (claim == null) {
            log.error("???????????? {}??????????????? {},??????:???token???token?????????",  request.getURI(), false);
            //50008: Illegal token
            DataBuffer dataBuffer = createResponseBody(50008, "??????token", response);
            return response.writeWith(Flux.just(dataBuffer));
        }

        String username = jwtGateWayUtils.getUserNameFromToken(token);
        String id = jwtGateWayUtils.getUserIdFromToken(token);
        String group = jwtGateWayUtils.getGroupFromToken(token);
        //?????????????????????token???????????????
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(id) || StringUtils.isEmpty(group)) {
            log.error("???????????? {}??????????????? {},??????:?????????????????????token???????????????",  request.getURI(), false);
            DataBuffer dataBuffer = createResponseBody(50008, "??????token", response);
            return response.writeWith(Flux.just(dataBuffer));
        }

        ////token?????????????????? ??????????????????????????????
        //boolean checkUri = StringUtil.containsAnyIgnoreCase(path, checktoken);
        //if (checkUri) {
        //    return chain.filter(exchange);
        //}
        //log.error("??????token???????????????{},???????????? {}??????????????? {}", Arrays.asList(checktoken), path, checkUri);


        //???token???????????????????????????,?????????????????????
        String key = String.format(jwtBlacklist, group);
        String blackToken = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(blackToken)) {
            log.error("???????????? {}??????????????? {},??????:???token???????????????????????????",  request.getURI(), false);
            if (StringUtil.containsAnyIgnoreCase(path, examExpired)){
                DataBuffer dataBuffer = createResponseBody(50009, "??????token", response);
                return response.writeWith(Flux.just(dataBuffer));
            }
                response.getHeaders().set(HttpHeaders.LOCATION, loginIndex);
                response.setStatusCode(HttpStatus.SEE_OTHER);
                return response.setComplete();
        }

        // redis???id?????????token?????????
        // ??????????????????token???redis????????????token?????????????????????????????????
        String redisToken = (String) redisTemplate.opsForHash().get(jwtToken, id);
        if (StringUtils.isEmpty(redisToken) || !redisToken.equals(token)) {
            log.error("???????????? {}??????????????? {},??????: redis???id?????????token???????????????????????????token???redis????????????token?????????,??????????????????",  request.getURI(), false);
            if (StringUtil.containsAnyIgnoreCase(path, examExpired)){
                DataBuffer dataBuffer = createResponseBody(50009, "??????token", response);
                return response.writeWith(Flux.just(dataBuffer));
            }
            response.getHeaders().set(HttpHeaders.LOCATION, loginIndex);
            response.setStatusCode(HttpStatus.SEE_OTHER);
            return response.setComplete();
        }

        //??????????????????????????????
        if (!jwtGateWayUtils.isHoldTime(token)) {
            log.error("???????????? {}??????????????? {},??????:??????????????????????????????,??????????????????",  request.getURI(), false);
            if (StringUtil.containsAnyIgnoreCase(path, examExpired)){
                DataBuffer dataBuffer = createResponseBody(50009, "??????token", response);
                return response.writeWith(Flux.just(dataBuffer));
            }
            //50014: Token expired;
            response.getHeaders().set(HttpHeaders.LOCATION, loginIndex);
            //303????????????????????????????????????????????????????????????URI????????????GET?????????????????????????????????
            response.setStatusCode(HttpStatus.SEE_OTHER);
            return response.setComplete();
        }

        //token?????????????????????????????????
        if (expiredTimeUrl.equals(path)) {
            log.error("???????????? {}??????????????? {} ,?????????token?????????????????????????????????",  request.getURI(), true);
            return chain.filter(exchange);
        }


        //token ??????
        if (jwtGateWayUtils.canRefresh(token)) {
            String refreshToken = jwtGateWayUtils.refreshToken(token);
            //???????????????
            ServerHttpRequest httpRequest = request.mutate().header(JwtConstant.tokenHeader, refreshToken).build();
            ServerWebExchange webExchange = exchange.mutate().request(httpRequest).build();
            log.info("???????????? {}??????????????? {},token????????????",  request.getURI(), true);
            return chain.filter(webExchange);
        }
        log.info("???????????? {}??????????????? {}",  request.getURI(), true);
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
           log.error("json???????????? {}",e.getLocalizedMessage());
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(str.getBytes());
        return dataBuffer;
    }

    /**
     * ????????????????????????????????????
     * @param serverHttpRequest
     * @return
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest){
        //???????????????
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
