package com.deepnighttwo.aircondition.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deepnighttwo.aircondition.acsum.util.ACSumInitializer;

@SuppressWarnings("serial")
public class AirConditionSumDateRetrieveInitializerServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		new ACSumInitializer().initSystem();
	}

}