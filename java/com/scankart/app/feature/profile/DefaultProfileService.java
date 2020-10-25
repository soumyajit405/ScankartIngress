package com.scankart.app.feature.profile;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scankart.app.dto.RestResponse;


@Service("DefaultProfileService")
public class DefaultProfileService implements ProfileService{

	@Autowired
	ProfileComponent profileComponent;
	
	@Override
	public RestResponse getUserProfile(int userId, String apikey) {
		// TODO Auto-generated method stub
		return profileComponent.getUserProfile(userId, apikey);
	}
	
	@Override
	public RestResponse updateUserProfile(int userId, String apikey, String userName) {
		// TODO Auto-generated method stub
		return profileComponent.updateUserProfile(userId, userName, apikey);
	}
	

}
