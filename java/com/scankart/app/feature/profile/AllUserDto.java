package com.scankart.app.feature.profile;


import java.io.Serializable;
import javax.persistence.*;

import com.scankart.app.model.AllMerchantAttribute;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
public class AllUserDto  {

	private int id;

	private String name;
	
	private String email;
	
	private String phone;
	
	
}