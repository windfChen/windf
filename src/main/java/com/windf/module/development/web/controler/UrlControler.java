package com.windf.module.development.web.controler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.modle.controler.ControlerCoder;
import com.windf.module.development.modle.controler.ControlerReturn;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.ModuleMaster;
import com.windf.module.development.service.UrlService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = Constant.WEB_BASE_PATH + "/url")
public class UrlControler extends BaseControler{
	
	@Resource
	private UrlService urlService ;
	
	@Override
	protected String getModulePath() {
		return Constant.WEB_BASE_PATH;
	}
	
	@ResponseBody
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public Map<String, Object> create() {
		// 验证参数
		String url = this.getParameter("url");
		if (ParameterUtil.hasEmpty(url)) {
			return jsonReturn.paramErrorMap();
		}
		
		// 调用服务
		try {
			urlService.createUrl(url);
		} catch (UserException e) {
			return jsonReturn.errorMap(e.getMessage());
		}
		
		return jsonReturn.returnMap(true, "创建成功");
	}
	
	@ResponseBody
	@RequestMapping(value = "/test", method = {RequestMethod.GET})
	public Map<String, Object> test()  {
		Module m = null;
		try {
			m = ModuleMaster.getInstance().findModuleByCode("example");
		} catch (UserException e) {
			e.printStackTrace();
		}
		
		return jsonReturn.returnMap(true, "test", m);
	}
	
	@ResponseBody
	@RequestMapping(value = "/testControler", method = {RequestMethod.GET})
	public Map<String, Object> testControler() {
		String name = this.getParameter("name");
		
		try {
			ControlerCoder controlerCoder = new ControlerCoder("example", name);
			
//			controlerCoder.setWebPath("/test");
//			controlerCoder.addSubPath("hello", "sayHello", true, false);
//			controlerCoder.addSubPath("bye", "sayGoodbye", true, false);
//			controlerCoder.write();
//			
//			Parameter p = new Parameter();
//			p.setName("name");
//			p.setType("String");
//			p.setDescript("商品名称");
//			p.setNotEmpty(true);
//			p.addPattern("^([0-9]{15})|([0-9]{17}[xX]{1})([0-9]{18})$", "身份证号");
//			Parameter p2 = new Parameter();
//			p2.setName("age");
//			p2.setType("Integer");
//			p2.addPattern("数字", "数字");
//			p2.setNotEmpty(true);
//			List<Parameter> parameters = new ArrayList<Parameter>();
//			parameters.add(p);
//			parameters.add(p2);
//			controlerCoder.addParameterVeriry("hello", parameters);
//			try {
//
////				ControlerReturn ret = new ControlerReturn(ControlerReturn.MAP_STRING_OBJECT);
////				ret.setSuccess(true);
////				ret.setDataName("whaty");
////				ret.setMessage("");
//				
//				ControlerReturn ret = new ControlerReturn(ControlerReturn.STRING);
//				ret.setSuccess(false);
////				ret.setViewPath("/dev/create");
//				ret.setMessage("创建成功");
////				ret.setSureButtonWord("现在登录");
////				ret.setSureHref("http://www.baidu.com");
//				
//				controlerCoder.setReturn("hello", ret);
//			} catch (CodeException e) {
//				e.printStackTrace();
//			}
//			controlerCoder.write();
			
//			List<Parameter> l = controlerCoder.getParameterVeriry("hello");
//			return jsonReturn.returnMap(true, "test", l);
			
//			ControlerReturn l2 = controlerCoder.getRetrun("hello");
//			return jsonReturn.returnMap(true, "test", l2);
			
			ControlerReturn l3 = controlerCoder.getRetrun("hello");
			return jsonReturn.returnMap(true, "test", l3);
		} catch (UserException e) {
			e.printStackTrace();
			return jsonReturn.returnMap(false, e.getMessage());
		}
		
	}

}
