call gr datam3o "localhost" "pek-wkst67766"
call gr datam3o "@ENABLE_DOMAINSERVICE@" "true"
call gr datam3o "@ENABLE_FEEDSERVER@" "false"
call gr datam3o "@ENABLE_HAFEEDSERVER@" "false"
call gr datam3o "@ENABLE_M3OSERVER@" "false"
call gr datam3o "@ENABLE_M3OSERVER_MONITORING@" "false"
call gr datam3o "@ENABLE_ESMS_MONITORING@" "false"
call gr datam3o "@MONITORING_INTERVAL@" "false"
call gr datam3o "@VIRTUALSERVER_PROVIDER_URL@" "http-remoting://pek-wkst67766:8080"
call gr datam3o "server>true<" "server>false<"
call gr datam3o "monitor>true<" "monitor>false<"
call gr jch "@CLUSTER_NODE_IP@" "pek-wkst67766"