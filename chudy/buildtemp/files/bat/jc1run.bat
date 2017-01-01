call jr1dir
cd bin
call standalone -Djboss.node.name=ds1 --server-config=standalone-full-ha.xml
