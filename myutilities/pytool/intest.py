strddd = 'this is a test str'
print 's' in strddd
print 'test' in strddd

users = ['u1', 'u2', 'u3']

print raw_input('user name:') in users


database = [
          ["u1", "p1"],
          ["u1", "p1"],
          ["u1", "p1"]          
          ]

username = raw_input("user name:")

pin = raw_input("user name:")

if [username, pin] in database: print 'Access granted'
