package com.scankart.app.model;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllMerchantRepository extends JpaRepository<AllMerchant, Long>
{
	@Query("select a from AllMerchant a where a.phone=?1")
	AllMerchant findUserByPhone(String phoneNumber);
	
	@Query("select a from AllMerchant a where a.loginId=?1 and a.code=?2")
	AllMerchant checkMerchant(String loginId, String code);
	
	@Query("select a from AllMerchant a where a.id=?1")
	AllMerchant findMerchantById(int id);
	
	@Query("select a from AllMerchantAttribute a where a.businessAttributesTypePl.name='Email' and a.value=?1")
	AllMerchantAttribute findUserByEmail(String email);
	
	@Query("select a from BusinessTypePl a where a.id in ?1")
	List<BusinessTypePl> findBusinessTypes(ArrayList<Integer> businessTypes);
	
	@Query("select COALESCE(max(id),0) from AllMerchant ")
	int findMerchantCount();
	
	@Query("select a from BusinessAttributesTypePl a where a.name=?1")
	BusinessAttributesTypePl getBusinessAttrMap(String name);
	
	@Modifying
	@Query("update AllMerchant set validatedStatus='Y' where phone=?1 ")
	void validateMerchant(String phone);
	
	@Modifying
	@Query("update AllMerchant set currentActiveSessions=?2 where phone=?1 ")
	void updateMerchantSessions(String phone, String sessions);
}