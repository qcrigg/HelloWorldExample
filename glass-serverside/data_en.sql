-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2014 at 10:58 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

create database qcri;
use qcri;

--
-- Database: `qcri`
--

-- --------------------------------------------------------

--
-- Table structure for table `data_en`
--

CREATE TABLE IF NOT EXISTS `data_en` (
  `entry` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ORIGINAL_WORD` varchar(200) NOT NULL DEFAULT '',
  `RECOGNIZED_WORD` varchar(200) NOT NULL DEFAULT '',
  `ORIGINAL_TRANSLATION` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `TRANSLATED_WORD` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `user_id` varchar(200) NOT NULL,
  `RECOGNITION_CORRECT` tinyint(1) NOT NULL,
  `TRANSLATION_CORRECT` tinyint(1) NOT NULL,
  `RECOGNITION_TIME` bigint(20) unsigned NOT NULL COMMENT 'Measured from when recognizer started to results received',
  `TRANSLATION_TIME` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`entry`),
  UNIQUE KEY `entry_2` (`entry`),
  KEY `entry` (`entry`),
  KEY `entry_3` (`entry`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=296 ;

--
-- Dumping data for table `data_en`
--

INSERT INTO `data_en` (`entry`, `ORIGINAL_WORD`, `RECOGNIZED_WORD`, `ORIGINAL_TRANSLATION`, `TRANSLATED_WORD`, `user_id`, `RECOGNITION_CORRECT`, `TRANSLATION_CORRECT`, `RECOGNITION_TIME`, `TRANSLATION_TIME`) VALUES
(78, 'right ', 'right', '\0', 'Ø­Ù‚', 'zeeshan', 0, 0, 2721, 163),
(79, 'ruler', 'ruler', '\0', 'Ø­Ø§ÙƒÙ…', 'zeeshan', 1, 0, 10319, 168),
(80, 'left', 'left', '\0*-EJD', 'Ø§Ù„ÙŠØ³Ø§Ø±', 'zeeshan', 1, 0, 2930, 173),
(81, 'gallery', 'Gallery', '\0HBHA', 'Ø±ÙˆØ§Ù‚', 'zeeshan', 1, 0, 2876, 312),
(82, 'loading', 'loading', '\05H1', 'ØªØ­Ù…ÙŠÙ„', 'zeeshan', 1, 0, 6391, 224),
(83, 'timeline', 'timeline', '\0%13D', 'Ø§Ù„Ø¬Ø¯ÙˆÙ„ Ø§Ù„Ø²Ù…Ù†ÙŠ', 'zeeshan', 1, 0, 3291, 217),
(84, 'train', 'train', '\0', 'Ù‚Ø·Ø§Ø±', 'zeeshan', 1, 0, 2817, 167),
(85, 'garden', 'garden', '\0', 'Ø­Ø¯ÙŠÙ‚Ø©', 'zeeshan', 1, 0, 8144, 176),
(86, 'down', 'down', '\0', 'Ø¥Ù„Ù‰', 'zeeshan', 1, 0, 9344, 173),
(87, 'bedroom', 'bedroom', '\0', 'Ø­Ø¬Ø±Ø© Ø§Ù„Ù†ÙˆÙ…', 'zeeshan', 1, 0, 2985, 202),
(88, 'login', 'login', '\0', 'Ø¯Ø®ÙˆÙ„', 'zeeshan', 1, 0, 3418, 226),
(89, 'delete', 'delete', '\0%3*E9', 'Ø­Ø°Ù', 'zeeshan', 1, 0, 2939, 177),
(90, 'train', 'train', '\0', 'Ù‚Ø·Ø§Ø±', 'zeeshan', 1, 0, 2803, 172),
(91, 'pause', 'was', '\0', 'ÙƒØ§Ù†', 'zeeshan', 0, 0, 2581, 173),
(92, 'gym', 'gym', '\0', 'Ø§Ù„Ø¬Ù…Ù†Ø§Ø²ÙŠÙˆÙ…', 'zeeshan', 1, 0, 11599, 3905),
(93, 'desk', 'desk', '\0', 'Ù…ÙƒØªØ¨', 'zeeshan', 1, 0, 3006, 176),
(94, 'table', 'table', '\0CJA', 'Ø¬Ø¯ÙˆÙ„', 'zeeshan', 1, 0, 3176, 218),
(95, 'read', 'read', '\0', 'Ù‚Ø±Ø£', 'zeeshan', 1, 0, 3001, 182),
(96, 'loading', 'loading', '\05H1', 'ØªØ­Ù…ÙŠÙ„', 'zeeshan', 1, 0, 3083, 170),
(97, 'sign-up', 'sign up', '\0', '', 'zeeshan', 0, 0, 2823, 171),
(98, 'send', 'send', '\0', 'Ø¥Ø±Ø³Ø§Ù„', 'zeeshan', 1, 0, 2826, 693),
(99, 'speech', 'speech', '\0', 'Ø®Ø·Ø§Ø¨', 'zeeshan', 1, 0, 3014, 158),
(100, 'stop', 'stop', '\0%D9(', 'ØªÙˆÙ‚Ù', 'zeeshan', 1, 0, 3152, 199),
(101, 'up', 'Opt', '\0%F*81', 'Ø§Ø®ØªØ§Ø±', 'zeeshan', 0, 0, 2603, 170),
(102, 'cucumber 	', 'cucumber', '\0', 'Ø®ÙŠØ§Ø±', 'zeeshan', 0, 0, 3122, 163),
(103, 'scroll', 'scroll', '\0', 'Ø§Ù„ØªÙ…Ø±ÙŠØ±', 'zeeshan', 1, 0, 2983, 183),
(104, 'swipe', 'swype', '\0*F2JD', 'SWYPE', 'zeeshan', 0, 0, 5099, 3695),
(105, 'write', 'right', '\0C(1', 'Ø­Ù‚', 'zeeshan', 0, 0, 29056, 159),
(106, 'zoom', 'zoom', '\0%3*E9', 'Ø²ÙˆÙ…', 'zeeshan', 1, 0, 2853, 172),
(107, 'right ', 'right', '\0*F2JD', 'Ø­Ù‚', 'zeeshan', 0, 0, 2984, 158),
(108, 'garden', 'garden', '\0', 'Ø­Ø¯ÙŠÙ‚Ø©', 'zeeshan', 1, 0, 6940, 168),
(109, 'chair', 'chair', '\0CJA', 'ÙƒØ±Ø³ÙŠ', 'zeeshan', 1, 0, 2871, 161),
(110, 'gallery', 'Gallery', '\0HBHA', 'Ø±ÙˆØ§Ù‚', 'zeeshan', 1, 0, 7510, 158),
(111, 'listen', 'listen', '\0E/13)', 'Ø§Ø³ØªÙ…Ø¹', 'zeeshan', 1, 0, 2826, 165),
(112, 'school', 'school', '\0', 'Ù…Ø¯Ø±Ø³Ø©', 'zeeshan', 1, 0, 2859, 175),
(113, 'gallery', 'Gallery', '\0', 'Ø±ÙˆØ§Ù‚', 'zeeshan', 1, 0, 2990, 170),
(114, 'delete', 'delete', '\0-/JB)', 'Ø­Ø°Ù', 'zeeshan', 1, 0, 3019, 3503),
(115, 'right ', 'right', '\0*F2JD', 'Ø­Ù‚', 'zeeshan', 0, 0, 2896, 165),
(116, 'listen', 'listen', '\0E/13)', 'Ø§Ø³ØªÙ…Ø¹', 'zeeshan', 1, 0, 2689, 169),
(117, 'menu', 'menu', '\0', 'Ù‚Ø§Ø¦Ù…Ø© Ø§Ù„Ø·Ø¹Ø§Ù…', 'zeeshan', 1, 0, 3012, 168),
(118, 'bedroom', 'bedroom', '\0E371)', 'Ø­Ø¬Ø±Ø© Ø§Ù„Ù†ÙˆÙ…', 'zeeshan', 1, 0, 20089, 218),
(119, 'car', 'car', '\0', 'Ø³ÙŠØ§Ø±Ø©', 'zeeshan', 1, 0, 2983, 191),
(120, 'browse', 'browse', '\0E1C0 \0*3HB', 'ØªØµÙØ­', 'zeeshan', 1, 0, 2995, 163),
(121, 'down', 'down', '\0', 'Ø¥Ù„Ù‰', 'zeeshan', 1, 0, 2845, 161),
(122, 'stop', 'stop', '\0', 'ØªÙˆÙ‚Ù', 'zeeshan', 1, 0, 2858, 165),
(123, 'scroll', 'scroll', '\0', 'Ø§Ù„ØªÙ…Ø±ÙŠØ±', 'zeeshan', 1, 0, 2949, 160),
(124, 'left', 'left', '\0', 'Ø§Ù„ÙŠØ³Ø§Ø±', 'zeeshan', 1, 0, 2951, 163),
(125, 'read', 'read', '\0', 'Ù‚Ø±Ø£', 'zeeshan', 1, 0, 2827, 164),
(126, 'ben', 'Ben', '\0EH2)', 'Ø¨Ù†', 'zeeshan', 1, 0, 2690, 166),
(127, 'capture', 'capture', '\0', 'Ø£Ø³Ø±', 'zeeshan', 1, 0, 2809, 163),
(128, 'truck', 'truck', '\0', 'Ø´Ø§Ø­Ù†Ø©', 'zeeshan', 1, 0, 2812, 168),
(129, 'left', 'left', '\0*-EJD', 'Ø§Ù„ÙŠØ³Ø§Ø±', 'zeeshan', 1, 0, 2812, 158),
(130, 'bedroom', 'bedroom', '\0', 'Ø­Ø¬Ø±Ø© Ø§Ù„Ù†ÙˆÙ…', 'zeeshan', 1, 0, 2699, 163),
(131, 'gym', 'gym', '\0', 'Ø§Ù„Ø¬Ù…Ù†Ø§Ø²ÙŠÙˆÙ…', 'zeeshan', 1, 0, 2823, 159),
(132, 'desk', 'desk', '\0EH2)', 'Ù…ÙƒØªØ¨', 'zeeshan', 1, 0, 2980, 171),
(133, 'chair', 'chair', '\0', 'ÙƒØ±Ø³ÙŠ', 'zeeshan', 1, 0, 2817, 165),
(134, 'ben', 'Ben', '\0H39', 'Ø¨Ù†', 'zeeshan', 1, 0, 25767, 166),
(135, 'scroll', 'scrool', '\0(1,', 'scrool', 'zeeshan', 0, 0, 3043, 168),
(136, 'living room', 'living room', '\0', '', 'zeeshan', 1, 0, 25327, 187),
(137, 'delete', 'delete', '\0%3*E9', 'Ø­Ø°Ù', 'zeeshan', 1, 0, 2943, 836),
(138, 'album', 'album', '\0', 'Ø§Ù„Ø£Ù„Ø¨ÙˆÙ…', 'zeeshan', 1, 0, 3238, 224),
(139, 'kitchen', 'kitchen', '\0BDE', 'Ù…Ø·Ø¨Ø®', 'zeeshan', 1, 0, 2725, 200),
(140, 'bedroom', 'bedroom', '\0E371)', 'Ø­Ø¬Ø±Ø© Ø§Ù„Ù†ÙˆÙ…', 'zeeshan', 1, 0, 7653, 211),
(141, 'music', 'music', '\0', 'Ù…ÙˆØ³ÙŠÙ‚Ù‰', 'zeeshan', 1, 0, 3258, 200),
(142, 'left', 'left', '\0', 'Ø§Ù„ÙŠØ³Ø§Ø±', 'zeeshan', 1, 0, 3091, 210),
(143, 'timeline', 'timeline', '\0', 'Ø§Ù„Ø¬Ø¯ÙˆÙ„ Ø§Ù„Ø²Ù…Ù†ÙŠ', 'zeeshan', 1, 0, 8274, 209),
(144, 'living room', 'living room', '\0', '', 'zeeshan', 1, 0, 2952, 212),
(145, 'move', 'move', '\0', 'Ø®Ø·ÙˆØ©', 'zeeshan', 1, 0, 2813, 1264),
(146, 'up', 'up', '\0', 'ÙÙˆÙ‚', 'zeeshan', 1, 0, 7071, 210),
(147, 'now', 'No', '\0BAD', 'Ù„Ø§', 'zeeshan', 0, 0, 2824, 820),
(148, 'now', 'now', '\0', 'Ø§Ù„Ø¢Ù†', 'zeeshan', 1, 0, 9762, 250),
(149, 'living room', 'living room', '\0E371)', '', 'zeeshan', 1, 0, 8947, 187),
(150, 'bedroom', 'bedroom', '\0E371)', 'Ø­Ø¬Ø±Ø© Ø§Ù„Ù†ÙˆÙ…', 'zeeshan', 1, 0, 3240, 833),
(151, 'pause', 'pause', '\0%D9(', 'ÙˆÙ‚ÙØ©', 'zeeshan', 1, 0, 3092, 200),
(152, 'cucumber 	', 'cucumber', '\0', 'Ø®ÙŠØ§Ø±', 'zeeshan', 0, 0, 3294, 195),
(153, 'loading', 'loading', '\05H1', 'ØªØ­Ù…ÙŠÙ„', 'zeeshan', 1, 0, 3319, 220),
(154, 'scroll', 'scroll', '\0', 'Ø§Ù„ØªÙ…Ø±ÙŠØ±', 'zeeshan', 1, 0, 23908, 170),
(155, 'stop', 'stop', '\0', 'ØªÙˆÙ‚Ù', 'zeeshan', 1, 0, 25869, 190),
(156, 'wait', 'great', '\0', 'Ø¹Ø¸ÙŠÙ…', 'null ferda', 0, 0, 3024, 200),
(157, 'bedroom', 'bedroom', '\0', 'Ø­Ø¬Ø±Ø© Ø§Ù„Ù†ÙˆÙ…', 'null ferda', 1, 0, 3047, 222),
(158, 'photo', 'photo', '\0HBHA \0E$B* \0', 'ØµÙˆØ±', 'null Ruger', 1, 0, 3002, 957),
(159, 'share', 'share', '\0.0', 'Ø­ØµØ©', 'null Ruger', 1, 0, 2853, 261),
(160, 'left', 'left', '\0*-EJD', 'Ø§Ù„ÙŠØ³Ø§Ø±', 'null rags', 1, 0, 3381, 369),
(161, 'play', 'click', '\0%B1#', 'Ø§Ù†Ù‚Ø±', 'null rags', 0, 0, 2799, 186),
(162, 'car', 'car', '\0%FBD', 'Ø³ÙŠØ§Ø±Ø©', 'null rags', 1, 0, 2819, 331),
(163, 'chair', 'Tran', '\0', 'ØªØ±Ø§Ù†', 'null rags', 0, 0, 2830, 171),
(164, 'speech', 'speech', '\0', 'Ø®Ø·Ø§Ø¨', 'null rags', 1, 0, 4749, 296),
(165, 'gym', 'gym', '\0', 'Ø§Ù„Ø¬Ù…Ù†Ø§Ø²ÙŠÙˆÙ…', 'null rags', 1, 0, 3029, 227),
(166, 'move', 'move', '\0', 'Ø®Ø·ÙˆØ©', 'null rags', 1, 0, 5918, 178),
(167, 'read', 'read', '\0', 'Ù‚Ø±Ø£', 'null rags', 1, 0, 3518, 186),
(168, 'car', 'car', '\0%FBD', 'Ø³ÙŠØ§Ø±Ø©', 'null rags', 1, 0, 2904, 284),
(169, 'music', 'music', '\0', 'Ù…ÙˆØ³ÙŠÙ‚Ù‰', 'null rags', 1, 0, 2745, 225),
(170, 'swipe', 'swype', '\0*F2JD', 'SWYPE', 'null rags', 0, 0, 2975, 185),
(171, 'table', 'table', '\0CJA', 'Ø¬Ø¯ÙˆÙ„', 'null rags', 1, 0, 2853, 213),
(172, 'delete', 'delete', '\0%3*E9', 'Ø­Ø°Ù', 'null rags', 1, 0, 2848, 207),
(173, 'take', 'kik', '\0', 'KIK', 'null rags', 0, 0, 6248, 174),
(174, 'write', 'right', 'ï¿½ï¿½%C*(', 'Ø­Ù‚', 'null rags', 0, 0, 2860, 180),
(175, 'photo', 'photo', '\0HBHA \0E$B* \0', 'ØµÙˆØ±', 'null rags', 1, 0, 3076, 194),
(176, 'adjust', 'adjust', '\0', 'Ø¶Ø¨Ø·', 'null rags', 1, 0, 3091, 271),
(177, 'share', 'share', '\0.0', 'Ø­ØµØ©', 'null rags', 1, 0, 3031, 274),
(178, 'bus', 'bus', '\0', 'Ø­Ø§ÙÙ„Ø©', 'null rags', 1, 0, 2698, 215),
(179, 'left', 'left', '\0*-EJD', 'Ø§Ù„ÙŠØ³Ø§Ø±', 'null rags', 1, 0, 2929, 303),
(180, 'gym', 'gym', '\0', 'Ø§Ù„Ø¬Ù…Ù†Ø§Ø²ÙŠÙˆÙ…', 'null rags', 1, 0, 2689, 212),
(181, 'login', 'login', '\0', 'Ø¯Ø®ÙˆÙ„', 'null rags', 1, 0, 3127, 261),
(182, 'move', 'move', '\0', 'Ø®Ø·ÙˆØ©', 'null rags', 1, 0, 8342, 202),
(183, 'downloading', 'downloading', '\0', 'ØªØ­Ù…ÙŠÙ„', 'null rags', 1, 0, 5413, 221),
(184, 'timeline', 'timeline', '\0%13D', 'Ø§Ù„Ø¬Ø¯ÙˆÙ„ Ø§Ù„Ø²Ù…Ù†ÙŠ', 'null rags', 1, 0, 5943, 188),
(185, 'write', 'right', 'ï¿½ï¿½%C*(', 'Ø­Ù‚', 'null rags', 0, 0, 3803, 438),
(186, 'timeline', 'timeline', '\0%13D', 'Ø§Ù„Ø¬Ø¯ÙˆÙ„ Ø§Ù„Ø²Ù…Ù†ÙŠ', 'null rags', 1, 0, 3016, 315),
(187, 'downloading', 'downloading', '\0', 'ØªØ­Ù…ÙŠÙ„', 'null rags', 1, 0, 3130, 532),
(188, 'table', 'Gabriel', '\0CJA', 'ØºØ§Ø¨Ø±ÙŠÙŠÙ„', 'null rags', 0, 0, 2813, 179),
(189, 'down', 'down', '\0', 'Ø¥Ù„Ù‰', 'null rags', 1, 0, 2708, 208),
(190, 'ben', 'Ben', '\0H39', 'Ø¨Ù†', 'null rags', 1, 0, 9049, 200),
(191, 'zoom', 'zoom', '\0', 'Ø²ÙˆÙ…', 'null rags', 1, 0, 5928, 173),
(192, 'train', 'train', '\0', 'Ù‚Ø·Ø§Ø±', 'null rags', 1, 0, 3411, 205),
(193, 'up', 'up', '\0%F*81', 'ÙÙˆÙ‚', 'null rags', 1, 0, 2601, 189),
(194, 'photo', 'photo', '\0HBHA \0E$B* \0', 'ØµÙˆØ±', 'null rags', 1, 0, 3380, 170),
(195, 'cucumber 	', 'cucumber', '\0', 'Ø®ÙŠØ§Ø±', 'null rags', 0, 0, 3272, 232),
(196, 'swipe', 'swype', '\0*F2JD', 'SWYPE', 'null rags', 0, 0, 2737, 163),
(197, 'down', 'down', '\0', 'Ø¥Ù„Ù‰', 'null rags', 1, 0, 2881, 231),
(198, '\0', 'blank screen', '\0', '', 'null Jack', 0, 0, 4177, 856),
(199, '\0*f2jd', 'JD', '\0*F2JD', 'Ø¯ÙŠÙ†Ø§Ø±', 'null Jack', 0, 0, 4259, 679),
(200, 'school', 'something', '\0', 'Ø´ÙŠØ¡', 'null ferda', 0, 0, 5549, 1062),
(201, 'banana', 'building', '\0', 'Ø¨Ù†Ø§Ø¡', 'null ferda', 0, 0, 4999, 790),
(202, 'take', 'take', 'Ø®Ø°', 'Ø£Ø®Ø°', 'null ferda', 1, 0, 2930, 816),
(203, 'tower', 'tower', 'Ø¨Ø±Ø¬', 'Ø¨Ø±Ø¬', 'null ferda', 1, 0, 3074, 178),
(204, 'carpet', 'carpet', 'Ø³Ø¬Ø§Ø¯Ø©', 'Ø³Ø¬Ø§Ø¯Ø©', 'null elevation', 1, 1, 4606, 1118),
(205, 'bus', 'the bus', 'Ø­Ø§ÙÙ„Ø©', '', 'null elevation', 0, 0, 4964, 234),
(206, 'truck', 'truck', 'Ø´Ø§Ø­Ù†Ø©', 'Ø´Ø§Ø­Ù†Ø©', 'null elevation', 1, 1, 3285, 743),
(207, 'car', 'car', 'Ø³ÙŠØ§Ø±Ø©', 'Ø³ÙŠØ§Ø±Ø©', 'null elevation', 1, 1, 7159, 210),
(208, 'chair', 'chair', 'ÙƒØ±Ø³ÙŠ', 'ÙƒØ±Ø³ÙŠ', 'null Zeeshan', 1, 1, 2861, 290),
(209, 'share', 'share', 'Ø§Ù†Ø´Ø±', 'Ø­ØµØ©', 'null Zeeshan', 1, 0, 4151, 202),
(210, 'delete', 'bleak', 'Ø¥Ù…Ø³Ø­', 'ÙƒØ¦ÙŠØ¨', 'null Zeeshan', 0, 0, 3075, 252),
(211, 'logout', 'log out', 'ØªØ³Ø¬ÙŠÙ„ Ø§Ù„Ø®Ø±ÙˆØ¬', 'ØªØ³Ø¬ÙŠÙ„ Ø§Ù„Ø®Ø±ÙˆØ¬', 'null future', 0, 1, 6205, 1262),
(212, 'browse', 'drugs', 'ØªØµÙØ­', 'ØªØµÙØ­', 'null future', 0, 1, 5002, 324),
(213, 'send', 'send', 'Ø¥Ø±Ø³Ù„', 'Ø¥Ø±Ø³Ø§Ù„', 'null future', 1, 0, 11758, 221),
(214, 'delete', 'delete', 'Ø¥Ù…Ø³Ø­', 'Ø­Ø°Ù', 'null charmed', 1, 0, 3039, 3197),
(215, 'loading', 'your day', 'ØªØ­Ù…ÙŠÙ„', 'ØªØ­Ù…ÙŠÙ„', 'null charmed', 0, 1, 4136, 288),
(216, '', '', '???? ????', '', '', 0, 0, 0, 0),
(217, '', '', 'وقوف مؤقت', '', '', 0, 0, 0, 0),
(218, '', '', 'وقوف مؤقت', '', '', 0, 0, 0, 0),
(219, 'bedroom', 'bedroom', 'ØºØ±ÙØ© Ø§Ù„Ù†ÙˆÙ…', 'Ø­Ø¬Ø±Ø© Ø§Ù„Ù†ÙˆÙ…', 'null Zeeshan', 1, 0, 3125, 1064),
(220, 'adjust', 'adjust', 'ÙƒÙŠÙ', 'Ø¶Ø¨Ø·', 'null Zeeshan', 1, 0, 3322, 993),
(221, 'music', 'music', 'Ø£ØºØ§Ù†ÙŠ', 'Ù…ÙˆØ³ÙŠÙ‚Ù‰', 'null Zeeshan', 1, 0, 3358, 209),
(222, 'gallery', 'Gallery', 'Ø§Ø³ØªÙˆØ¯ÙŠÙˆ', 'Ø±ÙˆØ§Ù‚', 'null best', 1, 0, 3222, 1941),
(223, 'zoom', 'zoom', 'ÙƒØ¨Ø±', 'Ø²ÙˆÙ…', 'null Zeeshan', 1, 0, 5991, 803),
(224, 'sign-up', 'sign up', 'Ø§Ù„Ø§Ø´ØªØ±Ø§Ùƒ', 'Ø§Ù„Ø§Ø´ØªØ±Ø§Ùƒ', 'null best', 0, 1, 6423, 3129),
(225, 'apple', 'actors', 'ØªÙØ§Ø­Ø©', 'ØªÙØ§Ø­Ø©', 'null test', 0, 1, 3162, 971),
(226, 'music', 'music', 'Ø£ØºØ§Ù†ÙŠ', 'Ù…ÙˆØ³ÙŠÙ‚Ù‰', 'null best', 1, 0, 2796, 1185),
(227, 'take', 'dick', 'Ø®Ø°', 'Ø£Ø®Ø°', 'null best', 0, 0, 2815, 796),
(228, 'mall', 'morning', 'Ù‚Ø§Ø¹Ø© Ø±ÙŠØ§Ø¶Ø© ', 'Ù…ÙˆÙ„', 'null best', 0, 0, 3825, 1330),
(229, 'mall', 'mole', 'Ù‚Ø§Ø¹Ø© Ø±ÙŠØ§Ø¶Ø© ', 'Ù…ÙˆÙ„', 'null test', 0, 0, 4188, 317),
(230, 'write', 'light', 'Ø¥Ù‚Ø±Ø£', 'Ø¥Ø±Ø³Ø§Ù„', 'null best', 0, 0, 2970, 194),
(231, 'pause', 'pause', 'ÙˆÙ‚ÙˆÙ', 'ÙˆÙ‚ÙØ©', 'null best', 1, 0, 3153, 389),
(232, 'table', 'Bieber', 'مكتب', 'جدول', 'null test', 0, 0, 2967, 784),
(233, 'loading', 'loading', 'تنزيل', 'تحميل', 'null artist', 1, 0, 3078, 1199),
(234, 'playlist', 'playlist', 'قائمة التشغيل ', 'قائمة التشغيل', 'null like best', 1, 0, 2937, 1043),
(235, 'send', 'send', 'إرسل', 'إرسال', 'null like best', 1, 0, 2996, 267),
(236, 'timeline', 'timeline', 'الجدول الزمني ', 'الجدول الزمني', 'null like best', 1, 0, 2975, 287),
(237, 'music', 'music', 'أغاني', 'موسيقى', 'null like best', 1, 0, 2936, 199),
(238, 'stop', 'stop', 'وقوف', 'توقف', 'null like best', 1, 0, 7498, 197),
(239, 'tomato', 'tomato', 'طماطم', 'طماطم', 'null like best', 1, 1, 3329, 192),
(240, 'pen', 'fan', 'قلم', 'قلم', 'null like best', 0, 1, 7670, 250),
(241, 'login', 'login', 'تسجيل الدخول', 'دخول', 'null Sean', 1, 0, 6186, 1082),
(242, 'up', 'up', 'الاعلى', 'فوق', 'null Sean', 1, 0, 2862, 200),
(243, 'menu', 'menu', 'القائمة', 'قائمة الطعام', 'null Sean', 1, 0, 7002, 230),
(244, 'speech', 'speech', 'كلام', 'خطاب', 'null Stefan', 1, 0, 3613, 193),
(245, 'delete', 'delete', 'إمسح', 'حذف', 'null Stefan', 1, 0, 3931, 276),
(246, 'now', 'now', 'الآن', 'الآن', 'null Stefan', 1, 1, 3208, 191),
(247, 'bus', 'bus', 'حافلة', 'حافلة', 'null Stefan', 1, 1, 12799, 197),
(248, 'timeline', 'timeline', 'الجدول الزمني', 'الجدول الزمني', 'null Stefan', 1, 1, 3866, 194),
(249, 'desk', 'desk', 'مكتب', 'مكتب', 'null Stefan', 1, 1, 3205, 315),
(250, 'pen', '10', 'قلم', 'قلم', 'null Stefan', 0, 1, 16264, 747),
(251, 'adjust', 'suggest', 'ضبط', 'ضبط', 'null Stefan', 0, 1, 3166, 198),
(252, 'table', 'table', 'طاولة', 'جدول', 'null Stefan', 1, 0, 3211, 234),
(253, 'write', 'right', 'إكتب', 'إرسال', 'null Stefan', 0, 0, 3048, 248),
(254, 'left', 'left', 'اليسار', 'اليسار', 'null Stefan', 1, 1, 2617, 227),
(255, 'listen', 'listen', 'إستمع', 'استمع', 'null Stefan', 1, 0, 3129, 193),
(256, 'garden', 'garden', 'حديقة', 'حديقة', 'null Stefan', 1, 1, 2803, 296),
(257, 'sea', 'C', 'بحر', 'بحر', 'null Stefan', 0, 1, 13731, 250),
(258, 'expand', 'expand', 'وسع', 'وسع', 'null Stefan', 1, 1, 3409, 262),
(259, 'send', 'send', 'إرسل', 'إرسال', 'null Stefan', 1, 0, 3090, 200),
(260, 'playlist', 'playlist', 'قائمة التشغي', 'قائمة التشغيل', 'null Stefan', 1, 0, 3179, 333),
(261, 'wait', 'rate', 'إنتظر', 'انتظر', 'null Stefan', 0, 0, 3090, 194),
(262, 'playlist', 'playlist', 'قائمة التشغي', 'قائمة التشغيل', 'null Stefan', 1, 0, 2952, 229),
(263, 'living room', 'living room', 'غرفة المعيش', '', 'null Stefan', 1, 0, 3531, 182),
(264, 'bus', 'bus', 'حافلة', 'حافلة', 'null Stefan', 1, 1, 3117, 730),
(265, 'kitchen', 'kitchen', 'مطبخ', 'مطبخ', 'null Stefan', 1, 1, 2859, 290),
(266, 'photo', 'photo', 'صور', 'صور', 'null Stefan', 1, 1, 3003, 299),
(267, 'logout', 'lockout', 'تسجيل الخروج', 'تسجيل الخروج', 'null Stefan', 0, 1, 3197, 226),
(268, 'cucumber', 'cucumber', 'خيار', 'خيار', 'null Stefan', 1, 1, 3419, 206),
(269, 'scroll', 'scroll', 'انتقل', 'التمرير', 'null Stefan', 1, 0, 3526, 191),
(270, 'move', 'movie', 'إنقل', 'خطوة', 'null Stefan', 0, 0, 2928, 227),
(271, 'play', 'play', 'شغل', 'لعب', 'null Stefan', 1, 0, 2806, 192),
(272, 'speech', 'speech', 'كلام', 'خطاب', 'null Stefan', 1, 0, 3154, 337),
(273, 'pen', '10', 'قلم', 'قلم', 'null Stefan', 0, 1, 2788, 191),
(274, 'read', 'read', 'إقرأ', 'قرأ', 'null Stefan', 1, 0, 13288, 258),
(275, 'downloading', 'downloading', 'تنزيل', 'تحميل', 'null Stefan', 1, 0, 3526, 263),
(276, 'gym', 'gym', 'قاعة رياض', 'الجمنازيوم', 'null Stefan', 1, 0, 3301, 187),
(277, 'banana', 'banana', 'موزة', 'موز', 'null Stefan', 1, 0, 3751, 204),
(278, 'music', 'music', 'أغاني', 'موسيقى', 'null Stefan', 1, 0, 3558, 320),
(279, 'now', 'now', 'الآن', 'الآن', 'null Stefan', 1, 1, 3362, 199),
(280, 'cucumber', 'cucumber', 'خيار', 'خيار', 'null Stefan', 1, 1, 5711, 188),
(281, 'garden', 'cotton', 'حديقة', 'حديقة', 'null Stefan', 0, 1, 3265, 192),
(282, 'delete', 'delete', 'إمسح', 'حذف', 'null Stefan', 1, 0, 3288, 192),
(283, 'pause', 'boss', 'وقوف مؤقت', 'وقفة', 'null Stefan', 0, 0, 12343, 246),
(284, 'listen', 'listen', 'إستمع', 'استمع', 'null Stefan', 1, 0, 3516, 185),
(285, 'right ', 'right', 'اليمين', 'حق', 'null Stefan', 0, 0, 2982, 195),
(286, 'table', 'table', 'طاولة', 'جدول', 'null Stefan', 1, 0, 2962, 277),
(287, 'expand', 'expand', 'وسع', 'وسع', 'null Stefan', 1, 1, 3202, 198),
(288, 'train', 'train', 'قطار', 'قطار', 'null Stefan', 1, 1, 3180, 288),
(289, 'stop', 'stop', 'وقوف', 'توقف', 'null Stefan', 1, 0, 2831, 198),
(290, 'tab ', 'tap', 'انقر', 'علامة التبويب', 'null Stefan', 0, 0, 2953, 325),
(291, 'school', 'school', 'مدرسة', 'مدرسة', 'null Stefan', 1, 1, 8430, 195),
(292, 'login', 'lookin', 'تسجيل الدخول', 'دخول', 'null Stefan', 0, 0, 3626, 192),
(293, 'downloading', 'downloading', 'تنزيل', 'تحميل', 'null Stefan', 1, 0, 3408, 194),
(294, 'logout', 'lookout', 'تسجيل الخروج', 'تسجيل الخروج', 'null Stefan', 0, 1, 3472, 435),
(295, 'music', 'get me out of here', 'أغاني', 'موسيقى', 'null Stefan', 0, 0, 8105, 193);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
