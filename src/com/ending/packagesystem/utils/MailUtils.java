package com.ending.packagesystem.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件的工具类
 * @author CodingEnding
 */

public class MailUtils {
	private static final String EMAIL_ADDRESS="ic_aphone@sohu.com";//邮箱地址
	private static final String EMAIL_PWD="BillFlower1314";//邮箱密码

	//身份验证
	private static Authenticator authenticator=new Authenticator(){  
        protected PasswordAuthentication getPasswordAuthentication() {  
            return new PasswordAuthentication(EMAIL_ADDRESS,EMAIL_PWD);  
        }
    };
	
	private MailUtils(){}

	/**********************内部方法***********************/
	//获得Properties
	private static Properties getProperties(){
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth","true");
		prop.setProperty("mail.host","smtp.sohu.com");
		//prop.setProperty("mail.transport.protocol","smtp");
		
		prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		prop.setProperty("mail.smtp.socketFactory.fallback","false");  
		prop.setProperty("mail.smtp.port","465");  
		prop.setProperty("mail.smtp.socketFactory.port","465");  
		return prop;
	}

	//根据主题和内容创造Message对象
	private static MimeMessage getMessage(Session session,String email,
			String subject,String content) throws AddressException, MessagingException{
		//创建邮件对象
		MimeMessage message = new MimeMessage(session);
		//指明邮件的发件人
		message.setFrom(new InternetAddress(EMAIL_ADDRESS));
		//指明邮件的收件人
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email,false));
		//邮件的标题
		message.setSubject(subject);
		//邮件的文本内容
		message.setContent(content,"text/html;charset=UTF-8");
		return message;
	}

	/***********************外部方法**************************/

	/**
	 * 发送验证码邮件
	 * @param code
	 * @email 接收者邮箱
	 */
	public static void sendCodeEmail(String code,String email){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Properties prop = getProperties();
				try {
					Session session = Session.getDefaultInstance(prop,authenticator); 
					session.setDebug(true);//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
					Message message = createSimpleCodeMail(session,code,email);//4、创建邮件
					Transport.send(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 发送新用户注册提示邮件
	 * @email 接收者邮箱
	 */
	public static void sendRegisterTipsEmail(String email){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Properties prop = getProperties();
				try {
					Session session = Session.getDefaultInstance(prop,authenticator); 
					session.setDebug(true);
					Message message = createRegisterTipsMail(session,email);
					Transport.send(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * 发送密码修改提示邮件
	 * @email 接收者邮箱
	 */
	public static void sendModifyPwdTipsEmail(String email){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Properties prop = getProperties();
				try {
					Session session = Session.getDefaultInstance(prop,authenticator);
					session.setDebug(true);
					Message message = createModifyPwdTipsMail(session,email);
					Transport.send(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 创建一封包含验证码的邮件
	 * @param session
	 * @param code 验证码
	 * @param email 接收人邮箱
	 * @throws Exception
	 */
	public static MimeMessage createSimpleCodeMail(Session session,String code,String email)
			throws Exception {
		String subject="套餐精灵-找回密码";
		String content="验证码："+code+"（请勿泄露验证码）";
		return getMessage(session,email,subject,content);
	}

	/**
	 * 创建一封提示邮箱注册的邮件
	 * @param session
	 * @param email 接收人邮箱
	 * @throws Exception
	 */
	public static MimeMessage createRegisterTipsMail(Session session,String email)
			throws Exception {
		String subject="套餐精灵-新用户注册";
		String content="您已经成功注册套餐精灵（如果不是您的操作，可以通过App内的[忘记密码]修改密码）";
		return getMessage(session,email,subject,content);
	}
	
	/**
	 * 创建一封提示密码发生修改的邮件
	 * @param session
	 * @param email 接收人邮箱
	 * @throws Exception
	 */
	public static MimeMessage createModifyPwdTipsMail(Session session,String email)
			throws Exception {
		String subject="套餐精灵-密码修改";
		String content="您的密码已经修改（如果不是您的操作，可以通过App内的[忘记密码]修改密码）";
		return getMessage(session,email,subject,content);
	}

}
