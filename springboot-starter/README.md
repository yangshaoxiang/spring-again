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

1：写一个自动装配的jar包，用来写自己启动器的核心逻辑.
2：在自动装配工程中导入redis原生的jar包（不要导入启动器的包）
3：在自动装配工程中创建一个meta-inf/spring.facotries文件  文件内容是
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.tuling.autoconfig.TulingRedisAutoConfiguration
4：把你的自动装配类 安装到自己本地maven仓库中

5:创建一个启动器工程 什么都不做，只做一个自动装配工程的依赖就可以了
6：把启动器工程同样安装到本地maven仓库中.
7：写一个测试工程来使用你自己的启动器好不好用.
	7.1)导入你自己的启动器工程(不是自动装配工程)
	7.2)在application.yml中配置属性.