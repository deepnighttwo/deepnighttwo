import os


if __name__ == "__main__":
    source = file('ASIN_GL_CATEGORY.2012-08-27.txt') # /apollo/env/Prioritization/var/input/prod/USAmazon/ASIN_GL_CATEGORY.2012-08-27.txt
    target = file('ASIN_GL_CATEGORY.2012-08-27_valid.txt', 'w') # /apollo/env/Prioritization/var/input/prod/USAmazon/ASIN_GL_CATEGORY.2012-08-27.txt
    validC = 0
    validS = 0
    invalidC = 0
    invalidS = 0
    validB = 0
    invalidB = 0
    for line in source:
        line = line[:len(line) - 1]
        row = line.split("\t")
        try:
            if int(row[3]) > 0:
                validC += 1
            else:
                invalidC += 1
        except:
            invalidC += 1
        
        try:
            if int(row[4]) > 0:
                validS += 1
            else:
                invalidS += 1
        except:
            invalidS += 1
            
        try:
            if int(row[4]) > 0 and int(row[3]) > 0:
                validB += 1;
                target.write(line + '\t1' + os.linesep)
            else:
                invalidB += 1
                target.write(line + '\t0' + os.linesep)
        except:
            invalidB += 1
            target.write(line + '\t0' + os.linesep)

    print "Valid Category:", validC, ". Valid Subcategory:", validS, ". Invalid Category:", invalidC, ". invalid Subcategory:", invalidS
    print "Valid Both:", validB, ". Invalid Any:", invalidB
    source.close()
    target.close()
