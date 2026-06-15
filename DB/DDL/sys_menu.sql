CREATE TABLE sys_menu (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    parent_id   BIGINT       NOT NULL DEFAULT 0 COMMENT '父菜单ID',
    name        VARCHAR(50)  NOT NULL COMMENT '菜单名',
    path        VARCHAR(200) DEFAULT NULL COMMENT '路由路径',
    component   VARCHAR(200) DEFAULT NULL COMMENT '组件路径',
    icon        VARCHAR(50)  DEFAULT NULL COMMENT '图标',
    sort        INT          NOT NULL DEFAULT 0 COMMENT '排序',
    menu_type   TINYINT      NOT NULL DEFAULT 0 COMMENT '类型:0目录 1菜单 2按钮',
    permission  VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态:1启用 0禁用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by   BIGINT       DEFAULT NULL COMMENT '创建人',
    update_by   BIGINT       DEFAULT NULL COMMENT '更新人',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';
