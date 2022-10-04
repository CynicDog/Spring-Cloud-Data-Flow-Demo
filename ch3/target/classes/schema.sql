create table IF NOT EXISTS `movies`(
    `id` int primary key auto_increment,
    `title` varchar(250),
    `actor` varchar(100),
    `year` smallint);