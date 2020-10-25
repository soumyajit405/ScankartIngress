package com.scankart.app.feature.signup;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.scankart.app.dto.RestResponse;

import lombok.experimental.Helper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
public interface SignupService 
{
    
    @RequestMapping(value ="registerUser" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public RestResponse registerUser(@RequestBody HashMap<String,String> inputDetails);
    
    @RequestMapping(value ="registerUserByMerchant" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public RestResponse registerUserByMerchant(@RequestBody HashMap<String,String> inputDetails);
    
    @RequestMapping(value ="registerMerchant" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public RestResponse registerMerchant(@RequestBody HashMap<String,Object> inputDetails);
    
    @RequestMapping(value ="verifyMobile/{mobileNumber}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public RestResponse verifyMobile(@PathVariable("mobileNumber") String mobileNumber);
    
    @RequestMapping(value ="verifyMobile/Merchant/{mobileNumber}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public RestResponse verifyMobileForMerchant(@PathVariable("mobileNumber") String mobileNumber);
    
    @RequestMapping(value ="verifyOtp" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public RestResponse verifyOtp(@RequestBody HashMap<String,Object> authenticationDetails);
    
    @RequestMapping(value ="verifyOtpForMerchant" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public RestResponse verifyOtpForMerchant(@RequestBody HashMap<String,Object> authenticationDetails);
    
    @RequestMapping(value ="loginMerchant" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public RestResponse loginMerchant(@RequestBody HashMap<String,Object> authenticationDetails);
    
    
    @RequestMapping(value ="resendOtp/{mobileNumber}/{type}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public RestResponse resendOtp(@PathVariable("mobileNumber") String mobileNumber, @PathVariable("type") String type);
    
    @RequestMapping(value ="resendOtpForMerchant/{mobileNumber}/{type}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public RestResponse resendOtpForMerchant(@PathVariable("mobileNumber") String mobileNumber, @PathVariable("type") String type);
    
    @RequestMapping(value ="logout/merchant/{merchantId}/{merchantPlayerId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public RestResponse logoutMerchant(@PathVariable("merchantId") int merchantId,@PathVariable("merchantPlayerId") int merchantPlayerId,@RequestHeader(value="api-key") String apiKey);
    
    @RequestMapping(value ="logout/user/{userId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public RestResponse logoutUser(@PathVariable("userId") int  mobileNumber,@RequestHeader(value="api-key") String apiKey);
    
    @RequestMapping(value ="health" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public RestResponse healthCheck();
        
}
