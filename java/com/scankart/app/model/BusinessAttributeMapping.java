package com.scankart.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the business_attribute_mapping database table.
 * 
 */
@Entity
@Table(name="business_attribute_mapping")
@NamedQuery(name="BusinessAttributeMapping.findAll", query="SELECT b FROM BusinessAttributeMapping b")
public class BusinessAttributeMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="is_mandatory")
	private String isMandatory;

	//bi-directional many-to-one association to AllOrder
	@OneToMany(mappedBy="businessAttributeMapping")
	private List<AllOrder> allOrders;

	//bi-directional many-to-one association to BusinessAttributesTypePl
	@ManyToOne
	@JoinColumn(name="business_attr_type_pl_id")
	private BusinessAttributesTypePl businessAttributesTypePl;

	//bi-directional many-to-one association to BusinessTypePl
	@ManyToOne
	@JoinColumn(name="business_type")
	private BusinessTypePl businessTypePl;

	public BusinessAttributeMapping() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsMandatory() {
		return this.isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public List<AllOrder> getAllOrders() {
		return this.allOrders;
	}

	public void setAllOrders(List<AllOrder> allOrders) {
		this.allOrders = allOrders;
	}

	public AllOrder addAllOrder(AllOrder allOrder) {
		getAllOrders().add(allOrder);
		allOrder.setBusinessAttributeMapping(this);

		return allOrder;
	}

	public AllOrder removeAllOrder(AllOrder allOrder) {
		getAllOrders().remove(allOrder);
		allOrder.setBusinessAttributeMapping(null);

		return allOrder;
	}

	public BusinessAttributesTypePl getBusinessAttributesTypePl() {
		return this.businessAttributesTypePl;
	}

	public void setBusinessAttributesTypePl(BusinessAttributesTypePl businessAttributesTypePl) {
		this.businessAttributesTypePl = businessAttributesTypePl;
	}

	public BusinessTypePl getBusinessTypePl() {
		return this.businessTypePl;
	}

	public void setBusinessTypePl(BusinessTypePl businessTypePl) {
		this.businessTypePl = businessTypePl;
	}

}