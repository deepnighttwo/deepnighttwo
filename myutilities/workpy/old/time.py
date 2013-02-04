import time
import datetime

sbsDate = "2012-8-4"

timeFormat = '%Y-%m-%d'

SUNDAY = 6

def getSBSDate():
    global sbsDate
    if sbsDate == None or len(sbsDate) == 0:
        sbsDate = datetime.datetime.now()
    else:
        sbsDate = time.strptime(sbsDate, timeFormat)
        y, m, d = sbsDate[0:3]
        sbsDate = datetime.date(y, m, d)
    day = sbsDate.weekday()
    print sbsDate.strftime("%A")
    delta = (SUNDAY - day - 7) % 7
    if delta > 0:
        delta -= 7
    sbsDate = sbsDate + datetime.timedelta(days=delta)
    print sbsDate.strftime(timeFormat)

    


#print time.time()
#
#print time.localtime()
#
#now = datetime.datetime.now()
#
##spec = datetime.datetime(2012, 07, 24)
#spec = 
#print spec
#y, m, d = spec[0:3]
#specTime = datetime.date(y, m, d)
#
#
#print now.weekday()
#print specTime.weekday()
#
#print time.strftime(timeFormat, time.localtime())
#

if __name__ == "__main__":
    getSBSDate()
