-- MySQL dump 10.13  Distrib 5.7.29, for Win64 (x86_64)
--
-- Host: 13.125.11.33    Database: zzalu
-- ------------------------------------------------------
-- Server version	5.5.5-10.10.2-MariaDB-1:10.10.2+maria~ubu2204

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `member_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `enroll_date` datetime DEFAULT NULL,
  `nickname` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `permitted_count` int(11) NOT NULL,
  `profile_message` varchar(255) DEFAULT NULL,
  `profile_path` varchar(255) DEFAULT NULL,
  `user_email` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `UK_gc3jmn7c2abyo3wf6syln5t2i` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,_binary '',_binary '',_binary '','2023-02-15 22:35:51','금붕어','$2a$10$8xjPA/1wO.p5yfZt9VWFmOKWTehs3LfTJMSKFrbdLxOJA8Vg.dhGW',5,'1등 10회 수상 목표','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/MemberProfile/be490982-2ae7-4845-a327-4bf53bf8b73b-KakaoTalk_20230216_202621866.jpg','canbe159@gmail.com','canbe159'),(2,_binary '',_binary '',_binary '','2023-02-16 11:53:54','comet','$2a$10$KHT3mCZuVeJz.BhaXHJpd.urR96i0wuEMKWPkMXi0PwSWrNoDgxYu',5,NULL,'https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/MemberProfile/3265214d-ab3c-40d5-a764-1b0867c3d648-1676265055706.jpg','khs971119@naver.com','fortuna3co'),(3,_binary '',_binary '',_binary '','2023-02-16 12:47:10','짤루사랑해♥','$2a$10$aEqiOgi6904P76fqUV3Gc.147Zt4vo7sSe4UxrFJSGowavs04DVt.',5,'짤루 많관부~~!!!','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/MemberProfile/0f2b1d70-3df3-48c1-a4bf-bb54e6fc737b-1676527678301.jpg','giokim12@naver.com','c109'),(4,_binary '',_binary '',_binary '','2023-02-16 13:44:38','내가김준영','$2a$10$0lJEBz6yboVTRXVhG.7/1e4C76gSf67/8dksz3etr62.5bRWXZT36',5,NULL,'https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/MemberProfile/936a2b89-cbac-4925-8b96-6d500166bf56-zzzz.gif','ats166@naver.com','ats166'),(5,_binary '',_binary '',_binary '','2023-02-16 14:03:09','테스트용어드민','$2a$10$MyKH35FHVskjogCMNFupJebJDva5Fd.iYJ7UAQba3kmVymeoffOze',5,NULL,NULL,'giokim1011@gmail.com','admin'),(6,_binary '',_binary '',_binary '','2023-02-16 14:42:47','최광창민','$2a$10$E/5Qg6X0iY6kCDXfkjkmkO0h0yFdXz9/TFHrAWCNRbVOF74ucgF2G',5,'개발이 하고 싶니?','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/MemberProfile/37b34311-c6d4-4037-b32e-0965364c2986-AB4D7532-A3D3-49D9-8839-6DE2408647F0.jpeg','logbymoon@naver.com','nana'),(7,_binary '',_binary '',_binary '','2023-02-16 14:47:31','김지오누구야','$2a$10$jGgUahAMAraDNpLy2pygXe//zS0YqWME8JyqdV/vRl7SGwjVroDw.',5,NULL,'https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/MemberProfile/b24f8a08-febc-4553-8816-97a94f4aa640-1676527678301.jpg','giokim1011@naver.com','gio'),(8,_binary '',_binary '',_binary '','2023-02-16 15:08:03','챈챈리','$2a$10$1FqXs9l6cjUErQuIwErj2.YJT8QHJ5hs6eBrIvvPHStvtClJRrPAW',5,'신난다 신나!','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/MemberProfile/98071c5d-f753-4c9a-93a4-cdd5b2a6ae96-chaen.gif','codms1104@naver.com','chaen'),(9,_binary '',_binary '',_binary '','2023-02-16 16:46:10','아!응앵','$2a$10$HDaXRJIzbJkZlCjXsmjCeeJI.mKUWcqOXZ5kByhmHa73j8HdiI/zi',0,NULL,NULL,'m5nacar5n@gmail.com','ssafy'),(10,_binary '',_binary '',_binary '','2023-02-16 16:53:32','min','$2a$10$ELq1p0zosn9hDbcii94i..xrnPibSnKNG1Va09kFo3ioWOkXnGMH6',5,NULL,NULL,'yeokyung502@naver.com','dong'),(11,_binary '',_binary '',_binary '','2023-02-16 21:52:44','백현부인','$2a$10$jKSwesBgxXrdx5f1EzdRC.kHtcm6ukwliDasa1UqAy.mIAXWFRD6e',0,NULL,NULL,'jiae2315@naver.com','jiae1');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  5:23:28
