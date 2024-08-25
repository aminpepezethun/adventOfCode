with open("input1.md", "r") as file:
    data = file.readlines()
    data_1 = [line.strip() for line in data]

with open("input2.md", "r") as file:
    data = file.readlines()
    data_2 = [line.strip() for line in data]

graph = [[0 for _ in range(1000)] for _ in range(1000)]

class Solution_1:
    def __init__(self, graph):
        self.graph = graph
    
    # Set light function: turn on/turn off/toggle
    # Define the set function according to the mode
    def set_light_(self, start, end, mode):
        x1 = int(start[0])
        y1 = int(start[1])
        x2 = int(end[0])
        y2 = int(end[1]) 

        # Loop through x,y 
        for x in range(x1, x2 + 1):
            for y in range(y1, y2 + 1):
                # Set "on" mode = 1
                if mode == "on":
                    self.graph[x][y] = 1
                # Set "off" mode = 0
                elif mode == "off":
                    self.graph[x][y] = 0
                # Set "toggle" mode: switch "off" to "on" and opposite
                elif mode == "toogle":
                    if self.graph[x][y] == 1:
                        self.graph[x][y] = 0
                    elif self.graph[x][y] == 0:
                        self.graph[x][y] = 1
            
    # Set light option: turn on/turn off/toggle
    # Identify the line for the "mode"
    def set_light(self, line):
        line = line.split()
        if line[0] == "turn":
            start = line[2].split(",")
            end = line[4].split(",")
            if line[1] == "on":
                self.set_light_(start, end, "on")
            elif line[1] == "off":
                self.set_light_(start, end, "on")
        elif line[0] == "toggle":
            start = line[1].split(",")
            end = line[3].split(",")
            self.set_light_(start, end, "toggle")
    
    # Count the light in the graph
    def count_light(self):
        count = 0
        for line in self.graph:
            for value in line:
                if value == 1:
                    count += 1
        return count

    def getCount(self):
        return self.count_light()
        
        

solution1 = Solution_1(graph)
for line in data_1:
    solution1.set_light(line)

print("Part 1:  " + str(solution1.getCount()))


