package com.yub.system.vo.auth;

import com.yub.system.entity.menu.SysMenu;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 当前用户信息响应
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 当前用户信息响应
 * @Version: 1.0.0
 */
@Data
@Builder
public class UserInfoRespVO {

    /**
     * 用户基本信息
     */
    private UserVO user;

    /**
     * 角色编码列表
     */
    private List<String> roles;

    /**
     * 菜单树
     */
    private List<SysMenu> menus;

    /**
     * 权限标识列表
     */
    private List<String> permissions;

    /**
     * 用户基本信息内部类
     */
    @Data
    @Builder
    public static class UserVO {
        /**
         * 用户ID
         */
        private Long id;
        /**
         * 账号
         */
        private String account;
        /**
         * 昵称
         */
        private String nickName;
        /**
         * 头像
         */
        private String avatarUrl;
        /**
         * 手机号
         */
        private String phone;
        /**
         * 邮箱
         */
        private String email;
    }
}
