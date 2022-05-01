package com.cyiplus.scarlett.service.impl;

import com.cyiplus.scarlett.entity.SysRecord;
import com.cyiplus.scarlett.mapper.SysRecordMapper;
import com.cyiplus.scarlett.service.SysRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-30
 */
@Service
public class SysRecordServiceImpl extends ServiceImpl<SysRecordMapper, SysRecord> implements SysRecordService {

    @Override
    public void Record(SysRecord sysRecord) {
       save(sysRecord);
        
    }

}
