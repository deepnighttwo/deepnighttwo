numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
numbers[1:] = [1, 1, 1, 1, 1]
print numbers
numbers[1:1] = [2, 3, 4, 5, 6, 7, 8, 9]
print numbers

print numbers.count(1);
numbers[9:]=[]
print numbers

print len(numbers)

print max(numbers)

print min(numbers)

numbers.append(10)


numbers.extend(numbers)

print numbers

print numbers.index(10, )