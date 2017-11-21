/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 10.1.13-MariaDB : Database - restoran_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`restoran_db` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `restoran_db`;

/*Table structure for table `bahan` */

DROP TABLE IF EXISTS `bahan`;

CREATE TABLE `bahan` (
  `id_bahan` int(11) NOT NULL,
  `nama_bahan` varchar(30) NOT NULL,
  `tgl_kadaluarsa` date NOT NULL,
  `stok` int(11) NOT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id_bahan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `bahan` */

/*Table structure for table `belanja` */

DROP TABLE IF EXISTS `belanja`;

CREATE TABLE `belanja` (
  `id_belanja` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `total` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_belanja`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `belanja` */

/*Table structure for table `histori_makanan` */

DROP TABLE IF EXISTS `histori_makanan`;

CREATE TABLE `histori_makanan` (
  `no_nota` int(11) NOT NULL,
  `makanan` varchar(30) NOT NULL,
  `harga` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  KEY `no_nota` (`no_nota`),
  CONSTRAINT `histori_makanan_ibfk_1` FOREIGN KEY (`no_nota`) REFERENCES `histori_nota` (`no_nota`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `histori_makanan` */

/*Table structure for table `histori_nota` */

DROP TABLE IF EXISTS `histori_nota`;

CREATE TABLE `histori_nota` (
  `no_nota` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `no_meja` int(2) NOT NULL,
  `tax` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  PRIMARY KEY (`no_nota`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `histori_nota` */

/*Table structure for table `kadaluarsa` */

DROP TABLE IF EXISTS `kadaluarsa`;

CREATE TABLE `kadaluarsa` (
  `id_makanan` int(11) NOT NULL,
  `tgl_kadaluarsa` date NOT NULL,
  `stok` int(11) NOT NULL,
  `status` int(1) DEFAULT NULL,
  KEY `id_makanan` (`id_makanan`),
  CONSTRAINT `kadaluarsa_ibfk_1` FOREIGN KEY (`id_makanan`) REFERENCES `makanan` (`id_makanan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `kadaluarsa` */

/*Table structure for table `makanan` */

DROP TABLE IF EXISTS `makanan`;

CREATE TABLE `makanan` (
  `id_makanan` int(11) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `jenis` smallint(6) NOT NULL,
  `tag` varchar(20) NOT NULL,
  `deskripsi` varchar(60) NOT NULL,
  `harga_beli` int(11) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  `path` varchar(80) NOT NULL,
  PRIMARY KEY (`id_makanan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `makanan` */

insert  into `makanan`(`id_makanan`,`nama`,`jenis`,`tag`,`deskripsi`,`harga_beli`,`harga_jual`,`path`) values 
(1,'Burger',0,'Fast Food','Menggunakan daging pilihan',12000,16000,'img/burger.jpg'),
(2,'Salad',0,'Vegetarian','Makanan sehat untuk tubuh yang kuat',6000,10000,'img/salad.png'),
(3,'Nasi Goreng',0,'Indonesian','Sentuhan rempah menggugah sukma',13000,17000,'img/nasi_goreng.jpg'),
(4,'Spaghetti',0,'Italian','Lezat di lidah mantap di hati',16000,20000,'img/spaghetti.jpg'),
(5,'Bakso',0,'Indonesian','Bakso kuah pembangkit semangat',8000,12000,'img/bakso.jpeg'),
(6,'Bandrek',1,'Indonesian','Penghangat di kala penat',5000,8000,'img/bandrek.jpg'),
(7,'Bajigur',1,'Indonesian','Pembuncah kegalauan yang manjur',7000,10000,'img/bajigur.jpg'),
(8,'Moccacino',1,'Italian','Kopi penghilang sepi',9000,12000,'img/moccacino.jpg'),
(9,'Cola',1,'Fast Food','Moodbooster di setiap suasana',4000,7000,'img/cola.jpg'),
(10,'Teh Hangat',1,'Vegetarian','Mari ngeteh mari bicara',4000,6500,'img/teh.jpg'),
(11,'Wajit',2,'Indonesian','Panganan manis dalam bungkusan unik',4000,6000,'img/wajit.jpg'),
(12,'Ali Agrem',2,'Indonesian','Bulat dan manis',5000,8000,'img/ali_agrem.jpg'),
(13,'Tiramisu',2,'Italian','Kue manis khas italia',10000,14000,'img/tiramisu.jpeg'),
(14,'Cherry Pie',2,'Fast Food','Kudapan manis khas amrik',8000,11000,'img/cherry_pie.jpeg'),
(15,'Strawberry Cake',2,'Vegetarian','Penutup yang pas untuk para vegan',12000,16000,'img/strawberry_chesscake.jpg');

/*Table structure for table `meja` */

DROP TABLE IF EXISTS `meja`;

CREATE TABLE `meja` (
  `no_meja` int(3) NOT NULL,
  `seat` int(2) NOT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`no_meja`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `meja` */

/*Table structure for table `nota` */

DROP TABLE IF EXISTS `nota`;

CREATE TABLE `nota` (
  `no_nota` int(11) NOT NULL AUTO_INCREMENT,
  `tanggal` date NOT NULL,
  `tax` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `no_meja` int(2) NOT NULL,
  `items` int(11) DEFAULT NULL,
  PRIMARY KEY (`no_nota`),
  KEY `no_meja` (`no_meja`),
  CONSTRAINT `nota_ibfk_1` FOREIGN KEY (`no_meja`) REFERENCES `meja` (`no_meja`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `nota` */

/*Table structure for table `rincian_bahan` */

DROP TABLE IF EXISTS `rincian_bahan`;

CREATE TABLE `rincian_bahan` (
  `id_makanan` int(11) NOT NULL,
  `id_bahan` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  KEY `id_makanan` (`id_makanan`),
  KEY `id_bahan` (`id_bahan`),
  CONSTRAINT `rincian_bahan_ibfk_1` FOREIGN KEY (`id_makanan`) REFERENCES `makanan` (`id_makanan`),
  CONSTRAINT `rincian_bahan_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rincian_bahan` */

/*Table structure for table `rincian_belanja` */

DROP TABLE IF EXISTS `rincian_belanja`;

CREATE TABLE `rincian_belanja` (
  `id_makanan` int(11) NOT NULL,
  `id_belanja` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  PRIMARY KEY (`id_makanan`,`id_belanja`),
  KEY `id_belanja` (`id_belanja`),
  CONSTRAINT `rincian_belanja_ibfk_1` FOREIGN KEY (`id_makanan`) REFERENCES `makanan` (`id_makanan`),
  CONSTRAINT `rincian_belanja_ibfk_2` FOREIGN KEY (`id_belanja`) REFERENCES `belanja` (`id_belanja`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rincian_belanja` */

/*Table structure for table `rincian_jajan` */

DROP TABLE IF EXISTS `rincian_jajan`;

CREATE TABLE `rincian_jajan` (
  `no_nota` int(11) NOT NULL,
  `id_makanan` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  PRIMARY KEY (`no_nota`,`id_makanan`),
  KEY `id_makanan` (`id_makanan`),
  CONSTRAINT `rincian_jajan_ibfk_1` FOREIGN KEY (`no_nota`) REFERENCES `nota` (`no_nota`),
  CONSTRAINT `rincian_jajan_ibfk_2` FOREIGN KEY (`id_makanan`) REFERENCES `makanan` (`id_makanan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rincian_jajan` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
