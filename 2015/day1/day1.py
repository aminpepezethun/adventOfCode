data = open('input.md', 'r')
data = str(data.read())

lst = []
for val in data:
    lst.append(val)

def day1_a(lst):
    accum = 0
    for val in lst:
        if val == "(":
            accum += 1
        elif val == ")":
            accum -= 1
    return accum

def day1_b(lst):
    accum = 0
    idx = 0
    for val in lst:
        if accum == -1:
            return idx 
        if val == "(":
            accum += 1
            idx += 1
        elif val == ")":
            accum -= 1
            idx += 1


