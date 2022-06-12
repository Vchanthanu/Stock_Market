INSERT INTO `stockmarket`.`company` (`co_code`, `co_name`, `co_ceo`, `co_turnover`, `co_website`) VALUES ('SAIL', 'Steel Authority of India Ltm', 'Deepak SS', '120000', 'www.sail.com');
INSERT INTO `stockmarket`.`company` (`co_code`, `co_name`, `co_ceo`, `co_turnover`, `co_website`) VALUES ('TAMO', 'Tata Motor', 'Deepak', '12345', 'www.tatamotor.com');

INSERT INTO `stockmarket`.`stock_exchange` (`se_name`, `se_code`) VALUES ('Bombay Stock Exchange', 'BSE');
INSERT INTO `stockmarket`.`stock_exchange` (`se_name`, `se_code`) VALUES ('National Stock Exchange', 'NSE');


INSERT INTO `stockmarket`.`company_stock_exchange` (`cse_co_code`, `cse_se_code`, `cse_price_date`, `cse_stock_price`) VALUES ('SAIL', 'BSE', CURDATE(), '94.50');
INSERT INTO `stockmarket`.`company_stock_exchange` (`cse_co_code`, `cse_se_code`,`cse_price_date`, `cse_stock_price`) VALUES ('SAIL', 'NSE',CURDATE(), '90.10');


INSERT INTO `stockmarket`.`role` (`ro_id`, `ro_type`) VALUES ('1', 'admin');
INSERT INTO `stockmarket`.`role` (`ro_id`, `ro_type`) VALUES ('2', 'user');

INSERT INTO `stockmarket`.`user` (`us_id`, `us_name`, `us_password`, `us_mobile_number`, `us_ro_id`, `us_email`) VALUES ('1', 'D', '1', '1234567890', '2', 'd@gmil.com');
