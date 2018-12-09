package com.companyName.projectName.login.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.companyName.projectName.login.entities.LoginEntity;
import com.companyName.projectName.login.service.imp.loginServiceImpl;

//如实现Controller需在springMVC.xml中配置<mvc:annotation-driven />
public class LoginController2 implements Controller{
	
private loginServiceImpl loginServiceImpl;
	
	public loginServiceImpl getLoginServiceImpl() {
		return loginServiceImpl;
	}

	public void setLoginServiceImpl(loginServiceImpl loginServiceImpl) {
		this.loginServiceImpl = loginServiceImpl;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("login controller2....");
		LoginEntity entity = loginServiceImpl.getlogin();
		
		//调用service查找数据库，查询商品列表，这里使用静态数据模拟
        List<LoginEntity> itemsList = new ArrayList<LoginEntity>();

      
        itemsList.add(entity);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(entity);
        modelAndView.setViewName("/WEB-INF/jsp/login.jsp");
        
		return modelAndView;
	}
}
