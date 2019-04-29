

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apts_app
-- ----------------------------
DROP TABLE IF EXISTS `apts_app`;
CREATE TABLE `apts_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '应用名',
  `file_name` varchar(50) DEFAULT NULL COMMENT '应用文件名',
  `file_url` varchar(100) DEFAULT NULL COMMENT '应用路径',
  `state` tinyint(4) DEFAULT '0' COMMENT '应用状态 0:待运行 1:启动中 2:已运行',
  `last_log_file_name` varchar(50) DEFAULT NULL COMMENT '最新的日志文件名',
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_delete` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='应用表';

-- ----------------------------
-- Table structure for apts_git
-- ----------------------------
DROP TABLE IF EXISTS `apts_git`;
CREATE TABLE `apts_git` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) DEFAULT NULL COMMENT '应用id',
  `git_name` varchar(32) DEFAULT NULL COMMENT 'git账号',
  `git_pwd` varchar(32) DEFAULT NULL COMMENT 'git密码',
  `git_url` varchar(255) DEFAULT NULL COMMENT 'git路径',
  `git_branch` varchar(32) DEFAULT NULL COMMENT 'git分支',
  `is_delete` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用对应的git项目';
