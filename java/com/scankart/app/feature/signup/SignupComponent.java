package com.scankart.app.feature.signup;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.scankart.app.dto.RestResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

public interface SignupComponent 
{
    
    public RestResponse registerUser(HashMap<String,String> inputDetails);
    
    public RestResponse registerUserByMerchant(HashMap<String,String> inputDetails);
    
    public RestResponse verifyMobile(String mobileNumber);
    
    public RestResponse verifyMobileForMerchant(String mobileNumber);
    
    public RestResponse verifyOtpForUser(String mobileNumber, String otp, String otpType, String playerId);
    
    public RestResponse verifyOtpForMerchant(String mobileNumber, String otp, String otpType, String playerId);
    
    public RestResponse loginMerchant(String loginId, String code,String playerId);
    
    public RestResponse resendOtp(String mobileNumber, String type);
    
    public RestResponse resendOtpForMerchant(String mobileNumber, String type);
    
    public RestResponse logoutMerchant(int merchantId, int merchantPlayerId,String apiKey);
    
    public RestResponse logoutUser(int userId, String apiKey);
    
    public RestResponse registerMerchant(HashMap<String,Object> inputDetails);
        
}
