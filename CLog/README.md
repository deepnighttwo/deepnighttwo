CLog
====

Centralized log system including hbase storage and efficient API for read and write. Here is the wiki: https://github.com/ctripframework/CLog/wiki

Instead of storing application logs to local files which is hard for future metrics statistics and event correlation, CLog uses hbase as centralized storage to persist logs and also leverages its distribution feature for scalability.  

A set of efficient APIs for read and write log can be integrated with host application.

For write API, it should be a straight-forward hbase write with buffering for efficiency.

For read API, it provides useful query languages to retrieve logs in a SQL like format.

Based on CLog, we can develop many comprehensive log products, for example vertical log view, horizontal log view etc.


