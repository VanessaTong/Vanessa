
import codecs
import sys

class NodeInfo:
    def __init__(self, w, d, f):
        self.word = w
        self.definition = d
        self.frequency = f
        
class Node:
    def __init__(self, frequency=0,pointer=0,counter=0):
        self.array = [0] * 27
        self.counter = 0    
        self.frequency = frequency  
        self.pointer = 0 

class trie:
    def __init__(self):
        self.root = Node()
      

    def insert(self, w, f, d):
        node = self.root
        if node.frequency < f or (f==node.frequency and node.pointer > ord(word[0]) - 96):
            node.pointer = ord(w[0]) - 96
            node.frequency = f
        node.counter = node.counter + 1
        i = 0
        n = len(w)
        while i < n:
            position = ord(w[i]) - 96
            if node.array[position] == 0:
                node.array[position] = Node(f)
                node.array[position].counter += 1
                node = node.array[position]
                if i < len(w) -1:
                    node.pointer = ord(w[i+1]) - 96
                i += 1
            else:
                node= node.array[position]
                if node.frequency < f or (f==node.frequency and node.pointer > ord(word[0]) - 96):
                    node.frequency = f
                    node.pointer = ord(w[i+1]) - 96
                node.counter += 1
                i+= 1         
        node.array[0] = NodeInfo(w,d,f)
        node.pointer = 0

    def search(self,prefix):
        node = self.root
        n = len(prefix)
        for i in range(n):
            j = ord(prefix[i]) - 96
            if node.array[j] != 0:
                node = node.array[j]
            else:
                return False
        counter = node.counter
        try:
            while node != 1:
                node = node.array[node.pointer]
        except:
            return node.word,node.definition,counter

dictionary = codecs.open('inputexample.txt','r','utf-8')
dictionaryarray = trie()
empty = 0
i = 0
for line in dictionary:
    if i == 0:
        word = line.strip().split(': ')
        word = word[1]
    if i == 1:
        frequency = line.strip().split(': ')
        frequency = int(frequency[1])
    if i == 2:
        definition = line.strip().split(': ')
        definition = definition[1:]
        empty = 1
    if empty == 1:
        dictionaryarray.insert(word,frequency,definition)
        empty = 0
        
    i = (i + 1) % 4 
while True:
    prefix = input("Enter a prefix: ")
    if prefix == "***":
        print("Bye Alice")
        sys.exit(0)
    elif dictionaryarray.search(prefix):
        word,definition,counter = dictionaryarray.search(prefix)
        definition = ' '.join(definition)
        print('Autocomplete suggestion: ' + str(word))
        print('Definition: ' + str(definition))
        print('There are ' + str(counter) + ' words in the dictionary that have "' + str(prefix) + '" as a prefix.' + "\n")
    else:
        print('There is no word in the dictionary that has "' + str(prefix) + '" as a prefix.'+"\n") 
        
