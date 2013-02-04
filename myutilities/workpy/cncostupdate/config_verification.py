

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
        transin = None
        transout = None
        recp = None
        remove = None
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


def readFCfromDB():
    dbfc = {}
    try:
        conn = MySQLdb.connect(host='oih-mysql-cn.db.amazon.com', user='oihadmin', passwd='', db='vendor_flex', port=3306, charset='utf8')
        try:
            cur = conn.cursor()
            sql = 'select warehouse_id,id,holdingcost_est from VENDOR_FCS'
            cur.execute(sql)
            fcs = cur.fetchall()
            for fc in fcs:
                fcname = fc[0]
                fcuuid = fc[1]
                try:
                    hc = float(fc[2])
                except:
                    hc = None
                dbfc[fcname] = [fcuuid, hc]
        finally:
            cur.close()
            conn.close()
    except Exception, e:
        print 'DB Connection Failure', e
    
    return dbfc

def readGLFCfromDB():
    fcgl = {}
    try:
        conn = MySQLdb.connect(host='oih-mysql-cn.db.amazon.com', user='oihadmin', passwd='', db='vendor_flex', port=3306, charset='utf8')
        try:
            cur = conn.cursor()
            sql = 'select a.warehouse_id, a.TRANSPORTION_IN_ESTIMATE,a.TRANSPORTION_OUT_ESTIMATE,a.REMOVALCOST_ESTIMATES,a.RECEIPTCOST_ESTIMATES,a.WAREHOUSE_INDEX, a.gl  from FC_GL_COST as a'
            cur.execute(sql)
            dbdata = cur.fetchall();
            for row in dbdata:
                warehouse_id = row[0]
                warehouseuuid = row[5]
                gl = int(row[6])
                
                transin = None
                transout = None
                remove = None
                recep = None

                try:
                    transin = float(row[1])
                except:
                    transin = None
                
                try:
                    transout = float(row[2])
                except:
                    transout = None
                
                try:
                    remove = float(row[3])
                except:
                    remove = None
                
                try:
                    recep = float(row[4])
                except:
                    recep = None
                
                
                if gl not in fcgl.keys():
                    fcgl[gl] = [set([]), []]
                
                if warehouse_id in fcgl[gl][0]:
                    print "Error: duplicated record for gl", gl, ". FC", warehouse_id
                
                fcgl[gl][0].add(warehouse_id);
                
                fcgl[gl][1].append([recep, remove, transin, transout, warehouse_id, warehouseuuid])
                
        finally:
            cur.close()
            conn.close()
    except Exception, e:
        print 'DB Connection Failure', e
    
    return fcgl;

def verifyHoldingCost():
    filefcs = getFCFileData()
    dbfcs = readFCfromDB()
    for filefc in filefcs.keys():
        if dbfcs[filefc] == None:
            print "Error: Missing FC", filefc
        elif filefcs[filefc] == -1:
            if dbfcs[filefc][1] != None:
                print "Warning:", filefc, "New FC HC is null but existing FC HC is ", dbfcs[filefc][1]
        elif filefcs[filefc] != dbfcs[filefc][1]:
            print "Error: New FC HC:", filefcs[filefc], ". DB FC HC:", dbfcs[filefc][1]
    print "FC verification pass"
    
def verifyRRTTCost():
    fcgldb = readGLFCfromDB()
    fcdb = readFCfromDB()
    fcset = set(fcdb.keys())
    fcglfile = getGLFromFile()
    for gl in fcglfile.keys():
        if gl not in fcgldb.keys():
            print "Error: gl missing ", gl
        if  (fcset != fcgldb[gl][0]):
            print "Not All fc added!", len(fcset), len(fcgldb[gl][0])
        
        #[recep, remove, transin, transout, warehouse_id, warehouseuuid]
        #recp, remove, transin, transout
        fileparas = fcgldb[gl][1]
        for filepara in fileparas:
            a = filepara[0] == fcglfile[gl][0]
            b = filepara[1] == fcglfile[gl][1]
            c = filepara[2] == fcglfile[gl][2]
            d = filepara[3] == fcglfile[gl][3]
            e = filepara[5] == fcdb[filepara[4]][0]

            if a and b and c and d and e:
                pass
            else:
                print "validation failure"
    print 'fc gl verification pass'
        
    
if __name__ == "__main__":
    verifyHoldingCost()
    verifyRRTTCost()
    
