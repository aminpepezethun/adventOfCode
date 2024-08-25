data = open("input.md", "r")
data = str(data.read())

def rm_dup(lst):
    lst = list(dict.fromkeys(lst))
    return lst

def day2_a(data):
    x = 0
    y = 0
    lst = [(0,0)]
    for dir in data:
        if dir == "^":
            x += 1
        elif dir == "v":
            x -= 1
        elif dir == ">":
            y += 1
        elif dir == "<":
            y -= 1
        coor = (x,y)
        lst.append(coor)
    
    lst = rm_dup(lst)
    return len(lst)


def day2_b(data):
    x1 = 0 # santa x-coordinate
    y1 = 0 # santa y-coordinate
    x2 = 0 # robot x-coordinate
    y2 = 0 # robot y-coordinate

    visited = [(0,0),(0,0)]

    satan_turn = True

    for dir in data: 
        if satan_turn == True:
            if dir == "^":
                x1 += 1
            elif dir == "v":
                x1 -= 1
            elif dir == ">":
                y1 += 1
            elif dir == "<":
                y1 -= 1
            coor = (x1,y1)
            visited.append(coor)
        if satan_turn == False:
            if dir == "^":
                x2 += 1
            elif dir == "v":
                x2 -= 1
            elif dir == ">":
                y2 += 1
            elif dir == "<":
                y2 -= 1
            coor = (x2,y2)
            visited.append(coor)
            
        satan_turn = not satan_turn; 
                 
    visited = rm_dup(visited)
    return len(visited)



