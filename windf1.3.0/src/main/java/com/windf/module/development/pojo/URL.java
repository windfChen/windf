package com.windf.module.development.pojo;

import java.util.List;

public class URL {
	private String name;
	private String descript;
	private List<Parameter> parameters;
	private ExceptionType exceptionType;
	private Return ret;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Return getReturn() {
		return ret;
	}

	public void setReturn(Return ret) {
		this.ret = ret;
	}

}
