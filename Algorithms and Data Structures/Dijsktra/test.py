
import heapq
class heap:
    def __init__(self):
        self.array = [0]
        self.count = 0
    
    def is_empty(self):
        return self.count == 0

    def __len__(self):
        return self.count

    def reposition(self, i, index):
        while True:
            if i > 1 and self.array[i][1] < self.array[i//2][1]:
                self.array[i], self.array[i//2] = self.array[i//2], self.array[i]
                index[self.array[i][0]], index[self.array[i//2][0]] = index[self.array[i//2][0]], index[self.array[i][0]]
                i = i // 2
            else:
                break
        if i != 0:
            return i
        elif i == 0:
            return 1

    def last(self, index):
        if self.is_empty():
            raise StopIteration("Nothin")
        self.array[1], self.array[-1] = self.array[-1], self.array[1]
        index[self.array[1][0]], index[self.array[-1][0]] = index[self.array[-1][0]], index[self.array[1][0]]
        mini= self.array[-1]
        #remove
        #done recheck correct
        self.array = self.array[:-1]
        self.count -= 1
        i = 1
        while True:
            if self.count >= 2*i:
                child = self.minimum(i)
                if self.array[i][1] > self.array[child][1]:
                    #recheck
                    self.array[i], self.array[child] = self.array[child], self.array[i]
                    index[self.array[i][0]], index[self.array[child][0]] = index[self.array[child][0]], index[self.array[i][0]]
                    i=child
                else:
                    i = child
            else:
                break
        return mini

    def minimum(self,i):
        #gotta return smallest
        #done recheck correct
        left = 2*i
        right = 2*i+1
        minimum = i
        if right < len(self.array):
            if self.array[left][1] > self.array[right][1]:
                minimum = right
                return minimum
            else:
                minimum = left
                return minimum
        else:
            minimum = left
            return minimum



class Dijkstra:
    def __init__(self, k):
        self.vertices = [[[], False] for i in range(k)]

    def dijkstra(self, userinput, k, total):
        index = [-2] * total
        road = [0] * total
        discover = heap()
        discover.array.append([userinput, 0])
        discover.count += 1
        index[userinput] = discover.count
        discover.reposition(discover.count, index)
        finalize = [] 
        while True:
            if len(discover) > 0:
                vertex = discover.array
                smallestdis = vertex[1]
                vertices = self.vertices[smallestdis[0]]
                for i in vertices[0]:
                    if index[i[1]] > 0 and vertex[index[i[1]]][1] > smallestdis[1] + i[2] and vertices[1] == False:
                        #recheck
                        road[i[1]] = i[0]
                        vertex[index[i[1]]][1] = smallestdis[1] + i[2]
                        discover.reposition(index[i[1]], index)
                    if index[i[1]] == -2 and vertices[1] == False:
                        road[i[1]] = i[0]
                        vertex.append([i[1], smallestdis[1] + i[2]])
                        discover.count += 1
                        index[i[1]] = discover.count
                        discover.reposition(discover.count, index)
                    else:
                        continue
            else:
                break
            #removing last item the shortest distance parent<>
            fv = discover.last(index) 
            index[fv[0]] = -1
            finalize.append(fv)
        resultslist=[]
        cameralist = []
        ilist = []
        n = len(finalize)
        ini = []
        i = 0
        while i < n:
            ini.append(finalize[i][0])
            i += 1
        j = 0
        camera = 0
        while j < len(ini):
            #recheck
            if self.vertices[ini[j]][1] == True and camera < k:
                results = []
                results.append(road[ini[j]])
                proad = road[ini[j]]
                while True:
                    if proad == userinput:
                        break
                    if proad != userinput: 
                        proad = road[proad]
                        results.append(proad)                  
                resultslist.append(results)
                cameralist.append(camera)
                ilist.append(finalize[j])
                camera += 1
                j+= 1
            else:
                j += 1
                continue
        return resultslist,cameralist,ilist
        


paths = Dijkstra(6105)
vertices = open('vertices.txt')
edges = open('edges.txt')
for line in vertices:
    line = line.strip().split()
    line = int(line[0])
    paths.vertices[line][1] = True
for line in edges:
    start = int(line.strip().split()[0])
    target = int(line.strip().split()[1])
    distance = float(line.strip().split()[2])
    if not line.endswith(('TOLL', 'TOLL\n')):
        paths.vertices[start][0].append([start, target, distance])
        paths.vertices[target][0].append([target, start, distance])

#recheck
userinput = int(input('Enter your location: '))
k = int(input('Enter k: '))
if paths.vertices[userinput][1] == True:
    print('Oops! Cannot help, Alice!!! Smile for the camera!')
else:
    #printing shud be right but
    #recheck
    resultslist,cameralist,ilist = paths.dijkstra(userinput, k,6105)
    n = len(resultslist)
    for i in range(n):
        shortest = 'Shortest path: '
        for j in range(len(resultslist[i])-1,-1,-1):
            shortest += str(resultslist[i][j]) + ' --> '
        shortest += str(ilist[i][0])
        cameralist[i] = cameralist[i] + 1
        print('\n' + 'Camera ' + str(cameralist[i]) + ': ' + str(ilist[i][0]) + '  Distance from your location: ' + str(ilist[i][1]))
        print(shortest+'\n')
    if len(cameralist) < k:
        print('Oops! You’re stuck here Alice!')
