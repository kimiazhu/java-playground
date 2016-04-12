/*==============================================================*/
/*=====================Table: auto increment====================*/
/*==============================================================*/
CREATE TABLE AUTOINCREMENT_SEQ
(
  id                        INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id) 
);

/*==============================================================*/
/*======================Table: SEQUENCE=========================*/
/*==============================================================*/
CREATE TABLE SEQUENCE
(
  name                      VARCHAR(64)                    NOT NULL,
  current_value             BIGINT                            NOT NULL,
  step                      INT                            NOT NULL DEFAULT 1,
  PRIMARY KEY (name) 
);

ALTER TABLE SEQUENCE COMMENT 'Sequence自增表';

DROP FUNCTION IF EXISTS currval;
DELIMITER $$
CREATE FUNCTION currval (seq_name VARCHAR(64))
RETURNS INTEGER
CONTAINS SQL
BEGIN
  DECLARE value INTEGER;
  SET value = 0;
  SELECT current_value INTO value
  FROM SEQUENCE
  WHERE name = seq_name;
  RETURN value;
END $$
DELIMITER ;

--DROP FUNCTION IF EXISTS nextval;
--DELIMITER $$
--CREATE FUNCTION nextval (seq_name VARCHAR(64))
--RETURNS INTEGER
--CONTAINS SQL
--BEGIN
--   UPDATE SEQUENCE
--   SET current_value = current_value + step
--   WHERE name = seq_name;
--   RETURN currval(seq_name);
--END;
--$$ 
--DELIMITER ;

DROP FUNCTION IF EXISTS nextval;
DELIMITER $$
CREATE FUNCTION nextval (seq_name VARCHAR(64))
RETURNS INTEGER
CONTAINS SQL
BEGIN
   UPDATE SEQUENCE
   SET current_value = last_insert_id(current_value + step) 
   WHERE name = seq_name;
   RETURN last_insert_id();
END;
$$ 
DELIMITER ;

DROP FUNCTION IF EXISTS setval;
DELIMITER $$
CREATE FUNCTION setval (seq_name VARCHAR(64), value INTEGER)
RETURNS INTEGER
CONTAINS SQL
BEGIN
   UPDATE SEQUENCE
   SET current_value = value
   WHERE name = seq_name;
   RETURN currval(seq_name);
END;
$$
DELIMITER ;
