package com.yeyi.um.service.impl;

import com.yeyi.framework.core.Result;
import com.yeyi.framework.core.ResultGenerator;
import com.yeyi.framework.utils.ShellUtil;
import com.yeyi.um.entity.AptsApp;
import com.yeyi.um.mapper.AptsAppMapper;
import com.yeyi.um.service.IAptsAppService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * <p>
 * 应用表 服务实现类
 * </p>
 *
 * @author 张国伟
 * @since 2018-12-04
 */
@Service
public class AptsAppServiceImpl extends ServiceImpl<AptsAppMapper, AptsApp> implements IAptsAppService {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShellUtil shellUtil;


    @Value("${apts.aptsFile.projectFiles}")
    private String projectFiles;

    @Transactional(rollbackFor = Exception.class)
    public Result startApp(Integer id) {
        log.info("执行startApp");
        AptsApp aptsApp = selectById(id);
        boolean flag = shellUtil.startFile(id, aptsApp.getFileName(), aptsApp.getFileUrl());
        if (flag) {
            aptsApp = new AptsApp();
            aptsApp.setId(id);
            aptsApp.setState(1);
            updateById(aptsApp);
        }
        if (flag) {
            return ResultGenerator.genSuccessResult("启动成功");
        }
        return ResultGenerator.genFailResult("启动失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public Result stopApp(Integer id) {
        log.info("执行stopApp");
        AptsApp aptsApp = selectById(id);
        shellUtil.stopFile(aptsApp.getFileName());
        aptsApp = new AptsApp();
        aptsApp.setId(id);
        aptsApp.setState(0);
        updateById(aptsApp);
        return ResultGenerator.genSuccessResult(aptsApp);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result saveApp(AptsApp aptsApp) {
        if (aptsApp.getId() == null) {
            String fileUrl = projectFiles + UUID.randomUUID().toString().replace("-", "");
            shellUtil.createDirectory(fileUrl);
            aptsApp.setFileUrl(fileUrl);
        }
        boolean flag = insertOrUpdate(aptsApp);
        return ResultGenerator.genSuccessResult(flag);
    }
}
