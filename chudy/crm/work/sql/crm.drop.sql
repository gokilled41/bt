alter table LotteryAward drop foreign key fk_lottery_award_activity;
alter table LotteryWhitelist drop foreign key fk_lottery_whitelist_activity;
alter table LotteryRecord drop foreign key fk_lottery_record_receiver;
alter table LotteryRecord drop foreign key fk_lottery_record_activity;
alter table LotteryRecord drop foreign key fk_lottery_record_award;

drop table if exists LotteryActivity;
drop table if exists LotteryAward;
drop table if exists LotteryUser;
drop table if exists LotteryWhitelist;
drop table if exists LotteryRecord;
