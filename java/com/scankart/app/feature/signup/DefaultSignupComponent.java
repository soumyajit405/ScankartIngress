package com.scankart.app.feature.signup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scankart.app.dto.ErrorResponse;
import com.scankart.app.dto.RestResponse;
import com.scankart.app.model.AllMerchant;
import com.scankart.app.model.AllMerchantAttrRepository;
import com.scankart.app.model.AllMerchantAttribute;
import com.scankart.app.model.AllMerchantPlayer;
import com.scankart.app.model.AllMerchantPlayerRepository;
import com.scankart.app.model.AllMerchantRepository;
import com.scankart.app.model.AllOtp;
import com.scankart.app.model.AllOtpRepository;
import com.scankart.app.model.AllUser;
import com.scankart.app.model.AllUserAttrRepository;
import com.scankart.app.model.AllUserAttribute;
import com.scankart.app.model.AllUserLogin;
import com.scankart.app.model.AllUserLoginRepository;
import com.scankart.app.model.AllUserRepository;
import com.scankart.app.model.BusinessAttrMappingRepository;
import com.scankart.app.model.BusinessAttributeMapping;
import com.scankart.app.model.BusinessAttributesTypePl;
import com.scankart.app.model.BusinessTypePl;
import com.scankart.app.model.LoginType;
import com.scankart.app.model.MerchantBusinessType;
import com.scankart.app.model.MerchantBusinessTypeRepository;
import com.scankart.app.model.UserAttributeTypePl;
import com.scankart.app.util.CommonUtility;
import com.scankart.app.util.CustomMessages;
import com.scankart.app.util.SHAUtility;
import com.scankart.app.util.ValidatorUtility;


@Transactional
@Repository
@Service("DefaultSignupComponent")
public class DefaultSignupComponent implements SignupComponent{

	
	@Autowired
	AllUserRepository alluserrepository;
	
	@Autowired
	AllOtpRepository allOtprepository;
	
	@Autowired
	AllUserLoginRepository allUserLoginRepo;
	
	@Autowired
	AllUserAttrRepository allUserAttrrepo;
	
	@Autowired
	AllMerchantRepository allMerchantRepo;
	
	@Autowired
	AllMerchantAttrRepository alMerchantAttrRepo;
	
	@Autowired
	BusinessAttrMappingRepository businessAttrMappRepo;
	
	@Autowired 
	ValidatorUtility validatorUtil;
	
	@Autowired 
	MerchantBusinessTypeRepository merchantBusinessTyperepo;
	
	@Autowired
	AllMerchantPlayerRepository allMerchantPlayerRepo;

	@Override
	public RestResponse registerUser(HashMap<String, String> inputDetails) {
		RestResponse response = new RestResponse();
		try {
			AllUser user = alluserrepository.findUserByPhone(inputDetails.get("phone"));
			if (user != null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("USE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			AllUserAttribute userAttr = alluserrepository.findUserByEmail(inputDetails.get("email"));
			if (userAttr != null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("USEE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}

			int count = alluserrepository.findUserCount()+1;
			AllUser alluser = new AllUser();
			alluser.setId(count);
			alluser.setName(inputDetails.get("name"));
			alluser.setPhone(inputDetails.get("phone"));
			alluser.setEmail(inputDetails.get("email"));
			alluserrepository.saveAndFlush(alluser);
			LoginType logintype = allUserLoginRepo.getLoginType("USER");
			AllUserLogin alluserlogin = new AllUserLogin();
			alluserlogin.setLoginType(logintype);
			alluserlogin.setUserId(count);
			alluserlogin.setLoginTime(new Timestamp(System.currentTimeMillis()));
			String token=SHAUtility.generateRandomToken();
			alluserlogin.setToken(token);
			allUserLoginRepo.saveAndFlush(alluserlogin);
			AllOtp allotp = new AllOtp();
			allotp.setOtp("1111");
			allotp.setLoginType(logintype);
			allotp.setUserId(count);
			allotp.setOtpType("SIGNUP");
			allOtprepository.saveAndFlush(allotp);
			AllUserAttribute alluserAttr = new AllUserAttribute();
			UserAttributeTypePl userAttrPl= alluserrepository.getAttrType("EMAIL");
			alluserAttr.setAllUser(alluser);
			alluserAttr.setUserAttributeTypePl(userAttrPl);
			alluserAttr.setValue(inputDetails.get("email"));
			allUserAttrrepo.saveAndFlush(alluserAttr);
			HashMap<String, Object> validationMessage = new HashMap<String, Object>();
			validationMessage.put("message", CustomMessages.getCustomMessages("AS"));
			validationMessage.put("key", 200);
			validationMessage.put("userId", alluser.getId());
			response.setResponse(validationMessage);
					
			} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;
	}
	
	@Override
	public RestResponse registerUserByMerchant(HashMap<String, String> inputDetails) {
		RestResponse response = new RestResponse();
		try {
			AllUser user = alluserrepository.findUserByPhone(inputDetails.get("phone"));
			if (user != null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("USE"));
				validationMessage.put("key", 200);
				validationMessage.put("userId", user.getId());
				response.setResponse(validationMessage);
				return response;
			}
//			AllUserAttribute userAttr = alluserrepository.findUserByEmail(inputDetails.get("email"));
//			if (userAttr != null ) {
//				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
//				validationMessage.put("message", CustomMessages.getCustomMessages("USEE"));
//				validationMessage.put("key", 200);
//				response.setResponse(validationMessage);
//				return response;
//			}
			int count = alluserrepository.findUserCount()+1;
			AllUser alluser = new AllUser();
			alluser.setId(count);
			alluser.setName(inputDetails.get("name"));
			alluser.setPhone(inputDetails.get("phone"));
			alluserrepository.saveAndFlush(alluser);
			LoginType logintype = allUserLoginRepo.getLoginType("USER");
			AllUserLogin alluserlogin = new AllUserLogin();
			alluserlogin.setLoginType(logintype);
			alluserlogin.setUserId(count);
			alluserlogin.setLoginTime(new Timestamp(System.currentTimeMillis()));
			String token=SHAUtility.generateRandomToken();
			alluserlogin.setToken(token);
			allUserLoginRepo.saveAndFlush(alluserlogin);
			AllOtp allotp = new AllOtp();
			allotp.setOtp("1111");
			allotp.setLoginType(logintype);
			allotp.setUserId(count);
			allotp.setOtpType("SIGNUP");
			allOtprepository.saveAndFlush(allotp);
//			AllUserAttribute alluserAttr = new AllUserAttribute();
//			UserAttributeTypePl userAttrPl= alluserrepository.getAttrType("EMAIL");
//			alluserAttr.setAllUser(alluser);
//			alluserAttr.setUserAttributeTypePl(userAttrPl);
//			alluserAttr.setValue("NA");
//			allUserAttrrepo.saveAndFlush(alluserAttr);
			HashMap<String, Object> validationMessage = new HashMap<String, Object>();
			validationMessage.put("message", CustomMessages.getCustomMessages("AS"));
			validationMessage.put("key", 200);
			validationMessage.put("userId", alluser.getId());
			response.setResponse(validationMessage);
					
			} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;
	}
	
	
	@Override
	public RestResponse registerMerchant(HashMap<String, Object> inputDetails) {
		RestResponse response = new RestResponse();
		try {
			AllMerchant merchant = allMerchantRepo.findUserByPhone((String)inputDetails.get("phone"));
			if (merchant != null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("USE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			} 
			AllMerchantAttribute merchantAttr1 = allMerchantRepo.findUserByEmail(((String)inputDetails.get("email")));
			if (merchantAttr1 != null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("USEE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			int count = allMerchantRepo.findMerchantCount()+1;
			AllMerchant newMerchant = createMerchant((String)inputDetails.get("phone"), (String)inputDetails.get("name"), count);
			if (newMerchant == null) {
				ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
				response.setResponse(errorResponse);
				return response;
			}
			CommonUtility cm = new CommonUtility();
			ArrayList<String> listOfBusinessTypes = (ArrayList<String>)inputDetails.get("businessTypes");
			ArrayList<Integer> businessList = cm.getIntegerArray(listOfBusinessTypes); //strArrayList is a collection of Strings as you defined.
			List<BusinessTypePl> businessTypes = allMerchantRepo.findBusinessTypes(businessList);
			int merchantBusinessCount = merchantBusinessTyperepo.findMerchantBusinessCount()+1;
			List<MerchantBusinessType> merchantBusinesses = new ArrayList<>();
			for (BusinessTypePl businessType: businessTypes) {
				MerchantBusinessType merchantBusinessTypes = new MerchantBusinessType();
				merchantBusinessTypes.setId(merchantBusinessCount);
				merchantBusinessTypes.setAllMerchant(newMerchant);
				merchantBusinessTypes.setBusinessType(businessType);
				merchantBusinesses.add(merchantBusinessTypes);
				merchantBusinessCount++;
			}
			merchantBusinessTyperepo.saveAll(merchantBusinesses);
//			LoginType logintype = allUserLoginRepo.getLoginType("MERCHANT");
//			AllUserLogin alluserlogin = new AllUserLogin();
//			alluserlogin.setLoginType(logintype);
//			alluserlogin.setUserId(count);
//			alluserlogin.setLoginTime(new Timestamp(System.currentTimeMillis()));
//			alluserlogin.setLogoutTime(null);
//			String token=SHAUtility.generateRandomToken();
//			alluserlogin.setToken(token);
//			allUserLoginRepo.saveAndFlush(alluserlogin);

//			AllOtp allotp = new AllOtp();
//			allotp.setOtp("1111");
//			allotp.setLoginType(logintype);
//			allotp.setUserId(count);
//			allotp.setOtpType("SIGNUP");
//			allOtprepository.saveAndFlush(allotp);
			BusinessAttributeMapping businessAttrMapping = new BusinessAttributeMapping();
			AllMerchantAttribute merchantAttr = new AllMerchantAttribute();
			count = businessAttrMappRepo.findAttrCount();
			BusinessAttributesTypePl businessAttrTypePl= allMerchantRepo.getBusinessAttrMap("Email");
			businessAttrMapping.setBusinessAttributesTypePl(businessAttrTypePl);
			businessAttrMapping.setIsMandatory("Y");
			businessAttrMapping.setId(count);
			businessAttrMappRepo.saveAndFlush(businessAttrMapping);
			merchantAttr.setAllMerchant(newMerchant);
			merchantAttr.setBusinessAttributesTypePl(businessAttrTypePl);
			merchantAttr.setValue((String)inputDetails.get("email"));
			count = alMerchantAttrRepo.findAttrCount();
			merchantAttr.setId(count);
			alMerchantAttrRepo.saveAndFlush(merchantAttr);
			HashMap<String, Object> validationMessage = new HashMap<String, Object>();
			validationMessage.put("message", CustomMessages.getCustomMessages("AS"));
			validationMessage.put("key", 200);
			validationMessage.put("userId", newMerchant.getId());
			//validationMessage.put("token", token);
			response.setResponse(validationMessage);
					
			} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;
	}

	@Override
	public RestResponse verifyMobile(String mobileNumber) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		try {
			
			AllUser user = alluserrepository.findUserByPhone(mobileNumber);
			//int count = alluserrepository.findUserCount()+1;
			if (user !=null) {
				allOtprepository.updateOtp("1111", user.getId(),1,"LOGIN");
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("USE"));
				validationMessage.put("key", 200);
				validationMessage.put("userId", user.getId());
				validationMessage.put("validationStatus", user.getValidatedStatus());
				response.setResponse(validationMessage);
			} else {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}
	
	@Override
	public RestResponse verifyMobileForMerchant(String mobileNumber) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		try {
			
			AllMerchant merchant = allMerchantRepo.findUserByPhone(mobileNumber);
			//int count = alluserrepository.findUserCount()+1;
			if (merchant !=null) {
				allOtprepository.updateOtp("1111", merchant.getId(),2,"LOGIN");
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("USE"));
				validationMessage.put("key", 200);
				validationMessage.put("userId", merchant.getId());
				validationMessage.put("validationStatus", merchant.getValidatedStatus());
				response.setResponse(validationMessage);
			} else {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}

	@Override
	public RestResponse verifyOtpForUser(String mobileNumber, String otp, String otpType, String playerId) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		try {
			
			AllUser user = alluserrepository.findUserByPhone(mobileNumber);
			if (user == null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			int count = allOtprepository.verifyOtp(user.getId(), otp,"USER",otpType); //Need to check
			if (count == 0) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("OTF"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
			} else {
				if (otpType.equalsIgnoreCase("SIGNUP")) {
					alluserrepository.validateUser(mobileNumber);
					LoginType logintype = allUserLoginRepo.getLoginType("USER");
					AllOtp allotp = new AllOtp();
					allotp.setOtp("1111");
					allotp.setLoginType(logintype);
					allotp.setUserId(user.getId());
					allotp.setOtpType("LOGIN");
					allOtprepository.saveAndFlush(allotp);
				}
				String token = SHAUtility.generateRandomToken();
				Timestamp loginTime = new Timestamp(System.currentTimeMillis());
				allUserLoginRepo.loginUser(token, user.getId(), 1,loginTime,playerId);
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("OTS"));
				validationMessage.put("key", 200);
				validationMessage.put("token", token);
				validationMessage.put("userId", user.getId());
				validationMessage.put("userName", user.getName());
				validationMessage.put("email", user.getEmail());
				response.setResponse(validationMessage);
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}
	
	
	@Override
	public RestResponse verifyOtpForMerchant(String mobileNumber, String otp, String otpType, String playerId) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		try {
			
			AllMerchant user = allMerchantRepo.findUserByPhone(mobileNumber);
			if (user == null ) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("USE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			int count = allOtprepository.verifyOtp(user.getId(), otp,"MERCHANT", otpType);
			if (count == 0) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("OTF"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
			} else {
				if (otpType.equalsIgnoreCase("SIGNUP")) {
					allMerchantRepo.validateMerchant(mobileNumber);
					LoginType logintype = allUserLoginRepo.getLoginType("MERCHANT");
					AllOtp allotp = new AllOtp();
					allotp.setOtp("1111");
					allotp.setLoginType(logintype);
					allotp.setUserId(user.getId());
					allotp.setOtpType("LOGIN");
					allOtprepository.saveAndFlush(allotp);
				}
				String token = SHAUtility.generateRandomToken();
				Timestamp loginTime = new Timestamp(System.currentTimeMillis());
				allUserLoginRepo.loginUser(token, user.getId(), 2,loginTime,playerId);
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("OTS"));
				validationMessage.put("key", 200);
				validationMessage.put("token", token);
				validationMessage.put("merchantId", user.getId());
				response.setResponse(validationMessage);
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}
	
	@Override
	public RestResponse loginMerchant(String loginId, String code, String playerId) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		HashMap<String, Object> validationMessage = new HashMap<String, Object>();
		try {
			
//			AllMerchant user = allMerchantRepo.findUserByPhone(mobileNumber);
//			if (user == null ) {
//				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
//				validationMessage.put("message", CustomMessages.getCustomMessages("USE"));
//				validationMessage.put("key", 200);
//				response.setResponse(validationMessage);
//				return response;
//			}
			AllMerchant merchant = allMerchantRepo.checkMerchant(loginId,code);
			
			if (merchant == null) {
				validationMessage.put("message", CustomMessages.getCustomMessages("IV"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			} else {
				int maxActiveSessions = Integer.parseInt(merchant.getMaxActiveSessionsAllowed());
				int currentActiveSessions = Integer.parseInt(merchant.getCurrentActiveSessions());
				String token = "";
				if (maxActiveSessions > currentActiveSessions) {
					if (currentActiveSessions == 0) {
						token= SHAUtility.generateRandomToken();
						Timestamp loginTime = new Timestamp(System.currentTimeMillis());
						allUserLoginRepo.loginMerchant(token, merchant.getId(), 2,loginTime);
						allMerchantRepo.updateMerchantSessions(merchant.getPhone(), "1");
						AllMerchantPlayer merchantPlayer = new AllMerchantPlayer();
						merchantPlayer.setAllMerchant(merchant);
						merchantPlayer.setPlayerId(playerId);
						allMerchantPlayerRepo.saveAndFlush(merchantPlayer);
						validationMessage.put("message", CustomMessages.getCustomMessages("OTS"));
						validationMessage.put("key", 200);
						validationMessage.put("token", token);
						validationMessage.put("merchantId", merchant.getId());
						response.setResponse(validationMessage);
						return response;
					} else {
						AllUserLogin allUserLogin= allUserLoginRepo.findLogin(2, merchant.getId());
						allMerchantRepo.updateMerchantSessions(merchant.getPhone(), Integer.toString(currentActiveSessions+1) );
						int playerCount = allMerchantPlayerRepo.findPlayerCount()+1;
						AllMerchantPlayer merchantPlayer = new AllMerchantPlayer();
						merchantPlayer.setAllMerchant(merchant);
						merchantPlayer.setId(playerCount);
						merchantPlayer.setPlayerId(playerId);
						allMerchantPlayerRepo.saveAndFlush(merchantPlayer);
						validationMessage.put("message", CustomMessages.getCustomMessages("OTS"));
						validationMessage.put("key", 200);
						validationMessage.put("token",allUserLogin.getToken() );
						validationMessage.put("merchantId", merchant.getId());
						validationMessage.put("merchantPlayerId", playerCount);
						response.setResponse(validationMessage);
						return response;
					}
					
					
					
				} else {
					validationMessage.put("message", CustomMessages.getCustomMessages("MLA"));
					validationMessage.put("key", 200);
					response.setResponse(validationMessage);
					return response;
				}
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}
	
	@Override
	public RestResponse resendOtp(String mobileNumber, String type) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		try {
			
			AllUser user = alluserrepository.findUserByPhone(mobileNumber);
			if (user !=null) {
				allOtprepository.updateOtp("1212",user.getId(),1,type); // Need to check
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("SUC"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
			} else {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
		
				
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}
	
	@Override
	public RestResponse logoutMerchant(int merchantId,int merchantPlayerId, String apiKey) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		try {
			
			int status = validatorUtil.validateUser(apiKey, merchantId, 2);
			if (status == 0 || status == -1) {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UAU"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
				return response;
			}
			AllMerchant merchant = allMerchantRepo.findMerchantById(merchantId);
			if (merchant !=null) {
				int activeSessions = Integer.parseInt(merchant.getCurrentActiveSessions());
				if (activeSessions == 1) {
					
					Timestamp logoutTime = new Timestamp(System.currentTimeMillis());
					allUserLoginRepo.logoutUser(merchantId, 2,logoutTime);
				}	
				allMerchantRepo.updateMerchantSessions(merchant.getPhone(),Integer.toString(activeSessions-1 ));
				allMerchantPlayerRepo.deletePlayer(merchantId, merchantPlayerId);
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("LS"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
			} else {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
		
				
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}
	
	
	@Override
	public RestResponse logoutUser(int userId, String apiKey) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
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
			if (user !=null) {
				Timestamp logoutTime = new Timestamp(System.currentTimeMillis());
				allUserLoginRepo.logoutUser(userId, 1,logoutTime);
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("LS"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
			} else {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
		
				
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}
	
	
	@Override
	public RestResponse resendOtpForMerchant(String mobileNumber, String type) {
		// TODO Auto-generated method stub
		RestResponse response = new RestResponse();
		try {
			
			AllMerchant merchant = allMerchantRepo.findUserByPhone(mobileNumber);
			if (merchant !=null) {
				allOtprepository.updateOtp("1212",merchant.getId(),2, type);
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("SUC"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
			} else {
				HashMap<String, Object> validationMessage = new HashMap<String, Object>();
				validationMessage.put("message", CustomMessages.getCustomMessages("UNE"));
				validationMessage.put("key", 200);
				response.setResponse(validationMessage);
		
				
			}
		} catch( Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse(CustomMessages.getCustomMessages("ISE"), 500);
			response.setResponse(errorResponse);
		}
		return response;

	}
	
	public AllMerchant createMerchant(String phone, String name, int count) {
		AllMerchant newmerchant = new AllMerchant();
		try {
			
			newmerchant.setId(count);
			newmerchant.setName(name);
			newmerchant.setPhone(phone);
			newmerchant.setValidatedStatus("N");
			newmerchant.setCurrentActiveSessions("0");
			newmerchant.setMaxActiveSessionsAllowed("10");
			newmerchant.setLoginId(phone.substring(0, 4)+count);
			newmerchant.setCode(phone.substring(0, 4)+count);
			allMerchantRepo.saveAndFlush(newmerchant);
						
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return newmerchant;
	}

}
