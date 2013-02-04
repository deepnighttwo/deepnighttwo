#remote host that need to copy resource to
remoteHosts=()


#workspace
wsName=changetoameaningfulname

remoteDir=/home/mengzang/${wsName}Test


#action marks
mkStoreDir=1
compileProj=1
scp2Remote=1

#store compiled jars
targetDir=/home/mengzang/${wsName}Temp

#external resources to copy to remote host
extralResDir=/home/mengzang/${wsName}Extra

mkdir ${extralResDir}

#US
remoteHosts[${#remoteHosts[@]}]=scos-oih-na-6007.iad6.amazon.com
remoteHosts[${#remoteHosts[@]}]=ops-new-launch-7236.iad7.amazon.com

remoteHosts[${#remoteHosts[@]}]=scos-oih-na-5001.iad5.amazon.com

#CA
remoteHosts[${#remoteHosts[@]}]=scos-oih-na-1003.vdc.amazon.com

#CN
remoteHosts[${#remoteHosts[@]}]=scos-oih-cn-34006.pek4.amazon.com

remoteHosts[${#remoteHosts[@]}]=scos-oih-cn-34003.pek4.amazon.com

#JP
remoteHosts[${#remoteHosts[@]}]=scos-oih-cn-34004.pek4.amazon.com

remoteHosts[${#remoteHosts[@]}]=scos-oih-fe-31003.sea31.amazon.com

#DE
remoteHosts[${#remoteHosts[@]}]=scos-oih-eu-13001.dub3.amazon.com

remoteHosts[${#remoteHosts[@]}]=scos-oih-eu-12002.dub2.amazon.com

#FR

remoteHosts[${#remoteHosts[@]}]=scos-oih-eu-12003.dub2.amazon.com

remoteHosts[${#remoteHosts[@]}]=scos-oih-eu-12004.dub2.amazon.com

#GB
remoteHosts[${#remoteHosts[@]}]=scos-oih-eu-12007.dub2.amazon.com

remoteHosts[${#remoteHosts[@]}]=scos-oih-eu-12008.dub2.amazon.com



#location of compiles jars
projGenLibRoot=/home/mengzang/ws/${wsName}/apollo-overrides
#workspace root
wsRoot=/home/mengzang/ws/${wsName}/src

#coding
if [ ${mkStoreDir} ]; then
echo ============Making Local Target Dir==============
rm -rf $targetDir
mkdir $targetDir
chmod 777 $targetDir
echo local target dir maked ${targetDir}
fi


if [ ${compileProj} ]; then

echo =========Deleting Jars============
rm -rf ${projGenLibRoot}/*;

echo ===========Compiling==============
for proj in ${wsRoot}/*; do
    echo working on $proj
    cd $proj
    brazil-build clean
    brazil-build apollo-pkg
done

echo =========Copying Jars============
for jardir in ${projGenLibRoot}/*; do
    echo copy jars in ${jardir}/lib
    ls -al ${jardir}/lib
    cd ${jardir}/lib
    cp *.jar $targetDir
done
fi

if [ -d ${extralResDir} ]; then
echo =========Copying Extral Resources============
echo copying resource in ${extralResDir}
cd ${extralResDir}
cp -r * $targetDir
fi


echo ===============Files in Traget Dir=================
ls -l ${targetDir}

if [ ${scp2Remote} ]; then
echo =========scp 2 Remote Hosts==========

cp /home/mengzang/sbs.py  ${targetDir}

for ((i=0;i<${#remoteHosts[@]};i++))
do
    echo copying to remote dir ${remoteHosts[$i]}
    ssh ${remoteHosts[$i]} "mkdir ${remoteDir}"
    scp -r ${targetDir}/* ${remoteHosts[$i]}:${remoteDir}/
done
fi
echo ===================finished==================

