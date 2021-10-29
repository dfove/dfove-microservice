CREATE TABLE `properties` (
  `id` int(11) NOT NULL,
  `key` varchar(50) NOT NULL,
  `value` varchar(500) NOT NULL,
  `application` varchar(50) NOT NULL,
  `profile` varchar(50) NOT NULL,
  `label` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO properties VALUES(1, 'com.didispace.message', 'test-stage-master', 'config-client', 'stage', 'master');
INSERT INTO properties VALUES(2, 'com.didispace.message', 'test-online-master', 'config-client', 'online', 'master');
INSERT INTO properties VALUES(3, 'com.didispace.message', 'test-online-develop', 'config-client', 'online', 'develop');
INSERT INTO properties VALUES(4, 'com.didispace.message', 'hello-online-master', 'hello-service', 'online', 'master');
INSERT INTO properties VALUES(5, 'com.didispace.message', 'hello-online-develop', 'hello-service', 'online', 'develop');