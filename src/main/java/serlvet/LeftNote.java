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
 * Created by Tyhj on 2017/5/30.
 */
public class LeftNote extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String sign = req.getParameter("sign");
        String data = req.getParameter("data");
        String imei=req.getParameter("imei");
        String note=req.getParameter("leftNote");
        JSONObject jsonObject = new JSONObject();

        try {
            boolean isYou = KeyUtil.verify(data.getBytes(), KeyUtil.Key, sign);
            if(isYou){
                jsonObject.put("code", 200);
                Mysql.leftNote(imei,note);
            }else {
                jsonObject.put("code", 401);
            }
            resp.getWriter().write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
