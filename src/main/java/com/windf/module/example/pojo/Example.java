package com.windf.module.example.pojo;

import java.io.UnsupportedEncodingException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Example {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String cookieValueAfterDecode = new String(Base64.decode("d3d3Lnpzb2wubmV0fHwwYzg1YzZiOWQwYzExZjBmZDZhMTA3MTlhZDBjYTZlNHx8MTM5MTAwMDM3OTV8fGdqZ2I="), "utf-8");
		String[] cookieValues = cookieValueAfterDecode.split("\\|\\|");
		System.out.println("2:" + cookieValueAfterDecode);
	}
}
