


def buildData(fileName):
    ret = {}
    datafile = file(fileName)
    for line in datafile:
        row = line.split(",")
        if len(row) < 2:
            continue;
        count = 0
        gl = 0
        try:
            count = int(row[0])
            gl = int(row[1])
        except:
            continue
        ret[gl] = count
    return ret
        
def getValue(dic, key):
    try:
        ret = dic[key]
        return ret
    except:
        return 0

if __name__ == "__main__":
    both = buildData("haveboth.csv")
    any = buildData("missone.csv")
    none = buildData("missboth.csv")
    total = buildData("total.csv")
    dwbothnum = buildData("dmpasinscategory.csv")
    dwbothne = buildData("dmpasinscategoryne.csv")
    
    print "GL,OIH Total ASIN,OIH Both Categories,OIH Any Category,OIH Neither Category,DW Both Number Categories,DW Both Not Empty Categories"
    for gl in total.keys():
        print "%s,%s,%s,%s,%s,%s,%s" % (gl, getValue(total, gl), getValue(both, gl), getValue(any, gl), getValue(none, gl), getValue(dwbothnum, gl), getValue(dwbothne, gl))
    
    
