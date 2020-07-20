package com.andy.authentication.service;

/**
 * @author min.lai
 * @date 2019/7/23 15:11
 * desc:
 */
public interface IPermissionService {

    /**
     * 无需鉴权
     * @param url url
     * @return
     */
    Boolean ignoreAuthentication(String url);
}
