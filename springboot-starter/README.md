1.引入依赖
```xml
     <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-autoconfigure</artifactId>
                <version>LATEST</version>
            </dependency>
        </dependencies>
```

2.创建一个希望自动注入的类--例如 HelloService 

3.创建读取配置文件的类 -- 例如 HelloProperties

4.配置自动装配的类 -- 例如 HelloServiceAutoConfig

5.在resources创建 META-INF 文件夹 其下创建spring.factories 文件 文件中加入如下内容:
```
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
第4步自动装配的类的全类名
```