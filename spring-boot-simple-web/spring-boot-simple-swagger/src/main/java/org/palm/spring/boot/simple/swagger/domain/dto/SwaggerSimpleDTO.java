package org.palm.spring.boot.simple.swagger.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
public class SwaggerSimpleDTO implements Serializable {

    private static final long serialVersionUID = -8540754941307171995L;

    private Integer userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty(hidden = true)
    private List<Object> children = new ArrayList<>();

}