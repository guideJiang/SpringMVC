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
