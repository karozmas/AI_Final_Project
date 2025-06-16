CREATE DATABASE  IF NOT EXISTS `expert_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `expert_system`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: expert_system
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `diagnosis_history`
--

DROP TABLE IF EXISTS `diagnosis_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnosis_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `temperature` decimal(4,1) DEFAULT NULL,
  `weight` decimal(5,2) DEFAULT NULL,
  `height` decimal(5,2) DEFAULT NULL,
  `selected_symptoms` text COLLATE utf8mb4_general_ci,
  `diagnosis` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `confidence` int DEFAULT NULL,
  `recommendation` text COLLATE utf8mb4_general_ci,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `diagnosis_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnosis_history`
--

LOCK TABLES `diagnosis_history` WRITE;
/*!40000 ALTER TABLE `diagnosis_history` DISABLE KEYS */;
INSERT INTO `diagnosis_history` VALUES (1,2,38.0,61.00,172.00,'cough,headache,fatigue,nausea,sore throat','Common Cold',2,'Rest, drink plenty of fluids, and take over-the-counter cold remedies.','2025-04-16 10:51:25'),(2,2,38.0,61.00,172.00,'itchy eyes,loss of taste,nausea,runny nose,sensitivity to light,shortness of breath,sore throat','Common Cold',2,'Rest, drink plenty of fluids, and take over-the-counter cold remedies.','2025-04-16 11:00:31'),(3,2,38.0,61.00,172.00,'itchy eyes,loss of taste,nasal congestion,nausea,rapid heartbeat,runny nose,sensitivity to light,shortness of breath,sore throat,visual disturbance','Migraine',3,'Lie down in a quiet, dark room and take migraine medication if prescribed.','2025-04-16 11:00:36'),(4,2,38.0,61.00,172.00,'confusion,cough,dizziness,fatigue,fever,runny nose','Heat Stroke',3,'Call emergency services immediately. Move to a cool area and apply cold compresses.','2025-04-16 11:02:37'),(6,2,234.0,234.00,234.00,'abdominal pain,cough,dry mouth,fever,itchy eyes,nasal congestion,sneezing,sore throat','Common Cold',3,'Rest, drink plenty of fluids, and take over-the-counter cold remedies.','2025-04-16 22:43:46'),(7,2,234.0,234.00,234.00,'abdominal pain,cough,dry mouth,fever,itchy eyes,nasal congestion,sneezing,sore throat','Common Cold',3,'Rest, drink plenty of fluids, and take over-the-counter cold remedies.','2025-04-16 22:43:48'),(8,2,2.0,234.00,234.00,'abdominal pain,cough,dry mouth,fever,itchy eyes,nasal congestion,sneezing,sore throat','Common Cold',3,'Rest, drink plenty of fluids, and take over-the-counter cold remedies.','2025-04-16 22:43:52'),(9,2,2.0,23.00,234.00,'abdominal pain,cough,dry mouth,fever,itchy eyes,nasal congestion,sneezing,sore throat','Common Cold',3,'Rest, drink plenty of fluids, and take over-the-counter cold remedies.','2025-04-16 22:43:54'),(10,2,2.0,23.00,23.00,'abdominal pain,cough,dry mouth,fever,itchy eyes,nasal congestion,sneezing,sore throat','Common Cold',3,'Rest, drink plenty of fluids, and take over-the-counter cold remedies.','2025-04-16 22:43:56'),(11,2,12.0,12.00,12.00,'abdominal pain,cough,lower abdominal pain,nasal congestion,nausea','Food Poisoning',2,'Stay hydrated, avoid solid food for a few hours, and seek medical attention if symptoms persist.','2025-04-16 22:46:53'),(12,2,12.0,12.00,12.00,'abdominal pain,cough,lower abdominal pain,nasal congestion,nausea','Food Poisoning',2,'Stay hydrated, avoid solid food for a few hours, and seek medical attention if symptoms persist.','2025-04-16 22:47:06');
/*!40000 ALTER TABLE `diagnosis_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rules`
--

DROP TABLE IF EXISTS `rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rules` (
  `id` int NOT NULL AUTO_INCREMENT,
  `symptoms` text COLLATE utf8mb4_general_ci NOT NULL,
  `conditions` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `confidence` int NOT NULL,
  `recommendation` text COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rules`
--

LOCK TABLES `rules` WRITE;
/*!40000 ALTER TABLE `rules` DISABLE KEYS */;
INSERT INTO `rules` VALUES (1,'sneezing,runny nose,sore throat,cough','Common Cold',4,'Rest, drink plenty of fluids, and take over-the-counter cold remedies.'),(2,'fever,headache,fatigue,chills,body aches','Influenza (Flu)',5,'Stay home, rest, drink fluids, and consult a doctor if symptoms persist.'),(3,'fever,cough,shortness of breath,loss of taste,loss of smell','COVID-19',5,'Self-isolate immediately and consult a healthcare provider. Consider PCR testing.'),(4,'headache,nausea,sensitivity to light,visual disturbance','Migraine',4,'Lie down in a quiet, dark room and take migraine medication if prescribed.'),(5,'nausea,vomiting,diarrhea,abdominal pain,fever','Food Poisoning',5,'Stay hydrated, avoid solid food for a few hours, and seek medical attention if symptoms persist.'),(6,'fatigue,dizziness,dry mouth,headache','Dehydration',4,'Drink water or electrolyte drinks. If symptoms are severe, seek medical attention.'),(7,'fever,cough,shortness of breath,chest pain,chills','Pneumonia',5,'Seek medical attention immediately. May require antibiotics and hospital observation.'),(8,'sneezing,runny nose,itchy eyes,nasal congestion','Allergic Rhinitis',4,'Avoid allergens. Take antihistamines and consult your allergist if needed.'),(9,'burning urination,frequent urination,lower abdominal pain,cloudy urine','Urinary Tract Infection (UTI)',4,'Increase fluid intake and consult a doctor for antibiotics.'),(10,'fever,dizziness,confusion,dry skin,rapid heartbeat','Heat Stroke',5,'Call emergency services immediately. Move to a cool area and apply cold compresses.');
/*!40000 ALTER TABLE `rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `full_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'vq4','1','Abdullah Alamri','2003-09-17'),(3,'Abdulelah','1','Abdu','2025-04-16'),(8,'AbdulelahN','123456789','AbdulelahNasser','2025-04-10');
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

-- Dump completed on 2025-06-16 10:33:30
