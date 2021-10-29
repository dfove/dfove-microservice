--------------------------------------------------------
--  文件已创建 - 星期四-七月-01-2021   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table TB_MENU
--------------------------------------------------------

  CREATE TABLE "JNYC"."TB_MENU" 
   (	"MENU_ID" VARCHAR2(64 BYTE), 
	"PARENT_ID" VARCHAR2(64 BYTE), 
	"MENU_NAME" VARCHAR2(45 BYTE), 
	"MENU_URL" VARCHAR2(1024 BYTE), 
	"MENU_REMARK" VARCHAR2(512 BYTE), 
	"MENU_TYPE" NUMBER(11,0), 
	"MENU_ORDER" NUMBER(11,0), 
	"MENU_IMG" VARCHAR2(1024 BYTE), 
	"COMPONENT_URL" VARCHAR2(255 BYTE), 
	"UPDATETIME" DATE DEFAULT sysdate
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DFOVE" ;

   COMMENT ON COLUMN "JNYC"."TB_MENU"."MENU_ID" IS '主键';
   COMMENT ON COLUMN "JNYC"."TB_MENU"."PARENT_ID" IS '父id';
   COMMENT ON COLUMN "JNYC"."TB_MENU"."MENU_NAME" IS '菜单名称';
   COMMENT ON COLUMN "JNYC"."TB_MENU"."MENU_URL" IS '菜单链接';
   COMMENT ON COLUMN "JNYC"."TB_MENU"."MENU_REMARK" IS '备注';
   COMMENT ON COLUMN "JNYC"."TB_MENU"."MENU_TYPE" IS '菜单类型';
   COMMENT ON COLUMN "JNYC"."TB_MENU"."MENU_ORDER" IS '排序,大的在后面';
   COMMENT ON COLUMN "JNYC"."TB_MENU"."MENU_IMG" IS '菜单图标';
   COMMENT ON COLUMN "JNYC"."TB_MENU"."COMPONENT_URL" IS '组件路径';
REM INSERTING into JNYC.TB_MENU
SET DEFINE OFF;
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('08a13f7e9bdd4f3cbf648d31a850f6ae','53b83412dc4d4edf964b76beaf7eaa6e','添加物流寄递企业档案','/data-collection/addEnterpriseArchive',null,3,20,null,'/data-collection/component/addEnterpriseArchive',to_date('2021-06-02 16:23:52','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('7c04493a9bcd421599ae2e0f96a17ddd','87b189a1ae734e13ad3bc12c6e79bd57','监控任务','/monitoring/monitorTask',null,1,1,null,'/monitoring/index',to_date('2021-06-04 18:46:20','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('f1e376b0d6d44386a28dbb9369931fc5','5189817e06ec48cb934d3b95a7a5d83b','中间库详情','/analyse/middleLibraryDetails',null,3,20,null,'/analyse/component/middleLibraryDetails',to_date('2021-06-08 11:58:43','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('72123e127e4d4316bdc6bd2b1d5defff','3b8f3ba685d24a81b10ae45f43bf1f54','案件资料详情','/data-collection/caseDetail/:id',null,3,20,null,'/data-collection/component/addDatabase',to_date('2021-06-08 11:55:41','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('5189817e06ec48cb934d3b95a7a5d83b','8d8612fcf84c4e558bb6a260ce894b38','中间库','/analyse/middleLibrary',null,1,20,null,'/analyse/index',to_date('2021-06-08 11:58:02','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('c321271ec12f4010b3e3f59c9f80f58b','5189817e06ec48cb934d3b95a7a5d83b','中间库列表','/analyse/middleLibrary',null,3,20,null,'/analyse/component/middleLibrary',to_date('2021-06-08 12:03:40','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('4fba3d9b09ea4009be8bbd0c2493cbf1','17b07a6be3414dd0b07bd2f9d86a2fa1','用户管理','/systemManage/organizationManage',null,1,1,'group1/M00/00/00/wKgfCGDRjauAfrcfAACS26NpYxo31.jpeg','/systemManage/component/organizationManage',to_date('2021-06-08 18:06:02','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('21f2cdda46a34bd1b453f1f7e492f4ac','17b07a6be3414dd0b07bd2f9d86a2fa1','角色管理','/systemManage/role',null,1,3,null,'/systemManage/component/role',to_date('2021-06-08 18:07:18','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('c6e636c0dd2547eb8ed021a0e7125974','17b07a6be3414dd0b07bd2f9d86a2fa1','图片管理','/systemManage/imageManage',null,1,22,null,'/systemManage/component/imageManage',to_date('2021-06-08 18:08:40','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('1edd15037ae64893ac70bd50312b8e54','0','态势分析','/situation',null,1,1,null,'/situational',to_date('2021-06-08 18:14:39','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('5eee9988839143fba35bbee9d759e225','dd9d107e620048419d7f4f02b4880032','稽查反馈列表','/clueCollection/clueCollectionList',null,1,1,null,'/clueCollection/component/clueCollection',to_date('2021-06-04 17:47:23','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('6c27a2887dd24122814fbc574e27b267','dd9d107e620048419d7f4f02b4880032','添加线索','/clueCollection/addClue',null,3,20,null,'/clueCollection/component/addClue',to_date('2021-06-04 17:48:22','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('8ed2850f847e4d9c843ac185fe46d7ff','dd9d107e620048419d7f4f02b4880032','稽查反馈详情','/clueCollection/clueCollectionDetails',null,3,20,null,'/clueCollection/component/clueCollectionDetails',to_date('2021-06-04 17:49:28','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('c97300f2a4e9445a982d092d2022c3da','3b8f3ba685d24a81b10ae45f43bf1f54','添加案件资料','/data-collection/addDatabase',null,3,20,null,'/data-collection/component/addDatabase',to_date('2021-06-04 18:17:40','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('260e98d2c01b4c0e9172ceacfd8a5800','3b8f3ba685d24a81b10ae45f43bf1f54','编辑案件资料','/data-collection/editDatabase/:id',null,3,20,null,'/data-collection/component/addDatabase',to_date('2021-06-04 18:35:19','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('8e37007522e444bdb421f3cc15b62c6f','69f30592e98445b5a28c5470f853d79f','寄递面单采集','/data-collection/gatherExpressWaybill',null,1,20,null,'/data-collection/component/gatherExpressWaybill',to_date('2021-06-04 18:36:27','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('d1093b7c6a3a4652b64052cd7310f4c4','69f30592e98445b5a28c5470f853d79f','涉案卷烟采集','/data-collection/gatherCigarette',null,1,20,null,'/data-collection/component/gatherCigarette',to_date('2021-06-04 18:37:06','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('8cca1755440447febb686adbb5f2cf26','0','通知管理','/report',null,2,99,null,'/report/index',to_date('2021-06-04 18:38:07','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('e562ccc8d44c441e90e40b501f3cd814','8cca1755440447febb686adbb5f2cf26','通知信息','/report/inform',null,1,20,null,'/report/component/inform',to_date('2021-06-04 18:38:58','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('cd89cb488d4241b5bd0c1554c6d5b2d4','0','全息档案','/archives',null,1,2,null,'/archives/index',to_date('2021-06-04 18:39:27','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('9e01645a22c749b99bef03bfbaa9a613','cd89cb488d4241b5bd0c1554c6d5b2d4','档案搜索','/archives/archivesSearch',null,1,1,null,'/archives/component/search',to_date('2021-06-04 18:40:49','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('c110745dbc634a7ca2a5e0a2583c7e2b','cd89cb488d4241b5bd0c1554c6d5b2d4','档案搜索列表','/archives/searchList',null,1,20,null,'/archives/component/searchList',to_date('2021-06-04 18:42:11','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('0d9a2cda1c2340c5bb5f5819f1d9bd09','cd89cb488d4241b5bd0c1554c6d5b2d4','档案列表','/archives/archivesList',null,1,20,null,'/archives/component/list',to_date('2021-06-04 18:42:47','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('ac6fc97abbf2481a9029ed59b237f15d','7c04493a9bcd421599ae2e0f96a17ddd','监控任务详情','/monitoring/monitoringDetails',null,3,20,null,'/monitoring/component/monitoringDetails',to_date('2021-06-04 18:47:26','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('812e6873b8c146b2b89e279f0301cfdb','87b189a1ae734e13ad3bc12c6e79bd57','目标监控','/monitoring/monitorTarget',null,1,8,null,'/monitoring/component/monitorTarget',to_date('2021-06-04 18:48:54','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('807cf78e973743a6a78b5ce881a0dde6','87b189a1ae734e13ad3bc12c6e79bd57','组合监控','/monitoring/monitorGroup',null,1,20,null,'/monitoring/component/monitorGroup',to_date('2021-06-04 18:49:39','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('3d707837620f47e3a7a6d0e0d01615c9','87b189a1ae734e13ad3bc12c6e79bd57','区域监控','/monitoring/monitorArea',null,1,20,null,'/monitoring/component/monitorArea',to_date('2021-06-04 18:50:18','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('f31413eaf2fa4e00b05d2516ac563016','87b189a1ae734e13ad3bc12c6e79bd57','目标互联监控','/monitoring/monitorTargetInternet',null,1,20,null,'/monitoring/component/monitorTargetInternet',to_date('2021-06-04 18:50:51','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('8d8612fcf84c4e558bb6a260ce894b38','0','寄递分析','/analyse',null,1,5,null,'/analyse/index',to_date('2021-06-04 18:51:45','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('b4243f55feef4387aeb15612dfe2adf3','8d8612fcf84c4e558bb6a260ce894b38','一键搜','/analyse/keySearch',null,1,1,null,'/analyse/component/keySearch',to_date('2021-06-04 18:52:22','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('2950f91e33e3496a9d5cccd1fa00b107','8d8612fcf84c4e558bb6a260ce894b38','批量查询','/analyse/batchQuery',null,1,20,null,'/analyse/component/batchQuery',to_date('2021-06-04 18:53:14','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('8979f7f6288143438444048f71329628','8d8612fcf84c4e558bb6a260ce894b38','精确搜索','/analyse/accurate',null,1,20,null,'/analyse/component/accurate',to_date('2021-06-04 18:53:39','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('441d4b257b7046c485c48b022f7a72a3','8d8612fcf84c4e558bb6a260ce894b38','数据分析','/analyse/analysis',null,1,20,null,'/analyse/component/analysis',to_date('2021-06-04 18:56:27','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('6ef6af6a56f44ee19d75ecef41d3552c','7c04493a9bcd421599ae2e0f96a17ddd','监控任务列表','/monitoring/monitorTask',null,3,1,null,'/monitoring/component/monitorTask',to_date('2021-06-17 11:41:23','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('17b07a6be3414dd0b07bd2f9d86a2fa1','0','系统管理','/systemManage',null,1,30,'group1/M00/00/00/wKgfCGDTB_SAXMuwAACp5_e5ezg864.jpg','/systemManage',to_date('2021-06-08 18:05:39','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('87648d0ad80a4776854df17b06d3bd70','8d8612fcf84c4e558bb6a260ce894b38','重点区域','/analyse/keyArea',null,1,20,null,'/toolManage/component/keyArea',to_date('2021-06-04 18:59:57','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('88bf0abb8d544e24919f2ad6729d112b','8d8612fcf84c4e558bb6a260ce894b38','涉烟关键词','/analyse/cant',null,1,20,null,'/toolManage/component/cant',to_date('2021-06-04 19:00:33','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('4df5f2726440455dbc58d7c19d69b53a','17b07a6be3414dd0b07bd2f9d86a2fa1','权限管理','/systemManage/privilegeManage',null,1,2,null,'/systemManage/component/privilegeManage',to_date('2021-06-08 18:06:25','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('b6bb5ee0391a4b9e9e2a578643a9ffd5','17b07a6be3414dd0b07bd2f9d86a2fa1','个人设置','/systemManage/userSetting',null,1,20,null,'/systemManage/component/userSetting',to_date('2021-06-08 18:07:39','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('835232b24ab24d499c6db52ca2e8aebe','17b07a6be3414dd0b07bd2f9d86a2fa1','数据字典','/systemManage/dictionaries',null,1,20,null,'/systemManage/component/dictionaries',to_date('2021-06-08 18:07:58','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('74e702b401e24b348c5369661e299db4','17b07a6be3414dd0b07bd2f9d86a2fa1','操作日志','/systemManage/logAudit',null,1,21,null,'/systemManage/component/logAudit',to_date('2021-06-08 18:08:19','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('ae3d7a3fa2dc4a6ca5a32cb4bf6fbc15','17b07a6be3414dd0b07bd2f9d86a2fa1','菜单管理2','/systemManage/menuManage',null,1,30,null,'/systemManage/component/menuManage2',to_date('2021-06-08 18:09:09','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('a5c7b8691a50478c8264a676114e2e03','17b07a6be3414dd0b07bd2f9d86a2fa1','角色管理2','/systemManage/roleManage',null,1,30,null,'/systemManage/component/roleMenu',to_date('2021-06-08 18:09:32','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('69f30592e98445b5a28c5470f853d79f','0','数据采集','/data-collection',null,1,6,null,'/data-collection/index',to_date('2021-06-02 16:23:52','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('53b83412dc4d4edf964b76beaf7eaa6e','69f30592e98445b5a28c5470f853d79f','物流寄递企业档案','/data-collection/enterpriseArchive',null,1,1,null,'/data-collection/index',to_date('2021-06-02 16:23:52','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('c0776e3af1ad4bae9e2f62ebeb427ae5','53b83412dc4d4edf964b76beaf7eaa6e','编辑物流寄递企业档案','/data-collection/editEnterpriseArchive/:id',null,3,20,null,'/data-collection/component/addEnterpriseArchive',to_date('2021-06-02 16:23:52','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('73e574fbe68247acb455217951d064a8','53b83412dc4d4edf964b76beaf7eaa6e','物流寄递企业档案详情','/data-collection/enterpriseArchiveDetail/:id',null,3,20,null,'/data-collection/component/addEnterpriseArchive',to_date('2021-06-02 16:23:52','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('dd9d107e620048419d7f4f02b4880032','0','稽查反馈','/clueCollection',null,1,20,null,'/clueCollection/index',to_date('2021-06-04 17:06:29','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('f7cc6a1a4b4846d1abac6526ea9469d9','53b83412dc4d4edf964b76beaf7eaa6e','监管信息','/data-collection/supervisionInfo',null,3,20,null,'/data-collection/component/supervisionInfo',to_date('2021-06-04 18:13:49','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('3b8f3ba685d24a81b10ae45f43bf1f54','69f30592e98445b5a28c5470f853d79f','案件资料库','/data-collection/caseDatabase',null,1,20,null,'/data-collection/index',to_date('2021-06-04 18:15:45','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('2ac5ee669dd44defbf75967aae91df52','cd89cb488d4241b5bd0c1554c6d5b2d4','档案详情','/archives/archivesDetails',null,3,20,null,'/archives/component/details',to_date('2021-06-04 18:44:50','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('87b189a1ae734e13ad3bc12c6e79bd57','0','监控中心','/monitoring',null,1,3,null,'/monitoring/index',to_date('2021-06-04 18:45:34','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('1bf52c8695a345a1a9e30bc02f3dacd5','8d8612fcf84c4e558bb6a260ce894b38','号码归属地','/analyse/numberAttribution',null,1,20,null,'/toolManage/component/numberAttribution',to_date('2021-06-07 16:34:33','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('e4d48674c7bc4261afea9c5c8a5bed8f','53b83412dc4d4edf964b76beaf7eaa6e','物流寄递企业档案列表','/data-collection/enterpriseArchive',null,3,1,null,'/data-collection/component/enterpriseArchive',to_date('2021-06-16 16:16:37','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('f27d004fc1824f0aa4363c4f4217239f','8d8612fcf84c4e558bb6a260ce894b38','行政区号','/analyse/urbanAreaCode',null,1,21,null,'/toolManage/component/urbanAreaCode',to_date('2021-06-07 16:35:08','yyyy-MM-dd hh24:mi:ss'));
Insert into JNYC.TB_MENU (MENU_ID,PARENT_ID,MENU_NAME,MENU_URL,MENU_REMARK,MENU_TYPE,MENU_ORDER,MENU_IMG,COMPONENT_URL,UPDATETIME) values ('8bc9f74fc4e24b7c9dbf5288d400f298','3b8f3ba685d24a81b10ae45f43bf1f54','案件资料库列表','/data-collection/caseDatabase',null,3,1,null,'/data-collection/component/caseDatabase',to_date('2021-06-16 20:32:43','yyyy-MM-dd hh24:mi:ss'));
--------------------------------------------------------
--  DDL for Index TB_MENU_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "JNYC"."TB_MENU_PK" ON "JNYC"."TB_MENU" ("MENU_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DFOVE" ;
--------------------------------------------------------
--  Constraints for Table TB_MENU
--------------------------------------------------------

  ALTER TABLE "JNYC"."TB_MENU" ADD CONSTRAINT "TB_MENU_PK" PRIMARY KEY ("MENU_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DFOVE"  ENABLE;
  ALTER TABLE "JNYC"."TB_MENU" MODIFY ("PARENT_ID" NOT NULL ENABLE);
  ALTER TABLE "JNYC"."TB_MENU" MODIFY ("MENU_ID" NOT NULL ENABLE);
