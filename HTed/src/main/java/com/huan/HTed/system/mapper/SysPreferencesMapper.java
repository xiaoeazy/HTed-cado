
package com.huan.HTed.system.mapper;

import com.huan.HTed.mybatis.common.Mapper;
import com.huan.HTed.system.dto.SysPreferences;

/**
 * 系统首选项Mapper.
 * 
 * @author huanghuan
 */
public interface SysPreferencesMapper extends Mapper<SysPreferences> {

    SysPreferences selectUserPreference(SysPreferences record);

    int updatePreferLine(SysPreferences record);
}