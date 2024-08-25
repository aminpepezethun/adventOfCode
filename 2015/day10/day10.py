word_list = input("Enter sequence: ")

# convert input into list of string
numList = list(word_list)

# convert input into list of integer
for i in range(len(numList)):
    numList[i] = int(numList[i])

def process(list) -> list:
    if not list:
        return []

    # initialize empty list
    result = []

    # initialize count = 1
    count = 1

    # start at index
    for i in range(1, len(list)):
        if list[i] != list[i - 1]:
            result.append(count)
            result.append(list[i - 1])
            count = 1
        else:
            count += 1

    result.append(count)
    result.append(list[-1])

    return result

# repeat process() function 40 times
for i in range(0, 40):
    numList = process(numList)

# print length of the final list
print(len(numList))
