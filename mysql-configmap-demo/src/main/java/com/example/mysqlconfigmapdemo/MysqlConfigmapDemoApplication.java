package com.example.mysqlconfigmapdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MysqlConfigmapDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlConfigmapDemoApplication.class, args);
	}
}

@RestController
class RestApiController {
	public static Connection getUserConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String url = System.getenv().getOrDefault("URL", "jdbc:mysql://yona.northstar.co.kr:6201/test");
		String nm = System.getenv().getOrDefault("NM", "test");
		String pw = System.getenv().getOrDefault("PW", "nsc@2018");
		
		Connection conn = DriverManager.getConnection(url, nm, pw);
		
		return conn;
	}
	
	public static Connection getBoardConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String url = System.getenv().getOrDefault("URL", "jdbc:mysql://yona.northstar.co.kr:6201/test");
		String nm = System.getenv().getOrDefault("NM", "test");
		String pw = System.getenv().getOrDefault("PW", "nsc@2018");
		
		Connection conn = DriverManager.getConnection(url, nm, pw);
		
		return conn;
	}
	
	@RequestMapping("/")
	public String getWelcome() {
		return "welcome";
	}
	
	@RequestMapping("/user")
	public String getUser() throws ClassNotFoundException, SQLException {
		
		Connection conn = getUserConn();		
		String sql = "select * from tb_user";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		String rtnStr = "USER DB >> ";
		while(rs.next()) {
			String id = rs.getString("ID");
			String name = rs.getString("NAME");
			String email = rs.getString("EMAIL");
			rtnStr += id + " " +  name + " " + email + " /";
		}
		return rtnStr;
	}
	
	@RequestMapping("/board")
	public String getBoard() throws ClassNotFoundException, SQLException {
		
		Connection conn = getBoardConn();		
		String sql = "select * from tb_board";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		String rtnStr = "BOARD DB >> ";
		while(rs.next()) {
			String id = rs.getString("ID");
			String title = rs.getString("TITLE");
			String content = rs.getString("CONTENT");
			rtnStr += id + " " +  title + " " + content + " /";
		}
		return rtnStr;
	}
}
