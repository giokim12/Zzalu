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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board` (
  `board_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `board_name` varchar(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`board_id`),
  KEY `FKsds8ox89wwf6aihinar49rmfy` (`member_id`),
  CONSTRAINT `FKsds8ox89wwf6aihinar49rmfy` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'아이유','2023-02-16 13:04:55',2),(2,'무한도전','2023-02-16 14:17:54',3),(3,'ㅇㅎㅇㅎ','2023-02-16 14:20:40',3),(4,'스폰지밥','2023-02-16 14:22:35',3),(5,'이것저것','2023-02-16 14:30:49',3),(6,'웃긴','2023-02-16 14:40:55',3),(7,'무한도전','2023-02-16 14:51:17',4),(8,'아이유','2023-02-16 14:51:53',6),(9,'무한도전','2023-02-16 14:52:09',6),(10,'소시','2023-02-16 14:53:00',6),(11,'타짜','2023-02-16 14:53:51',6),(13,'BTS','2023-02-16 15:53:48',2),(14,'원피스','2023-02-16 15:55:08',4),(15,'아이돌','2023-02-16 15:55:19',4),(16,'포켓몬스터','2023-02-16 15:55:55',2),(17,'강아지','2023-02-16 15:58:42',3),(18,'짱구','2023-02-16 16:00:21',4),(19,'포켓몬스터','2023-02-16 16:00:45',4),(20,'스폰지밥','2023-02-16 16:01:10',4),(21,'나머지','2023-02-16 16:30:19',2),(22,'ㅋㅋㅅㄱㅇ','2023-02-16 16:49:01',9),(23,'무한도전','2023-02-16 17:56:24',2),(24,'도라에몽','2023-02-16 19:09:03',4),(25,'감사합니다','2023-02-16 19:59:38',4),(26,'유머','2023-02-16 23:07:41',6),(27,'애니','2023-02-16 23:08:08',6);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  5:23:32
