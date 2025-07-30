-- 创建库
create database if not exists mianshiya;

-- 切换库
use mianshiya;

-- 用户表
CREATE TABLE IF NOT EXISTS `user`
(
    `id`                  bigint PRIMARY KEY,
    `account`             varchar(256)                       NOT NULL COMMENT '账号',
    `password`            varchar(512)                       NOT NULL COMMENT '密码',
    `nickname`            varchar(256)                       NOT NULL COMMENT '用户昵称',
    `avatar`              varchar(1024) COMMENT '头像',
    `email`               varchar(256) COMMENT '邮箱',
    `phone`               varchar(20) COMMENT '手机号码',
    `profile`             varchar(512) COMMENT '用户简介',
    `grade`               varchar(50) COMMENT '年级',
    `work_experience`     varchar(512) COMMENT '工作经验',
    `expertise_direction` varchar(512) COMMENT '擅长方向',
    `union_id`            varchar(256) COMMENT '微信开发平台id',
    `mp_open_id`          varchar(256) COMMENT '公众号 open id',
    `create_time `        datetime default CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time  `       datetime default CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete  `         tinyint  default 0                 NOT NULL COMMENT '是否删除',
    -- gender 后面在按需求看是做 enum 还是 数据字典
    index idx_union_id (union_id)
) COMMENT ='用户表' COLLATE = utf8mb4_unicode_ci;

-- 角色表
CREATE TABLE IF NOT EXISTS `role`
(
    `id`          bigint PRIMARY KEY COMMENT '角色ID',
    `name`        varchar(64)                        NOT NULL COMMENT '角色名称（如 admin、user）',
#     `code`        varchar(64)                        NOT NULL UNIQUE COMMENT '角色标识（唯一，英文）',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除'
) COMMENT ='角色表' COLLATE = utf8mb4_unicode_ci;


-- 用户角色表
CREATE TABLE IF NOT EXISTS `user_role`
(
    `id`          bigint PRIMARY KEY AUTO_INCREMENT,
    `user_id`     varchar(128)                       NOT NULL COMMENT '用户ID（对应 user.id）',
    `role_id`     bigint                             NOT NULL COMMENT '角色ID（对应 role.id）',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除',
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    INDEX idx_user_id (`user_id`),
    INDEX idx_role_id (`role_id`)
) COMMENT ='用户角色关联表' COLLATE = utf8mb4_unicode_ci;

-- 权限表
CREATE TABLE IF NOT EXISTS `permission`
(
    `id`          bigint PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    `code`        varchar(128)                       NOT NULL UNIQUE COMMENT '权限标识（如：user:read）',
    `url`         varchar(256) COMMENT '接口路径（可选）',
    `method`      varchar(10) COMMENT '请求方法（GET/POST/PUT/DELETE）',
    `description` varchar(256) COMMENT '权限描述',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除'
) COMMENT ='权限表' COLLATE = utf8mb4_unicode_ci;

-- 角色权限表
CREATE TABLE IF NOT EXISTS `role_permission`
(
    `id`            bigint PRIMARY KEY AUTO_INCREMENT,
    `role_id`       bigint                             NOT NULL COMMENT '角色ID（对应 role.id）',
    `permission_id` bigint                             NOT NULL COMMENT '权限ID（对应 permission.id）',
    `create_time`   datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`   datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`     tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除',
    UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
    INDEX idx_role_id (`role_id`),
    INDEX idx_permission_id (`permission_id`)
) COMMENT ='角色权限关联表' COLLATE = utf8mb4_unicode_ci;


-- 题库表
create table if not exists question_bank
(
    id          bigint auto_increment comment 'id' primary key,
    title       varchar(256)                       null comment '标题',
    description text                               null comment '描述',
    picture     varchar(2048)                      null comment '图片',
    user_id     bigint                             not null comment '创建用户 id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除',
    index idx_title (title)
) comment '题库' collate = utf8mb4_unicode_ci;

-- 题目表
create table if not exists question
(
    id          bigint auto_increment comment 'id' primary key,
    title       varchar(256)                       null comment '标题',
    content     text                               null comment '内容',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    answer      text                               null comment '推荐答案',
    user_id     bigint                             not null comment '创建用户 id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除',
    index idx_title (title),
    index idx_user_id (user_id)
) comment '题目' collate = utf8mb4_unicode_ci;

-- 题库题目表 一个题目可以放入多个题库 多对多的关系
create table if not exists question_bank_question
(
    id               bigint auto_increment comment 'id' primary key,
    question_bank_id bigint                             not null comment '题库 id',
    question_id      bigint                             not null comment '题目 id',
#     user_id          bigint                             not null comment '创建用户 id',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    unique (question_bank_id, question_id)
) comment '题库题目' collate = utf8mb4_unicode_ci;
