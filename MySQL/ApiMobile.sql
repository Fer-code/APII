DROP DATABASE IF EXISTS dbApiMobile;
CREATE DATABASE IF NOT EXISTS dbApiMobile;
USE dbApiMobile;

CREATE TABLE IF NOT EXISTS tbUser(
	id_user int auto_increment primary key,
    nome_user varchar(200),
    email varchar(50) unique default "",
    password_user varchar(20)
    );
    
    
CREATE TABLE IF NOT exists tbVisitedMuseum(
	id_museum int auto_increment primary key,
    nome_museum varchar(75),
	address_museum text,
    user_museum int,
    foreign key(user_museum) references tbUser(id_user)
    on delete cascade
);


CREATE TABLE IF NOT EXISTS tbArtistFavorito(
	id_artista int auto_increment primary key,
    id_user int not null,
    foreign key (id_user) references tbUser(id_user)
);

    CREATE TABLE IF NOT EXISTS tbExpoFavorito(
	id_expo int auto_increment primary key,
    id_user int not null,
    foreign key (id_user) references tbUser(id_user)
);
    
	