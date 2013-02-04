import subprocess
import time


if __name__ == "__main__":
    optQtyStart = 0
    optQtyEnd = 50
    dummyVals = ["true", "false"]
    scriptDir = "/home/mengzang/releasecmp/"
    logDir = "/local/hamlogs/US/case/"
    for dummyVal in dummyVals:
        for i in range(optQtyStart, optQtyEnd):
            scriptFileStr = scriptDir + "DummyFix_" + dummyVal + "_OQ_" + str(i) + ".sh"
            scriptFile = open(scriptFileStr, "w")
            scriptFile.write("export HAM_SCHED_ID=65491_1346835600;")
            scriptFile.write("export EXECUTION_UUID=9915272621159609196672809998197424129;")
            scriptFile.write("export TZ=US/Pacific;")
            scriptFile.write("export ORG=CA;")
            scriptFile.write("export DOMAIN=prod;")
            scriptFile.write("export IOG=11;")
            scriptFile.write("export INSPECTOR_OVERRIDE_FILE=/var/tmp/inspector_override.cfg;")
            scriptFile.write("export OIH_NFS=scos-oih-nfs-na-1101.vdc.amazon.com;")
            scriptFile.write("export AMAZON_ENVIRONMENT=ca-hq;")
            scriptFile.write("export PATH=${PATH}:/opt/third-party/bin;")
            scriptFile.write("export LD_LIBRARY_PATH=/apollo/env/Prioritization/lib;")
            scriptFile.write("export DB_HOST=oih-mysql-na.db.amazon.com;")
            scriptFile.write("export ORACLE_HOME=/opt/app/oracle/product/10.2.0.2/client;")
            scriptFile.write("export REALM=CAAmazon;")
            scriptFile.write("export ROOT=/apollo/env/Prioritization;")
            scriptFile.write("export CONFIG_FILE=/apollo/env/Prioritization/brazil-config/override/Priority.cfg;")
            scriptFile.write("export HAM_SCHED_ID=65491_1346835600;")
            scriptFile.write("export oq=" + str(i) + ";")
            scriptFile.write("export printlog=false;")
            scriptFile.write("export dummyfix=" + dummyVal + ";")
            scriptFile.write("/home/mengzang/releasecmp/etl.pl  --root ${ROOT}  --domain ${DOMAIN} --realm ${REALM} --end-date 2012-09-09 --override ${CONFIG_FILE} --weekly --transforms ParallelUnhealthyAsinDetails --offset-date --scope all --model new ;")
            scriptFile.close()
            command = scriptFileStr + " > " + logDir + "DummyFix_" + dummyVal + "_OQ_" + str(i) + ".log"
            print command
            subprocess.Popen(command, shell=True)
            time.sleep(20)
