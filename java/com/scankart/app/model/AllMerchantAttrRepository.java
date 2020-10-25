package com.scankart.app.model;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllMerchantAttrRepository extends JpaRepository<AllMerchantAttribute, Long>
{
	
	@Query("select COALESCE(max(id),0) from AllMerchantAttribute ")
	int findAttrCount();
}