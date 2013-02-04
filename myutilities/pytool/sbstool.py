import sys
import os
import shutil
import itertools

org = None

realm = None

sbsname = None

sbsDate = None

localdir = None

cdo = "/apollo/env/Prioritization/var/output/data/prod/%sAmazon"

cdi = "/apollo/env/Prioritization/var/input/prod/%sAmazon"

hamlogdir = '/local/hamlogs/%s/%s'

etlFile = "C:/sbs/etl.pl" #/apollo/env/Prioritization/bin/etl.pl'

orgRealmMapping = {"US":"USAmazon", "CA":"CAAmazon", "JP":"JPAmazon", "CN":"CNAmazon", "GB":"GBAmazon", "FR":"FRAmazon", "DE":"DEAmazon", "IT":"ITAmazon", "ES":"ESAmazon", "IN":"INAmazon"}



def genETL():
    etlFileLocal = localdir + os.sep + "etl.pl"
    if(os.path.isfile(etlFileLocal)):
        print "ETL File exists in local dir. skipping etl gen..."
        return
    print " == == = Generating etl.pl to current dir == == = "
    jarFiles = getFilesInDir(localdir, ".jar")

    copyFile(etlFile, etlFileLocal);

    if len(jarFiles) == 0:
        print "No jar file in current dir. just copy ETL file."
        return

    etlFileLocalRead = open(etlFileLocal, "r")
    etlFileLocalContent = []
    for line in etlFileLocalRead:
        if line.startswith("my $classpath"):
            etlFileLocalContent.append("#" + line)
            etlFileLocalContent.append("my $classpath = `find " + localdir + " -name '*.jar' -printf '%h/%f:'`" + os.linesep);
            notIncluded = ""
            for jarFile in jarFiles:
                notIncluded += " -and -not -name '" + jarFile + "' "
            etlFileLocalContent.append(("$classpath .= `find $root/lib -follow -name '*.jar' %s -printf " % (notIncluded)) + " '%h/%f:'`" + os.linesep)
        else:
            etlFileLocalContent.append(line)

    etlFileLocalRead.close()
    etlFileLocalWrite = open(etlFileLocal, "w")
    for line in etlFileLocalContent:
        etlFileLocalWrite.write(line)

    etlFileLocalWrite.close()
    print "=====etl.pl gen complete====="

def copyFile(source, target):
    source = open(source, "r")
    target = open(target, "w")
    for line in source:
        target.write(line)
    source.close()
    target.close()

def getFilesInDir(dirPath, *ends):
    ret = []
    filesInDir = os.listdir(dirPath)
    for filename in filesInDir:
        if endWith(filename, ends):
            ret.append(filename)
    return ret


def genShell():
    shellFile = localdir + os.sep + sbsname + ".sh"
    if(os.path.isfile(shellFile)):
        print "File exists:", shellFile
        override = raw_input("Override Shell File?(y/n)")
        if(override == "yes" or override == "y"):
            print "Override shell file"
        else:
            print "Skipp shell gen"
            return
    stdoutFiles = getFilesInDir(hamlogdir, ".stdout")
    matchFiles = []
    for stdoutFile in stdoutFiles:
        if(stdoutFile.find(sbsname) >= 0):
            matchFiles.append(stdoutFile)

    chosenFile = None
    if(len(matchFiles) > 1):
        print "Please Choose a stdout file to extract command:"
        counter = 1
        for matchFile in matchFiles:
            print counter, matchFile
            counter += 1
        index = input("Choose File:") - 1
        chosenFile = matchFiles[index]
    elif len(matchFiles) == 1:
        chosenFile = matchFiles[0]
    else:
        print "No file found to match " + sbsname
        return

    fileContent = extractExecutionCommandFromLog(hamlogdir + os.sep + chosenFile)
    if fileContent == None:
        return

    shellFile = os.open(shellFile, "w")
    for line in fileContent:
        shellFile.write(line)

    shellFile.close()


def extractExecutionCommandFromLog(logfile):
    logfile = os.open(logfile, "r");
    commandLine = None
    startMark = "executing"
    for line in logfile:
        idx = line.find(startMark)
        if idx >= 0:
            commandLine = line[idx + len(startMark)]
            break
    if commandLine == None:
        print "Commandline not found in file ", logfile
        return None

    start = 0
    curr = 0
    ret = []
    for ch in commandLine:
        curr += 1
        if ch == ';':
            command = commandLine[start:curr] + os.sep
            ret.append(command)
            start = curr
    return ret



def backupFile4SBS():

    pass

def getLastSunday():
    global sbsDate
    sbsDate = "2012-07-22"


def endWith(content, ends):
    return True in itertools.imap(content.endswith, ends)

def main():
    global org, realm, sbsname, sbsDate, localdir, cdo, cdi, hamlogdir, etlFile, orgRealmMapping
    print os.getcwd()

    print sys.argv[:]
    if(len(sys.argv) < 3):
        print "Need org(us,cn), sbs name(InventoryHealthy), sbs date(last Sunday by default) as parameter"
        print "Current parameters: ", sys.argv[:]
        sys.exit()
    localdir = os.getcwd()
    org = sys.argv[1]
    org = org.upper()
    realm = orgRealmMapping[org]
    cdo = cdo % (org)
    cdi = cdi % (org)
    sbsname = sys.argv[2]

    if(len(sys.argv) == 3):
        getLastSunday()
    else:
        sbsDate = sys.argv[3]


    hamlogdir = hamlogdir % (org, sbsDate)
    print "Org:", org, ". Realm:", realm, ". Local Dir:", localdir, "cdi:", cdi, "cdo:", cdo, "hamlog dir:", hamlogdir
    genETL()
    genShell()
    backupFile4SBS()



# param: org(us, cn), sbsDate(2012-07-22)
if __name__ == "__main__":
    main()


