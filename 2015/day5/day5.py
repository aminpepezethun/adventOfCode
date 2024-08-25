with open("input.md", "r") as file:
    data = file.readlines()
    data_1 = [line.strip() for line in data]

with open("input2.md", "r") as file:
    data = file.readlines()
    data_2 = [line.strip() for line in data]

class Solution_1:
    def __init__(self):
        self.vowels = ["a", "e", "i", "o", "u"]
        self.restriction = ["ab", "cd", "pq", "xy"]

    def vowel(self, words):
        count = sum(1 for char in words if char in self.vowels)
        return count >= 3

    def word_appear_twice(self, word):
        for i in range(len(word)-1):
            if word[i] == word[i+1]:
                return True

        return False
        
    def restrict(self, words):
        for duo in self.restriction:
            if duo in words:
                return False
        return True
        
    def check_all(self, data):
        count = 0
        for line in data:
            if self.vowel(line) and self.restrict(line) and self.word_appear_twice(line):
                count += 1

        return count
    
class Solution_2:
    def __init__(self):
        self.count = 0
        
    def repeat_letter_with_one_letter_in_middle(self, line):
        for i in range(len(line)-2):
            if line[i] == line[i+2]:
                return True
        return False

    def pair_appear_twice(self, line):
        pairs = {}
        for i in range(len(line) - 1):
            pair = line[i:i+2]
            if pair in pairs:
                if i - pairs[pair] > 1:
                    return True
            else:
                pairs[pair] = i
        return False
    
    def check_all(self, data):
        for line in data:
            if self.repeat_letter_with_one_letter_in_middle(line) and self.pair_appear_twice(line):
                self.count += 1
        return self.count
        


solution_1 = Solution_1()
solution_2 = Solution_2()
print("Part 1 :  " + str(solution_1.check_all(data_1)) + " pairs")
print("Part 2 :  " + str(solution_2.check_all(data_2)) + " pairs")

# line_ = "qjhvhtzxzqqjkmpb"
# for i in range(len(line_) - 3):
#     print(line_[i:(i+2)])