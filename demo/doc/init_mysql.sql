DROP TABLE IF EXISTS `security_module`;
CREATE TABLE `security_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `priority` int(11) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `sn` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL DEFAULT 'security' COMMENT '模块类型',
  `enable` int(2) DEFAULT '1' COMMENT '是否启用（1：启用，0：停用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;




-- ----------------------------
-- Records of security_module
-- ----------------------------
INSERT INTO `security_module` VALUES ('1', '所有模块的根节点，不能删除', '业务模块', '1', '#', null, '', '0', '1');
INSERT INTO `security_module` VALUES ('2', '安全管理:包含权限管理、模块管理', '安全管理', '1', '#', '1', 'Security', '1', '1');
INSERT INTO `security_module` VALUES ('3', '用户管理', '用户管理', '1', '/security/user/list', '2', 'User', '1', '1');
INSERT INTO `security_module` VALUES ('4', '角色管理', '角色管理', '2', '/security/role/list', '2', 'Role', '1', '1');
INSERT INTO `security_module` VALUES ('5', '模块管理', '模块管理', '4', '/security/module/tree', '2', 'Module', '1', '1');
INSERT INTO `security_module` VALUES ('6', '缓存管理', '缓存管理', '5', '/security/cacheManage/index', '2', 'CacheManage', '1', '1');
INSERT INTO `security_module` VALUES ('25', '组织管理', '组织管理', '4', '/security/organization/tree', '2', 'Organization', '1', '1');
INSERT INTO `security_module` VALUES ('7', '用户管理新增操作', '新增', '1', '#', '3', 'save', '2', '1');
INSERT INTO `security_module` VALUES ('8', '用户管理删除操作', '删除', '3', '#', '3', 'delete', '2', '1');
INSERT INTO `security_module` VALUES ('9', '用户管理修改操作', '修改', '2', '#', '3', 'edit', '2', '1');
INSERT INTO `security_module` VALUES ('10', '用户管理查询操作', '查看', '0', '#', '3', 'view', '2', '1');
INSERT INTO `security_module` VALUES ('11', '角色管理新增操作', '新增', '1', '#', '4', 'save', '2', '1');
INSERT INTO `security_module` VALUES ('12', '角色管理删除操作', '删除', '3', '#', '4', 'delete', '2', '1');
INSERT INTO `security_module` VALUES ('13', '角色管理修改操作', '修改', '2', '#', '4', 'edit', '2', '1');
INSERT INTO `security_module` VALUES ('14', '角色管理查询操作', '查看', '0', '#', '4', 'view', '2', '1');
INSERT INTO `security_module` VALUES ('15', '缓存管理新增操作', '新增', '1', '#', '6', 'save', '2', '1');
INSERT INTO `security_module` VALUES ('16', '缓存管理删除操作', '删除', '3', '#', '6', 'delete', '2', '1');
INSERT INTO `security_module` VALUES ('17', '缓存管理修改操作', '修改', '2', '#', '6', 'edit', '2', '1');
INSERT INTO `security_module` VALUES ('18', '缓存管理查询操作', '查看', '0', '#', '6', 'view', '2', '1');
--INSERT INTO `security_module` VALUES ('19', '模块管理新增操作', '新增模块', '1', '#', '5', 'save', '2', '1');
--INSERT INTO `security_module` VALUES ('20', '模块管理删除操作', '删除', '4', '#', '5', 'delete', '2', '1');
INSERT INTO `security_module` VALUES ('21', '模块管理修改操作', '修改', '3', '#', '5', 'edit', '2', '1');
INSERT INTO `security_module` VALUES ('22', '模块管理查询操作', '查看', '0', '#', '5', 'view', '2', '1');
--INSERT INTO `security_module` VALUES ('23', '模块管理新增业务操作', '新增操作', '2', '#', '5', 'saveoperation', '2', '1');
INSERT INTO `security_module` VALUES ('24', '用户管理查询操作', '查看', '0', '#', '2', 'view', '2', '1');
INSERT INTO `security_module` VALUES ('26', '查看操作', '查看', '1', '#', '25', 'view', '2', '1');
INSERT INTO `security_module` VALUES ('27', '组织管理新增操作', '新增', '1', '#', '25', 'save', '2', '1');
INSERT INTO `security_module` VALUES ('28', '组织管理删除操作', '删除', '3', '#', '25', 'delete', '2', '1');
INSERT INTO `security_module` VALUES ('29', '组织管理修改操作', '修改', '2', '#', '25', 'edit', '2', '1');


ALTER TABLE `security_module`
ADD COLUMN `CREATER`  bigint(20) NULL,
ADD COLUMN `CREATETIME`  CHAR(19) NULL,
ADD COLUMN `MODIFYER`  bigint(20) NULL,
ADD COLUMN `MODIFYTIME`  CHAR(19) NULL;

update security_module set CREATER = 1 ,MODIFYER = 1,CREATETIME=SYSDATE(),MODIFYTIME=SYSDATE()

-- ----------------------------
-- Table structure for security_organization
-- ----------------------------
DROP TABLE IF EXISTS `security_organization`;
CREATE TABLE `security_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `code` varchar(64) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_organization
-- ----------------------------
INSERT INTO `security_organization` VALUES ('1', '不能删除。', '根组织','ROOT', null);

-- ----------------------------
-- Table structure for security_role
-- ----------------------------
DROP TABLE IF EXISTS `security_role`;
CREATE TABLE `security_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_role
-- ----------------------------
INSERT INTO `security_role` VALUES ('1', '超级管理员');

-- ----------------------------
-- Table structure for security_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `security_role_permission`;
CREATE TABLE `security_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_role_permission
-- ----------------------------
INSERT INTO `security_role_permission` VALUES ('1', 'Security:view', '1');
INSERT INTO `security_role_permission` VALUES ('1', 'User:view', '2');
INSERT INTO `security_role_permission` VALUES ('1', 'User:save', '3');
INSERT INTO `security_role_permission` VALUES ('1', 'User:edit', '4');
INSERT INTO `security_role_permission` VALUES ('1', 'User:delete', '5');
INSERT INTO `security_role_permission` VALUES ('1', 'Role:view', '6');
INSERT INTO `security_role_permission` VALUES ('1', 'Role:save', '7');
INSERT INTO `security_role_permission` VALUES ('1', 'Role:edit', '8');
INSERT INTO `security_role_permission` VALUES ('1', 'Role:delete', '9');
INSERT INTO `security_role_permission` VALUES ('1', 'Module:view', '10');
INSERT INTO `security_role_permission` VALUES ('1', 'Module:save', '11');
INSERT INTO `security_role_permission` VALUES ('1', 'Module:saveoperation', '12');
INSERT INTO `security_role_permission` VALUES ('1', 'Module:edit', '13');
INSERT INTO `security_role_permission` VALUES ('1', 'Module:delete', '14');
INSERT INTO `security_role_permission` VALUES ('1', 'Organization:view', '15');
INSERT INTO `security_role_permission` VALUES ('1', 'Organization:save', '16');
INSERT INTO `security_role_permission` VALUES ('1', 'Organization:edit', '17');
INSERT INTO `security_role_permission` VALUES ('1', 'Organization:delete', '18');
INSERT INTO `security_role_permission` VALUES ('1', 'CacheManage:view', '19');
INSERT INTO `security_role_permission` VALUES ('1', 'CacheManage:save', '20');
INSERT INTO `security_role_permission` VALUES ('1', 'CacheManage:edit', '21');
INSERT INTO `security_role_permission` VALUES ('1', 'CacheManage:delete', '22');

-- ----------------------------
-- Table structure for security_user
-- ----------------------------
DROP TABLE IF EXISTS `security_user`;
CREATE TABLE `security_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `salt` varchar(32) NOT NULL,
  `status` varchar(20) NOT NULL,
  `username` varchar(32) NOT NULL,
  `email` varchar(128) NOT NULL,
  `realname` varchar(32) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `org_id` bigint(20) DEFAULT NULL,
  `userType` int(2) NOT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_user
-- ----------------------------
INSERT INTO `security_user` VALUES ('2', '2012-08-03 14:58:38', '334e65571ac44a565a74d11a43e0f853e091dfe6', '7fc59f59e241db8d', 'enabled', 'system', '', '系统后台/未知用户', '', '1', '1');
INSERT INTO `security_user` VALUES ('1', '2012-08-03 14:58:38', '334e65571ac44a565a74d11a43e0f853e091dfe6', '7fc59f59e241db8d', 'enabled', 'admin', '', '系统管理员', '', '1', '1');

-- ----------------------------
-- Table structure for security_user_role
-- ----------------------------
DROP TABLE IF EXISTS `security_user_role`;
CREATE TABLE `security_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_user_role
-- ----------------------------
INSERT INTO `security_user_role` VALUES ('1', '1', '1', '1');



#创建数据库表
DROP TABLE IF EXISTS BASE_DOC;
CREATE TABLE BASE_DOC (
    ID BIGINT(19)  NOT NULL  AUTO_INCREMENT  COMMENT '主键',
    NAME VARCHAR(64)  NOT NULL  COMMENT '文件名称',
    SUFFIX VARCHAR(16)  NOT NULL  COMMENT '文件类型',
    PATH VARCHAR(64)  COMMENT '存储路径（相对）',
    MD5 VARCHAR(32)  COMMENT 'MD5码',
    EXPIRATION CHAR(19)  COMMENT '(缓存文件)过期时间',
    CACHE_KEY VARCHAR(64)  COMMENT '(缓存文件)key值',
    CREATER DECIMAL(19)  COMMENT '创建人',
    MODIFYER DECIMAL(19)  COMMENT '最后修改人',
    CREATETIME CHAR(19)  COMMENT '创建时间',
    MODIFYTIME CHAR(19)  COMMENT '最后修改时间',
    PRIMARY KEY (ID)
) DEFAULT CHARSET=utf8 COMMENT='文件档案';



#创建数据库表
DROP TABLE IF EXISTS BASE_DOC_REL;
CREATE TABLE BASE_DOC_REL (
    ID BIGINT(19)  NOT NULL  AUTO_INCREMENT  COMMENT '主键',
    CACHE_KEY VARCHAR(64)  COMMENT '(缓存文件)key值',
    CREATER DECIMAL(19)  COMMENT '创建人',
    MODIFYER DECIMAL(19)  COMMENT '最后修改人',
    CREATETIME CHAR(19)  COMMENT '创建时间',
    MODIFYTIME CHAR(19)  COMMENT '最后修改时间',
    PRIMARY KEY (ID)
) DEFAULT CHARSET=utf8 COMMENT='档案引用';



