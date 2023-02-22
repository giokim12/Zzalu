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
-- Table structure for table `gif_statistics`
--

DROP TABLE IF EXISTS `gif_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gif_statistics` (
  `gifs_statistics_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `download_count` bigint(20) DEFAULT NULL,
  `gif_id` bigint(20) DEFAULT NULL,
  `use_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`gifs_statistics_id`),
  UNIQUE KEY `UK_fopo2aw3ex22iokoym5y8dhr2` (`gif_id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gif_statistics`
--

LOCK TABLES `gif_statistics` WRITE;
/*!40000 ALTER TABLE `gif_statistics` DISABLE KEYS */;
INSERT INTO `gif_statistics` VALUES (1,3,4225,6),(2,0,4228,1),(3,1,4229,4),(4,2,4234,4),(5,1,4247,2),(6,0,4252,1),(7,2,4241,2),(8,0,4242,1),(9,0,4254,1),(10,4,2778,2),(11,3,12812,2),(12,2,10801,0),(13,2,11529,0),(14,1,2820,0),(15,2,2819,0),(16,2,2780,3),(17,2,2537,0),(18,1,10418,2),(19,1,10381,1),(20,4,10468,2),(21,2,2679,0),(22,1,2680,0),(23,1,2681,0),(24,1,2690,0),(25,3,2684,0),(26,2,1377,0),(27,3,2678,0),(28,1,2688,0),(29,1,2694,0),(30,1,2702,0),(31,1,2701,0),(32,1,2700,0),(33,2,4613,0),(34,1,12203,0),(35,1,5121,0),(36,2,12372,1),(37,1,11590,2),(38,1,1693,1),(39,0,473,1),(40,0,6643,1),(41,0,6763,1),(42,0,6773,1),(43,0,12134,1),(44,2,1317,2),(45,0,7167,1),(46,2,1319,4),(47,0,1345,1),(48,0,1336,1),(49,2,1327,2),(50,0,10806,1),(51,0,2779,1),(52,1,2781,1),(53,0,2782,1),(54,1,4337,1),(55,1,4338,0),(56,1,4342,1),(57,1,1416,0),(58,1,1417,1),(59,1,1419,0),(60,2,1421,2),(61,1,1418,0),(62,2,1425,1),(63,1,1426,3),(64,1,1439,1),(65,2,6762,1),(66,1,6765,1),(67,1,1445,0),(68,0,3084,1),(69,2,1455,2),(70,0,3085,1),(71,1,12320,0),(72,1,12567,0),(73,1,1324,0),(74,2,6767,0),(75,1,1792,1),(76,1,1462,2),(77,1,1531,1),(78,1,4271,3),(79,1,1326,0),(80,0,4035,1),(81,0,7687,1),(82,0,7712,1),(83,0,1769,1),(84,0,953,1),(85,0,7784,1),(86,0,9568,1),(87,0,6788,1),(88,0,866,1),(89,0,8309,1),(90,0,5780,1),(91,0,7089,1),(92,0,16,1),(93,2,7464,0),(94,1,6810,1),(95,0,6818,1),(96,1,6813,1),(97,1,6811,1),(98,1,1330,0),(99,1,1316,0),(100,1,12319,0),(101,0,10653,1),(102,1,1325,0),(103,1,1335,0),(104,1,1318,0),(105,1,1322,0),(106,1,6817,0),(107,1,6830,0),(108,1,6822,1),(109,1,7425,0),(110,1,7168,0),(111,1,6912,0),(112,1,6657,0),(113,1,6613,0),(114,1,6635,0),(115,1,6646,0),(116,1,6829,0),(117,1,6781,0),(118,1,6784,0),(119,1,6760,0),(120,1,6825,0),(121,1,6779,0),(122,1,6783,0),(123,1,6776,0),(124,1,6775,0),(125,1,6782,0),(126,1,6787,0),(127,1,6792,0),(128,0,769,1),(129,1,6632,0),(130,1,6688,0),(131,1,6661,0),(132,1,6780,0),(133,1,6785,0),(134,1,1495,0),(135,1,6671,0),(136,1,6764,0),(137,0,1726,1),(138,0,4237,1),(139,0,4249,1),(140,0,11001,1),(141,2,11106,1),(142,0,12053,1),(143,1,11520,0),(144,1,1476,0),(145,0,11130,1),(146,0,9542,1),(147,0,7461,1),(148,0,1018,1),(149,0,1043,1),(150,0,411,1),(151,0,445,1),(152,0,460,1),(153,0,1408,1),(154,0,1399,1),(155,0,1384,1),(156,0,9145,1),(157,0,12751,1),(158,0,10744,1);
/*!40000 ALTER TABLE `gif_statistics` ENABLE KEYS */;
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
