#! /usr/bin/python
import MySQLdb


if __name__ == '__main__':
    try:
        conn = MySQLdb.connect(host='oih-mysql-cn.db.amazon.com', user='oihadmin', passwd='', db='vendor_flex', port=3306, charset='utf8')
        try:
            cur = conn.cursor()
            sql = 'select warehouse_id,holdingcost_est from VENDOR_FCS where status=\'enabled\''
            cur.execute(sql)
            fcs = cur.fetchall()
        finally:
            cur.close()
            conn.close()
    except Exception, e:
        print 'DB Connection Failure', e
        exit(1)

    dbfc = {}
    for row in fcs:
        fc = row[0]
        hc = -1;
        try:
            hc = int(row[1])
        except:
            hc = -1
        dbfc[fc] = hc
    print dbfc