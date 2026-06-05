CREATE DATABASE  IF NOT EXISTS `empresa_de_transportes` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `empresa_de_transportes`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: empresa_de_transportes
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `camiones`
--

DROP TABLE IF EXISTS `camiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `camiones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patente` varchar(20) NOT NULL,
  `marca` varchar(100) NOT NULL,
  `modelo` varchar(100) NOT NULL,
  `ano` date NOT NULL,
  `kmInicial` int NOT NULL,
  `id_conductor` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `patente` (`patente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `camiones`
--

LOCK TABLES `camiones` WRITE;
/*!40000 ALTER TABLE `camiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `camiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conductores`
--

DROP TABLE IF EXISTS `conductores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conductores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rut` varchar(10) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `licencia` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rut` (`rut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conductores`
--

LOCK TABLES `conductores` WRITE;
/*!40000 ALTER TABLE `conductores` DISABLE KEYS */;
/*!40000 ALTER TABLE `conductores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipos_oficina`
--

DROP TABLE IF EXISTS `equipos_oficina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipos_oficina` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `tipo` enum('PC','Impresora','Monitor','Router','Mouse','Teclado') NOT NULL,
  `marca` varchar(100) NOT NULL,
  `modelo` varchar(100) NOT NULL,
  `serie` varchar(100) DEFAULT NULL,
  `estado` enum('Activo','En reparación','Dado de baja') NOT NULL DEFAULT 'Activo',
  `id_software` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_equipo_software` (`id_software`),
  CONSTRAINT `fk_equipo_software` FOREIGN KEY (`id_software`) REFERENCES `software` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipos_oficina`
--

LOCK TABLES `equipos_oficina` WRITE;
/*!40000 ALTER TABLE `equipos_oficina` DISABLE KEYS */;
INSERT INTO `equipos_oficina` VALUES (1,'PC Recepción','PC','Dell','OptiPlex 7090','SN-DELL-001','Activo',NULL),(2,'PC Administración','PC','Lenovo','ThinkPad E15','SN-LNV-002','Activo',9),(3,'Impresora Bodega','Impresora','HP','LaserJet M404','SN-HP-003','Activo',3);
/*!40000 ALTER TABLE `equipos_oficina` ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE `historial_mantenimiento_equipos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_equipo` int NOT NULL,
  `tipo_mantenimiento` varchar(100) NOT NULL,
  `fecha` date NOT NULL,
  `tecnico` varchar(100) NOT NULL,
  `descripcion` text,
  `estado` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_historial_equipo` (`id_equipo`),
  CONSTRAINT `fk_historial_equipo` FOREIGN KEY (`id_equipo`) REFERENCES `equipos_oficina` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
-- Table structure for table `inventario_piezas`
--

DROP TABLE IF EXISTS `inventario_piezas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventario_piezas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `cantidad` int NOT NULL DEFAULT '0',
  `unidad` varchar(50) NOT NULL,
  `stock_minimo` int NOT NULL DEFAULT '1',
  `ubicacion` varchar(100) DEFAULT NULL,
  `fecha_registro` date NOT NULL,
  `ultima_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario_piezas`
--

LOCK TABLES `inventario_piezas` WRITE;
/*!40000 ALTER TABLE `inventario_piezas` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventario_piezas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mantenimientos`
--

DROP TABLE IF EXISTS `mantenimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mantenimientos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `camion` varchar(20) NOT NULL,
  `tipoMantenimiento` varchar(255) NOT NULL,
  `fecha` date NOT NULL,
  `kmActual` int NOT NULL,
  `costo` int NOT NULL,
  `estado` int NOT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `taller` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mantenimientos`
--

LOCK TABLES `mantenimientos` WRITE;
/*!40000 ALTER TABLE `mantenimientos` DISABLE KEYS */;
/*!40000 ALTER TABLE `mantenimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regkmcamiones`
--

DROP TABLE IF EXISTS `regkmcamiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regkmcamiones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_camion` int NOT NULL,
  `id_conductor` int NOT NULL,
  `kilometraje` int NOT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regkmcamiones`
--

LOCK TABLES `regkmcamiones` WRITE;
/*!40000 ALTER TABLE `regkmcamiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `regkmcamiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `software`
--

DROP TABLE IF EXISTS `software`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `software` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `software`
--

LOCK TABLES `software` WRITE;
/*!40000 ALTER TABLE `software` DISABLE KEYS */;
INSERT INTO `software` VALUES (1,'Microsoft Office 365'),(2,'Google Chrome'),(3,'Antivirus Kaspersky'),(4,'Driver HP LaserJet'),(5,'Adobe Acrobat Reader'),(7,'spotify'),(9,'opera gx');
/*!40000 ALTER TABLE `software` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `rut` varchar(10) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `rol` enum('conductor','admin_flota','admin_mantenimiento','admin_inventario','tecnico_it') NOT NULL,
  PRIMARY KEY (`rut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('20134567-7','Admin Inventario','inv1234','admin_inventario'),('22276870-5','Pedro Soto','pepe','admin_mantenimiento'),('22358026-2','Tecnico IT','colocolo','tecnico_it'),('22365874-1','Admin Flota','1234','admin_flota');
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

USE `empresa_de_transportes`;

CREATE TABLE `mantenimiento_piezas` (
  `id_mantenimiento` INT NOT NULL,
  `id_pieza` INT NOT NULL,
  `cantidad_usada` INT NOT NULL,
  PRIMARY KEY (`id_mantenimiento`, `id_pieza`),
  
  CONSTRAINT `fk_piezas_mantenimiento` 
    FOREIGN KEY (`id_mantenimiento`) REFERENCES `historial_mantenimiento_equipos` (`id`) 
    ON DELETE CASCADE ON UPDATE CASCADE,
    
  CONSTRAINT `fk_piezas_inventario` 
    FOREIGN KEY (`id_pieza`) REFERENCES `inventario_piezas` (`id`) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump completed on 2026-06-03 21:57:31
show tables;
select * from users;
select * from equipos_oficina;
select * from inventario_piezas;
select * from historial_mantenimiento_equipos;