/*==============================================================*/
/* Table: LotteryActivity                                       */
/*==============================================================*/
create table LotteryActivity 
(
   id integer not null auto_increment,
   name varchar(255) not null,
   cover varchar(255),
   gif varchar(255),
   start datetime,
   end datetime,
   participants integer,
   whitelist tinyint(1),
   creator varchar(255),
   createtime datetime,
   open tinyint(1),
   description varchar(1000),
   primary key (id)
)
DEFAULT CHARSET = utf8;

alter table LotteryActivity comment '抽奖活动';
alter table LotteryActivity add index pk_lottery_activity (name);


/*==============================================================*/
/* Table: LotteryAward                                        */
/*==============================================================*/
create table LotteryAward 
(
   id integer not null auto_increment,
   name varchar(255) not null,
   coupon varchar(255),
   activityId integer,
   category varchar(255),
   level integer,
   quantity integer,
   way varchar(255),
   description varchar(255),
   primary key (id)
)
DEFAULT CHARSET = utf8;

alter table LotteryAward comment '抽奖奖项';
alter table LotteryAward add index pk_lottery_award (name);


/*==============================================================*/
/* Table: LotteryUser                                        */
/*==============================================================*/
create table LotteryUser
(
   id integer not null auto_increment,
   name varchar(255) not null,
   openId varchar(255),
   phone varchar(255),
   primary key (id)
)
DEFAULT CHARSET = utf8;

alter table LotteryUser comment '抽奖用户';
alter table LotteryUser add index pk_lottery_user_name (name);
alter table LotteryUser add index pk_lottery_user_openid (openId);
alter table LotteryUser add index pk_lottery_user_phone (phone);


/*==============================================================*/
/* Table: LotteryWhitelist                                       */
/*==============================================================*/
create table LotteryWhitelist 
(
   activityId integer,
   userPhone varchar(255),
   primary key (activityId, userPhone)
)
DEFAULT CHARSET = utf8;

alter table LotteryWhitelist comment '抽奖白名单';


/*==============================================================*/
/* Table: LotteryRecord                                        */
/*==============================================================*/
create table LotteryRecord 
(
   id integer not null auto_increment,
   activityId integer,
   awardId integer,
   couponContent varchar(255),
   awardCode varchar(255),
   postName varchar(255),
   postPhone varchar(255),
   postAddress varchar(255),
   receiverId integer,
   receiveTime datetime,
   primary key (id)
)
DEFAULT CHARSET = utf8;

alter table LotteryRecord comment '抽奖记录';
alter table LotteryRecord add index pk_lottery_award (awardId);


alter table LotteryAward add constraint fk_lottery_award_activity foreign key (activityId) 
      references LotteryActivity (id) on delete restrict on update restrict;
alter table LotteryWhitelist add constraint fk_lottery_whitelist_activity foreign key (activityId) 
      references LotteryActivity (id) on delete restrict on update restrict;
alter table LotteryRecord add constraint fk_lottery_record_receiver foreign key (receiverId) 
      references LotteryUser (id) on delete restrict on update restrict;
alter table LotteryRecord add constraint fk_lottery_record_activity foreign key (activityId) 
      references LotteryActivity (id) on delete restrict on update restrict;
alter table LotteryRecord add constraint fk_lottery_record_award foreign key (awardId) 
      references LotteryAward (id) on delete restrict on update restrict;

