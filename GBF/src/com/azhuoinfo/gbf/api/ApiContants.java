package com.azhuoinfo.gbf.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.cangol.mobile.CoreApplication;
import mobi.cangol.mobile.utils.DeviceInfo;
import mobi.cangol.mobile.utils.StringUtils;
import com.azhuoinfo.gbf.utils.Constants;

import org.OpenUDID.OpenUDID_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class ApiContants {
	public static final String VERSION = "1";
	public static final String API_PAGE_SIZE = "5";
	public static final String API_PARAMS_SEX_MAN = "1";
	public static final String API_PARAMS_SEX_WOMAN = "2";
	/**
	 * test server address
	 */
	public static String TEST_SERVER_URI= "http://192.168.1.3:8080/cmweb/";
	/**
	* release server address
	*/
	public static String RELEASE_SERVER_URI= "http://192.168.1.3:8080/cmweb/";
	
	private Context context;
	public static  boolean DEBUG = true;// true:测试环境 false 生产环境  
	private ApiContants(Context context) {
		this.context = context;
		DEBUG=((CoreApplication)context.getApplicationContext()).isDevMode();
	}

	public static ApiContants instance(Context context) {
		if (context != null) {
			return new ApiContants(context);
		} else {
			throw new IllegalStateException("context isn't null");
		}
	}
	public static String getServerUrl() {
		return DEBUG?TEST_SERVER_URI:RELEASE_SERVER_URI;
	}
	public String getActionUrl(String action) {
		return (DEBUG?TEST_SERVER_URI:RELEASE_SERVER_URI) + action;
	}

	/**
	 * 签名
	 * 
	 * @param params
	 * @return
	 */
	public String sign(HashMap<String, String> params) {
		StringBuilder sb = new StringBuilder(DeviceInfo.getMD5Fingerprint(context));
		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
				params.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});
		for (int i = 0; i < infoIds.size(); i++) {
			if (StringUtils.isNotEmpty(infoIds.get(i).getValue()))
				sb.append(infoIds.get(i).getValue());
		}
		String sign = StringUtils.md5(sb.toString());
		return sign;
	}

	/**
	 * 基本参数，不参与签名
	 * 
	 * @return
	 */
	public HashMap<String, String> getBaseParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appId",String.valueOf(DeviceInfo.getAppMetaData(context, "APP_ID")));
		params.put("appVersion", DeviceInfo.getAppVersion(context));
		params.put("platform", "Android");
		params.put("osVersion", DeviceInfo.getOSVersion());
		params.put("deviceId",OpenUDID_manager.isInitialized() ? OpenUDID_manager.getOpenUDID() : DeviceInfo.getDeviceId(context));
		// 红米第一次获取openudid失败
		params.put("apiVersion", VERSION);
		params.put("channelId", getChannelID(context));
		return params;
	}

	public static String getChannelID(Context context) {
		SharedPreferences sharedPreferences=context.getSharedPreferences(Constants.SHARED, Context.MODE_PRIVATE);
		String channel_id=sharedPreferences.getString("CHANNEL_ID", null);
		return TextUtils.isEmpty(channel_id) ? String.valueOf(DeviceInfo.getAppMetaData(context, "CHANNEL_ID")) : channel_id;
	}
	public static String API_USER_AUTH= "api/user/auth.do";
	 /**
	  * 
	  * @param userId
	  * @param token
	  * @return
	  */
	public HashMap<String, String> userAuth(String userId, String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_USER_LOGIN= "api/user/login.do";
	 /**
	  * 
	  * @param username
	  * @param password
	  * @return
	  */
	public HashMap<String, String> userLogin(String username, String password) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_AUTOREGISTER= "api/user/autoRegister.do";
	/**
	 * 一键注册
	 * @param username
	 * @param password
	 * @return
	 */
	public HashMap<String, String> autoRegister() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_USER_THIRDREGISTER= "api/user/thirdRegister.do";
	/**
	 * 第三方注册|登录
	 * @param thirdAccount
	 * @return
	 */
	public HashMap<String, String> thirdRegister(String thirdAccount,String nickname,String avatar,String gender) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("thirdAccount", thirdAccount);
		params.put("nickname", nickname);
		params.put("avatar", avatar);
		params.put("gender", gender);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_USER_REGISTER= "api/user/register.do";
	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @return
	 */
	public HashMap<String, String> userRegister(String username, String password,String verifyCode) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		params.put("verifyCode", verifyCode);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_PROFILE= "api/user/profile.do";
	/**
	 * 
	 * @param username
	 * @param token
	 * @return
	 */
	public HashMap<String, String> userProfile(String userId, String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_MODIFYEMAIL= "api/user/modifyEmail.do";
	/**
	 * 
	 * @param userId
	 * @param token
	 * @param email
	 * @return
	 */
	public HashMap<String, String> modifyEmail(String userId, String token,String email) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("email", email);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_USER_MODIFYMOBILE= "api/user/modifyMobile.do";
	/**
	 * 
	 * @param userId
	 * @param token
	 * @param mobile
	 * @param verifyCode
	 * @return
	 */
	public HashMap<String, String> modifyMobile(String userId, String token,String mobile,String 
			verifyCode	) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("mobile", mobile);
		params.put("verifyCode", verifyCode);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_MODIFYNICKNAME= "api/user/modifyNickname.do";
	/**
	 * 
	 * @param userId
	 * @param token
	 * @param mobile
	 * @return
	 */
	public HashMap<String, String> modifyNickname(String userId, String token,String nickname) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("nickname", nickname);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_MODIFYGENDER= "api/user/modifyGender.do";
	/**
	 * 
	 * @param userId
	 * @param token
	 * @param mobile
	 * @return
	 */
	public HashMap<String, String> modifyGender(String userId, String token,String gender) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("gender", gender);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_MODIFYBIRTHDAY= "api/user/modifyBirthday.do";
	/**
	 * 
	 * @param userId
	 * @param token
	 * @param mobile
	 * @return
	 */
	public HashMap<String, String> modifyBirthday(String userId, String token,String birthday) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("birthday", birthday);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_MODIFYLOCATION= "api/user/modifyLocation.do";
	/**
	 * 
	 * @param userId
	 * @param token
	 * @param mobile
	 * @return
	 */
	public HashMap<String, String> modifyLocation(String userId, String token,String location) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("location", location);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_MODIFYPASSWORD= "api/user/modifyPassword.do";
	/**
	 * 
	 * @param userId
	 * @param token
	 * @param password
	 * @param newpassword
	 * @return
	 */
	public HashMap<String, String> modifyPassword(String userId, String token,String password,String newpassword) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("password", password);
		params.put("newpassword", newpassword);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_USER_MODIFYAVATAR= "api/user/modifyAvatar.do";
	/**
	 * 
	 * @param userId
	 * @param token
	 * @param avatar
	 * @return
	 */
	public HashMap<String, String> modifyAvatar(String userId, String token,String avatar) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("avatar", avatar);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_USER_FORGETPASSWORD= "api/user/forgetPassword.do";
	/**
	 * 
	 * @param username
	 * @param verifyCode
	 * @param newpassword
	 * @return
	 */
	public HashMap<String, String> forgetPassword(String username,String verifyCode,String newpassword) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("verifyCode", verifyCode);
		params.put("newpassword", newpassword);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_COMMON_UPGRADE= "api/common/upgrade.do";
	/**
	 * 升级检测
	 * @return
	 */
	public HashMap<String, String> upgrade() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_COMMON_FEEDBACK= "api/common/feedback.do";
	/**
	 * 意见反馈
	 * @param content
	 * @param contact
	 * @return
	 */
	public HashMap<String, String> feedback(String content,String contact) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("content", content);
		params.put("contact", contact);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}

	public static String API_COMMON_DEVICEREGISTER= "api/common/deviceRegister.do";
	/**
	 * 
	 * @param deviceRegId
	 * @return
	 */
	public HashMap<String, String> deviceRegister(String deviceRegId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deviceRegId", deviceRegId);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_COMMON_PUSHONOFF= "api/common/pushOnOff.do";
	/**
	 * 推送开关
	 * @param pushOnOff
	 * @return
	 */
	public HashMap<String, String> pushOnOff(String pushOnOff) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pushOnOff", pushOnOff);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_COMMON_SENDCODE= "api/common/sendCode.do";
	/**
	 * 发生验证码
	 * @param codeType
	 * @param codeTarget
	 * @return
	 */
	public HashMap<String, String> sendCode(String codeType,String codeTarget) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("codeType", codeType);
		params.put("codeTarget", codeTarget);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	


	
	
	
	
	
	
	
	
	
	
	
	public static String API_POLL_CATEGORY= "api/poll/category.do";
	/**
	 * vote area
	 * @param areaId
	 * @return
	 */
	public HashMap<String, String> pollCategory(String categoryId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("categoryId", categoryId);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_POLL_AREA= "api/poll/area.do";
	/**
	 * vote area
	 * @param areaId
	 * @return
	 */
	public HashMap<String, String> pollArea(String areaId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaId);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_NEW= "api/poll/new.do";
	/**
	 * vote list
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public HashMap<String, String> pollNew(String status,String pageNo,String pageSize) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("status", status);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_LIST= "api/poll/list.do";
	/**
	 * vote list
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public HashMap<String, String> pollList(String areaId,String pageNo,String pageSize) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_DETAILS= "api/poll/details.do";
	/**
	 * vote list
	 * @param userId
	 * @param token
	 * @return
	 */
	public HashMap<String, String> pollDetails(String pollId,String userId,String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pollId", pollId);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_COMMENT_LIST= "api/pollcomment/list.do";
	/**
	 * 评论list
	 * @param topicId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public HashMap<String, String> pollCommentList(String pollId,String pageNo,String pageSize) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pollId", pollId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_COMMENT_DELETE= "api/pollcomment/delete.do";
	/**
	 * 评论删除
	 * @return
	 */
	public HashMap<String, String> pollCommentDelete(String commentId
			,String userId
			,String token
			) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("commentId", commentId);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_COMMENT_PRASIED= "api/pollcomment/prasied.do";
	/**
	 * 评论点赞
	 * @return
	 */
	public HashMap<String, String> pollCommentPrasied(String commentId
			,String userId
			,String token
			) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("commentId", commentId);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_SUBMIT= "api/poll/submit.do";
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public HashMap<String, String> pollSubmit(String areaId
			,String userId
			,String token
			,String content
			,String begin
			,String end
			,String location
			,String points) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaId);
		params.put("content", content);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.put("beginTime", begin);
		params.put("endTime", end);
		params.put("location", location);
		params.put("points", points);
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_COMMENT_SUBMIT= "api/pollcomment/submit.do";
	/**
	 * 评论
	 * @return
	 */
	public HashMap<String, String> pollCommentSubmit(String pollId
			,String content
			,String userId
			,String token
			) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pollId", pollId);
		params.put("content", content);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_POLL_VOTE= "api/poll/vote.do";
	/**
	 * 观点
	 * @return
	 */
	public HashMap<String, String> pollVote(String pollId
			,String pointId
			,String userId
			,String token
			) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pollId", pollId);
		params.put("pointId", pointId);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_POLL_VOTEER= "api/poll/voter.do";
	
	public HashMap<String, String> pollVoter(String pollId,String pageNo,String pageSize) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pollId", pollId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_VOTER_FOLLOWER_LIST= "api/voter/follower.do";
	
	public HashMap<String, String> voterFollowerList(String voterId,String pageNo,String pageSize,String userId, String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("voterId", voterId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_VOTER_FOLLOWING_LIST= "api/voter/following.do";
	
	public HashMap<String, String> voterFollowingList(String voterId,String pageNo,String pageSize,String userId, String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("voterId", voterId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_VOTER_JOIN_LIST= "api/voter/join.do";
	
	public HashMap<String, String> voterJoinList(String voterId,String pageNo,String pageSize,String userId, String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("voterId", voterId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_VOTER_POST_LIST= "api/voter/post.do";
	
	public HashMap<String, String> voterPostList(String voterId,String pageNo,String pageSize,String userId, String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("voterId", voterId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_VOTER_MARK_LIST= "api/voter/mark.do";
	public HashMap<String, String> voterMarkList(String voterId,String pageNo,String pageSize,String userId, String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("voterId", voterId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_VOTER_INFO= "api/voter/info.do";
	public HashMap<String, String> voterInfo(String voterId, String userId,String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("voterId", voterId);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_VOTER_INIT= "api/voter/init.do";
	public HashMap<String, String> voterInit(String userId,String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	public static String API_VOTER_FOLLOW= "api/voter/follow.do";
	public HashMap<String, String> voterFollow(String voterId, String userId,String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("voterId", voterId);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
	
	public static String API_POLL_MARK= "api/poll/mark.do";
	public HashMap<String, String> pollMark(String pollId, String userId,String token) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pollId", pollId);
		params.put("userId", userId);
		params.put("token", token);
		params.put("sign", sign(params));
		params.putAll(getBaseParams());
		return params;
	}
}
