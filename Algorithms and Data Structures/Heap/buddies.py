# 2nd Function; import alist
def radixsort(aList):
      # aList represents the values of movie names for each innerlist
      radix = 10
      maxLength = False
      tmp,placement = -1, 1
      # print ("Before: " + str(aList))
      # run while loop while maxLength is true
      while not maxLength:
        maxLength = True
        # create a new array of len(10) to store the values of aList
        # re-create buckets as new array after each len(aList)
        buckets = [list() for _ in range(radix)]
        #Split alist between list
        for i in aList:
          # i = movie values found within aList
          tmp = i // placement
          # add values of i from aList into the new array buckets[remainder]
          buckets[tmp%radix].append(i)
          # false the loop after one value detected within bucket[1++], excluding bucket[0]
          if maxLength and tmp > 0:
            maxLength = False
        # next digit
        # change the value of placement after each len(aList)
        placement *= radix

        # transfer the result of buckets back to aList
        a = 0
        for b in range(radix):
          buck = buckets[b]
          for i in buck:
            aList[a] = i
            a += 1
      # print ("After:" + str(aList))

# 3rd Function; import alist
def sortmovies(lst):
    lst = []
    innerlist=[] # repeated
    inner2=[]

    for item in alist:
        # transfer inner list of sorted movie values into inner2
        if item[1] not in inner2:
            inner2.append(item[1])

    for item in inner2:
        innerlist = [] # a new array to store combination of inner1 and inner2
        inner1 = []
        # inner1 contributes to the Buddies
        for l in alist:
            if item == l[1]:
                inner1.append(l[0])
        # combine inner1 and inner2
        # lst = innerlist = inner1 + inner2
        innerlist.append(inner1)
        innerlist.append(item)
        lst.append(innerlist)
    return lst

# 1st Function
def movieindex(alist,movielist):
      # name the moviename within alist as numbers according to movielist
      for i in range(len(alist)): # 10,000
          for j in range(len(movielist)): # 51
              for k in range(len(alist[i][1])): # length of each list within alist
                  if alist[i][1][k] == movielist[j]:
                      alist[i][1][k] = j

# 4th Function to name back the values within mylist using movielist
def returnmovie(mylist, movielist):
      # mylist = alist
      for i in range(len(mylist)): # 9,982
          for j in range(len(movielist)): # 51
              for k in range(len(mylist[i][1])): # length of each list within mylist
                  if mylist[i][1][k] == j:
                      mylist[i][1][k] = movielist[j]

# ======== Main Function ========
# Import .txt file
favmovies = open("favoriteMovies.txt")
# Create a new list to store splited values
alist = []
for line in favmovies:
    line = line.strip().split(":")
    line[0] = int(line[0])
    line[1] = line[1].split(",")
    alist.append(line)
# Create another list to store the movie names found in alist
movielist =[]
# Only include new movie name found into the movielist without repeating
for item in alist:
    for movies in item[1]:
        if movies not in movielist:
            movielist.append(movies)

# Run the first function
movieindex(alist, movielist)
# Iterate 'radixsort' function till the maximum of alist
n = len(alist)
for i in range(n):
    radixsort(alist[i][1]) # second function to sort movie names
mylist = sortmovies(alist) # third function to contribute Buddies
returnmovie(mylist, movielist)  # forth function to contribute Movies
# == == == == Grouping of Movies == == == ==
# New array list2 created to store the results of grouped movies
list2 = []
for i in range(len(mylist)): # len(mylist) = 9,982
    # exclude those len(mylist[i][0]) with ones
    if len(mylist[i][0]) >= 2:
         list2.append(mylist[i])

for i in range(len(list2)): # len(list2) = 8 groups
    if len(list2[i][0]) >= 2:
          print("Group" + " " + str(i+1)+ "\n" + "Movies:" + (", ".join(str(j) for j in list2[i][1])) + "\n"+"Buddies:" + (", ".join(str(j) for j in list2[i][0])))



