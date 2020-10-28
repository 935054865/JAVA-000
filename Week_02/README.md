#作业

#### 1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。
+ SerialGC Xms = Xmx  执行3次 时间50秒
    +  
    | UseSerialGC  | 128M | 256M | 512M   | 1G     | 2G     | 4G     | 8G     |
    |--------------|------|------|--------|--------|--------|--------|--------|
    | young GC swt | 10ms  | 20ms  | 40ms    | 60ms    | 80ms    | 130ms  | 160ms  |
    | full GC swt   | 20ms  | 30ms  | 50ms    | 70ms    | 70ms    | 80ms    | 500ms  |
    | object count | OOM  | OOM  | 269979 | 614925 | 831968 | 996771 | 888742 |
    + 总结 
        + 堆内存越大，swt时间会越长， full gc 当4G之前时趋于稳定
        + 当到512m时 进行几次full gc 后不在进行young gc 无限full gc
            + 是因为 full gc后 的容量和年轻代晋升到老年代的大小 达成一种平衡吗？？
    + 问题
        + 第一次FULL GC后 young GC的 速度明显提升，但回收量是差不多的，但当到达8G时却没有明显提示
        + ![Image text](https://raw.githubusercontent.com/935054865/JAVA-000/main/Week_02/Images/%E5%8D%95%E9%97%AE%E9%A2%98.png)

+ ParallelGC Xms = Xmx  执行3次 时间50秒 默认4核cpu
    + 
    | UseSerialGC  | 128M  | 256M | 512M   | 1G     | 2G     | 4G      | 8G      |
    |--------------|-------|------|--------|--------|--------|---------|---------|
    | young GC swt | <10ms | 18ms | 28ms   | 60ms   | 58ms   | 66ms    | 66ms   |
    | full GC swt  | 10ms  | 18ms | 40ms   | 60ms   | 43ms   | 60ms    | 56ms    |
    | object count | OOM   | OOM  | 104905 | 641387 | 802005 | 1054943 | 1074586 |
    
    + 总结 ParallelGC和SerialGC对比
       + 对象分配速度性能相似，但是平均STW时间相差近半 特别是4G堆以上的时候
       + 吞吐量在1G-4G时 吞吐量差不大，当在堆在继续增大时SerialGC性能下降。
            + SerialGC因 单线程 GC swt时间变长导致吞吐量下降， ParallelGC因 swt 时间相对稳定加上并发机制，导致吞吐量持续增高

+ ConcMarkSweepGC PrintGCDetails
    +
        | UseSerialGC  | 128M  | 256M  | 512M   | 1G     | 2G     | 4G     | 8G     |
        |--------------|-------|-------|--------|--------|--------|--------|--------|
        | young GC swt | <10ms | 10ms  | 20ms   | 20ms   | 20ms   | 30ms   | 117ms  |
        | old GC swt   | 10ms  | <10ms | <10ms  | <10ms  | <10ms  | 10ms   | 12ms   |
        | object count | OOM   | OOM   | 279046 | 709485 | 723293 | 515322 | 468327 |

    + 总结
       + 整体GC稳定 10-20ms内， 但吞吐量降低
       + 在1-2G堆内存时吞吐量最高
       + 与 并发GC对比 吞吐量降低很多，当堆内存大于4G时为并发GC的一半，但gc时间稳定特别是 old gc      
​
+ G1 
    + -XX:+UseG1GC -XX:MaxGCPauseMillis=10  -Xms4G -Xmx4G  -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis
    +
        | UseSerialGC  | 128M | 256M | 512M   | 1G     | 2G     | 4G     | 8G     |
        |--------------|------|------|--------|--------|--------|--------|--------|
        | young GC swt | 5ms  | 5ms  | 8ms    | 8ms    | 8ms    | 10ms   | 19ms   |
        | old GC swt   |      |      | 3ms    | 3ms    | 5ms    | 4ms    | 5ms    |
        | object count | OOM  | OOM  | 184459 | 524057 | 639041 | 736968 | 877045 |

    + 总结
        + MaxGCPauseMillis 设置约小 young gc 频率越高，需要做精确测试 搭配最优参数
        + 开始 young gc 时间较长 但之后会降到 指定时间范围左右
            + 会根据之前的统计数据 会调整触发GC的条件， 启发式GC
        + swt 较cms和并发gc 平均swt时间短
        + 与并发GC相比
            + gc时间短 但相对于 并发gc 吞吐量低，
        + 与CMS相比
            + 4G 之前gc swt 时间差不多，但当随着堆内存增大，cms young gc 时间长，吞吐量下降，而G1则还是保持原有水平
        
#### 2、使用压测工具(wrk或sb)，演练gateway-server-0.0.1-SNAPSHOT.jar 示例。

`

    wrk -c10 -t4 -d30s --timeout 1s --latency http://localhost:8088/test
    
    Running 30s test @ http://localhost:8088/test
      4 threads and 10 connections
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency     3.25ms    9.44ms 179.50ms   97.60%
        Req/Sec     0.95k   322.58     1.77k    78.65%
      Latency Distribution
         50%    1.70ms
         75%    2.33ms
         90%    3.86ms
         99%   38.60ms
      113165 requests in 30.07s, 28.51MB read
      Non-2xx or 3xx responses: 113165
    Requests/sec:   3762.78
    Transfer/sec:      0.95MB
    
    
    //连接数相同 线程数为 8
    wrk -c10 -t8 -d30s --timeout 1s --latency http://localhost:8088/test
    
    Running 30s test @ http://localhost:8088/test
      8 threads and 10 connections
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency     1.77ms    2.94ms  68.25ms   97.80%
        Req/Sec   663.68    114.68     1.00k    83.29%
      Latency Distribution
         50%    1.36ms
         75%    1.62ms
         90%    2.02ms
         99%   10.30ms
      158656 requests in 30.04s, 39.97MB read
      Non-2xx or 3xx responses: 158656
    Requests/sec:   5280.90
    Transfer/sec:      1.33MB
    
    
    //线程数一样 连接数不一样
    wrk -c100 -t4 -d30s --timeout 1s --latency http://localhost:8088/test
    
    Running 30s test @ http://localhost:8088/test
      4 threads and 100 connections
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    18.90ms    2.67ms  49.60ms   89.82%
        Req/Sec     1.32k   154.82     1.50k    81.17%
      Latency Distribution
         50%   18.14ms
         75%   19.35ms
         90%   21.56ms
         99%   29.25ms
      157265 requests in 30.05s, 39.62MB read
      Non-2xx or 3xx responses: 157265
    Requests/sec:   5233.17
    Transfer/sec:      1.32MB
    
    
    //TCP连接数量过多反而会影响降低qps
    wrk -c100 -t8 -d30s --timeout 1s --latency http://localhost:8088/test
    
    Running 30s test @ http://localhost:8088/test
      8 threads and 100 connections
      Thread Stats   Avg      Stdev     Max   +/- Stdev
        Latency    20.92ms   11.78ms 205.27ms   94.40%
        Req/Sec   600.12    132.69   770.00     85.67%
      Latency Distribution
         50%   18.01ms
         75%   19.79ms
         90%   25.43ms
         99%   75.74ms
      143379 requests in 30.08s, 36.12MB read
      Non-2xx or 3xx responses: 143379
    Requests/sec:   4766.68
    Transfer/sec:      1.20MB
`