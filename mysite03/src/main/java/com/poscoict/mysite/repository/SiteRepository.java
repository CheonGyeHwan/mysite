package com.poscoict.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.poscoict.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo find() {
		return sqlSession.selectOne("site.find");
	}
	
	public boolean update(SiteVo siteVo) {
		return sqlSession.update("site.update", siteVo) == 1;
	}

}
