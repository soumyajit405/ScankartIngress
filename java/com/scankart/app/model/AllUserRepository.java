package com.scankart.app.model;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllUserRepository extends JpaRepository<AllUser, Long>
{
	@Query("select a from AllUser a where a.phone=?1")
	AllUser findUserByPhone(String phoneNumber);
	
	@Query("select a from AllUser a where a.id=?1")
	AllUser findUserById(int userId);
	
	
	@Query("select COALESCE(max(id),0) from AllUser ")
	int findUserCount();
	
	@Query("select a from UserAttributeTypePl a where a.name=?1")
	UserAttributeTypePl getAttrType(String name);
	
	@Modifying
	@Query("update AllUser set validatedStatus='Y' where phone=?1 ")
	void validateUser(String phone);
	
	@Query("select a from AllUserAttribute a where a.userAttributeTypePl.name='Email' and a.value=?1")
	AllUserAttribute findUserByEmail(String email);
	
	@Modifying
	@Query("update AllUser set name =?1 where id =?2")
	void updateUser(String name, int userId);
	
	
 
}