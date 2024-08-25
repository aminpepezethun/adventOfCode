

def process(list) -> list:
    if not list:
        return
    
    result = []
    count = 1

    for i in range(1, len(list)):
        if list[i] != list[i-1]:
            result.append(count)
            result.append(list[i-1])
            count = 1
        else:
            count+=1

    result.append(count)
    result.append(list[-1])

    return result

list_temp = [1,1,1,2,2,3]
expected = [3,1,2,2,1,3]

# count = 1
# list[1] = 1
# list[0] = 1
# list1 = list0 
    # count = 2

# list[2] = 1
# list[1] = 1
# list2 = list1
    # count = 3

# list[3] = 2
# list[2] = 1
# list3 != list2
    # result.append 

print(process(list_temp))