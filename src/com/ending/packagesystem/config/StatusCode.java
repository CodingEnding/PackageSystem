package com.ending.packagesystem.config;

/**
 * 响应状态码
 * @author CodingEnding
 */
public interface StatusCode {
	int CODE_SUCCEED=200;//请求成功
	int CODE_NO_RES=400;//资源不存在
	int CODE_OP_FAIL=440;//操作失败
	int CODE_QUERY_NONE=404;//查询失败（无数据）
	
	int CODE_REGISTER_ERROR=5400;//注册失败
	int CODE_EMAIL_EXSIT=5401;//邮箱已存在（注册功能）
	int CODE_USER_NONE=5402;//用户不存在
	int CODE_LOGIN_ERROR=5500;//登录失败
	int CODE_DEVICE_BACKUP_ERROR=5600;//设备信息更新失败
	int CODE_MODIFY_INFO_ERROR=5700;//修改用户信息失败
	int CODE_MODIFY_PWD_ERROR=5800;//修改用户密码失败
	int CODE_FORGET_ERROR=5900;//重置密码失败（忘记密码）
	int CODE_FORGET_SEND_ERROR=5901;//验证码发送失败（忘记密码）
	int CODE_FORGET_CODE_ERROR=5902;//验证码错误（忘记密码）
	int CODE_NO_PRIVILEGE=7000;//缺少用户权限
}
