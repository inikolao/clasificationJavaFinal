#Nikolaou Ioannis AM 4504 Martios 2015
#!/bin/bash

cd $HOME/NetBeansProjects/clasy/trainDat
Size="$(ls -1 | wc -l)"
Size=$(expr $Size - 1)
echo $Size
cd ..
portSt=9230
echo $portSt
localIP="$(curl ifconfig.me/ip)"
echo $localIP
#read -r b
# echo "checking ports....."
# ts="$(nc -z $localIp $portSt; echo $?)"
# if [ $ts -eq 1]
# then
#	echo "port $portSt is available"
# fi
source /root/local/share/jubatus/jubatus.profile
jubaclassifier --configpath clasfy.json --timeout 0 --rpc-port 9230&
cd target
java -Xms1G -Xmx8G -jar clasy-0.1.jar
