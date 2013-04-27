

def printEnum():
    asindetailsfile = file("asindetails.csv")
    head = asindetailsfile.readline()
    head = head.strip()
    colNames = head.split(",")
    i = 0
    for colName in colNames:
        colName = colName.strip()
        print colName.title().replace("_", ""), "(", i, ",", "\"" + colName + "\"", ",", "\"" + colName.title().replace("_"," ") + "\"),"
        i += 1;


if __name__ == "__main__":
     printEnum();
