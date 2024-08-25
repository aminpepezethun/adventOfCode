import itertools
import hashlib

inPut_str = "yzbqklnj"
def day4_a(string_val):
    return hash_find_5(inPut_str)

def day4_b(string_val):
    return hash_find_6(string_val)

def hash_find_5(string_val):
    for val in itertools.count():
        val = str(val)
        inp = string_val + val
        
        result = hex_coverter(inp)

        if str(result[:5]) == "00000":
            return val
        
def hash_find_6(string_val):
    for val in itertools.count():
        val = str(val)
        inp = string_val + val
        
        result = hex_coverter(inp)

        if str(result[:6]) == "000000":
            return val
        
def hex_coverter(inp):
    inp_byte = inp.encode('utf-8')
    hash_val = hashlib.md5(inp_byte)
    hex_digest = hash_val.hexdigest()
    return hex_digest


result = day4_b(inPut_str)
print(result)