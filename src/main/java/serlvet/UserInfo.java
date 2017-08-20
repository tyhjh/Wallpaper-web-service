package serlvet;

import database.Mysql;
import org.json.JSONObject;
import util.KeyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Tyhj on 2017/5/29.
 */
public class UserInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String sign = req.getParameter("sign");
        String data = req.getParameter("data");
        String AppVersion = req.getParameter("AppVersion");
        String imei = req.getParameter("imei");
        String model = req.getParameter("model");
        String name = req.getParameter("name");
        String AndroidVersion = req.getParameter("AndroidVersion");
        String os = req.getParameter("os");
        JSONObject jsonObject = new JSONObject();
        try {
            boolean isYou = KeyUtil.verify(data.getBytes(), KeyUtil.Key, sign);
            if (isYou) {
                System.out.println("OK");
                Mysql.userInfo(AppVersion, name, model, AndroidVersion, os, imei);
                jsonObject.put("code", 200);
                resp.getWriter().write(jsonObject.toString());
            }else {
                jsonObject.put("code", 401);
                resp.getWriter().write(jsonObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
