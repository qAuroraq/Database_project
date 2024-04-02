/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : 12306_3

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 18/06/2023 22:28:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `number` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`number`) USING BTREE,
  INDEX `pwd_index`(`password` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('4321', '123');
INSERT INTO `account` VALUES ('777', '123');
INSERT INTO `account` VALUES ('11', '12345');
INSERT INTO `account` VALUES ('12', '12345');
INSERT INTO `account` VALUES ('121', '12345');
INSERT INTO `account` VALUES ('121324', '12345');
INSERT INTO `account` VALUES ('123123', '12345');
INSERT INTO `account` VALUES ('1234567', '12345');
INSERT INTO `account` VALUES ('152', '12345');
INSERT INTO `account` VALUES ('1555', '12345');
INSERT INTO `account` VALUES ('1987', '12345');
INSERT INTO `account` VALUES ('66666', '12345');
INSERT INTO `account` VALUES ('admin', '123456');
INSERT INTO `account` VALUES ('321', '321');
INSERT INTO `account` VALUES ('123456', '555555');
INSERT INTO `account` VALUES ('7777', '7777');

-- ----------------------------
-- Table structure for passenger
-- ----------------------------
DROP TABLE IF EXISTS `passenger`;
CREATE TABLE `passenger`  (
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `id_card` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `age` smallint UNSIGNED NOT NULL,
  `sex` enum('男','女') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `number` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id_card`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of passenger
-- ----------------------------
INSERT INTO `passenger` VALUES ('1', '1', 13, '女', '7777');
INSERT INTO `passenger` VALUES ('yhs', '123', 18, '男', '777');
INSERT INTO `passenger` VALUES ('yhs', '123141', 19, '女', '121324');
INSERT INTO `passenger` VALUES ('fdfd', '123321', 1, '男', '1555');
INSERT INTO `passenger` VALUES ('hhxx', '123456', 18, '男', '123456');
INSERT INTO `passenger` VALUES ('gu', '12345678', 19, '男', '1234567');
INSERT INTO `passenger` VALUES ('mike', '321', 12, '男', '321');
INSERT INTO `passenger` VALUES ('李老师', '3333333333', 24, '男', '7777');
INSERT INTO `passenger` VALUES ('sss', '888', 18, '男', '4321');
INSERT INTO `passenger` VALUES ('yy', '99', 121, '男', '7777');
INSERT INTO `passenger` VALUES ('admin', 'admin', 21, '男', 'admin');

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `train_number` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `checi` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `id_card` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `src` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `dst` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `seat_type` enum('一等座','二等座','商务座','软卧') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `carriage_number` smallint UNSIGNED NOT NULL,
  `seat_num` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `ticket_type` enum('学生票','儿童票','全票') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `src_time` datetime NOT NULL,
  `dst_time` datetime NOT NULL,
  `train_type` enum('快车','特快','直达','高铁','动车') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`ticket_id`) USING BTREE,
  INDEX `id_card`(`id_card` ASC) USING BTREE,
  INDEX `train_number`(`train_number` ASC, `checi` ASC) USING BTREE,
  CONSTRAINT `车票_ibfk_1` FOREIGN KEY (`id_card`) REFERENCES `passenger` (`id_card`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ticket
-- ----------------------------
INSERT INTO `ticket` VALUES (31, '1', '3', '1', '北京', '上海', '二等座', 1, '1', '全票', '2022-01-01 16:32:02', '2022-01-02 16:32:22', '高铁');
INSERT INTO `ticket` VALUES (48, '1', '1', '123456', '北京', '上海', '二等座', 12, '5', '全票', '2022-01-01 21:44:02', '2022-01-02 21:44:06', '动车');
INSERT INTO `ticket` VALUES (49, '1', '3', '123456', '北京', '上海', '商务座', 1, '1', '全票', '2022-01-01 16:32:02', '2022-01-02 16:32:22', '高铁');
INSERT INTO `ticket` VALUES (61, '9', '9', '321', '上海', '北京', '二等座', 3, '3', '全票', '2023-06-06 02:02:00', '2023-06-14 02:01:00', '高铁');

-- ----------------------------
-- Table structure for ticket_manager
-- ----------------------------
DROP TABLE IF EXISTS `ticket_manager`;
CREATE TABLE `ticket_manager`  (
  `train_number` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `checi` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `src` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `src_time` datetime NOT NULL,
  `dst` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `dst_time` datetime NOT NULL,
  `seat_type` enum('一等座','二等座','商务座','软卧') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `train_type` enum('快车','特快','直达','高铁','动车') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `seat_number` smallint NOT NULL,
  `price` smallint NOT NULL,
  INDEX `train_number`(`train_number` ASC, `checi` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ticket_manager
-- ----------------------------
INSERT INTO `ticket_manager` VALUES ('11', '11', '北京', '2023-05-24 10:28:06', '上海', '2023-05-25 10:28:14', '二等座', '高铁', 8, 10);
INSERT INTO `ticket_manager` VALUES ('9', '9', '上海', '2023-06-06 21:00:00', '昆明', '2023-06-07 07:00:00', '商务座', '高铁', 20, 900);
INSERT INTO `ticket_manager` VALUES ('4', '5', '上海', '2023-06-06 21:00:00', '北京', '2023-06-07 07:00:00', '商务座', '高铁', 6, 500);
INSERT INTO `ticket_manager` VALUES ('6', '5', '上海', '2023-06-06 21:00:00', '北京', '2023-06-07 07:00:00', '商务座', '高铁', 20, 500);
INSERT INTO `ticket_manager` VALUES ('3', '3', '北京', '2023-06-13 00:00:00', '昆明', '2023-06-14 02:00:00', '一等座', '高铁', 30, 3);
INSERT INTO `ticket_manager` VALUES ('9', '9', '上海', '2023-06-06 02:02:00', '北京', '2023-06-14 02:01:00', '二等座', '高铁', 19, 30);

-- ----------------------------
-- View structure for ticket_information
-- ----------------------------
DROP VIEW IF EXISTS `ticket_information`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ticket_information` AS select distinct `ticket_manager`.`train_number` AS `train_number`,`ticket_manager`.`checi` AS `checi`,`ticket_manager`.`src` AS `src`,`ticket_manager`.`src_time` AS `src_time`,`ticket_manager`.`dst` AS `dst`,`ticket_manager`.`dst_time` AS `dst_time`,`ticket_manager`.`train_type` AS `train_type` from `ticket_manager`;

-- ----------------------------
-- Procedure structure for alter_password2
-- ----------------------------
DROP PROCEDURE IF EXISTS `alter_password2`;
delimiter ;;
CREATE PROCEDURE `alter_password2`(IN phone varchar(20),IN password varchar(30))
BEGIN
UPDATE account set password=password
WHERE number=phone;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for alter_pwd
-- ----------------------------
DROP PROCEDURE IF EXISTS `alter_pwd`;
delimiter ;;
CREATE PROCEDURE `alter_pwd`(IN number varchar(20),IN password varchar(30))
BEGIN
UPDATE account set password=password
WHERE number=number;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for drop_train
-- ----------------------------
DROP PROCEDURE IF EXISTS `drop_train`;
delimiter ;;
CREATE PROCEDURE `drop_train`(IN train_number varchar(20),IN checi varchar(10))
BEGIN
DELETE FROM ticket_manager
WHERE train_number=train_number and checi=checi;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for insert_account
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_account`;
delimiter ;;
CREATE PROCEDURE `insert_account`(IN number varchar(20),IN password varchar(30))
BEGIN
INSERT INTO account VALUES(number,password);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for insert_train
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_train`;
delimiter ;;
CREATE PROCEDURE `insert_train`(IN train_number varchar(50),IN checi varchar(20),IN src varchar(20),IN src_time datetime,IN dst varchar(20),IN dst_time datetime,IN seat_type enum('一等座','二等座','商务座','软卧'),IN train_type enum('快车','特快','直达','高铁','动车'),IN seat_number smallint,IN price smallint)
BEGIN
INSERT INTO ticket_manager VALUES(train_number,checi,src,src_time,dst,dst_time,seat_type,train_type,seat_number,price);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for query
-- ----------------------------
DROP PROCEDURE IF EXISTS `query`;
delimiter ;;
CREATE PROCEDURE `query`(IN id_card varchar(18))
BEGIN
SELECT ticket_id,src,dst,id_card
FROM ticket
where id_card=ticket.id_card;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for query1
-- ----------------------------
DROP PROCEDURE IF EXISTS `query1`;
delimiter ;;
CREATE PROCEDURE `query1`()
BEGIN
SELECT name,age,sex,src,dst
FROM passenger,ticket
WHERE passenger.id_card=ticket.id_card;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table ticket
-- ----------------------------
DROP TRIGGER IF EXISTS `buy_ticket`;
delimiter ;;
CREATE TRIGGER `buy_ticket` BEFORE INSERT ON `ticket` FOR EACH ROW BEGIN
SELECT seat_number FROM ticket_manager
WHERE NEW.train_number=ticket_manager.train_number
AND NEW.checi=ticket_manager.checi
AND NEW.src=ticket_manager.src
AND NEW.dst=ticket_manager.dst INTO @num;
IF @num=0 THEN
SIGNAL SQLSTATE'02000'SET MESSAGE_TEXT ='无票!';
ELSE
UPDATE ticket_manager SET seat_number=seat_number-1 WHERE NEW.train_number=ticket_manager.train_number
AND NEW.checi=ticket_manager.checi
AND NEW.src=ticket_manager.src
AND NEW.dst=ticket_manager.dst;
END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table ticket
-- ----------------------------
DROP TRIGGER IF EXISTS `cancel_ticket`;
delimiter ;;
CREATE TRIGGER `cancel_ticket` BEFORE DELETE ON `ticket` FOR EACH ROW BEGIN
UPDATE ticket_manager SET seat_number=seat_number+1 WHERE OLD.checi=ticket_manager.checi
AND OLD.train_number=ticket_manager.train_number
AND OLD.src=ticket_manager.src
AND OLD.dst=ticket_manager.dst
AND OLD.seat_type=ticket_manager.seat_type;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
