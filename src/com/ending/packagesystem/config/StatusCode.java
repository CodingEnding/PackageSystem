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
}
