-- MySQL dump 10.13  Distrib 8.4.5, for Linux (x86_64)
--
-- Host: vps-1266bd01.vps.ovh.net    Database: bd_alcorteccino
-- ------------------------------------------------------
-- Server version	5.5.5-10.11.11-MariaDB-0+deb12u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `bd_alcorteccino`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `bd_alcorteccino` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `bd_alcorteccino`;

--
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_CLIENTE` int(11) DEFAULT NULL,
  `ID_HORARIO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_CLIENTE` (`ID_CLIENTE`),
  KEY `ID_HORARIO` (`ID_HORARIO`),
  CONSTRAINT `fk_horario` FOREIGN KEY (`ID_HORARIO`) REFERENCES `horario` (`ID`) ON DELETE SET NULL,
  CONSTRAINT `fk_usuario` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `usuario` (`ID`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (1,3,1),(10,16,19),(11,15,2),(26,3,23),(29,2,18),(30,3,22),(49,3,27),(54,42,35),(55,42,33),(56,43,29),(57,43,20),(58,43,32);
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `ID` int(11) NOT NULL,
  `DESCRIPCION` varchar(200) DEFAULT NULL,
  KEY `ID` (`ID`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `usuario` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (2,'Soy un seleccionador calvo'),(3,'Soy papadopoulos'),(4,'Soy un fontanero mayor'),(2,NULL),(3,NULL),(4,NULL),(9,NULL),(11,NULL),(13,NULL),(15,NULL),(16,NULL),(16,'Soy un piloto que conseguirá la 33'),(17,NULL),(17,'Soy un cabron '),(26,NULL),(32,NULL),(32,'pequeño enrea'),(37,'Jugador argentino'),(41,'maduro casado'),(42,'Los molinos man dao pal pelo');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horario`
--

DROP TABLE IF EXISTS `horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horario` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA` date DEFAULT NULL,
  `HORA` time DEFAULT NULL,
  `fecha_es` varchar(50) GENERATED ALWAYS AS (date_format(`FECHA`,'%d-%m-%Y')) VIRTUAL,
  `hora_es` varchar(50) GENERATED ALWAYS AS (date_format(`HORA`,'%H:%i:%s')) VIRTUAL,
  `ID_PERSONAL` int(11) NOT NULL,
  `ID_SERVICIO` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_SERVICIO` (`ID_SERVICIO`),
  KEY `ID_PERSONAL` (`ID_PERSONAL`),
  CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`ID_SERVICIO`) REFERENCES `servicios` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `horario_ibfk_2` FOREIGN KEY (`ID_PERSONAL`) REFERENCES `personal` (`ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario`
--

LOCK TABLES `horario` WRITE;
/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
INSERT INTO `horario` (`ID`, `FECHA`, `HORA`, `ID_PERSONAL`, `ID_SERVICIO`) VALUES (1,'2023-06-01','05:50:59',7,4),(2,'2023-05-31','13:30:00',8,3),(18,'2023-05-31','11:30:00',8,5),(19,'2023-05-31','14:30:00',12,3),(20,'2023-06-05','11:00:00',5,3),(21,'2023-06-08','13:00:00',8,1),(22,'2023-06-08','12:00:00',8,5),(23,'2023-06-12','18:30:00',8,3),(24,'2023-06-06','12:30:00',7,3),(25,'2023-06-07','16:00:00',7,2),(27,'2023-06-05','18:00:00',8,3),(29,'2023-06-07','11:30:00',31,3),(31,'2023-06-15','14:30:00',8,2),(32,'2023-06-20','17:00:00',10,2),(33,'2023-06-23','18:00:00',6,5),(34,'2023-06-26','11:00:00',5,2),(35,'2023-06-15','12:30:00',10,5);
/*!40000 ALTER TABLE `horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horarios_asignados`
--

DROP TABLE IF EXISTS `horarios_asignados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horarios_asignados` (
  `id_personal` int(11) DEFAULT NULL,
  `fecha_inicio_semana` date DEFAULT NULL,
  `fecha_fin_semana` date DEFAULT NULL,
  `dia_semana` int(11) DEFAULT NULL,
  `hora` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios_asignados`
--

LOCK TABLES `horarios_asignados` WRITE;
/*!40000 ALTER TABLE `horarios_asignados` DISABLE KEYS */;
INSERT INTO `horarios_asignados` VALUES (7,'2023-05-22','2023-05-28',6,'05:50:00'),(7,'2023-05-22','2023-05-28',1,'15:45:00'),(8,'2023-05-22','2023-05-28',5,'13:00:00'),(8,'2023-05-22','2023-05-28',6,'12:00:00'),(8,'2023-05-22','2023-05-28',5,'18:30:00'),(8,'2023-05-22','2023-05-28',3,'20:15:00'),(8,'2023-05-22','2023-05-28',2,'11:30:00'),(10,'2023-05-29','2023-06-04',3,'17:53:00'),(12,'2023-05-22','2023-05-28',2,'14:30:00'),(5,'2023-06-05','2023-06-11',1,'11:00:00');
/*!40000 ALTER TABLE `horarios_asignados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal`
--

DROP TABLE IF EXISTS `personal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personal` (
  `ID` int(11) NOT NULL,
  `TIPO` varchar(20) NOT NULL,
  `SALARIO` float(7,2) DEFAULT 0.00,
  KEY `ID` (`ID`),
  CONSTRAINT `personal_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `usuario` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal`
--

LOCK TABLES `personal` WRITE;
/*!40000 ALTER TABLE `personal` DISABLE KEYS */;
INSERT INTO `personal` VALUES (1,'admin',1700.80),(5,'Peluquero',1090.72),(5,'',0.00),(6,'Peluquero',1085.00),(7,'Manicurista',1020.00),(8,'Peluquero',1090.00),(10,'',0.00),(12,'Peluquero',1080.00),(14,'',0.00),(31,'',0.00),(31,'Peluquero',0.00),(39,'Peluquero',0.00),(40,'Esteticista',0.00);
