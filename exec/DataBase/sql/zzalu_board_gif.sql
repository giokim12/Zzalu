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
-- Table structure for table `board_gif`
--

DROP TABLE IF EXISTS `board_gif`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_gif` (
  `board_id` bigint(20) NOT NULL,
  `gif_id` bigint(20) NOT NULL,
  KEY `FKckt5490uigtg34y9hbp2vuwo7` (`gif_id`),
  KEY `FK8x14se6b6pgmr8le9fusg9ueu` (`board_id`),
  CONSTRAINT `FK8x14se6b6pgmr8le9fusg9ueu` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`),
  CONSTRAINT `FKckt5490uigtg34y9hbp2vuwo7` FOREIGN KEY (`gif_id`) REFERENCES `gifs` (`gif_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_gif`
--

LOCK TABLES `board_gif` WRITE;
/*!40000 ALTER TABLE `board_gif` DISABLE KEYS */;
INSERT INTO `board_gif` VALUES (3,2819),(3,4225),(3,2820),(4,2678),(4,2537),(6,2684),(8,4241),(10,4613),(11,12203),(13,4342),(13,4338),(13,4337),(17,6767),(17,6765),(14,1792),(14,1693),(20,2678),(20,2537),(5,6765),(5,6762),(5,2700),(5,2701),(5,2702),(5,2694),(5,2688),(5,2678),(5,1377),(5,2684),(5,2690),(5,2681),(5,2680),(16,1462),(16,1455),(16,1445),(16,1439),(16,1426),(16,1425),(16,1418),(16,1421),(16,1419),(16,1417),(16,1416),(1,4271),(1,11590),(1,12372),(1,4229),(1,4247),(1,4241),(1,4225),(1,4234),(19,1455),(19,1421),(19,1425),(22,7464),(7,10801),(7,11529),(7,12812),(7,2780),(7,2778),(7,2781),(23,10468),(24,1377),(18,1322),(18,1327),(18,1318),(18,1335),(18,1325),(18,12319),(18,1316),(18,1330),(18,1317),(18,1326),(18,1324),(18,12567),(18,12320),(18,1319),(21,1495),(21,1319),(21,1531),(15,4234),(15,12372),(15,4225),(9,11106),(9,10468),(9,2778),(9,12812),(27,1317),(27,1327),(26,11520),(26,2819),(25,1476),(25,6764),(25,6780),(25,6688),(25,6792),(25,6787),(25,6782),(25,7464),(25,6775),(25,6776),(25,6783),(25,6779),(25,6825),(25,6760),(25,6784),(25,6781),(25,6829),(25,6646),(25,6635),(25,6613),(25,6657),(25,6912),(25,6762),(25,7168),(25,7425),(25,6767),(25,6822),(25,6830),(25,6817),(25,6813),(25,6810),(25,6811),(2,10418),(2,2780),(2,11529),(2,10801),(2,12812),(2,2778);
/*!40000 ALTER TABLE `board_gif` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  5:23:27
