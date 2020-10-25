package com.scankart.app.model;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllOtpRepository extends JpaRepository<AllOtp, Long>
{
 
	@Query("select COALESCE(max(id),0) from AllOtp where userId=?1 and otp=?2 and loginType.type=?3 and otpType=?4")
	int verifyOtp(int userId, String otp,String type, String otpType);
	
	@Query("select COALESCE(max(id),0) from AllOtp where userId=?1 and otp=?2 and loginType.type=?3")
	int verifyOtpForMerchant(int userId, String otp,String type);
	
	@Modifying
	@Query(value="update all_otp a set a.otp=?1 where a.user_id=?2 and a.type_id=?3 and a.otp_type=?4",nativeQuery = true)
	void updateOtp(String otp, int userId, int type, String otpType);
	
}