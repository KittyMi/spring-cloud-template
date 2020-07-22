package com.andy.gateway.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author min.lai
 * @date 2019/7/22 15:57
 * desc: TokenDTO
 */
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class TokenDTO implements Serializable {
    private static final long serialVersionUID = 7383703976967031542L;

    private Integer expiresIn;
    private Map<String, Object> additionalInformation;
    private Boolean expired;
    private List<String> scope;
    private Long expiration;
    private String tokenType;
    private String value;
    private Map<String, Object> refreshToken;

}
