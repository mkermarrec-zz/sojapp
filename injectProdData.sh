#!/bin/bash

remoteUser=b1cb46391f3641
remotePwd=161324bf
remoteHost=eu-cdbr-west-01.cleardb.com
remotePort=3306
remoteDb=heroku_403f6cf0439d1e3

tempFile="./dump.sql"

localUser=root
localPwd=root
localHost=localhost
localPort=3306
localDb=sojapp_db

# retrieve prod data into sql file 
mysqldump -h $remoteHost -u $remoteUser -p${remotePwd} $remoteDb > $tempFile

# rename tables with uppercase at first caracter
sed -i "s/\`borrowing\`/\`Borrowing\`/g" $tempFile
sed -i "s/\`user\`/\`User\`/g" $tempFile 
sed -i "s/\`game\`/\`Game\`/g" $tempFile
sed -i "s/\`user_borrowing\`/\`User_Borrowing\`/g" $tempFile

# delete local database
mysql -h $localHost -u $localUser -p${localPwd} -e "drop database $localDb"

# create local database
mysql -h $localHost -u $localUser -p${localPwd} -e "create database $localDb"

# inject data
mysql -h $localHost -u $localUser -p${localPwd} $localDb < $tempFile

# remove prod sql file
rm $tempFile

exit 0
