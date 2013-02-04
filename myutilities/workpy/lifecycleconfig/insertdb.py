
if __name__ == "__main__":
    rows = file("rows.txt")
    rowt = "INSERT INTO `oih_config` (`id`, `gl`, `iog`, `category`, `subcategory`, `asin`, `value`, `tag`, `valueType`) VALUES (uuid(), 23, %s, '%s', '%s', NULL, '%s', '%s', 'NUMBER');"
    catesubcate = set()

    for line in rows:
        col = line.split('\t')
        cate = col[0].strip()
        subcate = col[1].strip()
        qt = col[2].strip()
        meanage = col[3].strip()

        #print rowt % (7,col[0].strip(),col[1].strip(),col[3].strip(),col[2].strip())
        #print rowt % (13,col[0].strip(),col[1].strip(),col[3].strip(),col[2].strip())
        
        if cate + subcate in catesubcate:
            continue;
        
        catesubcate.add(cate + subcate)
        print '| 23'
        print '| CE'
        print '| %s' % (cate)
        print '| %s' % (subcate)
        print '| Y(2012-11-20)'
        print '|-'

        
        
