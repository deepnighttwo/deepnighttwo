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
    topdiff = {};
    necarton = 0
    for k in both:
        ov = o[k][0]
        nv = n[k][0]
        if (ov != nv):
            alldiff.write(k + ',' + ov + ',' + nv + '\n');

            alldiff.write("");
            ne += 1
            oi += ov
            ni += nv
            if len(topdiff) >= 30:
                sb = sorted(topdiff.items(), key=lambda e:e[1])
                minAsin = sb[0][0];
                if topdiff[minAsin][0] < abs(ov - nv):
                    del(topdiff[minAsin])
                    topdiff[k] = [abs(ov - nv), ov, nv]
            else:
                topdiff[k] = [abs(ov - nv), ov, nv]


        ov = o[k][1]
        nv = n[k][1]
        if (ov != nv):
            necarton += 1
            #print k + ',' + ov + ',' + nv
    print '========================================================='
    print 'Orig size=', so, ', missing=', len(no), ', miss rate=', len(no) * 100.0 / so
    print 'New size=', sn, ', missing=', len(on), ', miss rate=', len(on) * 100.0 / sn
    print 'Not equal count=', ne, ', not equal rate orig=', ne * 100.0 / so, ', not equal rate new=', ne * 100.0 / sn
    print 'Not match rate orig=', (ne + len(on)) * 100.0 / so, ', not match rate new=', (ne + len(on)) * 100.0 / sn
    print 'Not match total orig=', oi, ', Not match total new=', ni,
    print 'Not match carton', necarton,
    print "Top diff scope:(asin, diff, orig, new"
    sb = sorted(topdiff.items(), key=lambda e:e[1])
    for a in sb:
        print "%s,%s,%s,%s" % (a[0], a[1][0] - a[1][1], a[1][0], a[1][1])


def compareAMLFile(oFile, nFile):
    o = loadTipFromFile(sys.argv[1])
    n = loadTipFromFile(sys.argv[2])
    compareTip(o, n)

if __name__ == '__main__':
    print "Parameters are", sys.argv[1:]
    o = loadTipFromFile(sys.argv[1])
    n = loadTipFromFile(sys.argv[2])
    compareTip(o, n)
    
