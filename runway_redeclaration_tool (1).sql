-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 04, 2023 at 06:11 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `runway_redeclaration_tool`
--

-- --------------------------------------------------------

--
-- Table structure for table `airport`
--

CREATE TABLE `airport` (
  `airport_id` int(11) NOT NULL,
  `airport_name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `airport`
--

INSERT INTO `airport` (`airport_id`, `airport_name`) VALUES
(1, 'Heathrow Airport');

-- --------------------------------------------------------

--
-- Table structure for table `constant`
--

CREATE TABLE `constant` (
  `constant_id` int(11) NOT NULL,
  `RESA` int(11) NOT NULL,
  `strip_end` int(11) NOT NULL,
  `blast_protection` int(11) NOT NULL,
  `slope` int(11) NOT NULL,
  `minRunDistance` int(11) NOT NULL,
  `minLandingDistance` int(11) NOT NULL,
  `averageRunwayWidth` int(11) NOT NULL,
  `maxObsHeight` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `constant`
--

INSERT INTO `constant` (`constant_id`, `RESA`, `strip_end`, `blast_protection`, `slope`, `minRunDistance`, `minLandingDistance`, `averageRunwayWidth`, `maxObsHeight`) VALUES
(1, 2323, 60, 300, 50, 1000, 1000, 100, 50);

-- --------------------------------------------------------

--
-- Table structure for table `obstacle`
--

CREATE TABLE `obstacle` (
  `obstacle_id` int(11) NOT NULL,
  `name` varchar(16) NOT NULL,
  `height` int(8) NOT NULL,
  `length` int(8) NOT NULL,
  `width` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `obstacle`
--

INSERT INTO `obstacle` (`obstacle_id`, `name`, `height`, `length`, `width`) VALUES
(1, 'SCENARIO-1', 14, 12, 15),
(2, 'SCENARIO-2', 15, 10, 18),
(3, 'SCENARIO-3', 12, 100, 10),
(4, 'SCENARIO-4', 34, 30, 17);

-- --------------------------------------------------------

--
-- Table structure for table `obstacle_location`
--

CREATE TABLE `obstacle_location` (
  `location_id` int(11) NOT NULL,
  `obstacle_id` int(11) NOT NULL,
  `runway_id` int(11) NOT NULL,
  `distance_from_threshold_R` int(11) NOT NULL,
  `distance_from_threshold_L` int(11) NOT NULL,
  `distance_from_centerline` int(11) NOT NULL,
  `direction_from_centerline` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `obstacle_location`
--

INSERT INTO `obstacle_location` (`location_id`, `obstacle_id`, `runway_id`, `distance_from_threshold_R`, `distance_from_threshold_L`, `distance_from_centerline`, `direction_from_centerline`) VALUES
(219, 1, 1, 5248, 0, 0, 'North');

--
-- Triggers `obstacle_location`
--
DELIMITER $$
CREATE TRIGGER `obstacle_added` AFTER INSERT ON `obstacle_location` FOR EACH ROW BEGIN
  -- Delete all rows from the table
  DELETE FROM obstacle_update;
  -- Insert the latest row
  INSERT INTO obstacle_update (location_id, added_deleted, time_stamp)
  VALUES (NEW.location_id, 'added', NOW());
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `obstacle_deleted` AFTER DELETE ON `obstacle_location` FOR EACH ROW BEGIN
  -- Delete all rows from the table
   DELETE FROM obstacle_update WHERE location_id = OLD.location_id;
  -- Insert the latest row
  INSERT INTO obstacle_update(location_id, added_deleted, time_stamp)
  VALUES (old.location_id, 'deleted', NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `obstacle_update`
--

CREATE TABLE `obstacle_update` (
  `id` int(11) NOT NULL,
  `location_id` int(30) NOT NULL,
  `added_deleted` enum('added','deleted') NOT NULL,
  `time_stamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `runway`
--

CREATE TABLE `runway` (
  `runway_id` int(11) NOT NULL,
  `runway_name` varchar(8) NOT NULL,
  `designator_id_1` int(8) NOT NULL,
  `designator_id_2` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `runway`
--

INSERT INTO `runway` (`runway_id`, `runway_name`, `designator_id_1`, `designator_id_2`) VALUES
(1, '09R/27L', 1, 2),
(2, '09L/27R', 3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `runway_designator`
--

CREATE TABLE `runway_designator` (
  `designator_id` int(11) NOT NULL,
  `designator_name` varchar(8) NOT NULL,
  `TORA` int(8) NOT NULL,
  `TODA` int(8) NOT NULL,
  `ASDA` int(8) NOT NULL,
  `LDA` int(8) NOT NULL,
  `displaced_thres` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `runway_designator`
--

INSERT INTO `runway_designator` (`designator_id`, `designator_name`, `TORA`, `TODA`, `ASDA`, `LDA`, `displaced_thres`) VALUES
(1, '09R', 5555, 3333, 3660, 3353, 307),
(2, '27L', 5555, 3660, 3660, 3660, 0),
(3, '09L', 2342, 3902, 3902, 3595, 306),
(4, '27R', 2342, 1000, 3884, 3884, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_name` varchar(12) NOT NULL,
  `password` text NOT NULL,
  `role` enum('ADMIN','AM','ATC') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_name`, `password`, `role`) VALUES
('admin', 'daeccf0ad3c1fc8c8015205c332f5b42', 'ADMIN'),
('controller1', 'daeccf0ad3c1fc8c8015205c332f5b42', 'ATC'),
('manager1', 'c240642ddef994358c96da82c0361a58', 'AM');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `airport`
--
ALTER TABLE `airport`
  ADD PRIMARY KEY (`airport_id`);

--
-- Indexes for table `constant`
--
ALTER TABLE `constant`
  ADD PRIMARY KEY (`constant_id`);

--
-- Indexes for table `obstacle`
--
ALTER TABLE `obstacle`
  ADD PRIMARY KEY (`obstacle_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `obstacle_location`
--
ALTER TABLE `obstacle_location`
  ADD PRIMARY KEY (`location_id`),
  ADD UNIQUE KEY `runway_id_2` (`runway_id`),
  ADD UNIQUE KEY `runway_id_3` (`runway_id`),
  ADD UNIQUE KEY `runway_id_4` (`runway_id`),
  ADD KEY `obstacle_id` (`obstacle_id`,`runway_id`),
  ADD KEY `runway_id` (`runway_id`);

--
-- Indexes for table `obstacle_update`
--
ALTER TABLE `obstacle_update`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `runway`
--
ALTER TABLE `runway`
  ADD PRIMARY KEY (`runway_id`),
  ADD KEY `designator_id_1` (`designator_id_1`,`designator_id_2`),
  ADD KEY `designator_id_2` (`designator_id_2`);

--
-- Indexes for table `runway_designator`
--
ALTER TABLE `runway_designator`
  ADD PRIMARY KEY (`designator_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `airport`
--
ALTER TABLE `airport`
  MODIFY `airport_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `constant`
--
ALTER TABLE `constant`
  MODIFY `constant_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `obstacle`
--
ALTER TABLE `obstacle`
  MODIFY `obstacle_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `obstacle_location`
--
ALTER TABLE `obstacle_location`
  MODIFY `location_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=222;

--
-- AUTO_INCREMENT for table `obstacle_update`
--
ALTER TABLE `obstacle_update`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=207;

--
-- AUTO_INCREMENT for table `runway`
--
ALTER TABLE `runway`
  MODIFY `runway_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `runway_designator`
--
ALTER TABLE `runway_designator`
  MODIFY `designator_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `obstacle_location`
--
ALTER TABLE `obstacle_location`
  ADD CONSTRAINT `obstacle_location_ibfk_1` FOREIGN KEY (`obstacle_id`) REFERENCES `obstacle` (`obstacle_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `obstacle_location_ibfk_2` FOREIGN KEY (`runway_id`) REFERENCES `runway` (`runway_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `runway`
--
ALTER TABLE `runway`
  ADD CONSTRAINT `runway_ibfk_1` FOREIGN KEY (`designator_id_1`) REFERENCES `runway_designator` (`designator_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `runway_ibfk_2` FOREIGN KEY (`designator_id_2`) REFERENCES `runway_designator` (`designator_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
