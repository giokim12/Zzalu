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
-- Table structure for table `com_samsamoo_zzalu_gifs_entity_giphy_gifs`
--

DROP TABLE IF EXISTS `com_samsamoo_zzalu_gifs_entity_giphy_gifs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `com_samsamoo_zzalu_gifs_entity_giphy_gifs` (
  `gif_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `download_count` bigint(20) NOT NULL,
  `gif_path` varchar(255) NOT NULL,
  `import_datetime` varchar(255) DEFAULT NULL,
  `like_count` bigint(20) NOT NULL,
  `relations_video` varchar(255) DEFAULT NULL,
  `scrap_count` bigint(20) NOT NULL,
  `soruces` varchar(255) DEFAULT NULL,
  `soruces_post_url` varchar(255) DEFAULT NULL,
  `soruces_tld` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `use_count` bigint(20) NOT NULL,
  `visited_count` bigint(20) NOT NULL,
  PRIMARY KEY (`gif_id`),
  UNIQUE KEY `UK_29m5c1259whr2eot005911ecc` (`gif_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `com_samsamoo_zzalu_gifs_entity_giphy_gifs`
--

LOCK TABLES `com_samsamoo_zzalu_gifs_entity_giphy_gifs` WRITE;
/*!40000 ALTER TABLE `com_samsamoo_zzalu_gifs_entity_giphy_gifs` DISABLE KEYS */;
/*!40000 ALTER TABLE `com_samsamoo_zzalu_gifs_entity_giphy_gifs` ENABLE KEYS */;
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
