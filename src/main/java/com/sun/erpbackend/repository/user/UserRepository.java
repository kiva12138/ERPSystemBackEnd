package com.sun.erpbackend.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.user.User;

/**
 * 用户DAO层
 * @author 夜流歌
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {
	User findByNameAndPassword(String name, String password);
	User findByName(String name);
	/*
	 * 自定义查询 多表查询 通过用户ID查找用户的角色
	 */
	@Query(nativeQuery=true,value="select r.* from role r, user_role ur where r.id=ur.rid and ur.uid = ?1")
	List<Object[]> findUserRolesByUid(@Param ("id")Integer id);
	
	/*
	 * 修改用户密码
	 */
	@Modifying
	@Query(value="UPDATE User user SET user.password=:password WHERE user.id=:userId")
	int updatePassword(@Param ("password")String password, @Param ("userId") Integer id);
	
	/*
	 * 修改用户昵称
	 */
	@Modifying
	@Query(value="UPDATE User user SET user.name=:name WHERE user.id=:userId")
	int updateName(@Param ("name")String name, @Param ("userId") Integer id);
}
