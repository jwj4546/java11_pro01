package org.haeundae.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.haeundae.dto.Member;

public class MemberDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public List<Member> getMemberList() {
		List<Member> memList = new ArrayList<>();
		MySQLDB mysql = new MySQLDB();

		try {
			con = mysql.connect();
			pstmt = con.prepareStatement(SqlLang.SELECT_ALL_MEMBER);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member mem = new Member(rs.getString("id"), rs.getString("pw"), rs.getString("birth"), rs.getString("email"), rs.getString("tel"), rs.getString("addr"), rs.getString("postcode"));
				memList.add(mem);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			mysql.close(con, pstmt, rs);
		}
		return memList;
	}
	public Member getMember(String id) {
		Member mem = new Member();
		MySQLDB mysql = new MySQLDB();

		try {
			con = mysql.connect();
			pstmt = con.prepareStatement(SqlLang.SELECT_ONE_MEMBER);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mem.setId(rs.getString("id"));
				mem.setPw(rs.getString("pw"));
				mem.setBirth(rs.getString("birth"));
				mem.setEmail(rs.getString("email"));
				mem.setTel(rs.getString("tel"));
				mem.setAddr(rs.getString("addr"));
				mem.setPostcode(rs.getString("postcode"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			mysql.close(con, pstmt, rs);
		}
		return mem;
	}
	public int join(Member mem) {
		int cnt = 0;
		MySQLDB mysql = new MySQLDB();
		try {
			con = mysql.connect();
			pstmt = con.prepareStatement(MySQLDB.INS_MEMBER);
			pstmt.setString(1, mem.getId());
			pstmt.setString(2, mem.getPw());
			pstmt.setString(3, mem.getBirth());
			pstmt.setString(4, mem.getEmail());
			pstmt.setString(5, mem.getTel());
			pstmt.setString(6,  mem.getAddr());
			pstmt.setString(7, mem.getPostcode());
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			mysql.close(con, pstmt);
		}
		return cnt;
	}
	public int upMember(Member mem) {
		int cnt = 0;
		MySQLDB mysql = new MySQLDB();
		try { 
			con = mysql.connect();
			pstmt = con.prepareStatement(SqlLang.UPD_MEMBER);
			pstmt.setString(1, mem.getPw());
			pstmt.setString(2, mem.getBirth());
			pstmt.setString(3, mem.getEmail());
			pstmt.setString(4, mem.getTel());
			pstmt.setString(5, mem.getId());
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			mysql.close(con, pstmt);
		}
		return cnt;
	}
	public int memberOut(String id) {
		int cnt = 0;
		MySQLDB mysql = new MySQLDB();
		try {
			con = mysql.connect();
			pstmt = con.prepareStatement(SqlLang.DEL_MEMBER);
			pstmt.setString(1, id);
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			mysql.close(con, pstmt);
		}
		
		return cnt;
	}
	public boolean idCheck(String id) {
		boolean ck = false;
		MySQLDB mysql = new MySQLDB();
		
		try {
			con = mysql.connect();
			pstmt = con.prepareStatement(SqlLang.SELECT_ONE_MEMBER);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ck = true;
			} else {
				ck = false;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			mysql.close(con, pstmt, rs);
		}
		return ck;
	}
}

