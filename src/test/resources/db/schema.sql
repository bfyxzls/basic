DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT   COMMENT '主键',
  `name` varchar(30)   NULL COMMENT '姓名',
  `email` varchar(50)   NULL COMMENT '邮箱',
   `created_on`   TIMESTAMP NULL COMMENT '建立时间',
   `updated_on` TIMESTAMP   NULL COMMENT '更新时间',
   `is_delete` int(1)   NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ;

DROP TABLE IF EXISTS `user_extension`;
CREATE TABLE `user_extension` (
  `id` bigint(20) NOT NULL  AUTO_INCREMENT   COMMENT '主键',
  `user_id` bigint(20) NOT NULL,
  `real_name` varchar(10) NOT  NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ;