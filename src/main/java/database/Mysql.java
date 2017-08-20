package database;

import org.json.JSONArray;
import org.json.JSONObject;
import util.TimeUtil;

import java.sql.*;


public class Mysql {
    static String url3 = "jdbc:mysql://118.190.172.230/wallpaper?useUnicode=true&characterEncoding=utf8";
    static Connection conn;
    static Statement statement;

    //初始化
    public static synchronized void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // System.out.println("成功加载MySQL驱动！");
        } catch (Exception e) {
            System.out.println("找不到MySQL驱动!");
            e.printStackTrace();
        }
        try {
            conn = (Connection) DriverManager.getConnection(url3, "tyhj", "4444");
            // System.out.println("成功加载conn！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("找不到conn!");
        }
        try {
            if (conn != null) {
                statement = (Statement) conn.createStatement();
            } else {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static String url = "jdbc:mysql://192.168.43.18/mina?useUnicode=true&characterEncoding=utf8";

    static String url2 = "jdbc:mysql://192.168.31.215/mina?useUnicode=true&characterEncoding=utf8";


    //获取壁纸数据
    public static synchronized String getWallpapers() {
        String wallpaper = null;
        String sql = "select * from wallpaper";
        JSONObject jsonObject = new JSONObject();
        try {
            if (statement == null || conn.isClosed()){
                init();
            }
            ResultSet rs = null;
            rs = (ResultSet) statement.executeQuery(sql);
            jsonObject.put("code", 200);
            JSONArray array = new JSONArray();
            while (rs.next()) {
                JSONObject object = new JSONObject();
                object.put("id", rs.getInt("id"));
                object.put("name", rs.getString("name"));
                object.put("preview", rs.getString("preview"));
                object.put("image", rs.getString("image"));
                object.put("mv", rs.getString("mv"));
                array.put(object);
            }
            jsonObject.put("wallpapers", array);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jsonObject.put("code", 202);
                return jsonObject.toString();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        log("获取壁纸数据" + wallpaper);
        return jsonObject.toString();
    }

    //获取留言
    public static synchronized String getCallBack(String imei) {
        String note = null;
        String sql = "select * from callBack where imei = '" + imei + "' and status = 0";
        JSONObject jsonObject = new JSONObject();
        try {
            if (statement == null || conn.isClosed())
                init();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                jsonObject.put("code", 200);
                jsonObject.put("leftNote", resultSet.getString("note"));
                sql = "update callBack set status =1 where imei = '" + imei + "' and status = 0";
                statement.executeUpdate(sql);
                return jsonObject.toString();
            }
            jsonObject.put("code", 201);
        } catch (Exception e) {
            try {
                jsonObject.put("code", 202);
                return jsonObject.toString();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    //给我留言
    public static synchronized void leftNote(String imei, String leftNote) {
        String time = TimeUtil.getNowTime();
        //String sql = "insert into leftNote values('" + time + "','" + 0 + "','" + leftNote + "','" + imei + "')";
        String sql2 = "insert into leftNote values(?,?,?,?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql2);
            ps.setString(1, time);
            ps.setInt(2, 0);
            ps.setString(3, leftNote);
            ps.setString(4, imei);
            ps.executeUpdate();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        //action(sql);
    }

    //保存用户信息
    public static synchronized void userInfo(String appVersion, String name, String model, String androidVersion, String os, String imei) {
        String time = TimeUtil.getNowTime();
        String sql = "delete from userInfo where imei = '" + imei + "'";
        action(sql);
        sql = "insert into userInfo values('" + appVersion + "','" + name + "','" + model + "','" + androidVersion + "','" + os + "','" + imei + "','" + time + "')";
        action(sql);
    }

    //保存信息
    private static void action(String sql) {
        try {
            if (statement == null || conn.isClosed())
                init();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取桌面信息
    public static synchronized String getHome() {
        String home = null;
        String sql = "select * from home";
        JSONObject jsonObject = new JSONObject();
        try {
            if (statement == null || conn.isClosed())
                init();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                jsonObject.put("code", 200);
                jsonObject.put("imageUrl", resultSet.getString("imageUrl"));
                jsonObject.put("type", resultSet.getInt("type"));
                return jsonObject.toString();
            }
            jsonObject.put("code", 201);
        } catch (Exception e) {
            try {
                jsonObject.put("code", 202);
                return jsonObject.toString();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    //检查更新
    public static synchronized String checkUpdate() {
        String sql = "select * from App";
        JSONObject object = new JSONObject();
        try {
            if (statement == null || conn.isClosed())
                init();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.last()) {
                object.put("code", 200);
                object.put("versionCode", resultSet.getString("versionCode"));
                object.put("apkUrl", resultSet.getString("apkUrl"));
                object.put("imageUrl", resultSet.getString("imageUrl"));
                object.put("info", resultSet.getString("info"));
                object.put("version", resultSet.getInt("version"));

                return object.toString();
            }
            object.put("code", 201);
        } catch (Exception e) {
            try {
                object.put("code", 202);
                return object.toString();
            } catch (org.json.JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return object.toString();
    }


    public static void log(String msg) {
        System.out.println(msg);
    }

}
