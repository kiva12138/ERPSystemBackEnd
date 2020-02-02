package com.sun.erpbackend.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.erpbackend.entity.user.Role;
import com.sun.erpbackend.entity.user.User;
import com.sun.erpbackend.repository.user.UserRepository;

/**
 * 用户服务类，提供用户相关的服务
 * @author 夜流歌
 *
 */
@Service
public class UserService implements UserDetailsService{
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username);
		if(user == null) {
			throw new UsernameNotFoundException("账户不存在");
		}
		List<Object[]> roles = userRepository.findUserRolesByUid(user.getId());
		List<Role> rolesObj = new ArrayList<>();
		for (Object[] role : roles) {
			Role role2 = new Role();
			role2.setId((Integer)role[0]);
			role2.setName((String)role[1]);
			role2.setNameZh((String)role[2]);
			rolesObj.add(role2);
		}
		user.setRoles(rolesObj);
		return user;
	}
	
	/*
	 * Return 
	 * 1 Correct
	 * 2 Error
	 */
	@Secured("ROLE_ADMIN")
	@Transactional
	public int updatePassword(Integer id, String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		password = encoder.encode(password);
		int result = this.userRepository.updatePassword(password, id);
		if (result != 1) {
			return result;
		}
		return 1;
	}
	
	/*
	 * Return 
	 * 1 Correct
	 * 2 Error
	 */
	@Secured("ROLE_ADMIN")
	@Transactional
	public int updateName(Integer id, String name) {
		int result = this.userRepository.updateName(name, id);
		if (result != 1) {
			return result;
		}
		return 1;
	}
}
