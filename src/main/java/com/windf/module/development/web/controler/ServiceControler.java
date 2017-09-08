package com.windf.module.development.web.controler;

import java.util.ArrayList;
import java.util.List;
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
import com.windf.module.development.pojo.ExceptionType;
import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;
import com.windf.module.development.pojo.Service;
import com.windf.module.development.pojo.ServiceMethod;
import com.windf.module.development.service.ServiceService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = Constant.WEB_BASE_PATH + "/service")
public class ServiceControler extends BaseControler{
	
	@Resource
	private ServiceService serviceService ;
	
	@Override
	protected String getModulePath() {
		return Constant.WEB_BASE_PATH;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public Map<String, Object> list() {
		/*
		 * 获取参数
		 */
		String moduleCode = this.getParameter("moduleCode");
		
		/*
		 * 验证参数
		 */
		if (ParameterUtil.hasEmpty(moduleCode)) {
			return jsonReturn.paramErrorMap();
		}
		
		List<Service> data = null;
		try {
			data = serviceService.getAllService(moduleCode);
		} catch (UserException e) {
			return jsonReturn.errorMap(e.getMessage());
		}
		
		return jsonReturn.successMap(data);
	}
	
	@ResponseBody
	@RequestMapping(value = "/create", method = {RequestMethod.GET})
	public Map<String, Object> create() {
		/*
		 * 获取参数
		 */
		String moduleCode = this.getParameter("moduleCode");
		String serviceName = this.getParameter("name");
		String methodName = this.getParameter("mName");
		String parameterName = this.getParameter("pName");
		String parameterType = this.getParameter("pType");
		String returnType = this.getParameter("returnType");
		
		/*
		 * 验证参数
		 */
		if (ParameterUtil.hasEmpty(moduleCode, serviceName, methodName)) {
			return jsonReturn.paramErrorMap();
		}
		
		/*
		 * 组装参数
		 */
		Return ret = new Return(returnType);
		List<Parameter> parameters = new ArrayList<Parameter>();
		if (parameterName != null) {
			Parameter p = new Parameter();
			p.setName(parameterName);
			p.setType(parameterType);
			parameters.add(p);
		}
		ExceptionType exceptionType = new ExceptionType("UserException, CodeException");
		ServiceMethod serviceMethod = new ServiceMethod();
		serviceMethod.setName(methodName);
		serviceMethod.setRet(ret);
		serviceMethod.setParameters(parameters);
		serviceMethod.setExceptionType(exceptionType);
		
		try {
			serviceService.createServiceMethod(moduleCode, serviceName, serviceMethod);
		} catch (UserException e) {
			jsonReturn.errorMap(e.getMessage());
		}
		
		return jsonReturn.successMap();
	}
	
		
}
