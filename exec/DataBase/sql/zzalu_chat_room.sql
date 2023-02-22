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
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_room` (
  `chat_room_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `enroll_date` datetime DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `last_activation` datetime DEFAULT NULL,
  `like_count` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `room_id` varchar(255) DEFAULT NULL,
  `room_name` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`chat_room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (1,'징짱이최고시다','2023-02-16 11:59:25','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/66b4c09a-6d1d-462f-b0bd-add9ccdf1255-IU%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg','2023-02-17 02:26:20',2,2,'02ecc5af-7b2b-44f3-b0a6-9e5fadd2b964','아이유','아이유,이지은,이지금,징짱','comet'),(2,'고양이가 세상을 지배한다','2023-02-16 14:50:32','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/5ff2d325-852e-4cfc-bdb1-0f72aab3c59c-%EA%B3%A0%EC%96%91%EC%9D%B4%EC%8D%B8%EB%84%A4%EC%9D%BC.gif','2023-02-16 22:40:20',2,4,'30b8b1cf-1600-444a-af52-3eef6553f207','고양이','고양이,냥이','내가김준영'),(3,'우!! 예쁜누나다~~~','2023-02-16 14:52:24','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/1ea28438-8a47-4382-bf60-6ca8661b41d1-1676526676421.gif','2023-02-16 20:18:24',2,7,'b61e759b-b7e4-43cb-95eb-6f511804fbac','짱구','짱구,유리,훈이,맹구,투니버스,추억의애니','김지오누구야'),(4,'90000회~ 이러다가 다~~ 쥬거~~!','2023-02-16 14:53:37','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/5d8bb016-2776-40e2-a3ae-a986876c6d21-1676526767866.jpg','2023-02-16 15:59:36',0,7,'ce193cc7-5542-46a2-842a-6713ccea6070','오징어게임','넷플릭스,TV,오징어게임,이정재,꿀잼','김지오누구야'),(5,'고양이보단 강아지지','2023-02-16 14:57:47','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/0dc1aea9-9a6c-43cc-951f-6a701a87dedb-%EA%B0%95%EC%95%84%EC%A7%80%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg','2023-02-16 15:59:35',2,4,'8f4aa64a-485c-4de0-bee6-9e9773eca0bf','강아지','댕댕이,강아지,멍멍이','내가김준영'),(6,'제발 제발 제발 제발 제발 최첨단과 샤머니즘이 공존한다','2023-02-16 15:02:12','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/95410569-e042-472b-8024-d92310e9b203-%EA%B0%9C%EB%B0%9C%EC%9E%90%20%EC%8D%B8%EB%84%A4%EC%9D%BC.jfif','2023-02-17 02:32:54',2,4,'0e10307a-7739-4c47-8b4d-6ecf02f71cc2','개발자','개발,컴퓨터는,거짓말을,종종,하더라','내가김준영'),(7,'너 내 동료가 돼라','2023-02-16 15:15:44','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/a0ad7d22-ddd0-4728-a257-7a26e41a661c-1676528080485.jpg','2023-02-16 16:33:38',0,3,'7dc28b14-2393-4408-bb69-a94ee504b34e','원피스','애니,원피스,루피','짤루사랑해♥'),(8,'무도 없이는 못사는 사람들, 딱 한 번만 잡솨봐~~~','2023-02-16 15:21:55','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/94c48313-2c12-4ede-9093-c4e864c890f6-49D99F06-C45F-451F-A995-05F1657F9B48.jpeg','2023-02-16 23:38:19',6,6,'7394074e-d619-4eeb-82ac-f25f87029c30','무한도전','무한도전,무도,박명수,박차장,무한상사,유재석,김태호,오호츠크해,가요제','최광창민'),(9,'BTS','2023-02-16 15:55:04',NULL,'2023-02-16 17:57:52',0,2,'7c7ff427-7f67-4811-ae50-f342ce9565b9','BTS','BTS,고독방','comet'),(10,'자유롭게 이용하세요!','2023-02-16 16:02:05','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/45e832ca-49e2-468d-ad6c-470adfbe786c-SE-2a660faf-ad95-4849-9a93-a658c5b67b85.jpg','2023-02-17 01:48:50',1,2,'c9cf179b-9ae5-41f6-acf3-905767eafbfa','자유고독방','모든,짤,사용가능,재미만,있으면,그만','comet'),(11,'포켓몬스터 고독방!','2023-02-16 16:06:26','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/145cbacb-54ff-4ec0-af15-026d4b648865-257D1B3A58AE7CEB15.gif','2023-02-16 19:08:39',0,2,'b19a21b4-a10a-429f-bf09-4fab0132ab45','포켓몬스터','포켓몬,귀여워','comet'),(12,'숭랑해','2023-02-16 16:48:21',NULL,'2023-02-17 01:49:30',0,9,'14b5718e-364b-4e27-8e1b-5cf6cb7af605','12월14일엔모두숭랑해를외쳐','원숭이','아!응앵'),(13,'고독방','2023-02-16 17:12:12',NULL,'2023-02-16 21:56:15',0,10,'618fe3d3-e594-4d5e-82a9-b73821fbcb7b','백현 고독방','백현','min'),(14,'달건이들 모여라','2023-02-16 23:06:54','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/5e4c60a5-c143-46c5-afa4-8568ac167080-20210608_60bf28e569961.gif','2023-02-16 23:09:57',0,6,'31c95bd5-f606-4008-bfc1-7dc84ccb5446','타짜','타짜,곽철용,마포대교','최광창민'),(15,'와! 마인크래프트 아시는구나!','2023-02-17 02:22:13','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/7b882210-2fdb-48ff-9578-318f9d69f5e7-%EB%A7%88%EC%9D%B8%ED%81%AC%EB%9E%98%ED%94%84%ED%8A%B8%EC%8D%B8%EB%84%A4%EC%9D%BC.png','2023-02-17 02:22:37',1,8,'d4e7ee0b-a7ae-49d3-9334-953e9e33493f','마인크래프트','게임,마크,잼민이,잼민이아님','챈챈리'),(16,'고오오급시계','2023-02-17 02:25:47','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/e6a980bb-d599-4af9-b122-81ef7d2ead8e-%EC%98%A4%EB%B2%84%EC%9B%8C%EC%B9%98%EC%8D%B8%EB%84%A4%EC%9D%BC.png','2023-02-17 02:27:56',1,8,'18ae0a1d-0a9a-416f-a197-59f20ff4724e','오버워치','게임,오버워치,고급시계','챈챈리'),(17,'나도 진구하고싶다.. 도라에몽~~~ 도와줘~~~~','2023-02-17 02:30:00','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/BasicImage/6564a97d-0f94-422e-ac77-a4aadd2ea788-%EB%8F%84%EB%9D%BC%EC%97%90%EB%AA%BD%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg','2023-02-17 02:32:35',1,8,'876003f9-47ac-450e-84ba-b1d94933827b','도라에몽','도라에몽,진구,도라미','챈챈리');
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  5:23:31
