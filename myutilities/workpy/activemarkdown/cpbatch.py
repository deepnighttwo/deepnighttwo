import time
import datetime
import sys
import os

timeFormat = '%Y-%m-%d'

def loadAMLFromFile(f):
    aml = file(f)
    amm = {}
    skip = True
    for l in aml:
        if skip == True:
            skip = False
            continue
        l = l.strip()
        v = l.split(",")
        if len(v) != 2:
            v = l.split("\t")
        amm[v[0]] = v[1]
    return amm
    

def compareAMM(o, n):
    ok = set(o.keys());
    nk = set(n.keys());
    so = len(o)
    sn = len(n)
    on = (ok - nk)
    #print on
    no = (nk - ok)
    #print no
    both = ok & nk;
    ne = 0
    oi = 0
    ni = 0
    for k in both:
        ov = int(o[k])
        nv = int(n[k])
        if (ov != nv):
            ne += 1
            oi += ov
            ni += nv
            #print k + ',' + ov + ',' + nv
    print 'Not equal count=', ne, ', not equal rate orig=', ne * 100.0 / so, ', not equal rate new=', ne * 100.0 / sn
    print 'Not match rate orig=', (ne + len(on)) * 100.0 / so, ', not match rate new=', (ne + len(on)) * 100.0 / sn

def compareAMLFile(oFile, nFile):
    o = loadAMLFromFile(sys.argv[1])
    n = loadAMLFromFile(sys.argv[2])
    compareAMM(o, n)
    
def changeDateByDay(date, deltaDay):
    origDate = time.strptime(date, timeFormat)
    y, m, d = origDate[0:3]
    origDatetime = datetime.date(y, m, d)
    newDatetime = origDatetime + datetime.timedelta(days=deltaDay)
    return newDatetime.strftime(timeFormat)


def diffSourceCmp():
    origPath = '/apollo/env/Prioritization/var/output/data/prod/CNAmazon/ActiveMarkdownListCN%s.csv'
    dwPath = '/apollo/env/Prioritization/var/input/prod/CNAmazon/ActiveMarkdownList.%s.txt'

    cpdate = '2012-10-15'
    
    for i in range(18):
        tm = changeDateByDay(cpdate, -1 * i)
        op = origPath % (tm)
        dp = dwPath % (tm)
        if os.path.isfile(op) and os.path.isfile(dp):
            print '========================================================='
            print "Comparing" , tm
            o = loadAMLFromFile(op)
            n = loadAMLFromFile(dp)
            compareAMM (o, n)
        else:
            print op, os.path.isfile(op) 
            print dp, os.path.isfile(dp)

def diffDateCmp():
    origPath = '/apollo/env/Prioritization/var/output/data/prod/CNAmazon/ActiveMarkdownListCN%s.csv'

    cpdate = '2012-10-15'
    
    for i in range(18):
        tm1 = changeDateByDay(cpdate, -1 * i)
        tm2 = changeDateByDay(cpdate, -1 * (i + 1))
        op1 = origPath % (tm1)
        op2 = origPath % (tm2)
        if os.path.isfile(op2) and os.path.isfile(op1):
            print '========================================================='
            print "Comparing" , tm2, tm1
            o1 = loadAMLFromFile(op2)
            o2 = loadAMLFromFile(op1)
            compareAMM (o2, o1)




if __name__ == '__main__':
    diffSourceCmp()
   
   
    
