package org.palm.spring.boot.simple.swagger.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Swagger2配置类
 * @author
 * @since 2018-11-8
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${server.isProd}")
    private boolean isProd;

    @Bean
    public Docket createRestApi() {
        //参考：https://github.com/leelance/spring-boot-all   /*@Primary*/
        if(!isProd){
            return (new Docket(DocumentationType.SWAGGER_2))
                    //配置说明
                    .apiInfo(this.apiInfo())
                    //定义组
                    .groupName("Admin API")
                    //全局的操作参数
                    .globalOperationParameters(this.setHeaderToken())
                    .forCodeGeneration(true).pathMapping("/")
                    //选择那些路径和api会生成document
                    .select()
                    //拦截的包路径
                    /*.apis(RequestHandlerSelectors.basePackage("com.roncoo.education.controller"))*/
                    //配置扫描哪些类或者方法
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    //拦截的接口路径或者：.paths(paths())
                    /*.paths(regex("/api/.*"))*/
                    .paths(PathSelectors.any())
                    .build().useDefaultResponseMessages(false)
                    .ignoredParameterTypes(Map.class);
        }else{
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfoOnline()).select().paths(PathSelectors.none()).build();
        }

    }

    private Predicate<String> paths(){
        return Predicates.and(PathSelectors.regex("/.*"), Predicates.not(PathSelectors.regex("/error")));
        //return PathSelectors.regex("^/(?!error).*$");
    }

    private List<Parameter> setHeaderToken() {
        //@ApiImplicitParam(name = "Authorization", value = "认证信息", required = true, paramType = "header", defaultValue = "Bearer 467405f6-331c-4914-beb7-42027bf09a01", dataType = "string"),
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").defaultValue("32221635-C638-4A8E-8F26-8E7C6B58EAEB--item--487--item--9999--item--111111").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        List<Parameter> pars = new ArrayList();
        pars.add(tokenPar.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("IT技术中心", "http://www.baidu.com", "81222045@qq.com");
        return (new ApiInfoBuilder()).title("Apollo微服务文档").description("更多Api参考文档参考Confluence http://192.168.16.243/dashboard.action")
                //开源协议
                .license("Apache License Version 2.0").licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .termsOfServiceUrl("http://www.baidu.com/").contact("baiDuDu").version("1.0").build();
    }

    private ApiInfo apiInfoOnline() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("")
                .contact(new Contact("","", ""))
                .build();
    }

}
