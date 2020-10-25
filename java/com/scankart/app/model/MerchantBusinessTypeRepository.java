package com.scankart.app.model;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MerchantBusinessTypeRepository extends JpaRepository<MerchantBusinessType, Long>
{
	@Query("select COALESCE(max(id),0) from MerchantBusinessType ")
	int findMerchantBusinessCount();
}