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
public class CheckUpdate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String sign = req.getParameter("sign");
        String data = req.getParameter("data");
        JSONObject jsonObject = new JSONObject();
        try {
            boolean isYou = KeyUtil.verify(data.getBytes(), KeyUtil.Key, sign);
            if (isYou) {
                resp.getWriter().write(Mysql.checkUpdate());
            } else {
                jsonObject.put("code", 401);
                resp.getWriter().write(jsonObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
