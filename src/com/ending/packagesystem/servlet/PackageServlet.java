package com.ending.packagesystem.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.config.StatusCode;
import com.ending.packagesystem.core.PackageConsume;
import com.ending.packagesystem.core.RecommentCore;
import com.ending.packagesystem.po.PackagePO;
import com.ending.packagesystem.service.PackageService;
import com.ending.packagesystem.utils.MathUtils;
import com.ending.packagesystem.vo.DataResponse;
import com.ending.packagesystem.vo.BaseResponse;
import com.ending.packagesystem.vo.FlowConsumeVO;
import com.ending.packagesystem.vo.PackageVO;
import com.ending.packagesystem.vo.UserConsumeVO;
import com.google.gson.Gson;

/**
 * 统一处理套餐相关的请求
 * @author CodingEnding
 */
@WebServlet(urlPatterns={"/api/v1/package/recommend",
			"/api/v1/package/list","/api/v1/package/get",
			"/api/v1/package/score","/api/v1/package/search"})
public class PackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String URL_RECOMMEND="/api/v1/package/recommend";//请求推荐（POST）
    public static final String URL_LIST="/api/v1/package/list";//批量获取套餐（GET）
    public static final String URL_GET="/api/v1/package/get";//获取指定Id的套餐[精准查询]（GET）
    public static final String URL_SEARCH="/api/v1/package/search";//获取关键词搜索套餐（GET）
    public static final String URL_SCORE="/api/v1/package/score";//对指定的套餐进行评分（POST）
    
    private PackageService packageService=new PackageService();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		switch (servletPath) {
		case URL_RECOMMEND:
			response.setStatus(405);
			break;
		case URL_SCORE:
			response.setStatus(405);
			break;
		case URL_LIST:
			break;
		case URL_SEARCH:
			doSearch(request,response);
			break;
		case URL_GET:
			doQueryById(request,response);
			break;
		default:
			response.getWriter().append("Served at: ")
			.append(request.getContextPath())
			.append(servletPath);
			break;
		}
	}

	//执行推荐操作
	private void doRecommend(HttpServletRequest request,HttpServletResponse response) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(request.getInputStream()));
		String postBody=IOUtils.read(br);//将上传的post body读取出来
		br.close();
		UserConsumeVO userConsumeVO=new Gson()
				.fromJson(postBody,UserConsumeVO.class);//解析JSON数据
		
		//将结果以json格式返回
		List<PackageVO> dataList=RecommentCore.recomment(userConsumeVO);
		DataResponse<List<PackageVO>> jsonResponse=null;
		if(dataList!=null&&!dataList.isEmpty()){
			jsonResponse=new DataResponse<List<PackageVO>>(true,StatusCode.CODE_SUCCEED,dataList);
		}else{
			jsonResponse=new DataResponse<List<PackageVO>>(false,StatusCode.CODE_QUERY_NONE,new ArrayList<>());
		}
		String dataJSON=new Gson().toJson(jsonResponse);
		response.getWriter().append(dataJSON);
	}
	
	//执行对套餐的评分操作
	private void doScore(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int score=MathUtils.legalIntNum(request.getParameter("score"),0);//套餐评分（避免传入值不合法）
		int userId=MathUtils.legalIntNum(request.getParameter("user_id"),-1);//用户Id（避免传入值不合法，默认为-1避免影响其他的用户）
		int packageId=MathUtils.legalIntNum(request.getParameter("package_id"),-1);//套餐Id（避免传入值不合法，默认为-1避免影响其他的套餐）
		
		//将结果以json格式返回
		boolean isSucceed=packageService.scorePackage(score, userId, packageId);
		BaseResponse jsonResponse=null;
		if(isSucceed){
			jsonResponse=new BaseResponse(isSucceed,StatusCode.CODE_SUCCEED);
		}else{
			jsonResponse=new BaseResponse(isSucceed,StatusCode.CODE_OP_FAIL);
		}
		String dataJSON=new Gson().toJson(jsonResponse);
		response.getWriter().append(dataJSON);
	}
	
	//获取指定Id的套餐
	private void doQueryById(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int id=MathUtils.legalIntNum(request.getParameter("package_id"),-1);//套餐Id（避免传入值不合法，默认为-1避免影响其他的套餐
		
		//将结果以json格式返回
		PackageVO packageVO=packageService.getPackageVOById(id);
		DataResponse<PackageVO> jsonResponse=null;
		if(packageVO.getId()==Constants.QUERY_ERROR_ID){//此时查询失败
			jsonResponse=new DataResponse<PackageVO>(false,StatusCode.CODE_QUERY_NONE,null);
		}else{
			jsonResponse=new DataResponse<PackageVO>(true,StatusCode.CODE_SUCCEED,packageVO);
		}
		String dataJSON=new Gson().toJson(jsonResponse);
		response.getWriter().append(dataJSON);
	}
	
	//根据关键词查询套餐
	private void doSearch(HttpServletRequest request,HttpServletResponse response){
		//TODO 补充完整
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		String servletPath=request.getServletPath();
		switch (servletPath) {
		case URL_RECOMMEND:
			doRecommend(request, response);
			break;
		case URL_SCORE:
			doScore(request, response);
			break;
		default:
			doGet(request, response);
			break;
		}
	}

}
