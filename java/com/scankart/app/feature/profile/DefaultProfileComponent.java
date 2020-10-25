package com.scankart.app.feature.profile;

import java.util.HashMap;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scankart.app.dto.ErrorResponse;
import com.scankart.app.dto.RestResponse;
import com.scankart.app.model.AllUser;
import com.scankart.app.model.AllUserRepository;
import com.scankart.app.util.CustomMessages;
import com.scankart.app.util.ValidatorUtility;


@Transactional
@Repository
@Service("DefaultProfileComponent")
public class DefaultProfileComponent implements ProfileComponent{

	
	@Autowired
	AllUserRepository alluserrepository;
	
	@Autowired 
	ValidatorUtility validatorUtil;

	@Override
	public RestResponse getUserProfile(int userId, String apiKey) {
		RestResponse response = new RestResponse();
		DozerBeanMapper dozenBeanMapper = new DozerBeanMapper();
		try {
			int status = validatorUtil.validateUser(apiKey, userId, 1);
			if (status == 0 || status == -1) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UAU"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			AllUser user = alluserrepository.findUserById(userId);
			if (user == null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			AllUserDto userdto = new AllUserDto();
			userdto = dozenBeanMapper.map(user, AllUserDto.class);
			HashMap<String, Object> validationMessage = new HashMap<String, Object>();
			validationMessage.put("message", CustomMessages.getCustomMessages("RS"));
			validationMessage.put("key", 200);
			validationMessage.put("userData", userdto);
			response.setResponse(validationMessage);
					
			} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;
	}
	
	
	@Override
	public RestResponse updateUserProfile(int userId, String apiKey, String userName) {
		RestResponse response = new RestResponse();
		DozerBeanMapper dozenBeanMapper = new DozerBeanMapper();
		try {
			int status = validatorUtil.validateUser(apiKey, userId, 1);
			if (status == 0 || status == -1) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UAU"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			AllUser user = alluserrepository.findUserById(userId);
			if (user == null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			alluserrepository.updateUser(userName, userId);
			HashMap<String, Object> validationMessage = new HashMap<String, Object>();
			validationMessage.put("message", CustomMessages.getCustomMessages("AS"));
			validationMessage.put("key", 200);
			response.setResponse(validationMessage);
					
			} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;
	}
	
}
