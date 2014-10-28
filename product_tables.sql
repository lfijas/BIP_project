use BIP_project
go

/*drop table Products
drop table Food_groups
drop table Brands
go*/

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
	(barcode bigint primary key not null,
	name varchar(100) not null,
	size int,
	unit_size varchar(10),
	serving int,
	carbohydrates float,
	proteins float, 
	sugar float,
	fat float,
	saturated_fat float,
	cholesterol float,
	fiber float,
	sodium float,
	calcium float,
	iron float,
	vitamin_a float,
	vitamin_c float,
	calories float,
	barnd_id int foreign key references Brands(id) ,
	food_group_id int foreign key references Food_groups(id)
	)
	go
