drop database if exists achatenligne;
create database achatenligne;
use achatenligne;

create table produit (
  id int(11) not null auto_increment,
  code varchar(100) not null unique,
  libelle varchar(100) not null,
  prix double(10,2) not null,
  primary key(id)
);

insert into produit values (1, 'XTV12', 'Pomme', 1.12);
insert into produit values (2, 'WTV99', 'Poire', 1.30);

create table commande(
  id int(11) not null auto_increment,
  date_creation datetime default NOW(),
  primary key(id)
);

create table ligne_commande(
  commande_id int(11) not null,
  produit_id int(11) not null,
  foreign key commande_fk(commande_id) references commande(id),
  foreign key produit_fk(produit_id) references produit(id)
);
