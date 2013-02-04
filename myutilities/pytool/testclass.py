from  mytest.testmodule import TestMember
m = TestMember();
m.m1()
m.m1()
m.m1()
m.m1() 
m2 = TestMember()
print TestMember.member
print m.member
print m2.member

m2.member = 999

print m.member
print m2.member

print getattr(m, "m1")

print callable(getattr(m, "m1"))

setattr(m, "member", 888)

setattr(m2, "member", 888)

print m.member
print m2.member






