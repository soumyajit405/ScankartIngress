package com.scankart.app.model;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllUserLoginRepository extends JpaRepository<AllUserLogin, Long>
{
	@Query("select a from LoginType a where a.type=?1")
	LoginType getLoginType(String type);
	
	@Modifying
	@Query("update AllUserLogin set token=?1,loginTime=?4,logoutTime=null,playerId=?5 where userId=?2 and loginType.id=?3 ")
	void loginUser(String token, int userId, int loginType, Timestamp loginTime, String playerId);
	
	@Modifying
	@Query("update AllUserLogin set token=?1,loginTime=?4,logoutTime=null where userId=?2 and loginType.id=?3 ")
	void loginMerchant(String token, int userId, int loginType, Timestamp loginTime);
	
	@Modifying
	@Query("update AllUserLogin set loginTime=?3,logoutTime=null where userId=?1 and loginType.id=?2 ")
	void loginMultipleMerchant( int userId, int loginType, Timestamp loginTime);
	
	@Modifying
	@Query("update AllUserLogin set token=null,logoutTime=?3 where userId=?1 and loginType.id=?2 ")
	void logoutUser( int userId, int loginType, Timestamp loginTime);
	
	@Query("select a from AllUserLogin a where a.loginType.id=?1 and a.userId=?2 and a.token=?3")
	AllUserLogin checkUserValidity(int type, int userId, String token);
	
	@Query("select a from AllUserLogin a where a.loginType.id=?1 and a.userId=?2")
	AllUserLogin findLogin(int type, int userId);
 
}