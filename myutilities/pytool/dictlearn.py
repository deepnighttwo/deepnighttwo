


dic = {1:888, 2:"gogogo", "sss":"asdfasdf", (1, "999", 44):"yyyusadf"}

print dic

dic["asdf"] = '999'
print dic["asdf"]

print "asdf" in dic

print "asdfdddddddddddddddddddd" in dic

print dic.has_key("asdf")

print len(dic)

dic[35] = '99asdf'

print dic[35]

key = (1, "2", "88iiu")
dic[key] = 'kkkk11111'

print dic[key]

print len(dic)

print dic[(1, "999", 44)]

print len(dic)

del dic[(1, "999", 44)]

print len(dic)

dicn = dic.copy()

dic.clear()

print dic

print dicn

dic2 = {}

dic2 = dic2.fromkeys(["asdf", "qergfds", 56, (1, 2), "93kdsajfl"])

print dic2


print dic2.get(52)

print dic2.get(52, "mei zhao dao.")

print dic2.popitem()

dic2.setdefault(52, "ffffffffffffff")

print dic2.get(52)

dic2.update({52:999999999999})

print dic2

print dic2.keys()

print dic2.values()






























