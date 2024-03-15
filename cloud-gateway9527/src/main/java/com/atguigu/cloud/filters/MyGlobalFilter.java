package com.atguigu.cloud.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                    if (startTime != null) {
                        log.info("访问接口主机: " + exchange.getRequest().getURI().getHost());
                        log.info("访问接口端口: " + exchange.getRequest().getURI().getPort());
                        log.info("访问接口URL：" +exchange.getRequest().getURI().getPath());
                        log.info("访问接口URL参数："+ exchange.getRequest().getURI().getRawQuery());
                        log.info("访问接口耗时：" + (System.currentTimeMillis() - startTime) + "ms");
                        log.info("我是美丽分割线: ###################################################");
                        System.out.println();
                    }
                }
                 ));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
