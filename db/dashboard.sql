-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Procedure `pro_ds_getChild` 查询树形结构的存储过程
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS `pro_ds_getChild`;
CREATE PROCEDURE `pro_ds_getChild`(rootId varchar(36))
BEGIN
    DECLARE ptemp text; -- 由于是批量导入的数据，不是用id做标识，使用varchar字符串撑爆了
    DECLARE ctemp text;
    SET ctemp = rootId;
    SET ptemp = '#';
    WHILE ctemp is not null DO
      SET ptemp = concat(ptemp, ',', ctemp);
      SELECT group_concat(id)
      INTO ctemp
      FROM ds_dept
      WHERE FIND_IN_SET(pid, ctemp)>0;
    END WHILE;
        select
        id,pid
        from ds_dept where find_in_set(id,ptemp);
  END;

-- -----------------------------------------------------
-- Procedure `pro_ds_getLevel42Child` 查询树形结构经销商信息
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS `pro_ds_getLevel42Child`;
CREATE PROCEDURE `pro_ds_getLevel42Child`(rootId varchar(36))
BEGIN
    DECLARE ptemp text; -- 由于是批量导入的数据，不是用id做标识，使用varchar字符串撑爆了
    DECLARE ctemp text;
    SET ctemp = rootId;
    SET ptemp = '#';
    WHILE ctemp is not null DO
      SET ptemp = concat(ptemp, ',', ctemp);
      SELECT group_concat(id)
      INTO ctemp
      FROM ds_dept
      WHERE FIND_IN_SET(pid, ctemp)>0;
    END WHILE;
        select
        id,pid,`name`,`level`
        from ds_dept where find_in_set(id,ptemp) and `level` = '4-2';
  END;


