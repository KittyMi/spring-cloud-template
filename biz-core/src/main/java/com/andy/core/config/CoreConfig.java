package com.andy.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.andy.core.utils.SpringUtil;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

/**
 * @author ly
 * @date 2019/5/16 21:11
 * description: Core Spring Bean Config
 */
@Configuration
public class CoreConfig {

    /**
     * FastJson 对象序列化工具
     * @return HttpMessageConverters
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverts() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        // Spring Security 禁止MediaType.ALL带有通配符的配置
        List<MediaType> mediaTypes = Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON_UTF8,
                MediaType.APPLICATION_ATOM_XML,
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_OCTET_STREAM,
                MediaType.APPLICATION_PDF,
                MediaType.APPLICATION_RSS_XML,
                MediaType.APPLICATION_XHTML_XML,
                MediaType.APPLICATION_XML,
                MediaType.IMAGE_GIF,
                MediaType.IMAGE_JPEG,
                MediaType.IMAGE_PNG,
                MediaType.TEXT_EVENT_STREAM,
                MediaType.TEXT_HTML,
                MediaType.TEXT_MARKDOWN,
                MediaType.TEXT_PLAIN,
                MediaType.TEXT_XML
        );
        converter.setSupportedMediaTypes(mediaTypes);
        FastJsonConfig config = new FastJsonConfig();

       /*
        config.setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);

        converter.setFastJsonConfig(config);
        return new HttpMessageConverters(converter);*/
       //todo 允许为null
        config.setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);
        config.setSerializeFilters((ValueFilter) (o, s, source) -> {
            if (source == null) {
                return "";
            }
            return source;
        });
        converter.setFastJsonConfig(config);
        return new HttpMessageConverters(converter);
    }

    /**
     * Orika 对象拷贝工具
     * @return MapperFacade
     */
    @Bean
    public MapperFacade mapperFacade() {
        return new DefaultMapperFactory.Builder()
                .mapNulls(false)
                .build().getMapperFacade();
    }

    /**
     * SpringUtil 获取SpringApplicationContext
     * @return SpringUtil
     */
    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }
}
