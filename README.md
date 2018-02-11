# LStore
The LStore base on rocksdb is easy to use,and of course,ten billions of **KEY-VALUE pair** can easily store into it!And query them in **ms** latency with LStore-Client.It'll be awesome if you run it on **SSD**.

##### this version is not for release,I'm still working on it!

| method  | description | limits  |
|---------|:-----------:|:-------:|
| set | set key-value pair into store base  | no limits |
| get | get value from store base | no limits |
| mget  | get values with given keys  | no limits |
| mset  | set key-value pairs into store base | transactions guaranteed |
| scan  | scan data from store base with given key-prefix | 10000 records per time  |
| delete  | delete key-value pair with given key  | no limits |
| deleteRange | delete data by given range  | never try to delete range from begin to end while stored billions of data  |
| exists  | is key in store | no limits |
| info  | get informateions of store base | no limits |

##### how to?
- server
  - create a directory which LStore  will be in.
  - down load from release page (not ready yet!) and put it on your server and extracting them out.
  - make sure you'd Java Environment (1.8+) installed!
  - make some configration within config.properties which in conf directory
  - run start.sh to start the server
  
- client
  - make client.jar in your project
  - make some configration within config.properties which in conf directory
  - use com.mydb.client.command.Command to do something you like
  - enjoy
##### architecture
![architecture](https://raw.githubusercontent.com/lovingshu/LStore/master/architecture.png)
