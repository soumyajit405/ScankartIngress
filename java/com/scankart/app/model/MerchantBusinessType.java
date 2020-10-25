package com.scankart.app.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the merchant_business_types database table.
 * 
 */
@Entity
@Table(name="merchant_business_types")
@NamedQuery(name="MerchantBusinessType.findAll", query="SELECT m FROM MerchantBusinessType m")
public class MerchantBusinessType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	//bi-directional many-to-one association to AllMerchant
	@ManyToOne
	@JoinColumn(name="business_type_id")
	private BusinessTypePl businessType;

	public BusinessTypePl getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessTypePl businessType) {
		this.businessType = businessType;
	}

	//bi-directional many-to-one association to AllMerchant
	@ManyToOne
	@JoinColumn(name="merchant_id")
	private AllMerchant allMerchant;

	public MerchantBusinessType() {
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