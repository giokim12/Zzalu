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
-- Table structure for table `title_hakwon`
--

DROP TABLE IF EXISTS `title_hakwon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `title_hakwon` (
  `titlehakwon_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `open_date` varchar(255) NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  `zzal_url` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`titlehakwon_id`),
  UNIQUE KEY `UK_lqyq8vqd1m96eq4dqr0d8oc0o` (`open_date`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `title_hakwon`
--

LOCK TABLES `title_hakwon` WRITE;
/*!40000 ALTER TABLE `title_hakwon` DISABLE KEYS */;
INSERT INTO `title_hakwon` VALUES (1,'2023-02-13','D','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TitleHakwon/%EC%95%BD%EC%98%A4%EB%A5%B4%EC%A7%80.gif'),(2,'2023-02-14','D','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TitleHakwon/%EB%B0%80%EC%96%B4%EC%84%9C%EC%9E%A0%EA%B8%88%ED%95%B4%EC%A0%9C.gif'),(3,'2023-02-15','D','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TitleHakwon/%EB%AD%90%EB%A5%BC%EA%B3%A0%EB%A5%BC%EA%B9%8C%EC%9A%94.gif'),(4,'2023-02-16','P','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TitleHakwon/%EB%B9%84%EB%91%98%EA%B8%B0%EC%A7%A4.gif'),(5,'2023-02-17','N','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TitleHakwon/%EB%8B%A4%EB%A5%B8%EA%B1%B8%EA%B3%A0%EC%B9%98%EB%8B%88%ED%84%B0%EC%A7%90.gif');
/*!40000 ALTER TABLE `title_hakwon` ENABLE KEYS */;
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
