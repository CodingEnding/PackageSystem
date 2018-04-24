package com.ending.packagesystem.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.ending.packagesystem.vo.BaseResponse;
import com.google.gson.Gson;

/**
 * Servlet基类
 * @author CodingEnding
 */
public abstract class BaseServlet extends HttpServlet{
	private static final long serialVersionUID = 1L; 

	//将json数据返回给客户端
	protected void writeJsonToClient(BaseResponse jsonResponse,HttpServletResponse response) throws IOException{
		String dataJSON=new Gson().toJson(jsonResponse);//将数据对象转化为json字符串
		response.getWriter().append(dataJSON);
	}
	
	//设置响应的字符编码和内容格式
	protected void initCharsetContentType(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
	}
	
	//获取POST请求中的数据体（JSON字符串）
	protected String getPostBody(HttpServletRequest request) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(request.getInputStream()));
		String postBody=IOUtils.read(br);//将上传的post body读取出来
		br.close();
		return postBody;
	}
	
}
