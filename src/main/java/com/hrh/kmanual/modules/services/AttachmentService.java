package com.hrh.kmanual.modules.services;

import com.hrh.kmanual.commons.utils.DateTimeUtils;
import com.hrh.kmanual.modules.dao.entites.Attachment;
import com.hrh.kmanual.modules.dao.jpas.AttachmentRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author huangrenhao
 * @date 2018/8/31
 */
@Service
public class AttachmentService {


    @Value("${km.configs.upload-files-path}")
    private String FILES_PATH;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional(rollbackOn = {RuntimeException.class, IOException.class})
    public List<Attachment> persist(List<MultipartFile> list,String key ,String type) {
        List<Attachment> attachments = new ArrayList<>(list.size());
        Random random = new Random();
        String parentPath = FILES_PATH + "//" + DateTimeUtils.getCurrentDate() + "//";
        // 初始化父级文件夹
        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }

        for (MultipartFile file : list) {

            Attachment attachment = new Attachment();
            String path = parentPath + System.currentTimeMillis() + "-" + random.nextInt(1000000);
            String uploadFileName = file.getOriginalFilename();
            if (uploadFileName.contains(".")) {
                String fileType = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1, uploadFileName.length());
                attachment.setType(fileType);
                path += "." + fileType;
            }
            attachment.setName(uploadFileName);
            attachment.setUrl(path);
            attachment.setCreateTime(new Date());
            attachment.setFileType(type);
            attachment.setAsoKey(key);

            //文档附件（附件+富文本图片）
            if(Attachment.AttachmentType.KnowledgeFile.getType().equals(type)
                    ||Attachment.AttachmentType.KnowledgeImage.getType().equals(type)){
                attachment.setStatus(0);
            }else{
                attachment.setStatus(1);
            }

            // 持久化
            File tempFile = new File(path);
            try {
                file.transferTo(tempFile);
                attachments.add(attachment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        attachmentRepository.save(attachments);
        return attachments;
    }
}
