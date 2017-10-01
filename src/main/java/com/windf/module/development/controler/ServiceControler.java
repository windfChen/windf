package com.windf.module.development.controler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.ExceptionType;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.entity.Return;
import com.windf.module.development.entity.Service;
import com.windf.module.development.entity.ServiceMethod;
import com.windf.module.development.service.ServiceService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = ServiceControler.CONTROLER_PATH)
public class ServiceControler extends BaseControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/service";
	
	@Resource
	private ServiceService serviceService ;
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public String list() {
		/*
		 * 获取参数
		 */
		String moduleCode = this.paramenter.getString("moduleCode");
		
		/*
		 * 验证参数
		 */
		if (ParameterUtil.hasEmpty(moduleCode)) {
			return responseReturn.parameterError();
		}
		
		List<Service> data = null;
		try {
			data = serviceService.getAllService(moduleCode);
		} catch (UserException e) {
			return responseReturn.error(e.getMessage());
		}
		
		return responseReturn.successData(data);
	}
	
	@RequestMapping(value = "/create", method = {RequestMethod.GET})
	public String create() {
		/*
		 * 获取参数
		 */
		String moduleCode = paramenter.getString("moduleCode");
		String serviceName = paramenter.getString("name");
		String methodName = paramenter.getString("mName");
		String parameterName = paramenter.getString("pName");
		String parameterType = paramenter.getString("pType");
		String returnType = paramenter.getString("returnType");
		
		/*
		 * 验证参数
		 */
		if (ParameterUtil.hasEmpty(moduleCode, serviceName, methodName)) {
			return responseReturn.parameterError();
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
			responseReturn.error(e.getMessage());
		}
		
		return responseReturn.success();
	}
	
		
}
