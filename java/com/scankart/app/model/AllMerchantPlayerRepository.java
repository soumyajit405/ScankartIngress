package com.scankart.app.model;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllMerchantPlayerRepository extends JpaRepository<AllMerchantPlayer, Long>
{
	
	@Query("select COALESCE(max(id),0) from AllMerchantPlayer ")
	int findPlayerCount();
	
	@Modifying
	@Query("delete from AllMerchantPlayer where allMerchant.id=?1 and id=?2")
	void deletePlayer(int merchantId, int merchantPlayerId);
	
	@Query("select a.playerId from AllMerchantPlayer a where allMerchant.id=?1")
	List<String> getAllPlayersForMErchant(int merchantId);

}