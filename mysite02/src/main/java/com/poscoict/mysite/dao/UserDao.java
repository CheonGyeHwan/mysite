package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.poscoict.mysite.vo.UserVo;

public class UserDao {
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. Connect DB
			String url = "jdbc:mysql://192.168.0.63:3307/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			System.out.print("드라이버 로딩 실패 : " + e);
		}
		
		return conn;
	}
	
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "INSERT INTO user VALUES (NULL, ?, ?, ?, ?, NOW())";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			// 5. SQL 실행
			result = (pstmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
		
		return result;
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "SELECT no, name FROM user WHERE email = ? AND password = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			// 5. SQL 실행
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
		
		return result;
	}

	public UserVo findByNo(Long no) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "SELECT name, email, gender FROM user WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(binding)
			pstmt.setLong(1, no);
			
			// 5. SQL 실행
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
		
		return result;
	}

	public boolean update(UserVo userVo, boolean passwordIsNull) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = null;
			
			// 3. SQL 준비
			if (passwordIsNull) {
				sql = "UPDATE user SET name = ?, gender = ? WHERE no = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getGender());
				pstmt.setLong(3, userVo.getNo());
			} else {
				sql = "UPDATE user SET name = ?, password = ?, gender = ? WHERE no = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getPassword());
				pstmt.setString(3, userVo.getGender());
				pstmt.setLong(4, userVo.getNo());
			}
			
			// 5. SQL 실행
			result = (pstmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
		
		return result;
	}
	
	

}
