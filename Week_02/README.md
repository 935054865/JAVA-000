#作业

#### 1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。
### 串行 
+ UseSerialGC Xms = Xmx  执行3次结果 1G时 执行时间改为2秒 4 8 16
    +  
       | UseSerialGC       | 128M                | 256M           | 512M             | 1G             | 2G           | 4G           | 8G           |
       |-------------------|---------------------|----------------|------------------|----------------|--------------|--------------|--------------|
       | young GC real | OOM  0\.0\.1\-0\.02 | 0\.0\.1\-0\.03 | 0\.0\.3\-0\.0\.7 | 0\.06\-0\.22   | 0\.03\-0\.24 | 0\.14\-0\.19 | 0\.13\-0\.23 |
       | old GC real   | OOM  0\.0\.1\-0\.02 | 0\.01\-0\.05   | 0\.0\.6\-0\.07   | 0\.0\.6\-0\.10 | 0\.06\-0\.09 | 0\.14\-0\.20 | 0\.23\-0\.65 |
       | object count  |                     |                |                  | 729459         | 831968       |              |              |


​         