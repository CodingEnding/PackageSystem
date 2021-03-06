package com.ending.packagesystem.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ending.packagesystem.config.Config;
import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.config.StatusCode;
import com.ending.packagesystem.core.RecommentCore;
import com.ending.packagesystem.po.PackageScorePO;
import com.ending.packagesystem.service.PackageService;
import com.ending.packagesystem.utils.DebugUtils;
import com.ending.packagesystem.utils.MathUtils;
import com.ending.packagesystem.vo.BaseResponse;
import com.ending.packagesystem.vo.DataResponse;
import com.ending.packagesystem.vo.PackageVO;
import com.ending.packagesystem.vo.SimplePackageVO;
import com.ending.packagesystem.vo.UserConsumeVO;
import com.google.gson.Gson;

/**
 * 统一处理套餐相关的请求
 * @author CodingEnding
 */
@WebServlet(urlPatterns={"/api/v1/package/recommend",
		"/api/v1/package/list","/api/v1/package/get",
		"/api/v1/package/score","/api/v1/package/search",
		"/api/v1/package/hot","/api/v1/package/myScore",
		"/api/v1/package/scoreCount"})
public class PackageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String URL_RECOMMEND="/api/v1/package/recommend";//请求推荐（POST）
	private static final String URL_LIST="/api/v1/package/list";//批量获取套餐（GET）
	private static final String URL_HOT="/api/v1/package/hot";//批量获取热门套餐（GET）
	private static final String URL_GET="/api/v1/package/get";//获取指定Id的套餐[精准查询]（GET）
	private static final String URL_SEARCH="/api/v1/package/search";//获取关键词搜索套餐（GET）
	private static final String URL_SCORE="/api/v1/package/score";//对指定的套餐进行评分（POST）
	private static final String URL_MY_SCORE="/api/v1/package/myScore";//获取指定用户对指定套餐的评分（GET）
	private static final String URL_SCORE_COUNT="/api/v1/package/scoreCount";//获取指定套餐的评分人数（GET）

	private PackageService packageService=new PackageService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		initCharsetContentType(response);//设置响应的字符编码和内容格式
		switch (servletPath) {
		case URL_RECOMMEND:
		case URL_SCORE:
			response.setStatus(405);
			break;
		case URL_LIST:
			doQueryCategory(request,response);
			break;
		case URL_HOT:
			doQueryHot(request,response);
			break;
		case URL_SEARCH:
			doSearch(request,response);
			break;
		case URL_GET:
			doQueryById(request,response);
			break;
		case URL_MY_SCORE:
			doGetMyScore(request,response);
			break;
		case URL_SCORE_COUNT:
			doGetScoreCount(request,response);
			break;
		default:
			response.setStatus(404);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		initCharsetContentType(response);//设置响应的字符编码和内容格式
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

	//执行推荐操作
	private void doRecommend(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String postBody=getPostBody(request);//将上传的post body读取出来
		UserConsumeVO userConsumeVO=new Gson()
				.fromJson(postBody,UserConsumeVO.class);//解析JSON数据

		//将结果以json格式返回
		List<PackageVO> dataList=RecommentCore.recomment(userConsumeVO);
		DataResponse<List<PackageVO>> jsonResponse=null;
		if(dataList!=null&&dataList.isEmpty()){//此时说明推荐结果为空
			jsonResponse=new DataResponse<List<PackageVO>>(false,StatusCode.CODE_QUERY_NONE,new ArrayList<>());
		}else{
			jsonResponse=new DataResponse<List<PackageVO>>(true,StatusCode.CODE_SUCCEED,dataList);
		}
		writeJsonToClient(jsonResponse, response);//将json数据返回给客户端
	}

	//获取热门套餐
	private void doQueryHot(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int limit=MathUtils.legalIntNum(request.getParameter("limit"),Config.HOT_PACKAGE_COUNT);
		int page=MathUtils.legalIntNum(request.getParameter("page"),1);
		//将结果以json格式返回
		List<SimplePackageVO> dataList=packageService
				.getAllHotPackage(limit,page);
		DataResponse<List<SimplePackageVO>> jsonResponse;
		if(dataList.isEmpty()){//此时说明查询结果为空
			jsonResponse=new DataResponse<List<SimplePackageVO>>(false,StatusCode.CODE_QUERY_NONE,dataList);
		}else{
			jsonResponse=new DataResponse<List<SimplePackageVO>>(true,StatusCode.CODE_SUCCEED,dataList);
		}
		writeJsonToClient(jsonResponse,response);//将json数据返回给客户端
	}

	//获取指定分类的套餐
	private void doQueryCategory(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String categoryName=request.getParameter("category_name");//分类名称[运营商|合作方|...]
		String categoryValue=request.getParameter("category_value");//分类的具体值[中国联通|腾讯...]
		int limit=MathUtils.legalIntNum(request.getParameter("limit"),Config.CATEGORY_PACKAGE_COUNT);
		int page=MathUtils.legalIntNum(request.getParameter("page"),1);

		List<SimplePackageVO> dataList=packageService
				.getAllSimplePackageByCategory(categoryName,categoryValue, limit, page);
		DataResponse<List<SimplePackageVO>> jsonResponse;
		if(dataList.isEmpty()){//此时查询结果为空
			jsonResponse=new DataResponse<List<SimplePackageVO>>(false,StatusCode.CODE_QUERY_NONE,dataList);
		}else{
			jsonResponse=new DataResponse<List<SimplePackageVO>>(true,StatusCode.CODE_SUCCEED,dataList);
		}
		writeJsonToClient(jsonResponse, response);
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
		writeJsonToClient(jsonResponse, response);//将json数据返回给客户端
	}

	//获取指定用户对指定套餐的评分
	private void doGetMyScore(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int packageId=MathUtils.legalIntNum(request.getParameter("package_id"),-1);//套餐Id（避免传入值不合法，默认为-1避免影响其他的套餐）
		int userId=MathUtils.legalIntNum(request.getParameter("user_id"),-1);//用户Id（避免传入值不合法，默认为-1避免影响其他的用户）
		
		PackageScorePO packageScorePO=packageService.getMyScore(userId,packageId);
		DataResponse<Integer> jsonResponse=null;
		if(packageScorePO.getId()==Constants.QUERY_ERROR_ID){
			jsonResponse=new DataResponse<Integer>(false,StatusCode.CODE_QUERY_NONE,0);
		}else{
			jsonResponse=new DataResponse<Integer>(true,StatusCode.CODE_SUCCEED,packageScorePO.getScore());
		}
		writeJsonToClient(jsonResponse,response);//将json数据返回给客户端
	}
	
	//获取指定套餐的评分人数
	private void doGetScoreCount(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int packageId=MathUtils.legalIntNum(request.getParameter("package_id"),-1);//套餐Id（避免传入值不合法，默认为-1避免影响其他的套餐）
		
		int count=packageService.getScoreCount(packageId);
		DataResponse<Integer> jsonResponse=null;
		if(count==Constants.QUERY_ERROR_ID){
			jsonResponse=new DataResponse<Integer>(false,StatusCode.CODE_QUERY_NONE,0);
		}else{
			jsonResponse=new DataResponse<Integer>(true,StatusCode.CODE_SUCCEED,count);
		}
		writeJsonToClient(jsonResponse,response);//将json数据返回给客户端
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
		writeJsonToClient(jsonResponse,response);//将json数据返回给客户端
	}

	//根据关键词查询套餐
	private void doSearch(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int limit=MathUtils.legalIntNum(request.getParameter("limit"),Config.SEARCH_PACKAGE_COUNT);
		int page=MathUtils.legalIntNum(request.getParameter("page"),1);
		String key=request.getParameter("key");//关键词

		//将结果以json格式返回
		List<SimplePackageVO> simplePackageList=packageService.getAllSimplePackageByKey(key,limit,page);
		DataResponse<List<SimplePackageVO>> jsonResponse;
		if(simplePackageList.isEmpty()){//此时说明查询结果为空
			jsonResponse=new DataResponse<List<SimplePackageVO>>(false,StatusCode.CODE_QUERY_NONE,simplePackageList);
		}else{
			jsonResponse=new DataResponse<List<SimplePackageVO>>(true,StatusCode.CODE_SUCCEED,simplePackageList);
		}
		writeJsonToClient(jsonResponse, response);//将json数据返回给客户端
	}

}
