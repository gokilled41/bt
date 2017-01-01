call btdir
call ant sjb-clean-wildfly

call y
cd bw\src
call ant common-sync-home

call y
cd m3o\server\src
call ant common-sync-home

call y
cd m3o\ui\src
call ant common-sync-home

call y
cd m3o\j2ee\src
call ant common-sync-home

call y
cd m3o\bpa\emf\server\src
call ant common-sync-home

call y
cd m3o\projectrole\src
call ant common-sync-home

call y
cd m3o\utilities\src
call ant common-sync-home

call y
cd m3o\collaboration\src
call ant common-sync-home

call y
cd m3o\workflow\src
call ant common-sync-home

call y
cd esms\src
call ant common-sync-home

call y
cd opbook\src
call ant common-sync-home
