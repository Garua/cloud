#### eureka集群，互相注册，相互守望
##### 需要在RestTemplate配置类中加上 @LoadBalanced注解，不然不知道找哪个服务，报错
-  http://localhost:8001/actuator/health`浏览器中输入这个查看服务的状态
### zookeeper注册
#### win10 docker安装zookeeper
`进入cmd，docker search zookeeper，
 docker pull zookeeper 默认下载最新，
 docker run -p 2181:2181 --name zookeeper zookeeper 启动容器
 docker exec -it 容器id /bin/bash
 ./zkCli.sh 启动客户端
 ls / 查看节点
 当服务注册成功时
![img.png](img.png)
`
#### 配置中心
##### 消息总线的方式，刷新客户端配置，需要引入消息总线的包，并配置好
###### rabbitmq的环境以及相关yml配置，然后手动curl -X POST "http://localhost:3344/actuator/bus-refresh"
###### 这样3355，3366都能刷新配置

######通知指定的客户端，curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355"

 ----

- 当一段时候内收不到心跳是，不会马上剔除服务，隔一段时候收不到心跳，则剔除，临时结点

###三个注册中心的异同
- C:Consistency(强一致性)
- A:Availability(可用性)
- P:Partition tolerance(分区容错性)
- 分布式，P一定要保证

|组件名|语言|CAP|服务健康检查|对外暴露接口|spring集成|
|:----:|:----:|:----:|:----:|:----:|:----:|
|eureka|java|ap|可配支持|http|已集成|
|consul|go|cp|支持|http/dns|已集成|
|zookeeper|java|cp|支持|客户端|已集成|

nacos支持ap，cp。可以切换
`cur -X PUT $NACOS_SERVER:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP`

#### openfeign设置超时配置
`
 当服务端有耗时接口时，需要设置这个
 设置feign客户端的超时时间，openfeign默认支持ribbon
ribbon:
指的是建立连接所用的时间，适用于网络状况正常的情况下，两端连接所用的时间
ReadTimeout: 5000
指连接建立成功后，从服务器读取到可用资源所用时间
ConnectTimeout: 5000
`

######日志配置类
- NONE:默认的，不显示任何日志
- BASIC:仅记录请求方法，URL,响应状态码及执行时间
- HEADERS:除了BASIC中定义的信息外，还有请求和响应关的信息
- FULL:除了HEADERS中定义的信息之外，还有请求和响应正文及元数据

```
@Configuration
public class FeignConfig {
@Bean
public Logger.Level feignLoggerLevel() {
return Logger.Level.FULL;
}
}
```

####Hystrix
######重要三点
- 服务降级 fallback
- 服务熔断 break
- 服务限流 flowlimit
- 接近实时的监控
### Hystrix中@HystrixCommand不指定fallback时，如果配置了全局fallback就走全局的
`@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")`
#### 服务熔断
### 熔断机制是应对雪崩效应的一种微服务链路保护机制

----
#### Linux中nacos集群配置
```
一个nacos：直接启动在8848端口startup.sh,并配置数据库相关的信息。单机应该修改startup.sh中的启动模式为"standalone" 
三个nacos: 3333,4444,5555启动时，加-p参数，设置端口
  修改各自目录下的cluster.conf
     xxx.xxx.xxx.xxx:3333
     xxx.xxx.xxx.xxx:4444
     xxx.xxx.xxx.xxx:5555
  注意：xxx.xxx.xxx.xxx不能是127.0.0.1，要hostname -i命令能够识别的
  
修改nacos启动脚本：startup.sh
中的while部分
下面这里修改之后，老版本可能只是":m:f:s",并且后面的没有对应的case
while getopts ":m:f:s:c:p:" opt
do
    case $opt in
        m)
            MODE=$OPTARG;;
        f)
            FUNCTION_MODE=$OPTARG;;
        s)
            SERVER=$OPTARG;;
        c)
            MEMBER_LIST=$OPTARG;;
        p)
            EMBEDDED_STORAGE=$OPTARG;;
        ?)
        echo "Unknown parameter"
        exit 1;;
    esac
done
如果上面修改后，没有作用，再修改(修改前) nohup $JAVA ${JAVA_OPT} nacos.nacos >> ${BASE_DIR}/logs/start.out 2>&1 &
修改后：
nohup $JAVA -Dserver.port=${PORT} ${JAVA_OPT} nacos.nacos >> ${BASE_DIR}/logs/start.out 2>&1 &
最后，启动方式：./startup.sh -p 3333;./startup.sh -p 4444;./startup.sh -p 5555

再配置nginx

upstream cluster{
  server 127.0.0.1:3333;
  server 127.0.0.1:4444;
  server 127.0.0.1:5555;

}

server{
   listen   1111;
   server_name  localhost;
   
   location / {
      proxy_pass http://cluster;
   
   }

}
```

#####Sentinel,下载sentinel-dashboard-1.7.1.jar 包
- 然后直接 java -jar sentinel-dashboadr-1.7.1.jar 运行，有java环境，且8080端口可用
- sentinel采用的是懒加载机制，需要先访问一下我们的服务，才能在sentineldashboard中看到
- QPS限流，请求会被拦截在外面，而线程限流，请求会被放过来，但是请求的线程数量超过限制，则会限流
- 流控模式：
  >直接：api达到限流条件时，直接限流
  
  >关联： 当关联的资源达到阈值时，就限流自己，如果一个a资源，关联了B,那么我们访问B资源超过限制时(未访问a),我们再访问a时，将会被限流
  
  >链路：只记录指定链路上的流量(指定资源从入口资源进来的流量，如果到达阀值，则进行限流)
  
- 流控效果
 >直接->快速失败(默认的流控处理)

 > 预热->Warm UP:根据codeFactor(冷加载因子，默认是3)的值，从阈值/codeFactor，经过预热时长，才到达设置的QPS阈值
 >>例：阈值为10,预热时长设置为5秒，系统初始化的阈值10/约等于3，即阈值开始是3，然后过了5秒才慢慢升高到10
 
 > 排队等待->匀速排队，让请求以匀速的速度通过，阈值类型必须设置为QPS，否则无效
>>/testA每秒请求一次，超过的话就排队等待，等待的超时时间是20000毫秒，适用于间隔性的突发流量
 

- 降级规则，没有半开状态
> RT(平均响应时间，秒级)，平均响应时间超出阈值且在时间窗口内通过的请求>=5,两个条件同时满足后触发降级
> 窗口期过后关闭断路器
> RT最大4900(更大需要—Dcsp.sentinel.statistic.max.rt=XXX才能生效)

>异常比例数(秒级)
> QPS>=5且异常比例(秒级统计)超过阈值时，触发降级，时间窗口结束后，关闭降级,建议用压测工具测试(手动点击请求数很难达到阈值)


>异常数(分钟级)
>>异常数(分钟统计)超过阈值时，触发降级，时间窗口(时间窗口要>=60s)结束后，关闭降级

#####sentinel配置规则持久化
> 引入sentinel-datasource-nacos jar包
> 8401添加如下配置
```
spring:
  cloud:
    sentinel:
      datasource:
        ds1:
          nacos:
            server-addr: localhost:8848
            dataId: cloudalibaba-sentinel-service #服务名，不强制
            groupId: DEFAULT_GROUP
            data-type: json
            rule-type: flow
```
>nacos控制台，新增配置,dataID为cloudalibaba-sentinel-service，Group为DEFAULT_GROUP
```
[
    {
        "resource":"/rateLimit/byUrl",
        "limitApp":"default",
        "grade":1,
        "count":1,
        "strategy":0,
        "controlBehavior":0,
        "clusterMode":false
    }
]
```
>对上面的json说明：
> resource:资源名称
> limitApp:来源应用
> grade:阈值类型，0表示线程数，1表示QPS
> count:单机阈值
> strategy：流控模式，0表示直接，1表示关联，2表示链路
> controlBehavior：流控效果，0表示快速失败，1表示Warm UP，2表示排队等待
> clusterMode:是否集群

#####seata,分布式事务
>TM向TC申请开启一个全局事务，全局事务创建成功并生成一个全局唯一的XID
> XID在微服务调用链路的上下文中传播
> RM向TC注册分支事务，将其纳入XID对应的全局事务的管辖
> TM向TC发起针对XID的全局提交或者回滚决议
> TC调度XID下管辖的全部分支事务完成提交或者回滚请求


#####分布式事务协议
>>> 背景：在分布式系统中，每个节点都知道自己的操作是否成功或者失败，却无法知道其它节点的操作成功或者失败。
>>> 当一个事务跨多个节点时，为了保持事务的原子性与一致性，而引入一个协调者来统一掌控所有参与者的操作结果。
>>> 并指示它们是否把操作结果进行真正的提交或者回滚

######二阶段提交
>>> 二阶段提交协议是常用的分布式事务解决方案，即将事务的提交过程分为两个阶段来进行处理

> 阶段
> >准备阶段
> 
> >提交阶段

>参与角色
> >协调者：事务的发起者

> >参与者：事务的执行者

>第一阶段
> >协调者向所有参与者发送事务内容，询问是否可以提交事务，并等待答复
> >各参与者执行事务操作，将undo和redo信息废事务日志(不提交事务)
> >如参与者执行成功，给协调者反馈同意，否则反馈中止

>第二阶段
#####当协调者从所有参与节点获得的相应消息都为同意时：
- 1.协调者节点向所有参与者节点发出**正式提交**的请求
- 2.参与者节点正式完成操作，并释放在整个事务期间内占用的资源
- 3.参与者节点向协调者节点发送ack完成消息
- 4.协调者节点收到所有参与者节点反馈的ack消息后，完成事务。

#####如果任一参与者节点在第一阶段返回的响应消息为中止，或者协调者节点在第一阶段的询问超时
之前无法获取所有参与者节点的响应消息时：
- 1.协调者节点向所有参与者节点发出**回滚操作**的请求
- 2.参与者节点利用阶段1写入的undo信息执行回滚，并释放在整个事务期间内占用的资源
- 3.参与者节点向协调者节点发送ack回滚完成消息
- 4.协调者节点收到所有参与者节点反馈的ack回滚完成消息后取消事务。

#####不管最后结果如何，第二阶段都会结束当前事务 

----
####三阶段提交

>三阶段提交协议，是二阶段提交协议的改进版本，三阶段提交有两个改动点。
在协调者和参与者中都引入超时机制。
在第一阶段和第二阶段中插入一个准备阶段。保证了在最后提交阶段之前各参与节点的状态是一致的。

