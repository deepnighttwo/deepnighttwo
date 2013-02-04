from string import Template

s=Template("Hello ${xxx}")
print s.substitute(xxx='sb')

s=Template("Hello ${xxx}")
d={}
d['xxx']='da sha bi'
print s.substitute(d)


s='hello %s. Did you just say %.5f?'
print s%('big sb',999.111333555)