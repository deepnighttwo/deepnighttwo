package com.deepnighttwo.aircondition.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deepnighttwo.aircondition.acsum.dao.ACSumDAOManager;
import com.deepnighttwo.aircondition.acsum.dao.AirConditionSum;

@SuppressWarnings("serial")
public class AirConditionServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		List<AirConditionSum> acTop = ACSumDAOManager.getTopConditions(30);

		resp.setContentType("text/plain");

		for (AirConditionSum acs : acTop) {
			resp.getWriter().println(acs.toString());
		}
	}
}
