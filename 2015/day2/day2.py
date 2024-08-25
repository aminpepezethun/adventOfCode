with open("input.md", 'r') as file:
    data = file.readlines()

def get_value(dim):
    dim.replace("\n","")
    lst = dim.split("x")
    l = int(lst[0])
    w = int(lst[1])
    h = int(lst[2])
    return [l,w,h]

def get_smallest(lst):
    lst.sort()
    return lst[0]


def day2_a(data):
    accum = 0
    for dim in data:
        lwh = get_value(dim)
        area1 = lwh[0] * lwh[1]
        area2 = lwh[1] * lwh[2]
        area3 = lwh[2] * lwh[0]
        t_area = 2*(area1+area2+area3)
        sml = get_smallest([area1, area2, area3])
        total = t_area + sml
        accum += total
    return accum
    
def day2_b(data):
    accum = 0
    for dim in data:
        lwh = get_value(dim)
        lwh.sort()
        rib = 2*(lwh[0] + lwh[1])
        feet = lwh[0] * lwh[1] * lwh[2] 
        total = rib + feet
        accum += total
    return accum

