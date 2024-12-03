DROP DATABASE if EXISTS seata_at_order;

create database seata_at_order default character set utf8mb4 default collate utf8mb4_general_ci;
grant all on seata_at_order.* to 'seata'@'%' identified by '123456';
flush privileges;
    
use seata_at_order;
create table order_tbl(id int primary key auto_increment, user_id int not null, commodity_code varchar(50) not null, count int not null, money int not null, status int not null);
insert into order_tbl(user_id, commodity_code, count, money, status) VALUES ('1', 'C00001', 2, 20, 1), ('1', 'C000003', 6, 200, 1);
-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';
