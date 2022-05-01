package com.cyiplus.scarlett.service;

import com.cyiplus.scarlett.entity.SysRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ilkka Cai
 * @since 2022-04-30
 */
public interface SysRecordService extends IService<SysRecord> {

    public void Record(SysRecord sysRecord);

}
