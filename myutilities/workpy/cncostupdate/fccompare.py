#! /usr/bin/python
import MySQLdb




def getdata ():
    try:
        conn = MySQLdb.connect(host='oih-mysql-cn.db.amazon.com', user='oihadmin', passwd='', db='vendor_flex', port=3306, charset='utf8')
        try:
            cur = conn.cursor()
            sql = 'select warehouse_id,holdingcost_est from VENDOR_FCS'
            cur.execute(sql)
            fcs = cur.fetchall()
        finally:
            cur.close()
            conn.close()
    except Exception, e:
        print 'DB Connection Failure', e
        return

    dbfc = {}
    for row in fcs:
        fc = row[0]
        hc = -1;
        try:
            hc = int(row[1])
        except:
            hc = -1
        dbfc[fc] = hc
    return dbfc


def getFileData():
    fcfile = file("fc.txt")
    filefc = {}
    for line in fcfile:
        row = line.split("\t")
        fc = row[0].strip()
        hc = row[2].strip()
        try:
            hc = int(hc)
        except:
            hc = -1
        filefc[fc] = hc
    return filefc
        
if __name__ == '__main__':
    dbfc = getdata()
    filefc = getFileData()
    dbset = dbfc.keys()
    fileset = filefc.keys()
    newfc = []
    for fc in fileset:
        if fc not in dbset:
            newfc.append(fc)
    print "New FC:", newfc
    
    missedfc=[]
    for fc in dbset:
        if fc not in filefc:
            missedfc.append(fc)
    print "Missed FC:", missedfc
