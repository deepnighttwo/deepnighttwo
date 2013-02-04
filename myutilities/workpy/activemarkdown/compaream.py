import sys


def loadTipFromFile(f):
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
    

def compareTip(o, n):
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
    for k in both:
        ov = int(o[k])
        nv = int(n[k])
        if (ov != nv):
            ne += 1
            oi += ov
            ni += nv
            #print k + ',' + ov + ',' + nv
    print '========================================================='
    print 'Orig size=', so, ', missing=', len(no), ', miss rate=', len(no) * 100.0 / so
    print 'New size=', sn, ', missing=', len(on), ', miss rate=', len(on) * 100.0 / sn
    print 'Not equal count=', ne, ', not equal rate orig=', ne * 100.0 / so, ', not equal rate new=', ne * 100.0 / sn
    print 'Not match rate orig=', (ne + len(on)) * 100.0 / so, ', not match rate new=', (ne + len(on)) * 100.0 / sn
    print 'Not match total orig=', oi, ', Not match total new=', ni,

def compareAMLFile(oFile, nFile):
    o = loadTipFromFile(sys.argv[1])
    n = loadTipFromFile(sys.argv[2])
    compareTip(o, n)

if __name__ == '__main__':
    print "Parameters are", sys.argv[1:]
    o = loadTipFromFile(sys.argv[1])
    n = loadTipFromFile(sys.argv[2])
    compareTip(o, n)
    
