package com.scankart.app.feature.profile;

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
public interface ProfileService 
{
    
    @RequestMapping(value ="getUserProfile/{userId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public RestResponse getUserProfile(@PathVariable("userId") int userid, @RequestHeader(value="api-key") String apiKey);

    @RequestMapping(value ="updateUserProfile/{userId}/{userName}" , method =  RequestMethod.PUT , headers =  "Accept=application/json" )
    public RestResponse updateUserProfile(@PathVariable("userId") int userid,@PathVariable("userName") String userName, @RequestHeader(value="api-key") String apiKey);
     
}
