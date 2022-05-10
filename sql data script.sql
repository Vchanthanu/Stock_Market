INSERT INTO `stockmarket`.`company` (`co_code`, `co_name`, `co_ceo`, `co_turnover`, `co_website`) VALUES ('SAIL', 'Steel Authority of India Ltm', 'Deepak SS', '120000', 'www.sail.com');
INSERT INTO `stockmarket`.`company` (`co_code`, `co_name`, `co_ceo`, `co_turnover`, `co_website`) VALUES ('TAMO', 'Tata Motor', 'Deepak', '12345', 'www.tatamotor.com');


INSERT INTO `stockmarket`.`company_stock_exchange` (`cse_co_code`, `cse_se_code`, `cse_price_date`, `cse_stock_price`) VALUES ('SAIL', 'BSE', CURDATE(), '94.50');
INSERT INTO `stockmarket`.`company_stock_exchange` (`cse_co_code`, `cse_se_code`,`cse_price_date`, `cse_stock_price`) VALUES ('SAIL', 'NSE',CURDATE(), '90.10');
