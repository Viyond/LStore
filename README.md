# LStore
the LStore base on rocksdb with netty protocol,and easy to use,and of course,billions of **KEY-VALUE pair** can easily store into it!And query them with **ms** latency.It'll be awesome if you run it on **SSD**.

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
| info  | get informateions of store base | no limits |
