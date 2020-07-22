package com.andy.gateway.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ly
 * @date 2020/5/28 10:33
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;
    private final SwaggerKnifeProperties properties;

//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        List<String> routes = new ArrayList<>();
//        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
//        gatewayProperties.getRoutes().forEach(route -> route.getPredicates().stream()
//                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
//                        .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
//                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
//                                        .replace("/**", "/v2/api-docs")))));
//
////        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
////                .forEach(route -> route.getPredicates().stream()
////                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
////                        .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
////                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
////                                        .replace("/**", "/v2/api-docs")))));
//
//        return resources;
//    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        // 获取所有可用的host：serviceId
        routeLocator.getRoutes()
                .filter( route -> route.getUri().getHost() != null )
                .filter(route -> !properties.needIgnore(route.getUri().getHost().toLowerCase()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost().toLowerCase()));

        // 记录已经添加过的server，存在同一个应用注册了多个服务
        Set<String> set = new HashSet<>();
        routeHosts.forEach(instance -> {
            String url = "/" + instance + properties.getApiDocsPath();
            if (!set.contains(url)) {
                set.add(url);
                resources.add(
                        swaggerResource(
                                instance, url, properties.getSwaggerVersion()
                        )
                );
            }
        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String url, String version) {
        log.info("name:{},url:{}",name,url);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setUrl(url);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}