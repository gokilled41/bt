select 'drop table ' || table_name ||' cascade constraints;'||chr(13)||chr(10) from user_tables where table_name like 'VTXM_%' or table_name like 'VTDM_%' or table_name like 'VT_WF_%RECORD';