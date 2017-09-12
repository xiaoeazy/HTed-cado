package com.huan.HTed.intergration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huan.HTed.intergration.dto.HapInterfaceHeader;
import com.huan.HTed.mybatis.common.Mapper;

public interface HapInterfaceHeaderMapper extends Mapper<HapInterfaceHeader> {

    //查询所有的系统接口
   public  List<HapInterfaceHeader> getAllHeader(HapInterfaceHeader interfaceHeader);

    /*
    * 一对多关联查询 根据headerId 与语言
    * */
    public List<HapInterfaceHeader> getHeaderAndLineList(HapInterfaceHeader interfaceHeader);

    /*
    * 根据sysName 和 apiName 查询header 和 line
    * */
    public HapInterfaceHeader getHeaderAndLineBySysNameAndApiName(@Param("sysName") String sysName, @Param("apiName") String apiName);

    /*
    * 获取所有的header和line 数据--->HeaderAndHeaderTlDTO
    * */
    List<HapInterfaceHeader> getAllHeaderAndLine();

   /*
   * 根据headerId 获取header
   * */
   List<HapInterfaceHeader> getHeaderByHeaderId(HapInterfaceHeader interfaceHeader);

   /*
   * 根据lineId 获取数据  HeaderAndLineDTO
   * */
   HapInterfaceHeader getHeaderAndLineBylineId(HapInterfaceHeader interfaceHeader);


}
