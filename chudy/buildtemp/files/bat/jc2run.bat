call jr2dir
cd bin
call standalone -Djboss.node.name=ds2 --server-config=standalone-full-ha.xml
