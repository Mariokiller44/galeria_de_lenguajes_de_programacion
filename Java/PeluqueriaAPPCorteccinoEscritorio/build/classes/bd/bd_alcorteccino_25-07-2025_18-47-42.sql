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
/*!40000 ALTER TABLE `personal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(200) DEFAULT NULL,
  `STOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Peine',20),(2,'Maquina de cortar el pelo',22),(3,'Champú',63),(4,'Gelacondicionador',40),(5,'Secadora',9),(6,'Tinterojo',10),(7,'Tintenegro',40),(8,'Tintemarron',35),(10,'Cera',25),(11,'Laca',20),(12,'Lima',20);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicios`
--

DROP TABLE IF EXISTS `servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicios` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPCION` varchar(100) DEFAULT NULL,
  `PRODUCTO_ID` int(11) NOT NULL,
  `PRECIO` float(4,2) DEFAULT 0.00,
  PRIMARY KEY (`ID`),
  KEY `PRODUCTO_ID` (`PRODUCTO_ID`),
  CONSTRAINT `servicios_ibfk_1` FOREIGN KEY (`PRODUCTO_ID`) REFERENCES `productos` (`ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicios`
--

LOCK TABLES `servicios` WRITE;
/*!40000 ALTER TABLE `servicios` DISABLE KEYS */;
INSERT INTO `servicios` VALUES (1,'Secar el pelo',5,2.00),(2,'Peinar',1,15.00),(3,'Lavar',3,4.00),(4,'Limar uñas',12,21.00),(5,'Teñir de castaño',8,60.00),(6,'Teñir de rojo',6,54.50);
/*!40000 ALTER TABLE `servicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(200) DEFAULT NULL,
  `APELLIDOS` varchar(300) DEFAULT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `TELEFONO` int(9) DEFAULT NULL,
  `CUENTA` varchar(30) NOT NULL,
  `CONTRASENIA` varchar(100) NOT NULL,
  `TIPO_DE_USUARIO` varchar(8) NOT NULL DEFAULT 'Cliente',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Administrador','Mario','admin@alcortccino.es',635123152,'SuperUser','16e5862b1e21ae44d5f5ca30e7e6e466','Personal'),(2,'Luis','De La Fuente','luis@delafu.es',345678323,'LuisSelEsp','5e9372c2b32bb0bbbf20c43f2d393f6c','Cliente'),(3,'Paco Antonio','Gimenez Fernandez','pacogimenez@gmail.com',622442133,'pjfernandez002','70d40d48b09ef45e6aded4fda74831fe','Cliente'),(4,'Mario','Mario','mario@nintendo.jp',192955627,'MarioBrosRojo','073b7e4ff1d153a52b07eadb5898cb92','Cliente'),(5,'Raluka','Rusa','ralukalarusa@gmail.com',682243215,'RalukitaPh','060ec779956c086f27fa2913430925d3','Personal'),(6,'Carlos','Rodríguez Pérez','carlos.rodriguezperez@gmail.com',666555444,'carlosrp','1ddaf5837e09cfd2fc5bb086d347f5a1','Personal'),(7,'Ana','González Hernández','ana.gonzalezh@gmail.com',551234567,'agonzalezh','404f0e7437da485c6c5ae055ab6974de','Personal'),(8,'Miguel','García López','miguel.garcialopez@hotmail.com',212555212,'miguelgl','c4356ca3fc1945b0b30d758c58128c05','Personal'),(9,'Laura','Fernández Martínez','laura.fernandezm@gmail.com',412345678,'lfernandezm','23feaef1c5d75f4b33d3386586b3c550','Cliente'),(10,'José','Sánchez García','jose.sanchezg@hotmail.com',55551212,'josesg','29e645b871b6905c46ea191ed348a6f7','Personal'),(11,'Marta','Jiménez Pérez','marta.jimenezp@gmail.com',666444333,'martajp','b2403fcd129451cb9943ab5ce15e9b69','Cliente'),(12,'Juan','Rodríguez Sánchez','juan.rodriguezs@gmail.com',598765432,'jrodriguezs','eb2da899f402e797167bfce49cf970aa','Personal'),(13,'Sofía','Torres Hernández','sofia.torresh@gmail.com',125551234,'sofiath','3c393ab8f6bde45a65e19963a9a7d163','Cliente'),(14,'Daniel','Pérez Sánchez','daniel.perezs@hotmail.com',87654321,'danielps','22dab7886ca6fb8417bd4474bc52168a','Personal'),(15,'Andrea','García Martínez','andrea.garciam@gmail.com',505551234,'andreagm','efeaad537d38938e80d5ac8ae7adc8e3','Cliente'),(16,'Fernando','Alonso','fernando@alo-.es',622330332,'MagicAlonso','18cbf418c04cd1ea02ee58070ca7f299','Cliente'),(17,'Markitos','de los Palores','MarkitosPalote@gmail.com',123456789,'markitos15','bb8c82644ca9e181325e1ae3f59db7bb','Cliente'),(26,'Luis','Sanchez pizjuan','luissa@gmail.com',564875694,'luisSPelu','8d6335f950f350f3bb13b3bbf5336631','Cliente'),(31,'Cristo','dfasf','sdfdsafas@jofsdjofsa.com',111111111,'mario','8277e0910d750195b448797616e091ad','Personal'),(32,'matis','conductor','matisasferrary@gsgs.fev',333333333,'matias','6f8f57715090da2632453988d9a1501b','Cliente'),(37,'Nahuel','Molina','nahu@gmail.com',645130874,'nahuArg','dd131f1c8f85c358e604fa1396f0d383','Cliente'),(39,'Mateo','Messi','matimessi09@gmail.com',624894531,'mateomessi','17e744b6974f2f4854a53b0881807218','Personal'),(40,'Ana','Osuna','asoni01@gmail.com',674585221,'AnaCool2001','4bcb19cce09cb01749b8c6b6f428b26b','Personal'),(41,'Borja','Seguro','bseguro@feval.com',875428541,'bseguro','fb70dccdd1a880f66e62e84d45c49c0e','Cliente'),(42,'Federico','García Lorca','elquijote@gmail.com',111111111,'ElquijoteSanchoPanza','ae4bec8567a82c0a7bd8500a6a734132','Cliente'),(43,'Mario','Escribano Rejas ','mescribanor1410@gmail.com',645823784,'MarioKiller','c271aaf6ede6ffa5912db2186fb45830','Cliente');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`admin`@`localhost`*/ /*!50003 TRIGGER codificar_md5 BEFORE INSERT ON usuario FOR EACH ROW BEGIN SET NEW.contrasenia = MD5(NEW.contrasenia); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-25 18:49:11
