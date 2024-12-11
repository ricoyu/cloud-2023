# 一 启动步骤

笔记本恢复以后先执行一下项目根目录下的init.sql

1. nacos

   ```shell
   D:\nacos\bin\startup.cmd -m standalone
   ```

2. sentinel

   就在本项目根目录下, 是一个持久化规则到Nacos的修改版本, 网关规则持久化也做了

   ```shell
   java -jar -Dserver.port=9090 D:\Learning\awesome-cloud\sentinel-dashboard.jar
   ```

   http://localhost:9090/

   指定Nacos地址, 启动Dashboard的时候加 -Dnacos.host=localhost:8848

3. seata TC

   ```shell
D:\seata\bin\seata-server.bat
   ```
   
   三个微服务里面的schema.sql已经包含了业务表和AT模式需要的undo_log表了

4. Skywalking

   ```shell
D:\skywalking\bin\startup.bat
   ```
   
   http://localhost:8080/

5. 微服务接入Skywalking, 需要加入一下JVM 参数

   ```shell
    -javaagent:D:\skywalking\agent\skywalking-agent.jar -Dskywalking.agent.service_name=gateway-service -Dskywalking.collector.backend_service=127.0.0.1:11800 
   ```

## 1.1 微服务启动顺序

在D:\Learning\cloud-2023\portal-service\src\main\resources\templates下, 用命令行启动一个nodejs httpserver

```
http-server -p 80
```

然后就可以通过页面来发起测试了http://localhost/order.html

### 1.1.1 组合1 -- 演示 @LoadBalanced

分支 lb-001

测试URL: http://localhost:8081/portal/lb-restTemplate

要启动order, account, storage三个服务, 然后直接可以打开上面的页面来测试 http://localhost/order.html

* 创建订单接口是: http://localhost:8082/order/create

Ribbon组件已经被官方弃用, @LoadBalanced注解不生效的问题, 添加loadbalance组件即可解决

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

演示RestTemplate加上@LoadBalanced注解后便具有:

* ribbon客户端负载均衡功能
* 根据微服务名称来调用

1. awesome-service
   * server.port设为0, 就是每次启动分配一个随机端口号, Idea要允许它启动多个instance
   * 这个不需要连数据库
   
2. portal-service
   * 通过restTemplate.getForObject("http://awesome-service/aws/port", String.class);来循环调用awesome-service 100次, 拿到端口号
   
     

### 1.1.2 Demo2 -- 演示feign调用

分支 002-feign

测试URL http://localhost:8082/order/create

要启动order, account, storage三个服务, 然后直接可以打开上面的页面来测试 http://localhost/order.html



1.1.3 Demo3 -- 演示feign 日志

分支 003-feign-logging

测试URL http://localhost:8082/order/create

要启动order, account, storage三个服务, 然后直接可以打开上面的页面来测试 http://localhost/order.html



### 1.1.4 Demo4 -- 演示feign超时

分支 004-feign-timeout

account和storage设置了不同的read-timeout时间, 演示不同微服务设置不同的超时时间

测试URL http://localhost:8082/order/create

要启动order, account, storage三个服务, 然后直接可以打开上面的页面来测试 http://localhost/order.html
