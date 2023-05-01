-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: storenewdata
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_created` varchar(255) DEFAULT NULL,
  `date_deleted` varchar(255) DEFAULT NULL,
  `description` text,
  `enabled` bit(1) NOT NULL,
  `inventory_status` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price` bigint NOT NULL,
  `productname` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `rate` int NOT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'2023/04/19 15:24:56',NULL,'bia',_binary '','INSTOCK','Da nang',285000,'Bia Huda',10,0,'Thung/Lon',1),(2,'2023/04/23 11:42:45',NULL,'bia',_binary '','OUTOFSTOCK','Da nang',0,'Bia Larue',0,0,'Thung/Lon',1),(3,'2023/04/23 11:43:45',NULL,'sữa tươi tiệt trùng',_binary '','OUTOFSTOCK','Da nang',0,'Sữa Vinamilk hộp',0,0,'Thung/loc/hop',3),(4,'2023/04/23 11:44:27',NULL,'Nước ngọt',_binary '','OUTOFSTOCK','Da nang',0,'Pepsi',0,0,'Thung/loc/lon',2),(5,'2023/04/23 11:44:56',NULL,'cocacola',_binary '','OUTOFSTOCK','Da nang',0,'CocaCola',0,0,'Thung/loc/lon',2),(6,'2023/04/23 11:45:39',NULL,'null',_binary '','OUTOFSTOCK','Da nang',0,'Bia Larue Smooth',0,0,'Thung/Lon',1),(7,'2023/04/23 11:46:12',NULL,'null',_binary '','OUTOFSTOCK','Da nang',0,'Bia Tiger',0,0,'Thung/lon',1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-01 21:14:45
