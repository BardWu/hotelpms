package com.networks.pms.dao;


import com.networks.pms.common.db.GenericsUtils;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MybatisDao<T> {
	private SqlSessionTemplate sqlSession;

	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}  
	protected String insert =".insert";
	protected String insertList =".insertList";
	protected String update =".update";
	protected String updateList =".updateList";
	protected String delete =".delete";
	protected String select =".findById";
	protected String getPage =".getPage";
	protected String getList =".getList";
	protected String count =".count";
	
	protected Class<T> entityClass;
	@SuppressWarnings("unchecked" )
	public MybatisDao(){
		entityClass=GenericsUtils.getSuperClassGenricType(getClass());
		insert = entityClass.getSimpleName()+insert;
		insertList =  entityClass.getSimpleName()+insertList;
		update = entityClass.getSimpleName()+update;
		updateList = entityClass.getSimpleName()+updateList;
		delete = entityClass.getSimpleName()+delete;
		select = entityClass.getSimpleName()+select;
		getPage = entityClass.getSimpleName()+getPage;
		getList = entityClass.getSimpleName()+getList;
		count = entityClass.getSimpleName()+count;
	}
	
	
	/**
	 * 插入一条数据
	 * @param t
	 * @return
	 * @throws RuntimeException
	 */
	public int insert(T t)throws RuntimeException{
		return sqlSession.insert(insert, t);

	}
	/**
	 * 插入多条数据
	 * @param t
	 * @return
	 * @throws RuntimeException
	 */
	public int insertList(List<T> t)throws RuntimeException{
		return sqlSession.insert(insertList, t);
	}
	/**
	 * 修改一条数据
	 * @param t
	 * @return
	 * @throws RuntimeException
	 */
	public int update(T t)throws RuntimeException{
		
		return sqlSession.update(update, t);
	}
	/**
	 * 修改一条数据
	 * @param t
	 * @return
	 * @throws RuntimeException
	 */
	public int update(Map<String,Object> t)throws RuntimeException{

		return sqlSession.update(update, t);
	}
	/**
	 * 修改多条数据
	 * @param t
	 * @return
	 * @throws RuntimeException
	 */
	public int updateList(List<T> t)throws RuntimeException{
		return sqlSession.update(updateList, t);
	}
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 * @throws RuntimeException
	 */
	public T findById(String id)throws RuntimeException{
		
		  return sqlSession.selectOne(select, id); 	
    }
	/**
	 * 删除一条数据
	 * @param id
	 * @return
	 * @throws RuntimeException
	 */
	public int delete(String id)throws RuntimeException{
		
		return sqlSession.delete(delete,id);
	}
	/**
	 * 分页查询
	 *
	 * @return
	 * @throws RuntimeException
	 */
	public List<T> getPage(Map<String,Object> paramMap )throws RuntimeException{
		
		return sqlSession.selectList(getPage, paramMap);
	}
	/**
	 * 查询一个表中所有的信息
	 * @return
	 * @throws RuntimeException
	 */
	public List<T> getList(T t)throws RuntimeException{
		
		return sqlSession.selectList(getList,t);
	}
	/**
	 * 查询一个表中所有的信息
	 * @return
	 * @throws RuntimeException
	 */
	public List<T> getList()throws RuntimeException{

		return sqlSession.selectList(getList);
	}
	/**
	 * 查询一个表中所有的信息
	 * @return
	 * @throws RuntimeException
	 */
	public List<T> getList(Map<String,Object> map)throws RuntimeException{

		return sqlSession.selectList(getList,map);
	}
	/**
	 * 查询一共有多少条数据
	 * @return
	 */
	public int count(T t){
		return sqlSession.selectOne(count,t);
	}
	
	
}
