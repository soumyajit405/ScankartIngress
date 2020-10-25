package com.scankart.app.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the all_merchant_players database table.
 * 
 */
@Entity
@Table(name="all_merchant_players")
@NamedQuery(name="AllMerchantPlayer.findAll", query="SELECT a FROM AllMerchantPlayer a")
public class AllMerchantPlayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	//bi-directional many-to-one association to AllMerchant
	@ManyToOne
	@JoinColumn(name="merchant_id")
	private AllMerchant allMerchant;
	
	@Column(name="player_id")
	private String playerId;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public AllMerchantPlayer() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AllMerchant getAllMerchant() {
		return this.allMerchant;
	}

	public void setAllMerchant(AllMerchant allMerchant) {
		this.allMerchant = allMerchant;
	}

}