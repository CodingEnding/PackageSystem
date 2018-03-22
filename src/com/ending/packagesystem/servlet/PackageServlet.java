package com.ending.packagesystem.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.ending.packagesystem.core.PackageConsume;
import com.ending.packagesystem.core.RecommentCore;
import com.ending.packagesystem.po.PackagePO;
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
			"/api/v1/package/score"})
public class PackageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String URL_RECOMMEND="/api/v1/package/recommend";//请求推荐（POST）
    public static final String URL_LIST="/api/v1/package/list";//批量获取套餐（GET）
    public static final String URL_GET="/api/v1/package/get";//获取指定名称的套餐（GET）
    public static final String URL_SCORE="/api/v1/package/score";//对指定的套餐进行评分（POST）
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		switch (servletPath) {
		case URL_RECOMMEND:
			response.setStatus(405);
			break;
		case URL_SCORE:
			response.setStatus(405);
			break;
		case URL_LIST:
			break;
		case URL_GET:
			doSearch(request,response);
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
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		
		//将结果以json格式返回
		List<PackageVO> dataList=RecommentCore.recomment(userConsumeVO);
		String dataJSON=new Gson().toJson(dataList);
		response.setStatus(200);
		response.getWriter().append(dataJSON);
	}
	
	//执行对套餐的评分操作
	private void doScore(HttpServletRequest request,HttpServletResponse response){
		//TODO 补充完整
	}
	
	//执行对指定套餐的查询
	private void doSearch(HttpServletRequest request,HttpServletResponse response){
		//TODO 补充完整
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
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
