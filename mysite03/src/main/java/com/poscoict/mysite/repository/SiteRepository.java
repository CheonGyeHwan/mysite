package com.poscoict.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.poscoict.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo select() {
		SiteVo result = sqlSession.selectOne("admin.select");
		return result;
	}
	
	public boolean update(SiteVo siteVo) {
		int count = sqlSession.update("admin.update", siteVo);
		return count == 1;
	}

}
