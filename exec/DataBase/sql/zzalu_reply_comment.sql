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
-- Table structure for table `reply_comment`
--

DROP TABLE IF EXISTS `reply_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reply_comment` (
  `coment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `is_updated` bit(1) NOT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`coment_id`),
  KEY `FKhm147r16wx7o65tipq91dmmvd` (`member_id`),
  KEY `FKg15haukikje39gy4k9u6exh2i` (`parent_id`),
  CONSTRAINT `FKg15haukikje39gy4k9u6exh2i` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`comment_id`),
  CONSTRAINT `FKhm147r16wx7o65tipq91dmmvd` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply_comment`
--

LOCK TABLES `reply_comment` WRITE;
/*!40000 ALTER TABLE `reply_comment` DISABLE KEYS */;
INSERT INTO `reply_comment` VALUES (1,'ㅋㅋㅋㅋ','2023-02-16 19:25:38',_binary '\0','2023-02-16 19:25:38',4,4),(2,'진짜 공감...','2023-02-16 19:28:14',_binary '\0','2023-02-16 19:28:14',4,1),(4,'ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ','2023-02-16 22:11:52',_binary '\0','2023-02-16 22:11:52',1,4),(5,'ㅋㅋㅋㅋㅋ','2023-02-17 01:01:27',_binary '\0','2023-02-17 01:01:27',3,55),(6,'ㅋㅋㅋㅋ','2023-02-17 02:18:25',_binary '\0','2023-02-17 02:18:25',3,66),(7,'console.log','2023-02-17 02:19:09',_binary '\0','2023-02-17 02:19:09',3,1),(8,'aaaaa','2023-02-17 02:24:59',_binary '\0','2023-02-17 02:24:59',3,66),(9,'재밋다','2023-02-17 02:25:36',_binary '\0','2023-02-17 02:25:36',3,66);
/*!40000 ALTER TABLE `reply_comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  5:23:29
