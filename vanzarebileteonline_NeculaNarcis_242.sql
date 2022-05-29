CREATE DATABASE  IF NOT EXISTS `vanzarebileteonline` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `vanzarebileteonline`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: vanzarebileteonline
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eventName` varchar(100) NOT NULL,
  `eventType` varchar(45) NOT NULL,
  `author` varchar(45) DEFAULT NULL,
  `bookTitle` varchar(100) DEFAULT NULL,
  `hasBookRead` tinyint DEFAULT NULL,
  `price` double DEFAULT NULL,
  `ticketsLeft` int DEFAULT NULL,
  `prize` varchar(100) DEFAULT NULL,
  `artists` varchar(300) DEFAULT NULL,
  `listOfPlays` varchar(300) DEFAULT NULL,
  `groupName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (2,'BookSigning_1','BookSigning','Marian','Viata la tara',1,NULL,NULL,NULL,NULL,NULL,NULL),(3,'MusicConcert_1','MusicConcert',NULL,NULL,NULL,123,95,NULL,'Loaoa,banajs,asdasd',NULL,NULL),(4,'Play_1','Play',NULL,NULL,NULL,35,86,NULL,NULL,'play1,play2,play3','Papap'),(11,'asd','CarMeet',NULL,NULL,NULL,12,1212,'sdcgdf',NULL,NULL,NULL),(12,'q','CarMeet',NULL,NULL,NULL,12,123,'asdf',NULL,NULL,NULL);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `dateTime` varchar(45) DEFAULT NULL,
  `isOutside` tinyint DEFAULT NULL,
  `eventId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `location_event_idx` (`eventId`),
  CONSTRAINT `location_event` FOREIGN KEY (`eventId`) REFERENCES `events` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (2,'Library I','address library I','Feb 15, 2020 20:00',0,2),(3,'Concert 1','address concert 1','Jun 15, 2020 20:00',1,3),(4,'PLay 1','address play 1','Dec 15, 2020 20:00',1,4),(6,'qqq','qqqqqqqqqqq','Feb 08, 2001 20:00',1,12);
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `noTickets` int NOT NULL,
  `ticketId` int NOT NULL,
  `customerName` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tickets_order_idx` (`ticketId`),
  CONSTRAINT `tickets_order` FOREIGN KEY (`ticketId`) REFERENCES `tickets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,10,1,'Name'),(2,10,1,'Name'),(4,1,1,'lal'),(8,1,2,'nope'),(9,1,28,'Play_1'),(10,1,28,'Play_1'),(11,1,44,'BookSigning_1'),(12,1,44,'BookSigning_1'),(13,1,27,'MusicConcert_1');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eventName` varchar(100) NOT NULL,
  `eventPrice` double DEFAULT NULL,
  `location` varchar(100) NOT NULL,
  `time` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,'nsam',0,'22','sasas'),(2,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(3,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(4,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(5,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(6,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(7,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(8,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(9,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(10,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(11,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(12,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(13,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(14,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(15,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(16,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(17,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(18,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(19,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(20,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(21,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(22,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(23,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(24,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(25,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(26,'CarMeet_1',20,'Parking lot 123','2019-01-15T20:00'),(27,'MusicConcert_1',123,'Concert 1','2020-06-15T20:00'),(28,'Play_1',35,'PLay 1','2020-12-15T20:00'),(29,'Play_1',35,'PLay 1','2020-12-15T20:00'),(30,'MusicConcert_1',123,'Concert 1','2020-06-15T20:00'),(31,'Play_1',35,'PLay 1','2020-12-15T20:00'),(32,'MusicConcert_1',123,'Concert 1','2020-06-15T20:00'),(33,'Play_1',35,'PLay 1','2020-12-15T20:00'),(34,'Play_1',35,'PLay 1','2020-12-15T20:00'),(35,'Play_1',35,'PLay 1','2020-12-15T20:00'),(36,'Play_1',35,'PLay 1','2020-12-15T20:00'),(37,'Play_1',35,'PLay 1','2020-12-15T20:00'),(38,'Play_1',35,'PLay 1','2020-12-15T20:00'),(39,'Play_1',35,'PLay 1','2020-12-15T20:00'),(40,'Play_1',35,'PLay 1','2020-12-15T20:00'),(41,'Play_1',35,'PLay 1','2020-12-15T20:00'),(42,'Play_1',35,'PLay 1','2020-12-15T20:00'),(43,'Play_1',35,'PLay 1','2020-12-15T20:00'),(44,'BookSigning_1',0,'Library I','2020-02-15T20:00'),(45,'BookSigning_1',0,'Library I','2020-02-15T20:00'),(46,'BookSigning_1',0,'Library I','2020-02-15T20:00'),(47,'MusicConcert_1',123,'Concert 1','2020-06-15T20:00'),(48,'MusicConcert_1',123,'Concert 1','2020-06-15T20:00');
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Here we have the two types of users: Customers that can only place orders and see the history of theyr orders, and the admin which can add and edit and do whatever with customers, orders, items that are being sold, etc';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-29 15:11:14
