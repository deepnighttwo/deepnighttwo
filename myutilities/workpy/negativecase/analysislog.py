import time
import os


if __name__ == "__main__":
    #time.sleep(60 * 10);

    optQtyStart = 0
    optQtyEnd = 1
    dummyVals = ["true", "false"]
    scriptDir = "/home/mengzang/releasecmp/";
    logDir = "/local/hamlogs/US/case/"
    bestRevMark = "bestRevenue="
    totalUnhealthyMark = "Total Unhealthy="
    resultFileStr = logDir + "result.csv"
    resultFile = open(resultFileStr, "w")
    resultFile.write("Optimal Qry,Dummy Fix,Total Unhealthy,Best Revenue" + os.linesep)
    for dummyVal in dummyVals:
        for i in range(optQtyStart, optQtyEnd):
            logFile = open(logDir + "DummyFix_" + dummyVal + "_OQ_" + str(i) + ".log", "r")
            fl = list(logFile)
            lc = 0
            while lc < len(fl):
                line = fl[lc]
                lc += 1
                if line.startswith("adjustOptimalQuantityForCartonQuantity=") == False:
                    continue
                stateLine = fl[lc + 1]
                resultLine = fl[lc + 2]

                bestRvn = stateLine[stateLine.find(bestRevMark) + len(bestRevMark):len(stateLine) - 2].strip()
                totalUnhealthy = resultLine[resultLine.find(totalUnhealthyMark) + len(totalUnhealthyMark):resultLine.find(" Total Healthy =") - len(" Total Healthy =") - 1].strip()
                resultLine = str(i) + "," + dummyVal + "," + totalUnhealthy + "," + bestRvn
                print resultLine
                resultFile.write(resultLine + os.linesep)
    resultFile.close()
    print "finished"
                
