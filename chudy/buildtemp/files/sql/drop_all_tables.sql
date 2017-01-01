select 'drop table ' || table_name ||' cascade constraints;'||chr(13)||chr(10) from user_tables;
select 'drop view ' || view_name||';'||chr(13)||chr(10) from user_views;
select 'drop sequence ' || sequence_name||';'||chr(13)||chr(10) from user_sequences;
select 'drop function ' || object_name||';'||chr(13)||chr(10) from user_objects where object_type='FUNCTION';
select 'drop procedure ' || object_name||';'||chr(13)||chr(10) from user_objects where object_type='PROCEDURE';
select 'drop package ' || object_name||';'||chr(13)||chr(10) from user_objects where object_type='PACKAGE';