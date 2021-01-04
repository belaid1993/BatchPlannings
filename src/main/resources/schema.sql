-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Dim 13 Octobre 2019 à 17:41
-- Version du serveur :  5.7.11
-- Version de PHP :  5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `formations-db`
--

-- --------------------------------------------------------
DROP TABLE IF EXISTS `seances`;
DROP TABLE IF EXISTS `formations`;
DROP TABLE IF EXISTS `formateurs`;



CREATE TABLE `formateurs` (
  `id` int(11) NOT NULL,
  `nom` varchar(12) NOT NULL,
  `prenom` varchar(12) NOT NULL,
  `adresse_email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `formations` (
  `code` varchar(20) NOT NULL,
  `libelle` varchar(200) NOT NULL,
  `descriptif` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `seances` (
  `code_formation` varchar(20) NOT NULL,
  `id_formateur` int(11) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `formateurs`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `formations`
  ADD PRIMARY KEY (`code`);

ALTER TABLE `seances`
  ADD KEY `code_formation` (`code_formation`,`id_formateur`),
  ADD KEY `FK_FORMATEURS` (`id_formateur`);


ALTER TABLE `seances`
  ADD CONSTRAINT `FK_FORMATEURS` FOREIGN KEY (`id_formateur`) REFERENCES `formateurs` (`id`),
  ADD CONSTRAINT `FK_FORMATIONS` FOREIGN KEY (`code_formation`) REFERENCES `formations` (`code`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;