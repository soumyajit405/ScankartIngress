package com.scankart.app.feature.signup;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scankart.app.dto.RestResponse;


@Service("DefaultSignupService")
public class DefaultSignupService implements SignupService{

	@Autowired
	SignupComponent signupcomponent;
	
	@Override
	public RestResponse registerUser(HashMap<String, String> inputDetails) {
		// TODO Auto-generated method stub
		return signupcomponent.registerUser(inputDetails);
	}
	
	@Override
	public RestResponse registerUserByMerchant(HashMap<String, String> inputDetails) {
		// TODO Auto-generated method stub
		return signupcomponent.registerUserByMerchant(inputDetails);
	}
	
	@Override
	public RestResponse registerMerchant(HashMap<String, Object> inputDetails) {
		// TODO Auto-generated method stub
		return signupcomponent.registerMerchant(inputDetails);
	}

	@Override
	public RestResponse verifyMobile(String mobileNumber) {
	
		return signupcomponent.verifyMobile(mobileNumber);
	}
	
	@Override
	public RestResponse verifyMobileForMerchant(String mobileNumber) {
	
		return signupcomponent.verifyMobileForMerchant(mobileNumber);
	}
	
	
	@Override
	public RestResponse verifyOtp(HashMap<String,Object> authenticationDetails) {
	
		return signupcomponent.verifyOtpForUser((String)authenticationDetails.get("mobileNumber"), (String)authenticationDetails.get("otp"),  (String) authenticationDetails.get("otpType"),(String)authenticationDetails.get("playerId"));
	}
	
	@Override
	public RestResponse verifyOtpForMerchant(HashMap<String,Object> authenticationDetails) {
	
		return signupcomponent.verifyOtpForMerchant((String)authenticationDetails.get("mobileNumber"), (String)authenticationDetails.get("otp"), (String) authenticationDetails.get("otpType"),(String)authenticationDetails.get("playerId"));
	}
	
	@Override
	public RestResponse loginMerchant(HashMap<String,Object> authenticationDetails) {
	
		return signupcomponent.loginMerchant((String)authenticationDetails.get("loginId"), (String) authenticationDetails.get("code"),(String)authenticationDetails.get("playerId"));
	}
	
	@Override
	public RestResponse resendOtp(String mobileNumber, String type) {
	
		return signupcomponent.resendOtp(mobileNumber, type);
	}
	
	@Override
	public RestResponse resendOtpForMerchant(String mobileNumber, String type) {
	
		return signupcomponent.resendOtpForMerchant(mobileNumber, type);
	}
	
	@Override
	public RestResponse logoutUser(int userId, String apiKey) {
	
		return signupcomponent.logoutUser(userId, apiKey);
	}
	
	@Override
	public RestResponse logoutMerchant(int merchantId, int merchantPlayerId,String apiKey) {
	
		return signupcomponent.logoutMerchant(merchantId, merchantPlayerId,apiKey);
	}

	@Override
	public RestResponse healthCheck() {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		response.setResponse("Up and Running");
		return response;
	}

}
