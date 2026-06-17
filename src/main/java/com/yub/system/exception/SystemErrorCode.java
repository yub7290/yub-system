package com.yub.system.exception;

import com.yub.common.exception.ErrorCode;
import lombok.AllArgsConstructor;

/**
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 系统管理模块异常枚举
 * @Version: 1.0
 */
@AllArgsConstructor
public enum SystemErrorCode implements ErrorCode {

    /** 验证码错误 */
    CAPTCHA_ERROR(100001, "验证码错误"),
    /** 密码错误 */
    PASSWORD_ERROR(100002, "密码错误"),
    /** 账号已禁用 */
    ACCOUNT_DISABLED(100003, "账号已禁用"),
    /** 令牌已过期 */
    TOKEN_EXPIRED(100004, "令牌已过期"),
    /** 令牌无效 */
    TOKEN_INVALID(100005, "令牌无效"),
    /** 账号不存在 */
    ACCOUNT_NOT_FOUND(100006, "账号不存在"),
    /** 账号已存在 */
    ACCOUNT_EXISTS(100007, "账号已存在"),
    /** 不允许删除超级管理员 */
    SUPER_ADMIN_DELETE(100008, "不允许删除超级管理员"),
    /** 不允许禁用超级管理员 */
    SUPER_ADMIN_DISABLE(100009, "不允许禁用超级管理员"),
    /** 不允许重置超级管理员密码 */
    SUPER_ADMIN_RESET_PASSWORD(100010, "不允许重置超级管理员密码"),
    /** 角色编码已存在 */
    ROLE_CODE_EXISTS(100011, "角色编码已存在"),
    /** 角色已被用户关联无法删除 */
    ROLE_HAS_USERS(100012, "该角色下存在用户，无法删除"),
    /** 菜单名称已存在 */
    MENU_NAME_EXISTS(100013, "同级菜单下名称已存在"),
    /** 菜单存在子菜单无法删除 */
    MENU_HAS_CHILDREN(100014, "该菜单下存在子菜单，无法删除"),
    /** 菜单不存在 */
    MENU_NOT_FOUND(100015, "菜单不存在"),
    /** 部门不存在 */
    DEPT_NOT_FOUND(100016, "部门不存在"),
    /** 同级部门下名称已存在 */
    DEPT_NAME_EXISTS(100017, "同级部门下名称已存在"),
    /** 部门编码已存在 */
    DEPT_CODE_EXISTS(100018, "部门编码已存在"),
    /** 部门存在子部门无法删除 */
    DEPT_HAS_CHILDREN(100019, "该部门下存在子部门，无法删除"),
    /** 部门存在用户无法删除 */
    DEPT_HAS_USERS(100020, "该部门下存在用户，无法删除"),

    /** 字典类型不存在 */
    DICT_TYPE_NOT_FOUND(100021, "字典类型不存在"),
    /** 字典类型编码已存在 */
    DICT_TYPE_EXISTS(100022, "字典类型编码已存在"),

    /** 字典数据不存在 */
    DICT_DATA_NOT_FOUND(100023, "字典数据不存在"),

    /** 角色不存在 */
    ROLE_NOT_FOUND(100024, "角色不存在"),
    /** 角色名称已存在 */
    ROLE_NAME_EXISTS(100025, "角色名称已存在");

    private final int code;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
