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
