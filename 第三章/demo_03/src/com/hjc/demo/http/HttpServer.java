package com.hjc.demo.http;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HttpServer")
public class HttpServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HttpServer() {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get method");
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		System.out.println("param1=" + param1);
		System.out.println("param2=" + param2);
		response.getWriter().write("get method from server");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post method");
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		System.out.println("param1=" + param1);
		System.out.println("param2=" + param2);
		response.getWriter().write("post method from server");
	}
}
