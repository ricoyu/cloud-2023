# 一 启动步骤

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

演示RestTemplate加上@LoadBalanced注解后便具有:

* ribbon客户端负载均衡功能
* 根据微服务名称来调用

1. awesome-service
   * server.port设为0, 就是每次启动分配一个随机端口号, Idea要允许它启动多个instance
   * 这个不需要连数据库
2. portal-service
   * 通过restTemplate.getForObject("http://awesome-service/aws/port", String.class);来循环调用awesome-service 100次, 拿到端口号
   * 演示了sentinel的限流, 关注限流发生后如何处理的异常

### 1.1.2 组合2 演示自定义负载均衡策略

分支lb-002

基于NacosLoadBalancer实现了自己的WeightedNacosLoadBalancer, 但实际上NacosLoadBalancer已经支持Nacos的权重了

基于SpringCloud原生的Loadbalancer提供的ReactorServiceInstanceLoadBalancer实现了一个最简单的SimpleCustomLoadBalancer, 每次取第一个instance

1. awesome-service
   * server.port设为0, 就是每次启动分配一个随机端口号, Idea要允许它启动多个instance
   * 这个不需要连数据库
2. portal-service
   * 通过restTemplate.getForObject("http://awesome-service/aws/port", String.class);来循环调用awesome-service 100次, 拿到端口号

要覆盖默认的全局负载均衡策略, 必须通过

```java
@Configuration
@LoadBalancerClients(
		//value = {
		//		@LoadBalancerClient(name = "awesome-service", configuration = LoadBalancerConfig.AwesomeLBConfig.class),
		//		@LoadBalancerClient(name = "storage-service", configuration = LoadBalancerConfig.StorageLBConfig.class)
		//}
		defaultConfiguration = LoadBalancerConfig.DefaultLoadBalancerConfiguration.class
)
public class LBConfig {
}
```

但是要注意, 不能同时和@LoadBalancerClient一起使用, 否则请求时会报错

### 1.1.3 演示配置中心

分支: 003-config-server

Nacos中创建data-id为portal-service.yaml, portal-service-dev.yaml, portal-service-prod.yaml的配置文件

portal-service的ConfigController的属性注入从Nacos中配置文件中来, 并且支持基于spring.profiles.active来切换

http://localhost:8081/name



### 1.1.4 演示feign基础使用

分支: 004-feign-logging

portal-service调awesome-service, awesome-service Idea要允许它启动多个instance

请求url: http://localhost:8081/portal/port



### 1.1.5 演示feign配置httpclient5, 超时控制

分支: 005-feign-timeout

portal-service调awesome-service, awesome-service Idea要允许它启动多个instance

请求url: http://localhost:8081/portal/timeout-port
