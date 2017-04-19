/**
 * 
 */
package com.tssa.adminUsers.dao;


import com.tssa.common.dao.HibernateEntitryDaoImpl;


/**
 * @author gmc
 * @see 继承HibernateEntitryDaoImpl<Users>， 若父类方法不能满足需求要扩展功能，
 * 		直接在当前class中增加子类方法。
 */

public class UserDaoImpl extends HibernateEntitryDaoImpl implements UserDao {

}
