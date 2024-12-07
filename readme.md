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

### 1.1.1 组合1 -- 演示 @LoadBalanced

分支 lb-001

测试URL: http://localhost:8081/portal/lb-restTemplate

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
   
     

