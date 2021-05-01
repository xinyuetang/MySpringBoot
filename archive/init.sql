CREATE DATABASE `fd_cms` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE USER 'fd_cms'@'%' IDENTIFIED BY 'FuDan!123';
GRANT ALL ON fd_cms.* TO 'fd_cms'@'%';
FLUSH PRIVILEGES;

DROP TABLE IF EXISTS `fd_cms`.`cms_user`;
CREATE TABLE `fd_cms`.`cms_user`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `stu_id`      varchar(32) NOT NULL DEFAULT '' COMMENT '学号',
    `role_id`     int(11)     NOT NULL DEFAULT 0 COMMENT '权限身份',
    `name`        varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
    `telephone`   varchar(32) NOT NULL DEFAULT '' COMMENT '手机',
    `email`       varchar(32) NOT NULL DEFAULT '' COMMENT '邮箱',
    `mentor`      varchar(32) NOT NULL DEFAULT '' COMMENT '导师',
    `leader`      varchar(32) NOT NULL DEFAULT '' COMMENT '汇报人',
    `is_keshuo`   varchar(32) NOT NULL DEFAULT '' COMMENT '是否科硕',
    `type`        varchar(32) NOT NULL DEFAULT '' COMMENT '就读类型 0-学术型，1-结合型，2-技术型',
    `enroll_date` varchar(32) NOT NULL DEFAULT '' COMMENT '入学时间',
    `papers`      varchar(32) NOT NULL DEFAULT '' COMMENT '论文',
    `patents`     varchar(32) NOT NULL DEFAULT '' COMMENT '专利',
    `services`    varchar(32) NOT NULL DEFAULT '' COMMENT '服务',
    `projects`    varchar(32) NOT NULL DEFAULT '' COMMENT '项目',
    `status`      tinyint(4)  NOT NULL DEFAULT 0 COMMENT '状态',
    `deleted`     tinyint(4)  NOT NULL DEFAULT 0 COMMENT '删除状态',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `modify_time` datetime             DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_stu_id` (`stu_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户';

INSERT INTO `fd_cms`.`cms_user`
    (`stu_id`, `name`, `role_id`, `create_time`)
VALUES ('07175', '杨珉', 0, CURRENT_TIMESTAMP),
       ('35004', '杨哲慜', 0, CURRENT_TIMESTAMP),
       ('34083', '张源', 0, CURRENT_TIMESTAMP),
       ('04278', '朱东来', 0, CURRENT_TIMESTAMP),
       ('04795', '张谧', 0, CURRENT_TIMESTAMP),
       ('41274', '张磊', 0, CURRENT_TIMESTAMP),
       ('BH6114', '张晓寒', 0, CURRENT_TIMESTAMP),
       ('19110240038', '游小钰', 6, CURRENT_TIMESTAMP),
       ('20110240040', '戴嘉润', 6, CURRENT_TIMESTAMP),
       ('20110240046', '廉轲轲', 6, CURRENT_TIMESTAMP),
       ('20110240048', '谈心', 6, CURRENT_TIMESTAMP),
       ('20110240049', '肖浩宇', 6, CURRENT_TIMESTAMP),
       ('17302010026', '邓朋', 6, CURRENT_TIMESTAMP),
       ('17212010064', '刘昕伟', 6, CURRENT_TIMESTAMP),
       ('14110240013', '张季博宁', 6, CURRENT_TIMESTAMP),
       ('17212010067', '乔颖', 6, CURRENT_TIMESTAMP),
       ('18210240204', '魏诗萌', 6, CURRENT_TIMESTAMP),
       ('18212010007', '贺瑞', 6, CURRENT_TIMESTAMP),
       ('18212010023', '乔丹', 6, CURRENT_TIMESTAMP),
       ('18212010031', '王寒蕊', 6, CURRENT_TIMESTAMP),
       ('18212010040', '武多才', 6, CURRENT_TIMESTAMP),
       ('18212010044', '许朝智', 6, CURRENT_TIMESTAMP),
       ('18212010069', '邵凯强', 6, CURRENT_TIMESTAMP),
       ('18212010076', '许海龙', 6, CURRENT_TIMESTAMP),
       ('18212010079', '张怀恩', 6, CURRENT_TIMESTAMP),
       ('19210240040', '刘安聪', 6, CURRENT_TIMESTAMP),
       ('19210240042', '张智搏', 6, CURRENT_TIMESTAMP),
       ('19210240047', '施游堃', 6, CURRENT_TIMESTAMP),
       ('19210240049', '杨森', 6, CURRENT_TIMESTAMP),
       ('19210240051', '汪海沛', 6, CURRENT_TIMESTAMP),
       ('19210240054', '花楠', 6, CURRENT_TIMESTAMP),
       ('19210240059', '钟明', 6, CURRENT_TIMESTAMP),
       ('19210240146', '朱家明', 6, CURRENT_TIMESTAMP),
       ('19210240158', '杨悉瑜', 6, CURRENT_TIMESTAMP),
       ('19210240159', '林奕帆', 6, CURRENT_TIMESTAMP),
       ('19210240180', '王子雯', 6, CURRENT_TIMESTAMP),
       ('19210240213', '邓瑞祺', 6, CURRENT_TIMESTAMP),
       ('19212010062', '张卓', 6, CURRENT_TIMESTAMP),
       ('20210240039', '刘壮', 6, CURRENT_TIMESTAMP),
       ('20210240041', '叶瀚', 6, CURRENT_TIMESTAMP),
       ('20210240046', '曹家俊', 6, CURRENT_TIMESTAMP),
       ('20210240052', '盛钡娜', 6, CURRENT_TIMESTAMP),
       ('20210240056', '肖起凡', 6, CURRENT_TIMESTAMP),
       ('20210240058', '陈晓婷', 6, CURRENT_TIMESTAMP),
       ('20210240099', '卢苇', 6, CURRENT_TIMESTAMP),
       ('20210240108', '吕海铭', 6, CURRENT_TIMESTAMP),
       ('20210240111', '吴子成', 6, CURRENT_TIMESTAMP),
       ('20210240134', '孙心乾', 6, CURRENT_TIMESTAMP),
       ('20210240143', '宓晨远', 6, CURRENT_TIMESTAMP),
       ('20210240152', '张函歌', 6, CURRENT_TIMESTAMP),
       ('20210240155', '张嘉熙', 6, CURRENT_TIMESTAMP),
       ('20210240167', '张煜坤', 6, CURRENT_TIMESTAMP),
       ('20210240176', '徐梓航', 6, CURRENT_TIMESTAMP),
       ('20210240215', '李驰', 6, CURRENT_TIMESTAMP),
       ('20210240234', '毛硕', 6, CURRENT_TIMESTAMP),
       ('20210240238', '汪扬', 6, CURRENT_TIMESTAMP),
       ('20210240241', '洪文正', 6, CURRENT_TIMESTAMP),
       ('20210240280', '罗天涵', 6, CURRENT_TIMESTAMP),
       ('20210240329', '陈彦君', 6, CURRENT_TIMESTAMP),
       ('20210240353', '颜一帆', 6, CURRENT_TIMESTAMP),
       ('20212010021', '杜小林', 6, CURRENT_TIMESTAMP),
       ('20212010046', '龙京奇', 6, CURRENT_TIMESTAMP),
       ('20212010055', '叶浩祺', 6, CURRENT_TIMESTAMP),
       ('20212010088', '梁洁', 6, CURRENT_TIMESTAMP),
       ('20212010115', '袁明豪', 6, CURRENT_TIMESTAMP),
       ('20212010122', '赵钰迪', 6, CURRENT_TIMESTAMP);

DROP TABLE IF EXISTS `fd_cms`.`cms_user_account`;
CREATE TABLE `fd_cms`.`cms_user_account`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `stu_id`      varchar(32) NOT NULL COMMENT '学号',
    `salt`        varchar(32) NOT NULL COMMENT '盐',
    `password`    varchar(64) NOT NULL COMMENT '密码',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_stu_id` (`stu_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户账户';

INSERT INTO `fd_cms`.`cms_user_account`(`stu_id`, `salt`, `password`, `create_time`)
VALUES ('07175', '07175', MD5('07175123456'), CURRENT_TIMESTAMP),
       ('35004', '35004', MD5('35004123456'), CURRENT_TIMESTAMP),
       ('34083', '34083', MD5('34083123456'), CURRENT_TIMESTAMP),
       ('04278', '04278', MD5('04278123456'), CURRENT_TIMESTAMP),
       ('04795', '04795', MD5('04795123456'), CURRENT_TIMESTAMP),
       ('41274', '41274', MD5('41274123456'), CURRENT_TIMESTAMP),
       ('BH6114', 'BH6114', MD5('BH6114123456'), CURRENT_TIMESTAMP),
       ('19110240038', '19110240038', MD5('19110240038123456'), CURRENT_TIMESTAMP),
       ('20110240040', '20110240040', MD5('20110240040123456'), CURRENT_TIMESTAMP),
       ('20110240046', '20110240046', MD5('20110240046123456'), CURRENT_TIMESTAMP),
       ('20110240048', '20110240048', MD5('20110240048123456'), CURRENT_TIMESTAMP),
       ('20110240049', '20110240049', MD5('20110240049123456'), CURRENT_TIMESTAMP),
       ('17302010026', '17302010026', MD5('17302010026123456'), CURRENT_TIMESTAMP),
       ('17212010064', '17212010064', MD5('17212010064123456'), CURRENT_TIMESTAMP),
       ('14110240013', '14110240013', MD5('14110240013123456'), CURRENT_TIMESTAMP),
       ('17212010067', '17212010067', MD5('17212010067123456'), CURRENT_TIMESTAMP),
       ('18210240204', '18210240204', MD5('18210240204123456'), CURRENT_TIMESTAMP),
       ('18212010007', '18212010007', MD5('18212010007123456'), CURRENT_TIMESTAMP),
       ('18212010023', '18212010023', MD5('18212010023123456'), CURRENT_TIMESTAMP),
       ('18212010031', '18212010031', MD5('18212010031123456'), CURRENT_TIMESTAMP),
       ('18212010040', '18212010040', MD5('18212010040123456'), CURRENT_TIMESTAMP),
       ('18212010044', '18212010044', MD5('18212010044123456'), CURRENT_TIMESTAMP),
       ('18212010069', '18212010069', MD5('18212010069123456'), CURRENT_TIMESTAMP),
       ('18212010076', '18212010076', MD5('18212010076123456'), CURRENT_TIMESTAMP),
       ('18212010079', '18212010079', MD5('18212010079123456'), CURRENT_TIMESTAMP),
       ('19210240040', '19210240040', MD5('19210240040123456'), CURRENT_TIMESTAMP),
       ('19210240042', '19210240042', MD5('19210240042123456'), CURRENT_TIMESTAMP),
       ('19210240047', '19210240047', MD5('19210240047123456'), CURRENT_TIMESTAMP),
       ('19210240049', '19210240049', MD5('19210240049123456'), CURRENT_TIMESTAMP),
       ('19210240051', '19210240051', MD5('19210240051123456'), CURRENT_TIMESTAMP),
       ('19210240054', '19210240054', MD5('19210240054123456'), CURRENT_TIMESTAMP),
       ('19210240059', '19210240059', MD5('19210240059123456'), CURRENT_TIMESTAMP),
       ('19210240146', '19210240146', MD5('19210240146123456'), CURRENT_TIMESTAMP),
       ('19210240158', '19210240158', MD5('19210240158123456'), CURRENT_TIMESTAMP),
       ('19210240159', '19210240159', MD5('19210240159123456'), CURRENT_TIMESTAMP),
       ('19210240180', '19210240180', MD5('19210240180123456'), CURRENT_TIMESTAMP),
       ('19210240213', '19210240213', MD5('19210240213123456'), CURRENT_TIMESTAMP),
       ('19212010062', '19212010062', MD5('19212010062123456'), CURRENT_TIMESTAMP),
       ('20210240039', '20210240039', MD5('20210240039123456'), CURRENT_TIMESTAMP),
       ('20210240041', '20210240041', MD5('20210240041123456'), CURRENT_TIMESTAMP),
       ('20210240046', '20210240046', MD5('20210240046123456'), CURRENT_TIMESTAMP),
       ('20210240052', '20210240052', MD5('20210240052123456'), CURRENT_TIMESTAMP),
       ('20210240056', '20210240056', MD5('20210240056123456'), CURRENT_TIMESTAMP),
       ('20210240058', '20210240058', MD5('20210240058123456'), CURRENT_TIMESTAMP),
       ('20210240099', '20210240099', MD5('20210240099123456'), CURRENT_TIMESTAMP),
       ('20210240108', '20210240108', MD5('20210240108123456'), CURRENT_TIMESTAMP),
       ('20210240111', '20210240111', MD5('20210240111123456'), CURRENT_TIMESTAMP),
       ('20210240134', '20210240134', MD5('20210240134123456'), CURRENT_TIMESTAMP),
       ('20210240143', '20210240143', MD5('20210240143123456'), CURRENT_TIMESTAMP),
       ('20210240152', '20210240152', MD5('20210240152123456'), CURRENT_TIMESTAMP),
       ('20210240155', '20210240155', MD5('20210240155123456'), CURRENT_TIMESTAMP),
       ('20210240167', '20210240167', MD5('20210240167123456'), CURRENT_TIMESTAMP),
       ('20210240176', '20210240176', MD5('20210240176123456'), CURRENT_TIMESTAMP),
       ('20210240215', '20210240215', MD5('20210240215123456'), CURRENT_TIMESTAMP),
       ('20210240234', '20210240234', MD5('20210240234123456'), CURRENT_TIMESTAMP),
       ('20210240238', '20210240238', MD5('20210240238123456'), CURRENT_TIMESTAMP),
       ('20210240241', '20210240241', MD5('20210240241123456'), CURRENT_TIMESTAMP),
       ('20210240280', '20210240280', MD5('20210240280123456'), CURRENT_TIMESTAMP),
       ('20210240329', '20210240329', MD5('20210240329123456'), CURRENT_TIMESTAMP),
       ('20210240353', '20210240353', MD5('20210240353123456'), CURRENT_TIMESTAMP),
       ('20212010021', '20212010021', MD5('20212010021123456'), CURRENT_TIMESTAMP),
       ('20212010046', '20212010046', MD5('20212010046123456'), CURRENT_TIMESTAMP),
       ('20212010055', '20212010055', MD5('20212010055123456'), CURRENT_TIMESTAMP),
       ('20212010088', '20212010088', MD5('20212010088123456'), CURRENT_TIMESTAMP),
       ('20212010115', '20212010115', MD5('20212010115123456'), CURRENT_TIMESTAMP),
       ('20212010122', '20212010122', MD5('20212010122123456'), CURRENT_TIMESTAMP);

DROP TABLE IF EXISTS `fd_cms`.`cms_`;
CREATE TABLE `fd_cms`.`cms_verification`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`     bigint      NOT NULL COMMENT '用户id',
    `code`        varchar(32) NOT NULL COMMENT '验证码',
    `expire_time` datetime    NOT NULL COMMENT '过期时间',
    `deleted`     tinyint(4)  NOT NULL DEFAULT 0 COMMENT '删除标记',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `modify_time` datetime             DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户验证码';