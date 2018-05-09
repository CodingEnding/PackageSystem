package com.ending.packagesystem.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import com.ending.packagesystem.config.Config;
import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.config.StatusCode;
import com.ending.packagesystem.dao.DeviceDao;
import com.ending.packagesystem.dao.FeedBackDao;
import com.ending.packagesystem.dao.ForgetCodeDao;
import com.ending.packagesystem.dao.NotificationDao;
import com.ending.packagesystem.dao.UserDao;
import com.ending.packagesystem.po.DevicePO;
import com.ending.packagesystem.po.FeedBackPO;
import com.ending.packagesystem.po.ForgetCodePO;
import com.ending.packagesystem.po.NotificationPO;
import com.ending.packagesystem.po.UserPO;
import com.ending.packagesystem.utils.MailUtils;
import com.ending.packagesystem.utils.RandomUtils;
import com.ending.packagesystem.utils.SessionUtils;

/**
 * 提供与用户相关的服务
 * @author CodingEnding
 */
public class UserService {
	private UserDao userDao=new UserDao();
	private DeviceDao deviceDao=new DeviceDao();
	private ForgetCodeDao forgetCodeDao=new ForgetCodeDao();
	private FeedBackDao feedBackDao=new FeedBackDao();
	private NotificationDao notificationDao=new NotificationDao();
	
	public static final int TYPE_USERNAME=1;
	public static final int TYPE_PHONE=2;
	
	/**
	 * 注册新用户
	 * @param userPO
	 */
	public int register(UserPO userPO){
		int id=Constants.REGISTER_ERROR_ID;
		if(userDao.isExsitWithEmail(userPO.getEmail())){//先判断邮箱是否已经注册
			id=Constants.REGISTER_EMAIL_EXSIT;
			return id;
		}
		boolean isSucceed=userDao.insert(userPO);
		if(isSucceed){
			id=userDao.findIdByEmail(userPO.getEmail());
			//发送注册成功提示邮件
			MailUtils.sendRegisterTipsEmail(userPO.getEmail());
			return id;
		}
		return id;
	}
	
	/**
	 * 登录（包括绑定设备Device）
	 * @param email
	 * @param password
	 * @return UserPO
	 */
	public UserPO login(String email,String password,String deviceType,
			String systemVersion,String deviceFinger){
		UserPO userPO=new UserPO();
		userPO.setId(Constants.LOGIN_ERROR_ID);//默认设置登录失败的Id
		
		if(userDao.isExsitByEmailAndPass(email,password)){//登录成功
			String sessionToken=SessionUtils.createSession(email);
			Timestamp sessionExpire=SessionUtils.createSessionExpire();
			
			//更新User的sessionToken
			userDao.updateSessionByEmail(email,sessionToken,sessionExpire);
			userPO=userDao.findUserByEmail(email);
			
			//插入或更新Device
			DevicePO devicePO=new DevicePO();
			devicePO.setDeviceType(deviceType);
			devicePO.setSystemVersion(systemVersion);
			devicePO.setDeviceFinger(deviceFinger);
			devicePO.setUserId(userPO.getId());
			if(!deviceDao.isExsitWithFinger(deviceFinger)){//还不存在就插入
				deviceDao.insert(devicePO);
			}else{//已经存在就更新或绑定Device
				deviceDao.updateByFinger(devicePO,deviceFinger);
			}
		}
		return userPO;
	}
	
	/**
	 * 修改用户密码
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return 状态码
	 */
	public int modifyPwd(String email,String oldPwd,String newPwd){
		if(userDao.isPwdValidWithEmail(email,oldPwd)){//判断用户身份是否有效
			if(userDao.updatePwdByEmail(email,newPwd)){//修改密码
				MailUtils.sendModifyPwdTipsEmail(email);//发送提示邮件
				return StatusCode.CODE_SUCCEED;
			}
			return StatusCode.CODE_MODIFY_PWD_ERROR;
		}
		return StatusCode.CODE_NO_PRIVILEGE;//无权限
	}
	
	/**
	 * 修改用户信息
	 * @param type 修改的信息类型
	 * @param content 修改的内容
	 * @param sessionToken 用户令牌
	 * @param email 用户邮箱
	 * @return 状态码
	 */
	public int modifyInfo(int type,String content,String sessionToken,
			String email){
		if(userDao.isSessionValidWithEmail(email,sessionToken)){//判断用户身份是否有效
			boolean isSucceed=false;
			isSucceed=userDao.updateInfoByEmail(email,getColumnNameByType(type),content);
			if(isSucceed){
				return StatusCode.CODE_SUCCEED;
			}
			return StatusCode.CODE_MODIFY_INFO_ERROR;
		}
		return StatusCode.CODE_NO_PRIVILEGE;//无修改权限
	}
	
	/**
	 * 储存用户反馈信息
	 * @param email
	 * @param deviceFinger 设备指纹
	 * @param content 反馈内容
	 * @return 状态码
	 */
	public int saveFeedback(String email,String deviceFinger,String content){
		int userId=userDao.findUserIdByEmail(email);
		int deviceId=deviceDao.findDeviceIdByEmail(deviceFinger);
		if(userId!=Constants.QUERY_ERROR_ID&&deviceId!=Constants.QUERY_ERROR_ID){
			FeedBackPO feedBackPO=new FeedBackPO(deviceId,userId,content);
			if(feedBackDao.insert(feedBackPO)){
				return StatusCode.CODE_SUCCEED;
			}
			return StatusCode.CODE_OP_FAIL;
		}
		return StatusCode.CODE_OP_FAIL;
	}
	
	/**
	 * 获取系统通知列表
	 * @param limit
	 * @param page
	 */
	public List<NotificationPO> getNotificationList(int limit,int page){
		return notificationDao.findAll(limit,page);
	}
	
	/**
	 * 获取[忘记]密码的验证码（并存储在数据库中）
	 * @param email
	 * @return 状态码
	 */
	public int getForgetCode(String email){
		boolean isSucceed=false;
		String code=RandomUtils.createRandomCode(6);//生成6位随机码
		
		if(userDao.isExsitWithEmail(email)){//先判断用户是否存在
			Timestamp expire=SessionUtils.createSessionExpire(Config.CODE_EXPIRE_FIELD,Config.CODE_EXPIRE_MINUTE);
			if(forgetCodeDao.isExsitWithEmail(email)){//判断验证码表中是否存在已有记录
				isSucceed=forgetCodeDao.updateCodeByEmail(email,code,expire);//更新数据
			}else{
				ForgetCodePO forgetCodePO=new ForgetCodePO(email,code,expire);
				isSucceed=forgetCodeDao.insert(forgetCodePO);
			}
			MailUtils.sendCodeEmail(code,email);//发送验证码邮件
		}
		else{
			return StatusCode.CODE_USER_NONE;
		}
		return isSucceed?StatusCode.CODE_SUCCEED:StatusCode.CODE_FORGET_SEND_ERROR;
	}
	
	/**
	 * 重置密码
	 * @param email
	 * @param password
	 * @param code
	 * @return
	 */
	public int doForget(String email,String password,String code){
		if(userDao.isExsitWithEmail(email)){//判断用户是否存在
			if(forgetCodeDao.isCodeValidWithEmail(email,code)){
				if(userDao.updatePwdByEmail(email,password)){//重置密码
					long now=Calendar.getInstance().getTime().getTime();
					Timestamp expire=new Timestamp(now);//将验证码有效期设置为当前时间（也就是起到了让验证码过期的作用）
					forgetCodeDao.updateExpireByEmail(email,expire);
					MailUtils.sendModifyPwdTipsEmail(email);//通知用户密码已重置
					return StatusCode.CODE_SUCCEED;
				}
				return StatusCode.CODE_FORGET_ERROR;
			}
			return StatusCode.CODE_FORGET_CODE_ERROR;
		}
		return StatusCode.CODE_USER_NONE;
	}
	
	//根据type返回对应的列名
	private String getColumnNameByType(int type){
		if(type==TYPE_USERNAME){
			return "username";
		}else{
			return "phone";
		}
	}
	
}
