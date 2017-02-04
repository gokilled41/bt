/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   id                   integer not null auto_increment,
   userName             varchar(255) not null,
   password             varchar(255),
   nickName             varchar(255),
   employeeNo           varchar(255),
   phone                varchar(255),
   department           varchar(255),
   email                varchar(255),
   businessType         varchar(255),
   position             varchar(255),
   approvalRole         varchar(255),
   adminRole            varchar(255),
   status               varchar(255),
   createTime           datetime,
   timestamp            datetime,
   primary key (id)
)
DEFAULT CHARSET = utf8;

alter table User add index pk_user_userName (userName);
alter table User add index pk_user_nickName (nickName);

/*==============================================================*/
/* Table: Auth                                                  */
/*==============================================================*/
create table Auth
(
   id                   integer not null auto_increment,
   applyId              varchar(255),
   userName             varchar(255),
   authRole             varchar(255),
   timestamp            datetime,
   primary key (id)
)
DEFAULT CHARSET = utf8;

insert into User values (null, 'admin', 'ICy5YqxZB1uWSwcVLSNLcA==', '管理员', '管理员工号1', '13612345678', '管理部门', 'admin@company.com', '管理', '职员', '', '基本账号管理', '正常', now(), now()); /* password 123 */

commit;
