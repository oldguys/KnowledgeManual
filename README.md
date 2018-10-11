# KnowledgeManual
知识管理手册

#### 功能描述: 
用于日常的知识点整理：包括 链接 , 文档 , 附件。

--- 
### 环境:
+ `SpringBoot 1.5.15.RELEASE` `thymeleaf` `Spring-data-jpa 1.11` `druid` `jodconverter`
+ `boostrap 3 ` `boostrap-table` `boostrap-treeview` `boostrap-fileinput` `WangEidtor` 
+ `tomcat 8.0.29`

---
### 功能:
+ druid Monitor 监控
+ 按 标识 分类链接 + 分类统计 + crud 
+ 按 标识/目录 分类文档 + 分类统计 + crud
+ 树型可排序目录
+ 字典表管理(标识控制,目录类别控制,前端界面图标配置)
+ 附件表管理 + 附件下载 + 预览（office文档第一次 使用`OpenOffice4`转换为 PDF）
+ 文档集管理（维护多篇相关联文档）
+ peoperty文件 配置图标样式（为跟换主题功能扩展做铺垫）
+ 基于注解配置 `ORM` 对象间关联关系 + 基于反射更新表单值，避免对`Hibernate游离态`对象进行误操作
