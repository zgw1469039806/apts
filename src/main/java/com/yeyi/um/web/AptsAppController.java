package com.yeyi.um.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yeyi.framework.core.BaseController;
import com.yeyi.framework.core.Result;
import com.yeyi.framework.core.ResultGenerator;
import com.yeyi.um.entity.AptsApp;
import com.yeyi.um.service.impl.AptsAppServiceImpl;
import com.yeyi.um.vo.AptsAppVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 应用表 前端控制器
 * </p>
 *
 * @author 张国伟
 * @since 2018-12-04
 */
@RestController
@RequestMapping("/aptsApp")
public class AptsAppController extends BaseController {

    @Autowired
    private AptsAppServiceImpl aptsAppService;

    @Value("${apts.aptsFile.jarFiles}")
    private String jarFiles;

    @ApiOperation("查看应用")
    @PostMapping("/getApp")
    public Result getApp(Integer id) {
        log.info("执行getApp");
        AptsApp aptsApp = aptsAppService.selectById(id);
        return ResultGenerator.genSuccessResult(aptsApp);
    }

    @ApiOperation("保存应用")
    @PostMapping("/saveApp")
    public Result saveApp(AptsAppVo aptsAppVo) {
        log.info("执行saveApp");
        AptsApp aptsApp = new AptsApp();
        BeanUtils.copyProperties(aptsAppVo, aptsApp);
        return aptsAppService.saveApp(aptsApp);
    }

    @ApiOperation("删除应用")
    @PostMapping("/deleteApp")
    public Result deleteApp(Integer id) {
        log.info("执行deleteApp");
        boolean flag = this.aptsAppService.deleteById(id);
        return ResultGenerator.genResult(flag);
    }

    @ApiOperation("查询应用列表")
    @PostMapping("/selectAppList")
    public Result selectAppList(AptsAppVo aptsAppVo) {
        log.info("执行selectAppList");
        Page page = new Page(aptsAppVo.getCurrent(), aptsAppVo.getSize());
        System.out.println(aptsAppVo.getName());
        Wrapper<AptsApp> wrapper = new EntityWrapper<AptsApp>().orderBy("create_time", false)
                .like("name", "%" + aptsAppVo.getName() + "%");
        page = aptsAppService.selectPage(page, wrapper);
        return ResultGenerator.genSuccessResult(page);
    }

    @ApiOperation("上传文件")
    @PostMapping("/uploadAppFile")
    public Result uploadAppFile(MultipartFile[] file) throws IOException {
        List<String> fileList = new ArrayList<>();
        for (MultipartFile item : file) {
            String fileName = System.currentTimeMillis() + ".jar";
            fileList.add(fileName);
            File filePath = new File(jarFiles + fileName);
            item.transferTo(filePath);
        }
        return ResultGenerator.genSuccessResult(fileList);
    }

    @ApiOperation("启动应用")
    @PostMapping("/startApp")
    public Result startApp(Integer id) {
        log.info("执行startApp");
        return aptsAppService.startApp(id);
    }

    @ApiOperation("停止应用")
    @PostMapping("/stopApp")
    public Result stopApp(Integer id) {
        log.info("执行stopApp");
        return aptsAppService.stopApp(id);
    }
}

