import sys
import os
import shutil
import itertools
import time
import datetime
import subprocess

org = None

realm = None

sbsname = None

sbsDate = None

localdir = None

isGenETL = False

cdo = "/apollo/env/Prioritization/var/output/data/prod/%sAmazon"

cdi = "/apollo/env/Prioritization/var/input/prod/%sAmazon"

hamlogdir = '/local/hamlogs/%s/%s'#"C:/sbs/%s/%s"# 

etlFile = '/apollo/env/Prioritization/bin/etl.pl'#"C:/sbs/etl.pl" #

etlFileLocal = None

orgRealmMapping = {"US":"USAmazon", "CA":"CAAmazon", "JP":"JPAmazon", "CN":"CNAmazon", "GB":"GBAmazon", "FR":"FRAmazon", "DE":"DEAmazon", "IT":"ITAmazon", "ES":"ESAmazon", "IN":"INAmazon"}

timeFormat = '%Y-%m-%d'

SUNDAY = 6

paramFmt = "org(e.g. us,cn), sbs name(e.g. asindetails), sbs date(optional, last Sunday by default) as parameter"
paramExamples = "Parameter Examples: 1)xxx.py us asindetails 2)xxx.py us pending 2012-07-29"

shouldContinue = True

#Return True if files in dir is newer than orig file
def checkFileUpdated(origFile, fileDir, end):
    origModify = os.path.getmtime(origFile)
    cmpFiles = getFilesInDir(fileDir, end)
    for cmpFile in cmpFiles:
        fileModifyTime = os.path.getmtime(fileDir + os.sep + cmpFile)
        if fileModifyTime > origModify:
            return True
    return False    


def askForYes(message):
    override = raw_input(message)
    override = override.upper()
    if override == "Y" or override == "YES":
        return True
    return False


def genETL():
    global etlFileLocal, isGenETL
    jarFiles = getFilesInDir(localdir, ".jar")
    if len(jarFiles) == 0:
        if askForYes('No jar in current file, still copy etl.py to current dir?(y/n)') == False:
            print "=====ETL.pl Gen Skipped because of no jar found====="
            return
    
    isGenETL = True
    etlFileLocal = localdir + os.sep + "etl.pl"
    if(os.path.isfile(etlFileLocal)):
        message = ''
        if checkFileUpdated(etlFileLocal, localdir, ".jar"):
            message = 'Jar file updated after elt.pl generated. Would you like to re-gen etl.pl?(y/n)'
        else:
            message = 'etl.pl exists. Would you like to re-gen etl.pl?(y/n)'
        
        if askForYes(message) == False:
            print "=====ETL.pl Gen Skipped====="
            return
        print "Override etl.pl file..."
    print "===== Generating etl.pl to current dir ====="

    copyFile(etlFile, etlFileLocal);

    if len(jarFiles) == 0:
        print "No jar file in current dir. just copy ETL file."
        print "=====ETL.pl Gen Skipped====="
        return

    etlFileLocalRead = open(etlFileLocal, "r")
    etlFileLocalContent = []
    for line in etlFileLocalRead:
        if line.startswith("my $classpath"):
            etlFileLocalContent.append("#" + line)
            etlFileLocalContent.append("my $classpath = `find " + localdir + " -name '*.jar' -printf '%h/%f:'`;" + os.linesep);
            notIncluded = ""
            for jarFile in jarFiles:
                notIncluded += " -and -not -name '" + jarFile + "' "
            etlFileLocalContent.append(("$classpath .= `find $root/lib -follow -name '*.jar' %s -printf " % (notIncluded)) + " '%h/%f:'`;" + os.linesep)
        else:
            etlFileLocalContent.append(line)

    etlFileLocalRead.close()
    etlFileLocalWrite = open(etlFileLocal, "w")
    for line in etlFileLocalContent:
        etlFileLocalWrite.write(line)

    etlFileLocalWrite.close()
    print "=====ETL.pl Gen complete====="

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
        message = ''
        if checkFileUpdated(shellFile, localdir, ".cfg"):
            message = 'Cfg file updated after shell generated. Would you like to re-gen shell?(y/n)'
        else:
            message = 'Shell exists. Would you like to re-gen shell?(y/n)'
        
        if askForYes(message) == False:
            print "=====Skip Shell Gen for", sbsname + "====="
            return
        print "Override shell file..."

    stdoutFiles = getFilesInDir(hamlogdir, ".stdout")
    matchFiles = []
    for stdoutFile in stdoutFiles:
        if(stdoutFile.find(sbsname) >= 0):
            matchFiles.append(stdoutFile)

    chosenFile = None
    if(len(matchFiles) > 1):
        print "Please choose a stdout file to extract command:"
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
        print "=====Skip Shell Gen for", sbsname + "====="
        return

    fileContent = extractExecutionCommandFromLog(hamlogdir + os.sep + chosenFile)
    if fileContent == None:
        return

    shellFile = open(shellFile, 'w')
    for line in fileContent:
        shellFile.write(line)

    shellFile.close()
    print "=====Gen Shell for", sbsname + " Finished====="



def extractExecutionCommandFromLog(logfile):
    global isGenETL, etlFile
    logfile = open(logfile, 'r');
    commandLine = None
    startMark = "executing"
    for line in logfile:
        idx = line.find(startMark)
        if idx >= 0:
            commandLine = line[idx + len(startMark):]
            break
    if commandLine == None:
        print "Commandline not found in file ", logfile
        return None

    start = 0
    curr = 0
    ret = []
    etlpl = "etl.pl"
    for ch in commandLine:
        curr += 1
        if ch == ';' or curr == len(commandLine) :
            command = commandLine[start:curr].strip() + os.linesep
                        
            if command.find(etlpl) > 0:
                commandETLFile = etlFile
                if isGenETL:
                    commandETLFile = etlFileLocal
                command = (commandETLFile + "  --root ${ROOT} " + command[command.find(etlpl) + len(etlpl):]).strip() + os.linesep
            ret.append(command)
            
            start = curr


    cfgFiles = getFilesInDir(localdir, ".cfg")
    if len(cfgFiles) == 0:
        return ret
    
    cfgMark = "export CONFIG_FILE="
    
    for i in range(0, len(ret)):
        line = ret[i]
        if line.find(cfgMark) >= 0:
            configVal = cfgMark + '"\\"('
            originalCfgFile = line[len(cfgMark):line.find(";")]
            originalCfgFile.strip()
            configVal += originalCfgFile
            for cfgFile in cfgFiles:
                configVal += ("," + localdir + os.sep + cfgFile)
            configVal += ')"\\";' + os.linesep
            ret[i] = configVal
    return ret


def endWith(content, ends):
    return True in itertools.imap(content.endswith, ends)


def initApp():
    global org, realm, sbsname, sbsDate, localdir, cdo, cdi, hamlogdir, etlFile, orgRealmMapping, paramFmt, paramExamples
    calculateSBSDate()
    #print os.getcwd()

    print "Parameters are", sys.argv[1:]
    if(len(sys.argv) < 3):
        print "PARAMETER ERROR: Need", paramFmt
        print paramExamples
        sys.exit()
    localdir = os.getcwd()
    org = sys.argv[1]
    org = org.upper()
    realm = orgRealmMapping[org]
    cdo = cdo % (org)
    cdi = cdi % (org)
    sbsname = sys.argv[2]

    hamlogdir = hamlogdir % (org, sbsDate)
    print "Org:", org, ". Realm:", realm, ". Local Dir:", localdir, "cdi:", cdi, "cdo:", cdo, "hamlog dir:", hamlogdir
    print "=====System Init Complete====="

def backupFile4SBS():
    files2Bak = ("InventoryHealthMetrics-transformed.%s.txt", "UnhealthyAsinDetails-transformed.%s.txt")
    fstartdate = changeDateByDay(sbsDate, -8)
    fenddate = changeDateByDay(sbsDate, -1)
    bakPostfix = ".orig"
    for file2Bak in files2Bak:
        file2Bak = cdo + os.sep + (file2Bak % (fstartdate + "_to_" + fenddate))
        if os.path.isfile(file2Bak + bakPostfix) :
            print "File", file2Bak , "is baked already..."
        elif os.path.isfile(file2Bak + ".gz" + bakPostfix) :
            print "File", file2Bak + ".gz" , "is baked already..."
        elif os.path.isfile(file2Bak):
            print "Rename", file2Bak, "for bak by adding", bakPostfix
            bakFileAndCheck(file2Bak)
        elif os.path.isfile(file2Bak + ".gz"):
            file2Bak = file2Bak + ".gz"
            bakFileAndCheck(file2Bak)
        else:
            print "File", file2Bak, "is not found or baked"
    print "=====SBS File Backup Complete====="

def bakFileAndCheck(file2Bak):
    global shouldContinue
    bakPostfix = ".orig"
    bakFileName = file2Bak + bakPostfix;
    os.rename(file2Bak, bakFileName)
    if os.path.isfile(bakFileName):
        print "Rename to", bakFileName, "success"
    else:
        print "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Rename to", bakFileName, "FAILED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
        shouldContinue = False;


def calculateSBSDate():
    global sbsDate
    sbsDate = None
    if len(sys.argv) > 3:
        sbsDate = sys.argv[3]
    if sbsDate == None or len(sbsDate) == 0:
        sbsDate = datetime.datetime.now()
    else:
        sbsDate = sbsDate.strip()
        print "SBS Date is", sbsDate
        return
    day = sbsDate.weekday()
    # print sbsDate.strftime("%A"), day
    delta = (SUNDAY - day - 7) % 7
    if delta > 0:
        delta -= 7
    sbsDate = changeDateByDay(sbsDate.strftime(timeFormat), delta)
    print "SBS Date is", sbsDate


def changeDateByDay(date, deltaDay):
    origDate = time.strptime(date, timeFormat)
    y, m, d = origDate[0:3]
    origDatetime = datetime.date(y, m, d)
    newDatetime = origDatetime + datetime.timedelta(days=deltaDay)
    return newDatetime.strftime(timeFormat)

def changeFileMode():
    subprocess.Popen("chmod 777 " + localdir + os.sep + "*", shell=True)
    
def main():
    global shouldContinue
    initApp()
    if shouldContinue == True:
        #backupFile4SBS()
        pass
    else:
        print "back up file 4 sbs skipped."
        
    if shouldContinue == True:
        genETL()
    else:
        print "gen ETL skipped."
        
    if shouldContinue == True:
        genShell()
    else:
        print "gen shell skipped."
        
    if shouldContinue == True:
        changeFileMode()
    else:
        print "change file mode skipped."
        
def printHelp():
    global paramFmt, paramExamples

    print "What does this script do?"
    print "This script will:"
    print "1) generate etl.pl for jar override in current dir based on jar files in current dir. "
    print "2) extract shell script from hamlog for sbs based on sbs name parameter. Adding config file override to shell"
    print "3) backup SBS files if it's not backuped yet. Now only backup InventoryHealthMetrics-transformed.DATE.txt and UnhealthyAsinDetails-transformed.DATE.txt or it's gz file. "
    print ""
    print "How to use this script:"
    print "Place this script in the same dir with jar files to override."
    print "Parameter format:", paramFmt
    print paramExamples
    

# param: org(us, cn), sbs name(asindetails), sbsDate(2012-07-22)
if __name__ == "__main__":
    
    print "need to consider config file insert, jar file update, config file update"
    if len(sys.argv) == 1:
        printHelp()
    else:
        main()


