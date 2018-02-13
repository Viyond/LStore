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
  - download LStore-PreRC.tar from release page and put it on your server and extracting them out.
  - make sure you'd Java Environment (1.8+) installed and $JAVA_HOME configured!
  - make some configration within config.properties which in conf directory
  - run start.sh to start the server,when something like "DB inited,cost:xxx,Base Path:xxx,column families:xxx" means server is ready
  
- client
  - download or clone from https://github.com/lovingshu/LStore.git and check mydb-example to learn how to use.
  - make some configration within config.properties in conf directory,the conf directory should be in your project,create it if miss it.If you don't know how to,check mydb-example
  - use com.mydb.client.command.Command to do something you like
  - enjoy
  
- config.properties
  - server
    - bind:the ip or host to access to
    - port:the port to access to
    - auth:the password to access
    - auth.expire:time-out of access in millisecond
    - auth.check.interval:the interval to expire un-authed connections in seconds
    - dbpath:the path to store the data
    - msg.queue.size:size of command queue waiting to be run
    - msg.queue.thread:how many threads to run the command
    - app.thread.size:how many threads to run within apps
  - client
    - bind:the ip of LStore-Server to connect to
    - port:the port of LStore-Server to connect to
    - auth:the password to access
    - auth.expire:time-out of access in seconds
    - maxidle:max idle connections in connection-pool
    - maxtotal:max connections in connection-pool
    - minidle:min connections in connection-pool
    - borrow.timeout:time-out to borrow a connection from connection-pool in millisecond
    - msg.queue.size:size of result queue waiting to be run
    - msg.queue.thread:how many threads to run the result
    - execute.timeout:time-out to execute a command in seconds
    - app.thread.size:how many threads to run within apps
  
##### architecture
![architecture](https://raw.githubusercontent.com/lovingshu/LStore/master/architecture.png)
