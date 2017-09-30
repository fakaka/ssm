# Spring、Spring MVC、MyBatis 整合文件配置详解
---

SSM框架的项目的文件配置。

# 相关资源
[Spring docs](http://spring.io/docs)  
[MyBatis](http://www.mybatis.org/mybatis-3/)  
[Github 样例项目](https://github.com/fakaka/ssm)

## web.xml的配置

``` xml
<!-- 配置前端控制器 -->
<servlet>
    <servlet-name>spring</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <!-- ContextconfigLocation配置springmvc加载的配置文件适配器、处理映射器等-->
        <param-name>contextConfigLocation</param-name>
        <param-value>WEB-INF/classes/spring/springmvc.xml</param-value>
</init-param>
</servlet>
<servlet-mapping>
    <servlet-name>spring</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
<!--
在<servlet-mapping>中url如果是.action，前端控制器就只会拦截以.action结尾的请求，并不会理会静态的文件。对静态页面的控制就要通过其他的手段。以/作为url的话就会拦截所有的请求，包括静态页面的请求。这样的话就可以拦截任何想要处理的请求，但是有一个问题。如果拦截了所有的请求，如果不在拦截器中做出相应的处理那么所有静态的js、css以及页面中用到的图片就会访问不到造成页面无法正常显示。但这可以通过静态资源的配置来解决这个问题。后面会提到。
-->

配置spring容器：
<!-- 其中applicationContext-*.xml包含3个配置文件，是springIoC容器的具体配置。后面会提到。-->
<context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>WEB-INF/classes/spring/applicationContext-*.xml</param-value>

</context-param>

<listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

## springmvc.xml的配置

### 视图解析器的配置：
在Controller中设置视图名的时候会自动加上前缀和后缀。
``` xml
<!-- 配置视图解析器 -->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
        <!-- 使用前缀和后缀 -->
        <property name="prefix" value="WEB-INF/jsp/"></property>
        <property name="suffix" value=".jsp"></property>
</bean>
```
### Controller的配置

``` xml
<!-- 使用组件扫描的方式可以一次扫描多个Controller -->
<context:component-scan base-package="com.wxisme.ssm.controller">

<!-- 配置注解的处理器映射器和处理器适配器 -->
<mvc:annotation-driven />

<!--配置上传文件数据解析器  -->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <property name="maxUploadSize">
        <value>9242880</value>
    </property>
</bean>

<mvc:resources mapping="/img/**" location="/img/" />
<mvc:resources mapping="/css/**" location="/css/" />  
<mvc:resources mapping="/js/**" location="/js/" />
```

## applicationContext-*.xml的配置

applicationContext-*.xml包括三个配置文件，分别对应数据层控制、业务逻辑service控制和事务的控制。

### 数据访问层的控制，applicationContext-dao.xml的配置：

``` xml
<!-- 加载数据库连接的资源文件 -->
<context:property-placeholder location="classpath:jdbc.properties"/>


<!-- 配置数据源   dbcp数据库连接池 -->
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="${jdbc.driver}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```

其中jdbc.properties文件的内容如下：
``` properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/database
jdbc.username=root
jdbc.password=1234
```
配置sqlSessionFactory

``` xml
<!-- 配置sqlSessionFactory -->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <!-- 注入数据源 -->
    <property name="dataSource" ref="dataSource"/>
    <!-- 扫描mybatis核心配置文件 -->
    <property name="configLocation" value="classpath:mybatis.xml"/>
    <!-- 扫描java bean，自动使用别名 -->
    <property name="typeAliasesPackage" value="com.lyt48.ssm.bean"/>
    <!-- 扫描mybatis的SQL配置文件 -->
    <property name="mapperLocations" value="classpath:mapper/*.xml"/>
</bean>
```

配置Mapper扫描器，扫描mapper包下的所有mapper文件和类，要求mapper配置文件和类名需要一致。

``` xml
<!-- 扫描Dao接口包 -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    <property name="basePackage" value="com.lyt48.ssm.dao"/>
</bean>
```

### 业务逻辑控制，applicationContext-service.xml的配置

``` xml
<!-- 扫描service包 -->
<context:component-scan base-package="com.lyt48.ssm.service" />

<!-- 配置事务管理器 -->
<bean id="transactionManager"  class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  
    <property name="dataSource" ref="dataSource"/>
</bean>

<!-- 事务采用全注解方式 -->
<tx:annotation-driven transaction-manager="transactionManager"/>

<!-- 通知 -->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        <!-- 传播行为 -->
        <tx:method name="save*" propagation="REQUIRED"/>
        <tx:method name="insert*" propagation="REQUIRED"/>
        <tx:method name="update*" propagation="REQUIRED"/>
        <tx:method name="delete*" propagation="REQUIRED"/>
        <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
        <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
    </tx:attributes>
</tx:advice>

<!-- 配置aop  --> 
<aop:config>
    <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.wxisme.ssm.service.impl.*.*(..))"/>
</aop:config>
```

## MyBatis的配置
没什么特别的地方