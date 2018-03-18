--
-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 7.3.137.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 18.03.2018 23:28:34
-- Версия сервера: 5.7.19
-- Версия клиента: 4.1
--


-- 
-- Отключение внешних ключей
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Установить режим SQL (SQL mode)
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Установка кодировки, с использованием которой клиент будет посылать запросы на сервер
--
SET NAMES 'utf8';

--
-- Установка базы данных по умолчанию
--
USE module3;

--
-- Удалить таблицу "tables"
--
DROP TABLE IF EXISTS tables;

--
-- Удалить таблицу "data"
--
DROP TABLE IF EXISTS data;

--
-- Установка базы данных по умолчанию
--
USE module3;

--
-- Создать таблицу "data"
--
CREATE TABLE data (
  id int(11) NOT NULL AUTO_INCREMENT,
  row_idx int(11) DEFAULT NULL,
  col_idx int(11) DEFAULT NULL,
  text varchar(255) DEFAULT NULL,
  table_id int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id (id),
  CONSTRAINT FK_data_table_id FOREIGN KEY (table_id)
  REFERENCES tables (id) ON DELETE NO ACTION ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 48
AVG_ROW_LENGTH = 4096
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
-- Создать таблицу "tables"
--
CREATE TABLE tables (
  id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  col_count int(11) DEFAULT NULL,
  row_count int(11) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 25
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

-- 
-- Вывод данных для таблицы data
--
INSERT INTO data VALUES
(44, 0, 0, '211', 1),
(45, 0, 1, '222', 1),
(46, 0, 2, 'f', 1),
(47, 0, 3, NULL, 1);

-- 
-- Вывод данных для таблицы tables
--
INSERT INTO tables VALUES
(1, 'table1', 4, 1);
-- 
-- Восстановить предыдущий режим SQL (SQL mode)
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
