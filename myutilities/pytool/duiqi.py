import string


width = 99
priceWidth=7
line='%-*s%*.2f'
print line % (width-priceWidth, "This is line one", priceWidth, 999.9)
print line % (width-priceWidth, "one", priceWidth, 999.9)
print line % (width-priceWidth, "This is line three", priceWidth, 999.9)


print "test%10.2ftest" % 1.1


print string.printable



