package com.ending.packagesystem.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ending.packagesystem.config.Config;
import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.config.StatusCode;
import com.ending.packagesystem.service.AppUpdateService;
import com.ending.packagesystem.utils.DebugUtils;
import com.ending.packagesystem.utils.MathUtils;
import com.ending.packagesystem.vo.AppUpdateInfoVO;
import com.ending.packagesystem.vo.BaseResponse;
import com.ending.packagesystem.vo.DataResponse;
import com.google.gson.Gson;

/**
 * 统一处理与应用相关的请求
 * @author CodingEnding
 */
@WebServlet(urlPatterns={"/api/v1/app/check"})
public class AppServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public static final String URL_DOWNLOAD="/api/v1/app/download";//下载最新的App版本（GET）[暂时弃用]
    public static final String URL_CHECK="/api/v1/app/check";//检测是否存在更新（GET）

    private AppUpdateService appUpdateService=new AppUpdateService();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json;charset=utf-8");
		initCharsetContentType(response);//设置响应的字符编码和内容格式
		switch (servletPath) {
		case URL_DOWNLOAD:
			break;
		case URL_CHECK:
			doCheck(request,response);
			break;
		default:
			response.setStatus(405);
			break;
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initCharsetContentType(response);//设置响应的字符编码和内容格式
		doGet(request, response);
	}

	//检查是否存在新的应用版本
	private void doCheck(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String packageName=request.getParameter("package");//包名
		int versionCode=MathUtils.legalIntNum(request
				.getParameter("version"),1);//版本号默认为1
		String channel=request.getParameter("channel");//渠道名

		//以JSON的形式返回
		String apkFolderPath=request.getServletContext().getRealPath(Config.APK_FOLDER_PATH);//获取apk文件夹的真实路径
		AppUpdateInfoVO appUpdateInfoVO=appUpdateService.checkUpdate(apkFolderPath,versionCode);
		DataResponse<AppUpdateInfoVO> jsonResponse=null;
		if(appUpdateInfoVO.getId()==Constants.QUERY_ERROR_ID){//此时查询失败
			jsonResponse=new DataResponse<AppUpdateInfoVO>(false,
					StatusCode.CODE_QUERY_NONE,appUpdateInfoVO);
		}else{
			jsonResponse=new DataResponse<AppUpdateInfoVO>(true,
					StatusCode.CODE_SUCCEED,appUpdateInfoVO);
		}
		writeJsonToClient(jsonResponse,response);
	}
	
//	//将json数据返回给客户端
//	private void writeJsonToClient(BaseResponse jsonResponse,HttpServletResponse response) throws IOException{
//		String dataJSON=new Gson().toJson(jsonResponse);//将数据对象转化为json字符串
//		response.getWriter().append(dataJSON);
//	}

}
