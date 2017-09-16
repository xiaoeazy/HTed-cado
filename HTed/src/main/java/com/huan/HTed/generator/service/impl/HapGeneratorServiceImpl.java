package com.huan.HTed.generator.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huan.HTed.generator.dto.DBColumn;
import com.huan.HTed.generator.dto.DBTable;
import com.huan.HTed.generator.service.IHapGeneratorService;

import freemarker.template.TemplateException;

@Service
public class HapGeneratorServiceImpl implements IHapGeneratorService {


    @Autowired
    SqlSession sqlSession;

    @Autowired
    DBUtil db;

    @Autowired
    FileUtil fileUtil;

    @Override
    public List<String> showTables() {
        List<String> tables;
        try {
            Connection conn=db.getConnectionBySqlSession(sqlSession);
            tables =db.showAllTables(conn);
            conn.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    @Override
    public int generatorFile(GeneratorInfo info) {
        int rs=0;
        String tableName=info.getTargetName();
        DBTable dbTable=getTableInfo(tableName);
        fileUtil.setGeneratorInfo(info);
        try {
            rs=createFile(dbTable,info);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return rs;
    }


    //获取table信息
    public DBTable getTableInfo(String tableName){
        Connection conn= null;
        DBTable dbTable=new DBTable();
        List<DBColumn> columns=dbTable.getColumns();
        List<String> multiColumns=null;
        List<String> NotNullColumns=null;
        try {
            //设置tablename
            dbTable.setName(tableName);
            conn = db.getConnectionBySqlSession(sqlSession);
            DatabaseMetaData dbmd=conn.getMetaData();
            //是否为多语言表
            boolean multiLanguage=db.isMultiLanguageTable(tableName);
            if(multiLanguage) {
                dbTable.setMultiLanguage(multiLanguage);
                multiColumns=db.isMultiLanguageColumn(tableName,dbmd);
                //判断多语言字段
            }
            //获取主键字段
            String column_PK=db.getPrimaryKey(tableName,dbmd);
            //获取不为空的字段
            NotNullColumns=db.getNotNullColumn(tableName,dbmd);
            //获取表列信息
            ResultSet rs1=db.getTableColumnInfo(tableName,dbmd);
            while (rs1.next()){
                DBColumn column=new DBColumn();
                String columnName=rs1.getString("COLUMN_NAME");
                if(columnName.equals("OBJECT_VERSION_NUMBER")){
                    break;
                }
                column.setName(columnName);
                String typeName=rs1.getString("TYPE_NAME");
                column.setType(typeName);
                //判断是否为主键
                if(columnName.equals(column_PK)){
                    column.setId(true);
                }
                //判断是否为null字段
                for(String n:NotNullColumns) {
                    if (columnName.equals(n)&&!columnName.equals(column_PK)){
                        if(typeName.equals("BIGINT")){
                            column.setNotNull(true);
                        }else if(typeName.equals("VARCHAR")){
                            column.setNotEmpty(true);
                        }
                    }
                }
                //判断多语言表中的多语言字段
                if(multiLanguage) {
                    for (String m : multiColumns){
                        if(m.equals(columnName)){
                            column.setMultiLanguage(true);
                            break;
                        }
                    }
                }
                columns.add(column);
            }
            //是否是多语言表
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbTable;
    }

    public int createFile(DBTable table,GeneratorInfo info) throws IOException, TemplateException {

        int rs=fileUtil.isFileExist();
        if(rs==0){
            if(!info.getDtoStatus().equals("NotOperation")) {
                fileUtil.createDto(table);
            }
            if(!info.getControllerStatus().equals("NotOperation")) {
                fileUtil.createFtlInfoByType(FileUtil.pType.Controller, table);
            }
            if(!info.getMapperStatus().equals("NotOperation")){
                fileUtil.createFtlInfoByType(FileUtil.pType.Mapper, table);
            }
            if(!info.getImplStatus().equals("NotOperation")) {
                fileUtil.createFtlInfoByType(FileUtil.pType.Impl, table);
            }
            if(!info.getServiceStatus().equals("NotOperation")) {
                fileUtil.createFtlInfoByType(FileUtil.pType.Service, table);
            }
            if(!info.getMapperXmlStatus().equals("NotOperation")) {
                fileUtil.createFtlInfoByType(FileUtil.pType.MapperXml, table);
            }
            if(!info.getHtmlStatus().equals("NotOperation")){
                fileUtil.createFtlInfoByType(FileUtil.pType.Html,table);
            }
        }
        return rs;
    }

}
