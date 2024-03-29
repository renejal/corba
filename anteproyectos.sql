CREATE DATABASE bdAnteproyecto

drop table if exists USUARIOS_ANTEPROYECTO;
/* table : anteproyecto */
create table ANTEPROYECTO
(
   ID_ANTEPROYECTO      varchar(30) not null,
   TITULO               varchar(50) not null,
   MODALIDAD            varchar(30) not null,
   ESTADO               numeric(8,0) default 1,
   FECHAREGISTRO        varchar(20) not null,
   FECHAAPROBACION      varchar(20),
   CONCEPTO             numeric(8,0) default 2,
   primary key (ID_ANTEPROYECTO)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO
(
   ID_USUARIO           varchar(30) not null,
   ROL                  numeric(8,0) not null,
   NOMBRE               varchar(50) not null,
   APELLIDO             varchar(50) not null,
   USER                 varchar(20) not null,
   PASSWORD             varchar(20) not null,
   primary key (ID_USUARIO)
);

/*==============================================================*/
/* Table: USUARIOS_ANTEPROYECTO                                 */
/*==============================================================*/
create table USUARIOS_ANTEPROYECTO
(
   ID_ANTEPROYECTO      varchar(30) not null,
   ID_USUARIO           varchar(30) not null,
   ROL_ANTEPROYECTO     numeric(8,0)default null,
   CONCEPTO             numeric(8,0) default 2,
   FECHAREVISION        varchar(20) ,
   primary key (ID_ANTEPROYECTO,ID_USUARIO)
);

alter table USUARIOS_ANTEPROYECTO add constraint FK_USUARIOS_USUARIOS__ANTEPROY foreign key (ID_ANTEPROYECTO)
      references ANTEPROYECTO (ID_ANTEPROYECTO) on delete restrict on update restrict;

alter table USUARIOS_ANTEPROYECTO add constraint FK_USUARIOS_USUARIOS__USUARIO foreign key (ID_USUARIO)
      references USUARIO (ID_USUARIO) on delete restrict on update restrict;


INSERT INTO `usuario`(`ID_USUARIO`, `ROL`, `NOMBRE`, `APELLIDO`, `USER`, `PASSWORD`) VALUES (2,1,"pedro","sanches","pedro","123");
INSERT INTO `usuario`(`ID_USUARIO`, `ROL`, `NOMBRE`, `APELLIDO`, `USER`, `PASSWORD`) VALUES (3,2,"pedro3","sanches3","pedro3","123");
INSERT INTO `usuario`(`ID_USUARIO`, `ROL`, `NOMBRE`, `APELLIDO`, `USER`, `PASSWORD`) VALUES (4,3,"pedro","sanches4","pedro4","123");
INSERT INTO `usuario`(`ID_USUARIO`, `ROL`, `NOMBRE`, `APELLIDO`, `USER`, `PASSWORD`) VALUES (5,4,"pedro5","sanches5","pedro3","123");
INSERT INTO `usuario`(`ID_USUARIO`, `ROL`, `NOMBRE`, `APELLIDO`, `USER`, `PASSWORD`) VALUES (10,1,"pedroest","sanches","pedro","123");
INSERT INTO `usuario`(`ID_USUARIO`, `ROL`, `NOMBRE`, `APELLIDO`, `USER`, `PASSWORD`) VALUES (15,1,"pedroest3","sanches3","pedro3","123");
INSERT INTO `usuario`(`ID_USUARIO`, `ROL`, `NOMBRE`, `APELLIDO`, `USER`, `PASSWORD`) VALUES (20,1,"pedroestd","sanches4","pedro4","123");
INSERT INTO `usuario`(`ID_USUARIO`, `ROL`, `NOMBRE`, `APELLIDO`, `USER`, `PASSWORD`) VALUES (21,1,"pedroetsd5","sanches5","pedro3","123")

