months = [
       "yi1", "yi2", "yi3", "yi4", "yi5", "yi6", "yi7", "yi8", "yi9", "yi10", "yi11", "yi12"
       ]
endings = ['st', 'nd', 'rd'] + 17 * ['th']\
+ ['st', 'nd', 'rd'] + 7 * ['th']\
+ ['st']

year = raw_input("Year:")
month = raw_input("Month:")
day = raw_input("day:")

month_number = int(month)
day_number = int(day)

month_name = months[month_number - 1]
ordinal = day + endings[day_number - 1]
print month_name + ' ' + ordinal + '. ' + year


sequence=[None, None, None]

print sequence;