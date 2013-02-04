
if __name__ == "__main__":
    rows = file("63.txt")
    rowt = "INSERT INTO `oih_config` (`id`, `gl`, `iog`, `category`, `subcategory`, `asin`, `value`, `tag`, `valueType`) VALUES (uuid(), 63, %s, '%s', '%s', NULL, '%s', '%s', 'NUMBER');"
    cate = ''
    subcate = ''
    catesubcate = set()
    for line in rows:
        col = line.split('\t')
        catetmp = col[0].strip()
        subcatetmp = col[1].strip()
        qt = col[2].strip()
        meanage = col[3].strip()
        
        if len(catetmp) > 0:
            cate = catetmp

        if len(subcatetmp) > 0:
            subcate = subcatetmp
        
        #print rowt % (7, cate, subcate, meanage, qt)
        #print rowt % (13, cate, subcate, meanage, qt)
        
        if cate + subcate in catesubcate:
            continue;
        
        catesubcate.add(cate + subcate)
        
        print '| 63'
        print '| VG'
        print '| %s' % (cate)
        print '| %s' % (subcate)
        print '| Y(2012-11-20)'
        print '|-'

