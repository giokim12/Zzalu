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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `is_updated` bit(1) NOT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `like_num` int(11) NOT NULL DEFAULT 0,
  `member_id` bigint(20) DEFAULT NULL,
  `titlehakwon_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKmrrrpi513ssu63i2783jyiv9m` (`member_id`),
  KEY `FKm2p4khhciy65aeyj35c6k571w` (`titlehakwon_id`),
  CONSTRAINT `FKm2p4khhciy65aeyj35c6k571w` FOREIGN KEY (`titlehakwon_id`) REFERENCES `title_hakwon` (`titlehakwon_id`),
  CONSTRAINT `FKmrrrpi513ssu63i2783jyiv9m` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'내 코드 같네','2023-02-16 14:41:20',_binary '\0','2023-02-17 04:47:49',5,1,4),(2,'돌아가면 그만','2023-02-16 15:29:35',_binary '\0','2023-02-16 23:48:19',9,3,4),(4,'발상의전환','2023-02-16 19:24:20',_binary '\0','2023-02-17 01:34:33',11,3,4),(6,'야. 형보고 잘 배워','2023-02-13 14:14:20',_binary '\0',NULL,21,3,1),(8,'그거 밀어서 잠금해제야','2023-02-14 14:14:20',_binary '\0',NULL,14,1,2),(9,'어느것을 고를까요','2023-02-15 14:14:20',_binary '\0',NULL,17,8,3),(11,'확인확인','2023-02-16 21:55:40',_binary '\0','2023-02-16 21:55:40',0,4,4),(12,'제발 날기라도해라','2023-02-16 21:56:20',_binary '\0','2023-02-16 21:56:20',0,8,4),(13,'흠...??','2023-02-16 21:58:48',_binary '\0','2023-02-16 21:58:48',0,8,4),(14,'앵','2023-02-16 21:59:32',_binary '\0','2023-02-16 21:59:32',0,8,4),(16,'ㅇㅇㅇㅇ','2023-02-16 22:02:30',_binary '\0','2023-02-16 22:02:30',0,8,4),(29,'312412512','2023-02-16 22:05:46',_binary '\0','2023-02-16 22:05:46',0,4,4),(33,'11111','2023-02-16 22:06:21',_binary '\0','2023-02-16 22:06:21',0,2,4),(37,'비보잉 고수','2023-02-13 14:14:20',_binary '\0',NULL,7,1,1),(38,'추락하지마라','2023-02-16 22:16:43',_binary '\0','2023-02-16 22:32:48',0,1,4),(39,'야호','2023-02-16 22:40:19',_binary '\0','2023-02-16 23:13:30',0,2,4),(40,'빌드','2023-02-16 22:40:36',_binary '\0','2023-02-16 22:40:36',0,4,4),(41,'비비빌드','2023-02-16 22:41:11',_binary '\0','2023-02-16 22:41:11',0,4,4),(43,'ㅎㅇ','2023-02-16 22:42:12',_binary '\0','2023-02-16 22:42:12',0,2,4),(44,'ㅇㅋ 굿','2023-02-16 22:42:20',_binary '\0','2023-02-16 22:42:20',0,2,4),(46,'안된다고','2023-02-16 22:42:23',_binary '\0','2023-02-16 22:42:23',0,8,4),(47,'친구한테 밀어서 잠금해제 라고 했더니 5분 째 저런상태','2023-02-14 14:14:20',_binary '\0',NULL,16,1,2),(48,'발상','2023-02-16 23:12:44',_binary '\0','2023-02-16 23:12:44',0,4,4),(49,'형 하는거 잘봐.','2023-02-13 14:14:20',_binary '\0',NULL,3,2,1),(50,'돈 벌기 힘드네','2023-02-13 14:14:20',_binary '\0',NULL,1,6,1),(52,'빨리 동영상 틀어줘!!!','2023-02-14 14:14:20',_binary '\0',NULL,1,7,2),(53,'옐로우 카드 개꿀ㅋㅋ','2023-02-15 14:14:20',_binary '\0',NULL,1,1,3),(54,'야 이리와서 골라봐','2023-02-15 14:14:20',_binary '\0',NULL,6,3,3),(55,'돌고 돌아 에러','2023-02-17 00:54:02',_binary '\0','2023-02-17 00:54:02',0,8,4),(56,'자니','2023-02-17 01:12:08',_binary '\0','2023-02-17 01:12:08',0,8,4),(57,'여보세요 나야...','2023-02-17 01:24:08',_binary '\0','2023-02-17 01:24:08',0,4,4),(58,'거기 잘... 지내니','2023-02-17 01:24:15',_binary '\0','2023-02-17 01:24:15',0,4,4),(59,'여보세요 왜 말 안하니','2023-02-17 01:24:26',_binary '\0','2023-02-17 01:24:26',0,4,4),(60,'ㅎㅇㅇ','2023-02-17 01:36:45',_binary '\0','2023-02-17 01:36:45',0,2,4),(62,'하','2023-02-17 01:41:34',_binary '\0','2023-02-17 01:41:34',0,8,4),(63,'ㅎㅇ','2023-02-17 02:12:27',_binary '\0','2023-02-17 02:12:27',0,2,4),(64,'혹시모를 기대감','2023-02-17 02:12:43',_binary '\0','2023-02-17 02:12:43',0,8,4),(65,'ㅎㅇ','2023-02-17 02:12:51',_binary '\0','2023-02-17 02:12:51',0,3,4),(66,'ㅎㅎㅎㅎ','2023-02-17 02:13:08',_binary '\0','2023-02-17 02:13:08',0,2,4),(67,'댓글!','2023-02-17 02:18:18',_binary '\0','2023-02-17 02:18:18',0,2,4),(68,'어제의 우리!','2023-02-17 02:25:30',_binary '\0','2023-02-17 02:25:30',0,2,4);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
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
