def saleslist(n,k,alist,final,price):
    p = 0
    maxie = 0
    while p<n:
        i = 2
        while i < n:
            lis = [p+1]
            h=p
            pricehouses=[price[p]]
            for j in range(i,n):
                if alist[j]-k > h:
                    lis.append(j+1)
                    pricehouses.append(price[j])
                    h = j
                else:
                    pass
            i += 1
            pricehouses = sum(pricehouses)
            if maxie<pricehouses:
                maxie =pricehouses
                final = lis
        else:
            p += 1
    return final,maxie

k = int(input("Enter value of k: "))
pricelist = []
with open("houses.txt","r") as price:
    n = int(price.readline())
    for line in price:
        pricelist.extend([int(line) for line in line.split()])
alist = []
i = 0
while i < n:
    alist.append(i)
    i += 1
n = len(alist)
final = []
final,maxie = saleslist(n,k,alist,final,pricelist)
print("Houses: " + str(final) + "\n" + "Total Sale: " + str(maxie))
