INSERT INTO `stockmarket`.`company` (`co_code`, `co_name`, `co_ceo`, `co_turnover`, `co_website`) VALUES ('SAIL', 'Steel Authority of India Ltd', 'Deepak SS', '120000', 'www.sail.com');
INSERT INTO `stockmarket`.`company` (`co_code`, `co_name`, `co_ceo`, `co_turnover`, `co_website`) VALUES ('TAMO', 'Tata Motor', 'Deepak', '12345', 'www.tatamotor.com');

INSERT INTO `stockmarket`.`stock_exchange` (`se_name`, `se_code`) VALUES ('Bombay Stock Exchange', 'BSE');
INSERT INTO `stockmarket`.`stock_exchange` (`se_name`, `se_code`) VALUES ('National Stock Exchange', 'NSE');


INSERT INTO `stockmarket`.`company_stock_exchange` (`cse_co_code`, `cse_se_code`, `cse_price_date`, `cse_stock_price`) VALUES ('SAIL', 'BSE', CURDATE(), '94.50');
INSERT INTO `stockmarket`.`company_stock_exchange` (`cse_co_code`, `cse_se_code`,`cse_price_date`, `cse_stock_price`) VALUES ('SAIL', 'NSE',CURDATE(), '90.10');



INSERT INTO `stockmarket`.`user` (`us_id`, `us_name`, `us_password`, `us_mobile_number`, `us_email`) VALUES ('1', 'user', 'user', '1234567890', 'user@gmil.com');
