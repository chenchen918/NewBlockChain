SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_transaction
-- ----------------------------
DROP TABLE IF EXISTS `t_transaction`;
CREATE TABLE `t_transaction`
(
    `transaction_id`      int(11)  NOT NULL auto_increment,
    `txid`                char(255),
    `txhash`              char(64),
    `time`                bigint   not null,
    `amount`              double,
    `fees`                double,
    `confirmations`       int(11),
    `status`              tinyint,
    `sizeOnDisk`          int,
    `weight`              int,
    `total_input`         double,
    `total_output`        double,
    `fee_per_byte`        double,
    `fee_per_weight_unit` double,
    `block_id`            int,
    PRIMARY KEY (`transaction_id`),
    index `idx_txid` (`txid`),
    index `idx_txhash` (`txhash`),
    index `idx_time` (`time`),
    index `idx_block_id` (`block_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  auto_increment = 1;
