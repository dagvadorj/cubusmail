/etc/init.d/tomcat6 stop
rm -r /srv/tomcat6/webapps/cubusmail
cp cubusmail.war /srv/tomcat6/webapps
/etc/init.d/tomcat6 start
