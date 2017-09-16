package com.huan.HTed.generator.service.impl;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBUtil {
    public Connection getConnectionBySqlSession(SqlSession sqlSession) throws SQLException {
        Connection connection=null;
         connection=sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
        return connection;
    }

    public List<String> showAllTables(Connection conn) throws SQLException {
        List<String> tables =new ArrayList<String>();
        DatabaseMetaData dbmd=conn.getMetaData();
        String database=conn.getCatalog();
        ResultSet rs=dbmd.getTables(database,null,null,new String[]{"TABLE"});
        while(rs.next()){
            tables.add(rs.getString("TABLE_NAME"));
        }
        return  tables;
    }

    public ResultSet getTableColumnInfo(String table,DatabaseMetaData dbmd) throws SQLException {
        ResultSet rs=dbmd.getColumns(null,null,table,null);
        return  rs;
    }

    public boolean isMultiLanguageTable(String table) throws SQLException {
        boolean result=false;
        table=table.toUpperCase();
        if(table.endsWith("_B")){
            result=true;
        }
        return result;
    }

    public List<String> getNotNullColumn(String table,DatabaseMetaData dbmd) throws SQLException {
        List<String> result=new ArrayList<String>();
        ResultSet rs=dbmd.getColumns(null,null,table,null);
        while(rs.next()){
            if(rs.getString("IS_NULLABLE").equals("NO")){
                result.add(rs.getString("COLUMN_NAME"));
            }
        }
        return result;
    }

    public List<String> isMultiLanguageColumn(String table,DatabaseMetaData dbmd) throws SQLException {
        List<String> result=new ArrayList<String>();
        String tl_table=table.substring(0,table.length()-2)+"_tl";
        ResultSet rs=getTableColumnInfo(tl_table,dbmd);
        boolean key=false;
        while(rs.next()){
             if(rs.getString("COLUMN_NAME").equals("OBJECT_VERSION_NUMBER")){
                key=false;
                break;
            }
            if(key){
                result.add(rs.getString("COLUMN_NAME"));
            }
            if(rs.getString("COLUMN_NAME").equals("LANG")){
                key=true;
            }
        }
        return result;
    }

    public String getPrimaryKey(String table,DatabaseMetaData dbmd) throws SQLException {
        String column_PK=null;
        ResultSet rs=dbmd.getPrimaryKeys(null,null,table);
        while(rs.next()){
            column_PK=rs.getString("COLUMN_NAME");
        }
        return column_PK;
    }
}
