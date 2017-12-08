"""
Spyder Editor

@author: Plot The Graph Team.
"""
#importing matplot library which is used to plot graph
import matplotlib.pyplot as plt
#from mpl_toolkits.mplot3d import Axes3d
from matplotlib import style
#opening the file containing usernames of all the user
namefile=open("users.txt","r")

#reading that file and printing 
s=namefile.read()
print "----USERS----"
print s

#User inputing the name of user1 and searching that its a valid user or not
#USER 1
name=raw_input("Enter Name of user :")
if name in s:
    tmp=name
else:
    print "User Not found"
    tmp=''
l=0
#USER 2
name_2=raw_input("Enter Name of 2nd user :")
if name in s:
    tmp1=name_2
else:
    print "User Not found"
    tmp1=''

#style of graph
style.use('fivethirtyeight')

#initializing the plot
fig = plt.figure()

#function to convert time into hour
def convert(zt):
    x,y,z=zt.split(':')
    z=z[:2]
    return float(x)*1+float(y)/60+float(z)/3600
    
#opening the required user's file
graph_data=open(name+'.txt','r').read()
#reading each line from the file
lines=graph_data.split('\n')
#empty lists to store latitude, longitudes and time of the user
#USER 1
xs=[]
ys=[]
zs=[]
#USER2
xs1=[]
ys1=[]
zs1=[]

#opening the file of both users

graph_data=open(tmp+'.txt','r').read()
lines=graph_data.split('\n')

graph_data1=open(tmp1+'.txt','r').read()
lines1=graph_data1.split('\n')

#appending the data of each user into their respective lists
#USER 1
for line in lines:
    if len(line)>1:
        x,y,zt,d=line.split(",")
        xs.append(float(x))
        ys.append(float(y))
        z=convert(zt) #calling function
        zs.append(float(z))

#USER 2
for line in lines1:
    if len(line)>1:
        x,y,zt,d=line.split(",")
        xs1.append(float(x))
        ys1.append(float(y))
        z=convert(zt) #calling function
        zs1.append(float(z))


#Plotting the graph in a specified section
ax=plt.figure()
ax=fig.add_subplot(111,projection='3d')
#USER 1
ax.scatter(zs,xs,ys,c='r')
#USER 2
ax.scatter(zs1,xs1,ys1,c='g')
#Displaying the graph
plt.show()