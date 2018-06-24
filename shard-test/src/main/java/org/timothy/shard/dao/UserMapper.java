package org.timothy.shard.dao;


import org.timothy.shard.domain.User;
import org.apache.ibatis.session.SqlSession;

public class UserMapper {
    private SqlSession sqlSession;

    public int insert(User record) {
        return this.sqlSession.insert("org.timothy.shard.dao.UserMapper.insert", record);
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}