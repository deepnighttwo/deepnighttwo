
import MySQLdb


def getFCFileData():
    fcfile = file("fc.txt")
    filefc = {}
    for line in fcfile:
        row = line.split("\t")
        fc = row[0].strip()
        hc = row[2].strip()
        try:
            hc = float(hc)
        except:
            hc = -1
        filefc[fc] = hc
    return filefc

def getGLFromFile():
    glfcdata = file("gl.txt")
    glcost = {}
    for line in glfcdata:
        row = line.split("\t");
        transin = 'null'
        transout = 'null'
        recp = 'null'
        remove = 'null'
        gl = int(row[0])
        try:
            transin = float(row[3])
        except:
            print row[3]
        
        try:
            transout = float(row[4])
        except:
            print row[4]
        
        try:
            recp = float(row[5])
        except:
            print row[5]
        
        try:
            remove = float(row[6])
        except:
            print row[6]
            
        cost = [recp, remove, transin, transout]
        glcost[gl] = cost
    return glcost


def deleteGLfromGLFC(glcost):
    for gl in glcost.keys():
        try:
            conn = MySQLdb.connect(host='oih-mysql-cn.db.amazon.com', user='oihadmin', passwd='', db='vendor_flex', port=3306, charset='utf8')
            try:
                cur = conn.cursor()
                sql = 'delete from FC_GL_COST where gl=%s;' % (gl)
                print sql
                #print cur.execute(sql)
                #conn.commit()
            finally:
                cur.close()
                conn.close()
        except Exception, e:
            print 'DB Connection Failure', e
            return
    
def getDBFC():
    dbfc = {}
    try:
        conn = MySQLdb.connect(host='oih-mysql-cn.db.amazon.com', user='oihadmin', passwd='', db='vendor_flex', port=3306, charset='utf8')
        try:
            cur = conn.cursor()
            sql = 'select warehouse_id,id from VENDOR_FCS'
            cur.execute(sql)
            fcs = cur.fetchall()
            for fc in fcs:
                dbfc[fc[0]] = fc[1]
        finally:
            cur.close()
            conn.close()
    except Exception, e:
        print 'DB Connection Failure', e
    
    return dbfc

def getAllFC():
    fcf = getFCFileData()
    dbfc = getDBFC()
    fcset = set([])
    for fcone in fcf.keys():
        fcset.add(fcone)
    for fcone in dbfc.keys():
        fcset.add(fcone)
    return fcset

def handleGLFC():
    glcost = getGLFromFile();
    deleteGLfromGLFC(glcost)
    fcs = getDBFC()
    try:
        conn = MySQLdb.connect(host='oih-mysql-cn.db.amazon.com', user='oihadmin', passwd='', db='vendor_flex', port=3306, charset='utf8')
        try:
            cur = conn.cursor()
            for gl in glcost.keys():
                for fc in fcs.keys():
                    warehouesid = fcs[fc]
                    recp = glcost[gl][0]
                    remove = glcost[gl][1] 
                    transin = glcost[gl][2]
                    transout = glcost[gl][3]
                    sql = "INSERT INTO `FC_GL_COST` (`ID`, `WAREHOUSE_ID`, `GL`, `WAREHOUSE_INDEX`, `RECEIPTCOST_ESTIMATES`, `REMOVALCOST_ESTIMATES`, `HOLDINGCOST_EST`, `TRANSPORTION_IN_ESTIMATE`, `TRANSPORTION_OUT_ESTIMATE`) VALUES (uuid(), '%s', %s, '%s', %s, %s, null, %s, %s);" % (fc, gl, warehouesid, recp, remove, transin, transout)
                    print sql
                    #print "insert gl-fc", cur.execute(sql)
                    #conn.commit()
        finally:
            cur.close()
            conn.close()
    except Exception, e:
        print 'DB Connection Failure', e


def handleFC():
    filefc = getFCFileData()
    dbfc = getDBFC()
    
    try:
        conn = MySQLdb.connect(host='oih-mysql-cn.db.amazon.com', user='oihadmin', passwd='', db='vendor_flex', port=3306, charset='utf8')
        try:
            cur = conn.cursor()
            for newfc in filefc.keys():
                holdingcost = filefc[newfc]
                if holdingcost < 0:
                    holdingcost = 'null'
                if newfc in dbfc.keys():
                    if holdingcost == 'null':
                        continue
                    sql = 'update VENDOR_FCS set HOLDINGCOST_EST=%s where WAREHOUSE_ID=\'%s\';' % (holdingcost, newfc)
                    print sql
                    #print "update fc", cur.execute(sql)
                    #conn.commit()
                else:
                    sql = "insert into VENDOR_FCS(`ID`, `WAREHOUSE_ID`, `MARKETPLACE_ID`, `IOG`, `IS_VALID_FC`,`HOLDINGCOST_EST_CURRENCY`,`HOLDINGCOST_EST`, `FS_SPLIT_RATIO`, `CATEGORY_NAME`, `STATUS`, `FULFILLMENT_NODE_TYPE`, `REALM`,  `TASK_ID`) " + " values (uuid(),'%s', 3240, 71, b'10000000','CNY', %s, 0.15, 'NonSortableFC', 'enabled', 'FulfillmentCenter', 'CNAmazon', '0015847556');" % (newfc, holdingcost)
                    print sql
                    #print "insert fc", cur.execute(sql)
                    #conn.commit()
        finally:
            cur.close()
            conn.close()
    except Exception, e:
        print 'DB Connection Failure', e

if __name__ == "__main__":
    #handleFC()
    print "##############################"
    handleGLFC()
