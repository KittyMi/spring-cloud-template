SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` char(24) NOT NULL COMMENT '密码',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `gmt_create` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_updated` datetime NOT NULL COMMENT '记录更新时间',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`) USING BTREE COMMENT 'username唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=322 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'andy', '2kG87/l7HPlgeP+ySbPWbg==', '0', '2020-07-21 15:41:07', '2020-07-21 15:41:30', 'andy', null, null);
INSERT INTO `user_info` VALUES ('2', 'admin', 'ISMvKXpXpadDiUoOSoAfww==', '0', '2020-07-21 15:42:19', '2020-07-21 15:42:21', 'admin', null, null);
INSERT INTO `user_info` VALUES ('3', 'test', 'CY9rzUYh03PK3k6DJie09g==', '0', '2020-07-21 15:43:34', '2020-07-21 15:43:37', 'test', null, null);