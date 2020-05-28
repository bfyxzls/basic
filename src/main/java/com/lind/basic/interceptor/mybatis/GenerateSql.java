package com.lind.basic.interceptor.mybatis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateSql {
    public static String generateInsertSql(String sql, String userId, String tenantId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String left = "";
        String right = "";
        if (sql.indexOf("tenant_id") == -1) {
            left += " tenant_id,";
            right += tenantId + ", ";
        }
        if (sql.indexOf("create_time") == -1) {
            left += " create_time,";
            right += "'" + dateFormat.format(new Date()) + "'" + ", ";
        }
        if (sql.indexOf("create_by") == -1) {
            left += " create_by,";
            right += userId + ", ";
        }
        if (sql.indexOf("update_time") == -1) {
            left += " update_time,";
            right += "'" + dateFormat.format(new Date()) + "'" + ", ";
        }
        if (sql.indexOf("update_by") == -1) {
            left += " update_by,";
            right += userId + ", ";
        }

        String sql_1 = sql.substring(0, sql.indexOf("(") + 1);
        String sql_2 = sql.substring(sql.indexOf("(") + 1, sql.lastIndexOf("(") + 1);
        String sql_3 = sql.substring(sql.lastIndexOf("(") + 1, sql.length());
        sql = sql_1 + left + sql_2 + right + sql_3;
        return sql;
    }

    public static String generateUpdateSql(String sql,String userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addSql = "";
        if (sql.indexOf("update_time") == -1) {
            addSql += ", update_time = " + "'" + dateFormat.format(new Date()) + "'";
        }
        if (sql.indexOf("update_by") == -1) {
            addSql += ", update_by = " + userId;
        }

        if (sql.toLowerCase().indexOf("where") == -1) {
            sql += addSql;
        } else {
            String sql1 = sql.substring(0, sql.toLowerCase().indexOf("where"));
            String sql2 = sql.substring(sql.toLowerCase().indexOf("where"), sql.length());
            sql = sql1 + addSql + " " + sql2;
        }
        return sql;
    }

    public static String generateDeleteSql(String sql, String tenantId) {

        String sql1 = sql.substring(0, sql.toLowerCase().indexOf("where") + 5);
        String sql2 = sql.substring(sql.toLowerCase().indexOf("where") + 5, sql.length());
        String addSql = " tenant_id = " + tenantId + " and";
        sql = sql1 + addSql + sql2;
        return sql;
    }

    public static String generateSelectSql(String sql, String tenantId) {

        String sql1 = sql.substring(0, sql.toLowerCase().indexOf("where") + 5);
        String sql2 = sql.substring(sql.toLowerCase().indexOf("where") + 5, sql.length());
        String addSql = " tenant_id = " + tenantId + " and";
        sql = sql1 + addSql + sql2;
        return sql;
    }

}
