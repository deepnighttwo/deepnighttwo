

if __name__ == "__main__":
    source = open("C:\\sbs\\POHistoryDump-transformed.2012-07-28_to_2012-08-04.txt.cp", "r")
    result = open("C:\\sbs\\POHistoryDump-transformed.2012-07-28_to_2012-08-04.txt.clean", "w")
    keys = set([]);
    for line in source:
        row = line.split(",")
        key = row[0] + "-" + row[1] + "-" + row[2] + "-" + row[4]
        if key in keys:
            print "dup: " + line
            continue
        keys.add(key)
        result.write(line)
    source.close()
    result.close()
