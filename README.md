# SpringMVC
springMVC注解的方式和非注解的方式的学习和对比
## Spring MVC 环境描述
    jdk：jdk1.8
    tomcat： tomcat8.5
    maven：3.6
    mysql：8.0.12
    编辑器：ecplise2018
## Spring相关配置文件
### pom.xml（maven 配置文件）
```java
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>gudie.spring</groupId>
    <artifactId>SpringMVCDemoTest</artifactId>
    <packaging>war</packaging>
    <version>0.0.1-SNAPSHOT</version>
    <name>SpringMVCDemoTest Maven Webapp</name>
    <url>http://maven.apache.org</url>
  
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <!-- jar 版本设置 -->
        <spring.version>4.2.4.RELEASE</spring.version>
    </properties>
  
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.8.2</version>
            <!-- 表示开发的时候引入，发布的时候不会加载此包 -->
            <scope>test</scope>
        </dependency>
          <!-- spring框架-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>


        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.12</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.2.4</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.18</version>
        </dependency>

        <dependency>
            <groupId>commons-dbcp</groupId>
            <artifactId>commons-dbcp</artifactId>
            <version>1.4</version>
        </dependency>
        <!-- jstl -->
        <dependency>  
                <groupId>javax.servlet</groupId>  
                <artifactId>jstl</artifactId>  
                <version>1.2</version>  
                <scope>runtime</scope>  
            </dependency>  
        <dependency>
            <groupId>taglibs</groupId>
            <artifactId>standard</artifactId>
            <version>1.1.2</version>
        </dependency>
    </dependencies>
    <build>
        <finalName>SpringMVCDemoTest</finalName>
    </build>
    </project>
```
### Web.xml 配置文件
```java
    <!DOCTYPE web-app PUBLIC
    "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd" >

    <web-app>
    <display-name>Archetype Created Web Application</display-name>
  
    <!-- 配置上下时加载application的配置文件 -->
    <context-param>
  	    <param-name>contextConfigLocation</param-name>
  	    <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
    <!-- 加入contextLoaderListen的监听器 -->
    <listener>
		<listener-class>
			org.springframework.web.context.ContextLoaderListener
		</listener-class>
	</listener>
  
  
    <servlet>
		<servlet-name>springmvc</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<!-- contextConfigLocation配置springmvc加载的配置文件（配置处理器映射/适配器等等）
			若不配置，默认加载WEB-INF/servlet名称-servlet（springmvc-servlet.xml）
		 -->
		 <init-param>
		 	<param-name>contextConfigLocation</param-name>
		 	<param-value>classpath:springmvc-nonannotation.xml</param-value>
		 </init-param>
		 <load-on-startup>1</load-on-startup>
    </servlet>
  
    <servlet-mapping>
  	   <servlet-name>springmvc</servlet-name>
  	<!-- 
  		第一种：*.action,访问.action结尾，有dispartcherServlet进行解析
  		第二种：/，所有访问的地址有DispatcherServlet进行解析，对静态文件的解析需要配置不让DispatcherServlet进行解析
  		第三种：/*，这样配置不对，使用这种配置，最终要转发到一个jsp页面时，仍然会由DispatcherServlet解析jsp地址，
  		不能根据jsp页面找到handler，会报错
  	 -->
  	<url-pattern>*.action</url-pattern>
    </servlet-mapping>
  
    </web-app>
```    
   web.xml 需要注意的地方：
   <load-on-startup>1</load-on-startup>,数字<=0时最先加载

### applicationContext.xml的配置文件
```java
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:mvc="http://www.springframework.org/schema/mvc"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:tx="http://www.springframework.org/schema/tx" 
        xsi:schemaLocation="
        http://www.springframework.org/schema/beans 
        http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context 
         http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx 
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop 
        http://www.springframework.org/schema/aop/spring-aop.xsd
         http://www.springframework.org/schema/mvc
         http://www.springframework.org/schema/mvc/spring-mvc.xsd"> 

		<!-- connection databases of mysql -->
		<!-- 加载db.properties的配置文件，必须啊时key命名的规则 -->
		<context:property-placeholder location="classpath:/db.properties" ignore-unresolvable="true"/>
		
		<!-- 配置datasource配置 -->
		<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
			<property name="driverClassName" value="${jdbc.driver}"></property>
			<property name="url" value="${jdbc.url}"></property>
			<property name="username" value="${jdbc.username}"></property>
			<property name="password" value="${jdbc.password}"></property>
		</bean>
		
		<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
			<property name="dataSource" ref="dataSource"></property>		
		</bean>
    </beans>
```    
完整的beans的头，可适用于Spring所有配置文件：
```java
    <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:mvc="http://www.springframework.org/schema/mvc"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:tx="http://www.springframework.org/schema/tx" 
        xsi:schemaLocation="
        http://www.springframework.org/schema/beans 
        http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context 
         http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx 
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop 
        http://www.springframework.org/schema/aop/spring-aop.xsd
         http://www.springframework.org/schema/mvc
         http://www.springframework.org/schema/mvc/spring-mvc.xsd"> 
```
ApplicationContext.xml主要负责数据库的连接和除了Controller外的Service和dao的注册
    数据库配置如下：
```java
     <!-- connection databases of mysql -->
        <!-- 加载db.properties的配置文件，必须啊时key命名的规则 -->
    <context:property-placeholder location="classpath:/db.properties" ignore-unresolvable="true"/>
    
    <!-- 配置datasource配置 -->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <property name="driverClassName" value="${jdbc.driver}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>
    
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>        
    </bean>
```    
db.properties：
```java    
    #JDBC connection Mysql5
    #jdbc.driver=com.mysql.jdbc.Driver
    #JDBC connect mysql6 above version use this driver
    jdbc.driver=com.mysql.cj.jdbc.Driver
    #if msyql version on mysql5 above,please url addition serverTimezone=HongKong
    jdbc.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
    jdbc.username=root
    jdbc.password=00000
```
### springmvc.xml 的配置
    由于这个项目我学习是区分注解和非注解的方式学习的，所以我有2个配置文件，一个是springmvc-nonannotation.xml和springmvc-annotation.xml
### springmvc-nonannotation.xml
```java
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:mvc="http://www.springframework.org/schema/mvc"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:tx="http://www.springframework.org/schema/tx" 
        xsi:schemaLocation="
        http://www.springframework.org/schema/beans 
        http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context 
         http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx 
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop 
        http://www.springframework.org/schema/aop/spring-aop.xsd
         http://www.springframework.org/schema/mvc
         http://www.springframework.org/schema/mvc/spring-mvc.xsd"> 
	
	 <mvc:annotation-driven /> 
    <!-- 非注解的完整的配置文件 start -->	
	    <!-- 处理器映射器
		将bean的name作为url进行查找，需要在配置Handler时指定beanname（就是url）
	 -->
	<!--  <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"></bean> -->
	 
	 <!-- 处理器适配器
	 	所有处理器适配器都实现了HandlerAdapter接口
	  -->
	 	<!-- <bean class="org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter"></bean> -->

	<!-- 视图解析器
		解析jsp，默认使用jstl，classpath下要有jstl的包
	 -->	
	 <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"></bean>
    <!-- 非注解的完整的配置文件 end -->
	
	<!-- 注册controller service dao-->	
	<!-- 注册dao -->
	<bean id="loginDao" class="com.companyName.projectName.login.dao.imp.LoginDaoImpl">
			<property name="jdbcTemplate" ref="jdbcTemplate"></property>		
	</bean>
	<!-- 注册service -->
	<bean id="loginService" class="com.companyName.projectName.login.service.imp.loginServiceImpl">
		 <property name="loginDao" ref="loginDao"></property> 
	</bean>
	
	<!-- 注册controller -->
	<bean id="loginController" name="/login" class="com.companyName.projectName.login.controller.LoginController">
		  <property name="loginServiceImpl" ref="loginService"></property> 
	</bean>
	<bean id="loginController2" class="com.companyName.projectName.login.controller.LoginController2">
		  <property name="loginServiceImpl" ref="loginService"></property> 
	</bean>
	
	<!-- 简单的url映射，可根据不同的请求url调用不同的controller -->
	<bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
		<property name="mappings">
			<props>
				<prop key="/login.action">loginController</prop>
				<prop key="/login2.action">loginController2</prop>
			</props>
		</property>
	</bean>
    </beans>
```
注意：
1. 如果配置了<mvc:annotation-driven /> 可以不配置处理器映射器和适配器
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"></bean>
    <bean class="org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter"></bean>
但如果Controller实现的是Controller的类，就必须使用<mvc:annotation-driven />，否则识别不到
2.配置视图解析器时记得要在maven引入jstl.jar和standard.jar
```java  
<dependency>  
                <groupId>javax.servlet</groupId>  
                <artifactId>jstl</artifactId>  
                <version>1.2</version>  
                <scope>runtime</scope>  
            </dependency>  
        <dependency>
            <groupId>taglibs</groupId>
            <artifactId>standard</artifactId>
            <version>1.1.2</version>
        </dependency>
  </dependencies>
```
由于tomcat会有自己的jstl，所有我们要在jsp页面上加上下面这一句,否则jstl无法识别
```java
   <!-- jstl 需要用以下的设置屏蔽tomcat自身的jstl -->
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
```
3.将Controller/service/dao注入
```java
<!-- 注册controller service dao-->    
    <!-- 注册dao -->
    <bean id="loginDao" class="com.companyName.projectName.login.dao.imp.LoginDaoImpl">
            <property name="jdbcTemplate" ref="jdbcTemplate"></property>        
    </bean>
    <!-- 注册service -->
    <bean id="loginService" class="com.companyName.projectName.login.service.imp.loginServiceImpl">
         <property name="loginDao" ref="loginDao"></property> 
    </bean>
    
    <!-- 注册controller -->
    <bean id="loginController" name="/login" class="com.companyName.projectName.login.controller.LoginController">
          <property name="loginServiceImpl" ref="loginService"></property> 
    </bean>
    <bean id="loginController2" class="com.companyName.projectName.login.controller.LoginController2">
          <property name="loginServiceImpl" ref="loginService"></property> 
    </bean>
    
    <!-- 简单的url映射，可根据不同的请求url调用不同的controller -->
    <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <prop key="/login.action">loginController</prop>
                <prop key="/login2.action">loginController2</prop>
            </props>
        </property>
    </bean>
```
 注意<bean></bean>中的<property>中的name 必须和注入类中的名字相同，否则会报错;
 
 可以用SimpleUrHandlerMapping根据不同的请求的路径调用不同的Controller
 ```java
<bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <prop key="/login.action">loginController</prop>
                <prop key="/login2.action">loginController2</prop>
            </props>
        </property>
    </bean>
 ```
### 项目目录结构
![](https://raw.githubusercontent.com/guideJiang/SpringMVC/master/SpringMVCDemoTest/src/main/webapp/WEB-INF/img/structure.png)

### Controller类
```java
package com.companyName.projectName.login.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import com.companyName.projectName.login.entities.LoginEntity;
import com.companyName.projectName.login.service.imp.loginServiceImpl;

public class LoginController implements HttpRequestHandler{

	private loginServiceImpl loginServiceImpl;
	
	public loginServiceImpl getLoginServiceImpl() {
		return loginServiceImpl;
	}

	public void setLoginServiceImpl(loginServiceImpl loginServiceImpl) {
		this.loginServiceImpl = loginServiceImpl;
	}

	public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException {
			
		System.out.println("login controller....");
		LoginEntity entity = loginServiceImpl.getlogin();
		
		//调用service查找数据库，查询商品列表，这里使用静态数据模拟
        List<LoginEntity> itemsList = new ArrayList<LoginEntity>();

      
        itemsList.add(entity);

		
		//由于HttpRequestHandler的httpservletrequest没有返回ModelAndView所有我们可以用以下的方式转发
		//设置模型数据
        httpServletRequest.setAttribute("itemsList",itemsList);
        //设置转发的视图
        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(httpServletRequest,httpServletResponse);

	}
}
```
### service类
```java
package com.companyName.projectName.login.service.imp;

import com.companyName.projectName.login.dao.imp.LoginDaoImpl;
import com.companyName.projectName.login.entities.LoginEntity;
import com.companyName.projectName.login.service.loginService;

public class loginServiceImpl implements loginService {
	
	private LoginDaoImpl loginDao;
	
	

	public LoginDaoImpl getLoginDao() {
		return loginDao;
	}



	public void setLoginDao(LoginDaoImpl loginDao) {
		this.loginDao = loginDao;
	}



	public LoginEntity getlogin() {
		//
		System.out.println("login service");
		LoginEntity entity = loginDao.getloginUser();
		return entity;
	}
}
```
注意：
 注入的对象需有get/set方法，不然无法注入而报错

### dao类
```java
package com.companyName.projectName.login.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.companyName.projectName.login.dao.LoginDao;
import com.companyName.projectName.login.entities.LoginEntity;


public class LoginDaoImpl implements LoginDao{

	private JdbcTemplate jdbcTemplate;
	
	
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}



	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	@Override
	public LoginEntity getloginUser() {
		List<Map<String, Object>> array = jdbcTemplate.queryForList("select * from login");
		
		LoginEntity entity = new LoginEntity();
		
		for (Map<String, Object> map : array) {
			String username = (String) map.get("username");
			String pwd = (String) map.get("password");
			int role =  (int) map.get("role");
			entity.setUsername(username);
			entity.setPassword(pwd);
			entity.setRole(role);
		}
		
		System.out.println(entity.toString());
		return entity;
	}
}
```
### jsp页面
```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <!-- jstl 需要用以下的设置屏蔽tomcat自身的jstl -->
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:forEach items="${itemsList }" var="item">
		<p>${item.username }</p>
		<p>${item.password }</p>
		<p>${item.role }</p>
	</c:forEach>	
</body>
</html>
```



    
    

    


    
