package com.hrh.kmanual.modules.controllers;

import com.hrh.kmanual.commons.dto.WangEditorImageInfo;
import com.hrh.kmanual.commons.utils.HttpJsonUtils;
import com.hrh.kmanual.commons.utils.Log4jUtils;
import com.hrh.kmanual.commons.utils.ReflectUtils;
import com.hrh.kmanual.modules.dao.entites.Attachment;
import com.hrh.kmanual.modules.dao.jpas.AttachmentRepository;
import com.hrh.kmanual.modules.services.AttachmentService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author huangrenhao
 * @date 2018/8/31
 */
@RequestMapping("Attachment")
@RestController
public class AttachmentController {

    public static final String VIEW_TYPE = "view";
    public static final String DOWNLOAD_TYPE = "download";

    private static final Set<String> OFFICE_SET;

    static {
        OFFICE_SET = new HashSet<>(6);
        OFFICE_SET.add("doc");
        OFFICE_SET.add("docx");
        OFFICE_SET.add("xls");
        OFFICE_SET.add("xlsx");
        OFFICE_SET.add("ppt");
        OFFICE_SET.add("pptx");

    }


    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private DocumentConverter documentConverter;

    @GetMapping("view/{id}")
    public void view(@PathVariable("id") Long id, String type, HttpServletResponse response) throws IOException, OfficeException {

        type = type == null ? VIEW_TYPE : DOWNLOAD_TYPE;

        Log4jUtils.getInstance(this.getClass()).info("查看文件:" + id);
        Attachment attachment = attachmentRepository.findOne(id);
        Log4jUtils.getInstance(this.getClass()).info("文件路径:" + attachment.getUrl());
        if (null != attachment) {

            String fileName = attachment.getOtherName() == null ? attachment.getName() : attachment.getOtherName() + "." + attachment.getType();

            File source = new File(attachment.getUrl());

            if (!source.exists()) {
                return;
            }

            // office 不存在则转换
            if (OFFICE_SET.contains(attachment.getType())) {
                String url = attachment.getUrl();
                String perFileName = url.substring(0, url.lastIndexOf("."));

                System.out.println(url);
                System.out.println(perFileName);
                perFileName += ".pdf";
                File target = new File(perFileName);
                if (!target.exists()) {
                    documentConverter.convert(source).to(target).execute();
                }
                source = target;
            }

            if (type.equals(DOWNLOAD_TYPE)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
                response.setCharacterEncoding("utf-8");
                if (StringUtils.isNoneBlank(attachment.getType())) {
                    response.setContentType("application/" + attachment.getType());
                } else {
                    response.setContentType("application/octet-stream");
                }
            }
            InputStream inputStream = new FileInputStream(source);
            IOUtils.copy(inputStream, response.getOutputStream());
            inputStream.close();
        }
    }

    @GetMapping("{id}/info")
    public Object getInfo(@PathVariable("id") Long id) {
        return attachmentRepository.findOne(id);
    }

    @PostMapping("{id}/update")
    public Object update(@PathVariable("id") Long id, Attachment attachment) {
        Attachment entity = attachmentRepository.findOne(id);
        if (entity == null) {
            return HttpJsonUtils.buildError("未找到该对象");
        }

        ReflectUtils.updateFieldByClass(attachment, entity);
        attachmentRepository.save(entity);

        return HttpJsonUtils.buildSuccess("更新成功！", entity);
    }

    @GetMapping("all")
    public Object list() {
        return attachmentRepository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
    }

    @PostMapping("save")
    public Object persist(@RequestParam List<MultipartFile> files, String key, String type) {
        return HttpJsonUtils.buildSuccess("保存成功！", attachmentService.persist(files, key, type));
    }

    @PostMapping("image/upload")
    public Object imageUpload(@RequestParam List<MultipartFile> files, String key, String type, HttpServletRequest request) {

        List<Attachment> list = attachmentService.persist(files, key, type);
        List<String> urlList = new ArrayList<>(list.size());
        for (Attachment temp : list) {
            urlList.add(request.getContextPath() + "/Attachment/view/" + temp.getId());
        }

        WangEditorImageInfo info = new WangEditorImageInfo();
        info.setErrno(0);
        info.setData(urlList);
        return info;
    }
}
