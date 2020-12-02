/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 127.0.0.1:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 25/11/2020 17:42:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_order
-- ----------------------------
DROP TABLE IF EXISTS `m_order`;
CREATE TABLE `m_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `order_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `user_address_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户配送地址ID',
  `spu_snapshot_id` int(11) NOT NULL COMMENT '商品快照表ID',
  `pay_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '付款ID',
  `pay_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单支付时间',
  `pay_type` tinyint(4) unsigned NOT NULL COMMENT '订单支付方式 1支付宝 2微信',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '订单状态 0待付款 1已完成 2已取消，3. 支付中，4. 支付失败',
  `pay_amount` decimal(10,2) NOT NULL COMMENT '应付金额',
  `real_pay` decimal(10,2) NOT NULL COMMENT '实付金额',
  `is_del` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';

-- ----------------------------
-- Table structure for m_order_spu
-- ----------------------------
DROP TABLE IF EXISTS `m_order_spu`;
CREATE TABLE `m_order_spu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `order_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `spu_id` int(11) unsigned NOT NULL COMMENT '商品ID',
  `sku_id` int(64) unsigned NOT NULL COMMENT 'sku_id',
  `sku_value_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'sku_value_id',
  `buy_amount` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '购买数量',
  `pay_amount` decimal(10,2) NOT NULL COMMENT '应付金额',
  `real_pay` decimal(10,2) NOT NULL COMMENT '实付金额',
  `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';

-- ----------------------------
-- Table structure for m_sku
-- ----------------------------
DROP TABLE IF EXISTS `m_sku`;
CREATE TABLE `m_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `spu_id` int(11) NOT NULL COMMENT '商品Id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '规格名称',
  `img_path` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '规格图片地址',
  `cost_price` decimal(10,2) NOT NULL COMMENT '成本价',
  `in_price` decimal(10,2) NOT NULL COMMENT '在售价',
  `origin_price` decimal(10,2) NOT NULL COMMENT '划线价',
  `status` tinyint(4) unsigned NOT NULL COMMENT '销售状态 0 仓库中 1售卖中 2已售罄',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建人',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改者ID',
  PRIMARY KEY (`id`),
  KEY `idx_sku_spu_id` (`spu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品sku价格';

-- ----------------------------
-- Table structure for m_sku_value
-- ----------------------------
DROP TABLE IF EXISTS `m_sku_value`;
CREATE TABLE `m_sku_value` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `created_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `spu_id` int(11) unsigned NOT NULL COMMENT '商品ID m_spu.id',
  `sku_id` int(11) unsigned NOT NULL COMMENT '商品规格ID m.sku_id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '规格值',
  `status` tinyint(4) unsigned NOT NULL COMMENT '销售状态 0 仓库中 1售卖中 2已售罄',
  `total_inventory` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总库存',
  `remaining_inventory` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '剩余库存',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改者ID',
  `is_del` tinyint(1) unsigned DEFAULT '0' COMMENT '0未删除 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='规格信息表';

-- ----------------------------
-- Table structure for m_spu
-- ----------------------------
DROP TABLE IF EXISTS `m_spu`;
CREATE TABLE `m_spu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `spu_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品编号',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `describe` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品描述 简介',
  `main_photo_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品主图',
  `shuffling_figure_path` json NOT NULL COMMENT '轮播图地址',
  `spu_class_id` int(11) unsigned NOT NULL COMMENT '商品分类ID',
  `spu_support_id` int(11) unsigned NOT NULL COMMENT '商品支持',
  `spu_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '销售状态 0 仓库中 1售卖中 2已售罄',
  `shelves_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '上架方式 1、立即售卖 2、指定时间上架 3、仓库中',
  `shelves_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '上架时间 架为指定时间是 不能为空',
  `shop_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '店铺ID',
  `is_completed` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '完成状态 0未完成 1已完成',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建人',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改者ID',
  `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_spu_status` (`spu_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品表';

-- ----------------------------
-- Table structure for m_spu_detail
-- ----------------------------
DROP TABLE IF EXISTS `m_spu_detail`;
CREATE TABLE `m_spu_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `spu_id` int(11) unsigned NOT NULL COMMENT '商品地',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '商品详情',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建人',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改者ID',
  `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品详情';

-- ----------------------------
-- Table structure for m_spu_detail_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `m_spu_detail_snapshot`;
CREATE TABLE `m_spu_detail_snapshot` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `spu_id` int(11) unsigned NOT NULL COMMENT '商品地',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '商品详情',
  `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品详情快照表';

-- ----------------------------
-- Table structure for m_spu_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `m_spu_snapshot`;
CREATE TABLE `m_spu_snapshot` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改时间',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `spu_id` int(11) NOT NULL COMMENT 'spu 商品ID',
  `sku_id` int(11) NOT NULL COMMENT 'sku_id',
  `sku_value_id` int(11) NOT NULL COMMENT '商品规格值ID',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `describe` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品描述 简介',
  `main_photo_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品主图',
  `shuffling_figure_path` json NOT NULL COMMENT '轮播图地址',
  `spu_class_id` int(11) unsigned NOT NULL COMMENT '商品分类ID',
  `spu_support_id` int(11) unsigned NOT NULL COMMENT '商品支持',
  `shop_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '店铺ID',
  `sku_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '规格名称',
  `sku_value_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '商品规格值名称',
  `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品快照表\n';

-- ----------------------------
-- Table structure for m_user_base
-- ----------------------------
DROP TABLE IF EXISTS `m_user_base`;
CREATE TABLE `m_user_base` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `phone` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `maill` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '邮箱',
  `password` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '昵称',
  `head_url` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `modified_time` int(10) unsigned DEFAULT '0' COMMENT '修改时间',
  `created_time` int(10) unsigned DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户基本信息表';

-- ----------------------------
-- Table structure for m_user_safe
-- ----------------------------
DROP TABLE IF EXISTS `m_user_safe`;
CREATE TABLE `m_user_safe` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
