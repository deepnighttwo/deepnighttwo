package com.deepnighttwo.angularjs.startup;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * User: mzang
 * Date: 2014-08-26
 * Time: 19:54
 */

@WebServlet(name = "angularjsajax", urlPatterns = {"/angularjsajax"})
public class AngularJSAjax extends HttpServlet {

    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");

        int size = 9;
        Random random = new Random();
        Person[] persons = new Person[size];
        for (int i = 0; i < size; i++) {
            persons[i] = new Person();
            persons[i].setName("testname");
            persons[i].setPhoneNumber(String.valueOf(random.nextLong()));
        }
        String content = gson.toJson(persons);
        resp.getOutputStream().write(content.getBytes());
        resp.setHeader("Content-Type", "text/html; charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
    }
}
