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
-- Table structure for table `temp_gif`
--

DROP TABLE IF EXISTS `temp_gif`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `temp_gif` (
  `temp_gif_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `gif_path` varchar(255) DEFAULT NULL,
  `origin_gifs_id` bigint(20) DEFAULT NULL,
  `permitted_count` int(11) DEFAULT NULL,
  `relations_video` varchar(255) DEFAULT NULL,
  `request_type` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `writer_username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`temp_gif_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temp_gif`
--

LOCK TABLES `temp_gif` WRITE;
/*!40000 ALTER TABLE `temp_gif` DISABLE KEYS */;
INSERT INTO `temp_gif` VALUES (1,NULL,NULL,4241,1,NULL,'U','연예인,아이돌,아이유,가수','c109'),(2,'내가 달건이 생활을 열일곱에 시작했다. 그 나이때 달건이 시작한 놈이 백명이라 치면은 지금 나만큼 사는 놈은 나 혼자 뿐이야. 나는 어떻게 여기까지 왔느냐? 잘난 놈 제끼고 못난 놈 보내고. 안경잽이같이 배신하는 새끼들 다 죽였다.',NULL,12203,0,'https://youtu.be/DDfWAb6OG78','U','곽철용,타짜,김응수,마포대교,담배,하나,찔러봐,고니야','nana'),(3,NULL,NULL,4613,0,NULL,'U','연예인,아이돌,소녀시대,가수,윤아','c109'),(4,NULL,NULL,2537,0,NULL,'U','애니,스폰지밥,스폰지','ats166'),(9,NULL,'https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TempGif/8e163e91-33c2-445e-a9c7-87991f1e42a0-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C%ED%8C%8C%EC%9D%BC.gif',NULL,0,NULL,'C','애니,스폰지밥','gio'),(11,NULL,'https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TempGif/3bba0e2e-be2e-4de4-bdf3-582a39b18ca0-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C%ED%8C%8C%EC%9D%BC.gif',NULL,2,NULL,'C','애니,스폰지밥','gio'),(14,'아이유 팔레트 뮤비 2:54 부분',NULL,4234,1,'https://www.youtube.com/watch?v=d9IxdwEFk1c','U','연예인,아이돌,아이유,팔레트','ats166'),(15,'아이유 팔레트 뮤비 2분 55초',NULL,4234,1,NULL,'U','연예인,아이돌,아이유,가수','c109'),(17,'뉴진스 Ateention 공식 뮤비 2분38초 ','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TempGif/a2b9719a-d773-4fad-8225-aa9249bec6b6-img.gif',NULL,0,'https://www.youtube.com/watch?v=js1CtxSY38I&ab_channel=HYBELABELS','C','뉴진스,하니,Attention,연예인,아이돌','ats166'),(18,'팔레트 뮤비 2분 55초',NULL,4234,0,NULL,'U','연예인,아이돌,아이유,가수','c109'),(21,'NCT 지성이라는 맴버의 과거 영상..','https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TempGif/0e6d9b80-b944-474f-9c48-3f59f693ca66-DisfiguredGeneralAffenpinscher-size_restricted.gif',NULL,0,'https://youtu.be/pAvJuODBLFg','C','웃다가,생각해보니,웃을때가,아님,NCT,지성','nana'),(36,NULL,'https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TempGif/a60da1c5-f9a8-42ab-86a5-611d0c749d49-chaen.gif',NULL,0,NULL,'C','동물','chaen'),(37,NULL,'https://zzalu-buket.s3.ap-northeast-2.amazonaws.com/TempGif/af8e3bb2-3156-495e-9bfd-13e50aa9a706-%EA%B2%81%EC%9F%81%EC%9D%B4%EC%95%8C%ED%8C%8C%EC%B9%B4.gif',NULL,0,NULL,'C','동물','chaen'),(38,'무한상사 2012 그랬구나 박명수 vs 정준하',NULL,11106,0,NULL,'U','아,그랬구나,무한도전,박명수,무한상사,감흥,없는,이야기,수긍할때,의미없다,움짤','nana');
/*!40000 ALTER TABLE `temp_gif` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  5:23:30
