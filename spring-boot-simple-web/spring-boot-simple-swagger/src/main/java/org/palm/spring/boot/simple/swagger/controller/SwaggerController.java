package org.palm.spring.boot.simple.swagger.controller;

import io.swagger.annotations.*;
import org.palm.spring.boot.simple.swagger.domain.dto.SwaggerSimpleDTO;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 注解@ApiImplicitParam的参数paramType的可选值有：body(指定类型)/query(string)
 */
@RestController
@RequestMapping("/api/swagger")
@Api(value = "swagger服务接口", tags = "swagger", description = "swagger服务接口")
public class SwaggerController {

    @ApiIgnore
    @ApiOperation(value = "获取用户详情",httpMethod = "GET",notes="获取用户详情")
    @RequestMapping(value="/getUserDetail/id_{id}/{name}", method=RequestMethod.GET)
    public String getUserDetail(@PathVariable int id,@PathVariable String name){

        return "getUserDetail";
    }

    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "认证信息", defaultValue = "Bearer 25c997df-b4da-4b0d-8bef-435c2af66f9f", required = true, paramType = "header", dataType = "string"),
            @ApiImplicitParam(name = "telephone", value = "登录人手机号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/sendMsgForLogin", method = RequestMethod.GET)
    public String sendMsgForLogin(@RequestHeader("Authorization") String token,@RequestParam("telephone") String telephone,@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return "sendMsgForLogin";
    }




    @ApiOperation(value = "保存数据",httpMethod = "POST",notes="保存数据")
    @ApiImplicitParam(name = "info", value = "参数信息", required = true, paramType = "body", dataType = "SwaggerSimpleDTO")
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(@RequestBody SwaggerSimpleDTO info){
        //复合类型使用GET和POST请求==
        return "save";
    }

    @ApiOperation(value = "获取详情信息",httpMethod = "POST",notes="获取详情信息")
    @RequestMapping(value = "/getDetail", consumes="application/json", method = RequestMethod.POST)
    public String getDetail(String name) {
        //swagger请求类型默认是：application/json，单个参数请求POST请求，请求类型application/json，接收不到
        return "getDetail_01";
    }

}