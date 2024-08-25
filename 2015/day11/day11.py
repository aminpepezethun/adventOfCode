def increment(psswd: list[str]):
    n = len(psswd)
    if n == 0:
        return psswd

    for i in range(n-1,-1,-1):
        if psswd[i] == 'z':
            psswd[i] = 'a'
        else:
            psswd[i] = chr(ord(psswd[i]) + 1)
            return psswd

    return psswd

def validity(psswd: list[str]) -> bool:
    return sizeValidity(psswd) and threeElementIncrease(psswd) and excludeiol(psswd) and pairExist(psswd)

def sizeValidity(psswd: list[str]) -> bool:
    return len(psswd) == 8

def threeElementIncrease(psswd: list[str]) -> bool:
    n = len(psswd)
    count = 1
    i = 0
    while i < n - 1:
        dif = ord(psswd[i+1]) - ord(psswd[i])
        if dif == 1:
            count+=1
            if count >= 3:
                return True
        else:
            count = 1

        i += 1

    return False

def excludeiol(psswd: list[str]) -> bool:
    for char in psswd:
        if char in ['i', 'o', 'l']:
            return False
    return True

def pairExist(psswd: list[str]) -> bool:
    n = len(psswd)
    pairs = 0
    i = 0

    while i < n -1:
        if psswd[i] == psswd[i+1]:
            pairs += 1
            i += 2
        else:
            i += 1

    return pairs == 2

def main():
    def getPassword():
        pswd = input("Enter initial password: ").strip()
        return [i for i in pswd]

    try:
        psswd = getPassword()
    except Exception as e:
        print('Input handling error:', e)
        psswd = []

    while True:
        psswd = increment(psswd)

        if not validity(psswd):
            psswd = increment(psswd)
        else:
            break

    print(''.join(psswd))

if __name__ == "__main__":
    main()







