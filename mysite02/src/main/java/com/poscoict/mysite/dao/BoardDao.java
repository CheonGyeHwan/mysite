package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.paging.Criteria;

public class BoardDao {
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			// 1. JDBC Driver 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. Connect DB
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			System.out.print("드라이버 로딩 실패 : " + e);
		}
		
		return conn;
	}
	
	public List<BoardVo> find(String method, Long boardNo, String kwd, Criteria cri) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = null;
			if ("all".equals(method)) {
				sql = "SELECT a.no, a.title, a.contents, a.hit, a.g_no, a.o_no, a.depth, DATE_FORMAT(a.reg_date, '%Y-%m-%d %H:%i:%s') AS reg_date, a.user_no , b.name as user_name "
						+ "FROM board a "
						+ "INNER JOIN user b "
						+ "ON a.user_no = b.no "
						+ "ORDER BY g_no DESC, o_no ASC ";
				
				if (cri != null) {
					sql += "LIMIT " + cri.getSkip() + ", " + cri.getAmount();
				}
				
			} else if ("one".equals(method)) {
				sql = "SELECT a.no, a.title, a.contents, a.hit, a.g_no, a.o_no, a.depth, DATE_FORMAT(a.reg_date, '%Y/%m/%d %H:%i:%s') AS reg_date, a.user_no , b.name as user_name "
						+ "FROM board a "
						+ "INNER JOIN user b "
						+ "ON a.user_no = b.no "
						+ "WHERE a.no = " + boardNo;
			} else if ("search".equals(method)) {
				sql = "SELECT a.no, a.title, a.contents, a.hit, a.g_no, a.o_no, a.depth, DATE_FORMAT(a.reg_date, '%Y-%m-%d %H:%i:%s') AS reg_date, a.user_no , b.name as user_name "
						+ "FROM board a "
						+ "INNER JOIN user b "
						+ "ON a.user_no = b.no "
						+ "WHERE a.title LIKE '%" + kwd + "%' "
						+ "OR a.contents LIKE '%" + kwd + "%' "
						+ "OR b.name LIKE '%" + kwd + "%' "				
						+ "ORDER BY g_no DESC, o_no ASC ";
				
				if (cri != null) {
					sql += "LIMIT " + cri.getSkip() + ", " + cri.getAmount();
				}
			} 
			
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				int groupNo = rs.getInt(5);
				int orderNo = rs.getInt(6);
				int depth = rs.getInt(7);
				String regDate = rs.getString(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setRegDate(regDate);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				
				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.print("error : " + e);
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
				System.out.print("error : " + e);
			}
		}
		
		return result;
	}
	
	public boolean insert(String method, BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = null;		
			
			if ("new".equals(method)) {
				sql = "INSERT INTO board VALUES (NULL, ?, ?, 0, IFNULL((SELECT MAX(sub.g_no) + 1 FROM board AS sub), 1), 1, 1, NOW(), ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getUserNo());
				
			} else if ("reply".equals(method)) {
				update("reply", vo);
				
				sql = "INSERT INTO board VALUES (NULL, ?, ?, 0, ?, ?, ?, NOW(), ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setInt(3, vo.getGroupNo());
				pstmt.setInt(4, vo.getOrderNo() + 1);
				pstmt.setInt(5, vo.getDepth() + 1);
				pstmt.setLong(6, vo.getUserNo());
			}
			
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
	
	public boolean update(String method, BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = null;
			
			// 3. SQL 준비
			if ("notReply".equals(method)) {
				sql = "UPDATE board SET title = ?, contents = ?, reg_date = NOW() WHERE no = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getNo());
				
			} else if ("reply".equals(method)) {
				sql = "UPDATE board SET o_no = (o_no + 1) WHERE o_no > ? AND g_no = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, vo.getOrderNo());
				pstmt.setInt(2, vo.getGroupNo());
				
			} else if ("hit".equals(method)) {
				sql = "UPDATE board SET hit = (hit + 1) WHERE no = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, vo.getNo());
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
	
	public boolean delete(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql = "DELETE FROM board WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩(Binding)
			pstmt.setLong(1, no);
			
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
