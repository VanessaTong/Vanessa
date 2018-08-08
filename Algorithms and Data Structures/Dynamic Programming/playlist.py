def filtervalue(n,index,songlist,T,array,array2):
  if n <= index:
    if T == 0:
        return 1
    elif T != 0:
        return 0
  if [index,T] not in array: 
    value = filtervalue(n,index+1,songlist,T,array,array2)
    value = value + filtervalue(n,index + 1,songlist,T - songlist[index],array,array2)
    array.append([index,T])
    array2.append(value)
  else:
      pass
  return array2[array.index([index,T])]


def getsong(n,songlist, T, array,array2):
  result = []
  i = 0
  while i < n:
    if filtervalue(n,i+1,songlist,T - songlist[i],array,array2) > 0:
        T = T - songlist[i]
        result.append(songlist[i])
        i = i+1
    else:
        pass
        i = i+1
  return result

T = int(input("Enter trip length: "))
songlist=[]
with open('songs.txt' , 'r') as songs:
    n = int(songs.readline())
    for line in songs:
        songlist.extend([int(line) for line in line.split()])
array = []
array2=[]
if filtervalue(n,0,songlist,T,array,array2) > 0:
    result = (getsong(n,songlist,T,array,array2))
    print("Playlist")
    for i in result:
        print("ID: " + str(songlist.index(i)+1)+" Duration: " +str(i))
else:
    print("Bad Luck Alice!")
