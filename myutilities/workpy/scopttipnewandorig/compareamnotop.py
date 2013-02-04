import sys


def loadTipFromFile(f):
    aml = file(f)
    amm = {}
    for l in aml:
        l = l.strip()
        v = l.split(",")
        amm[v[0]] = [int(v[2]), int(v[3])]
    return amm
    

def compareTip(o, n):
    alldiff = open("alldiff.csv", "w")
    ok = set(o.keys());
    nk = set(n.keys());
    so = len(o)
    sn = len(n)
    print 'Asin count in original file:', so
    print 'Asin count in new file:', sn
    on = (ok - nk)
    print 'Asin in Original not in New:', len(on)
    #print on
    no = (nk - ok)
    print 'Asin in New not in Original:', len(no)
    #print no
    both = ok & nk;
    print 'Asin have different value: (Asin, original value, New value)'
    ne = 0
    oi = 0
    ni = 0
    
    alldiff.write("=====Asin Missed In Original Scope Tip API====\n")
    for asin in no:
        alldiff.write(asin + '\n');
    alldiff.write("\n\n=====Asin Missed In New Scope Tip API====\n")
    for asin in on:
        alldiff.write(asin + '\n');

    alldiff.write("\n\n=====Asin Scopt Tip Not Match in Original and New API====\n")
    alldiff.write("ASIN,Original Scope TIP,New Scope TIP\n")
    for k in both:
        ov = o[k][0]
        nv = n[k][0]
        if (ov != nv):
            alldiff.write(k + ',' + str(ov) + ',' + str(nv) + '\n');
    
    alldiff.close()

def compareAMLFile(oFile, nFile):
    o = loadTipFromFile(sys.argv[1])
    n = loadTipFromFile(sys.argv[2])
    compareTip(o, n)

if __name__ == '__main__':
    print "Parameters are", sys.argv[1:]
    o = loadTipFromFile(sys.argv[1])
    n = loadTipFromFile(sys.argv[2])
    compareTip(o, n)
    
