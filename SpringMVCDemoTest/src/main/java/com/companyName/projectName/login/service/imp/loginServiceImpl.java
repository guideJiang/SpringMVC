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
