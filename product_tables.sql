use BIP_project
go

create table Brands
	(
	id int primary key not null,
	name varchar(50) not null
	)
	go
	
create table Food_groups
	(
	id int primary key not null,
	name varchar(50) not null,
	)
	go
	
	

create table Products
	(GTIN_13 bigint primary key not null,
	name varchar(50) not null,
	size varchar(20) not null,
	serving_size varchar(20),
	carbohydrates varchar(15), 
	sugar varchar(15),
	fat varchar(15),
	calories varchar(15),
	barnd_id int foreign key references Brands(id) ,
	food_group_id int foreign key references Food_groups(id)
	)
	go
