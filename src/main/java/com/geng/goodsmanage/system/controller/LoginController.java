package com.geng.goodsmanage.system.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.geng.goodsmanage.system.model.Role;
import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.system.service.UserService;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private HttpServletRequest request;
	static String strCode = null;
	public static final char[] CHARS = { '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L',
			'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	public static Random random = new Random();
	@Autowired
	@Qualifier("userService")
	private UserService userService;


    @RequestMapping("/toLogin")
	public ModelAndView toLogin(HttpSession httpSession){
		User user=(User) httpSession.getAttribute("user");
		ModelAndView mav=new ModelAndView();
		if(null!=user){
			mav.setViewName("index");
		}else{
			mav.setViewName("login");
		}
		//ModelAndView mav=new ModelAndView("login");
		return mav;
	}

	/**
	 * 
	 * 此方法描述的是：用户登陆
	 * 
	 * @author: genghc
	 * @version: 2013年10月20日 下午4:58:21
	 */
	@RequestMapping("/login")
	@ResponseBody
	public JSON login(User user, String checkcode,HttpSession httpSession) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (strCode.equalsIgnoreCase(checkcode)) {
			User u = userService.find(user);
		
			if (null != u) {
				httpSession.setAttribute("user", u);
				map.put("success", true);
				map.put("msg", "登陆成功!");
				Set<Role> roles=u.getRoles();
				for(Role role:roles){
					System.out.println("1"+role.getRoleName());
				}
			} else {
				map.put("success", false);
				map.put("msg", "密码错误!");
			}

		} else {
			map.put("success", false);
			map.put("msg", "验证码输入错误!");
		}

		JSON json = JSONObject.fromObject(map);
		return json;

	}
	/**
	 * 
	 * @Title: index
	 * @Description: TODO(定向到首页)
	 * @param @param httpSession
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession httpSession){
		User user=(User) httpSession.getAttribute("user");
		ModelAndView mav=new ModelAndView();
		if(null!=user){
			mav.setViewName("index");
		}else{
			mav.setViewName("user/fail");
		}
		return mav;
	}

	/**
	 * 
	 * 此方法描述的是：获取验证码
	 * 
	 * @author: genghc
	 * @version: 2013年10月6日 下午7:54:18
	 */
	@RequestMapping("/getCode")
	public void getCode(HttpServletRequest request, HttpServletResponse response) {
		OutputStream stream = null;
		try {
			ByteArrayInputStream inputStream = this.createImage();
			byte[] data = new byte[1024];
			int length = inputStream.read(data);
			stream = response.getOutputStream();
			while (length >= 0) {
				stream.write(data);
				length = inputStream.read(data);
			}
			inputStream.close();
			stream.flush();
			stream.close();
			response.flushBuffer();
			
		} catch (IOException e) {
			System.out.println("获取验证码出错了");
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					System.out.println("关闭输出流出错了");
				}
			}
		}

	}

	// 获取四位随机数
	public static String getRandomString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		strCode = buffer.toString();
		System.out.println("4位随机数：" + strCode);
		return strCode;
	}

	// 创建图片
	public ByteArrayInputStream createImage() {
		String randomString = getRandomString();// 获取随机字符串

		int width = 80;// 设置图片的宽度
		int height = 30;// 高度

		Color color = getRandomColor();// 获取随机颜色，作为背景色
		Color reverse = getReverseColor(color);// 获取反色，用于前景色

		// 创建一个彩色图片
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();// 获取绘制对象
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));// 设置字体

		g.setColor(color);// 设置颜色

		g.fillRect(0, 0, width, height);// 绘制背景
		g.setColor(reverse);// 设置颜色
		g.drawString(randomString, 5, 23);

		// 最多绘制100个噪音点
		for (int i = 0, n = random.nextInt(100); i < n; i++) {
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
		}

		// 返回验证码图片的流格式
		ByteArrayInputStream bais = convertImageToStream(image);

		return bais;

	}

	// 获取随机颜色
	public static Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255),
				random.nextInt(255));
	}

	// 返回某颜色的反色
	public static Color getReverseColor(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(),
				255 - c.getBlue());
	}

	// 将BufferedImage转换成ByteArrayInputStream
	private static ByteArrayInputStream convertImageToStream(BufferedImage image) {

		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);
		try {
			jpeg.encode(image);
			byte[] bts = bos.toByteArray();
			inputStream = new ByteArrayInputStream(bts);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

}
