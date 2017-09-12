package com.huan.HTed.cache.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huan.HTed.intergration.dto.HapInterfaceHeader;
import com.huan.HTed.intergration.mapper.HapInterfaceHeaderMapper;


/**
 * Created by user on 2016/8/1.
 */
public class ApiConfigCache<T>  extends HashStringRedisCache<HapInterfaceHeader> {

    private static final Logger logger = LoggerFactory.getLogger(ApiConfigCache.class);
    private String apiSql = HapInterfaceHeaderMapper.class.getName() + ".getAllHeaderAndLine";
    private String lineSql = HapInterfaceHeaderMapper.class.getName() + ".getHeaderLineByLineId";


    @Override
    public void init() {
        setType(HapInterfaceHeader.class);
        strSerializer = getRedisTemplate().getStringSerializer();
        initLoad();
    }

    @Override
    public HapInterfaceHeader getValue(String key) {
        return super.getValue(key);
    }

    @Override
    public void setValue(String key, HapInterfaceHeader headerAndLineDTO) {
        super.setValue(key, headerAndLineDTO);
    }


    public void initLoad() {

        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(apiSql, (resultContext) -> {
                HapInterfaceHeader headerAndLineDTO = (HapInterfaceHeader) resultContext.getResultObject();
                logger.info("cache result:{}", headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode());
                setValue(headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode(), headerAndLineDTO);
            });

        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("init api cache error:", e);
            }
        }

    }


    public void reload(Object lineId) {
        logger.info("test  lineId:{}", lineId);
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            HapInterfaceHeader headerAndLineDTO = sqlSession.selectOne(lineSql, lineId);
            setValue(headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode(), headerAndLineDTO);

        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("reload api cache error:", e);
            }
        }


    }


}
