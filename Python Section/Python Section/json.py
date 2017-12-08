# -*- coding: utf-8 -*-
"""
Created on Thu Oct 26 01:00:55 2017

@author: Plot The Graph

@username: Plot3d

@password: helloproject
"""
#importing necessaery libraries
import urllib,json
#sending request for a json array to the server 
url="http://plot3d.heliohost.org/QueryProcessor_3Dpath.php?qry=select_sp__star__sp_from_sp_stauts"  
#checking the response from the server
response=urllib.urlopen(url)   
if(response):
    print "CONNECTED !!!"
#reading the data in json format storing the json array    
datayo=json.loads(response.read())   
ii= datayo["data"]

#empty dictionary for interacting with json
dict={}
arr=[]
i=0
j=0
   
#storing the json data into the dictionary
while True:
    try:
       if(ii[i]['uname'] in dict):
           #using username as key ,we are storing his latitude,longitude,time and date
           dict[ ii[i]['uname']]=dict[ii[i]['uname']]+[ii[i]['lati'],ii[i]['longi'],ii[i]['time'],ii[i]['date']]
       else:
           dict[ii[i]['uname']]=[ii[i]['lati'],ii[i]['longi'],ii[i]['time'],ii[i]['date']]
           arr=arr+[ii[i]['uname']]
           j=j+1
       i=i+1
    except IndexError:
        break

#opening a file to store the name of users
namefile=open("users.txt","w")
for i in arr:
    namefile.write(i+"\n")
namefile.close()


#creating file of every user
for name in arr:
    fileptr=open(name+".txt","w")
    lat=[]
    lon=[]
    tim=[]
    dat=[]
    for i in range(0,len(dict[name]),4):
        lat=lat+[("%.7f" %float(dict[name][i]))]            #0.7f to set precision upto 7 decimal places
        lon=lon+[("%.7f" %float(dict[name][i+1]))]
        tim=tim+[dict[name][i+2]]
        dat=dat+[dict[name][i+3]]
    fileptr.close()
    fileptr=open(name+".txt","w")
    for i in range (len(lat)):
        fileptr.writelines(str(lat[i])+","+str(lon[i])+","+str(tim[i])+','+str(dat[i])+"\n")  #storing the data in csv file
    fileptr.close()
print "Data Retrived and stored ..!!"



    
    
    