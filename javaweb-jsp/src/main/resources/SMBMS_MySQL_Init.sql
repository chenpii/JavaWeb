CREATE DATABASE smbms;

USE smbms;

DROP TABLE IF EXISTS smbms_address;

CREATE TABLE smbms_address (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����ID',
contact varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��ϵ������',
addressDesc varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '�ջ���ַ��ϸ',
postCode varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '�ʱ�',
tel varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��ϵ�˵绰',
createdBy bigint(20) DEFAULT NULL COMMENT '������',
creationDate datetime DEFAULT NULL COMMENT '����ʱ��',
modifyBy bigint(20) DEFAULT NULL COMMENT '�޸���',
modifyDate datetime DEFAULT NULL COMMENT '�޸�ʱ��',
userId bigint(20) DEFAULT NULL COMMENT '�û�ID',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into smbms_address(id,contact,addressDesc,postCode,tel,createdBy,creationDate,modifyBy,modifyDate,userId) values (1,'����','�����ж�������������44��','100010','13678789999',1,'2016-04-13 00:00:00',NULL,NULL,1),(2,'�ź���','�����к����������3��','100000','18567672312',1,'2016-04-13 00:00:00',NULL,NULL,1),(3,'��־ǿ','�����ж����������ݺ��23��','100021','13387906742',1,'2016-04-13 00:00:00',NULL,NULL,1),(4,'��ӱ','�����г������������ϴ��14��','100053','13568902323',1,'2016-04-13 00:00:00',NULL,NULL,2),(5,'���','�����������������·������3��','100032','18032356666',1,'2016-04-13 00:00:00',NULL,NULL,3),(6,'����ǿ','������˳��������Ӫ�����ҵ��18��','100061','13787882222',1,'2016-04-13 00:00:00',NULL,NULL,3);

DROP TABLE IF EXISTS smbms_bill;

CREATE TABLE smbms_bill (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����ID',
billCode varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '�˵�����',
productName varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��Ʒ����',
productDesc varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��Ʒ����',
productUnit varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��Ʒ��λ',
productCount decimal(20,2) DEFAULT NULL COMMENT '��Ʒ����',
totalPrice decimal(20,2) DEFAULT NULL COMMENT '��Ʒ�ܶ�',
isPayment int(10) DEFAULT NULL COMMENT '�Ƿ�֧����1��δ֧�� 2����֧����',
createdBy bigint(20) DEFAULT NULL COMMENT '�����ߣ�userId��',
creationDate datetime DEFAULT NULL COMMENT '����ʱ��',
modifyBy bigint(20) DEFAULT NULL COMMENT '�����ߣ�userId��',
modifyDate datetime DEFAULT NULL COMMENT '����ʱ��',
providerId bigint(20) DEFAULT NULL COMMENT '��Ӧ��ID',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into smbms_bill(id,billCode,productName,productDesc,productUnit,productCount,totalPrice,isPayment,createdBy,creationDate,modifyBy,modifyDate,providerId) values (2,'BILL2016_002','��������ҩ��','����Ʒ-����','��','1000.00','10000.00',2,1,'2016-03-23 04:20:40',NULL,NULL,13),(3,'BILL2016_003','����','ʳƷ-ʳ����','��','300.00','5890.00',2,1,'2014-12-14 13:02:03',NULL,NULL,6),(4,'BILL2016_004','�����','ʳƷ-����ʳ����','��','200.00','9800.00',2,1,'2013-10-10 03:12:13',NULL,NULL,7),(5,'BILL2016_005','ϴ�ྫ','����Ʒ-�������','ƿ','500.00','7000.00',2,1,'2014-12-14 13:02:03',NULL,NULL,9),(6,'BILL2016_006','����������','ʳƷ-���','��','300.00','5000.00',2,1,'2016-04-14 06:08:09',NULL,NULL,4),(7,'BILL2016_007','��ԡҺ������','����Ʒ-��ԡ��','ƿ','500.00','23000.00',1,1,'2016-07-22 10:10:22',NULL,NULL,14),(8,'BILL2016_008','���������','����Ʒ-�����þ�','��','600.00','6000.00',2,1,'2016-04-14 05:12:13',NULL,NULL,14),(9,'BILL2016_009','���ϱ�','����Ʒ-����','��','350.00','1750.00',2,1,'2016-02-04 11:40:20',NULL,NULL,14),(10,'BILL2016_010','���꽴','ʳƷ-����','ƿ','200.00','2000.00',2,1,'2013-10-29 05:07:03',NULL,NULL,8),(11,'BILL2016_011','��֮��','����-����','ƿ','50.00','10000.00',1,1,'2016-04-14 16:16:00',NULL,NULL,1),(12,'BILL2016_012','֥��ʿ','����-���','ƿ','20.00','6000.00',1,1,'2016-09-09 17:00:00',NULL,NULL,1),(13,'BILL2016_013','���Ǻ����Ѿ�','����-���','ƿ','60.00','800.00',2,1,'2016-11-14 15:23:00',NULL,NULL,1),(14,'BILL2016_014','̩������','ʳƷ-����','��','400.00','5000.00',2,1,'2016-10-09 15:20:00',NULL,NULL,3),(15,'BILL2016_015','��������','ʳƷ-����','��','600.00','4000.00',2,1,'2016-11-14 14:00:00',NULL,NULL,3),(16,'BILL2016_016','�ɿڿ���','����','ƿ','2000.00','6000.00',2,1,'2012-03-27 13:03:01',NULL,NULL,2),(17,'BILL2016_017','����','����','ƿ','1500.00','4500.00',2,1,'2016-05-10 12:00:00',NULL,NULL,2),(18,'BILL2016_018','�۹���','����','ƿ','2000.00','4000.00',2,1,'2015-11-24 15:12:03',NULL,NULL,2);

DROP TABLE IF EXISTS smbms_provider;

CREATE TABLE smbms_provider (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����ID',
proCode varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��Ӧ�̱���',
proName varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��Ӧ������',
proDesc varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��Ӧ����ϸ����',
proContact varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��Ӧ����ϵ��',
proPhone varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��ϵ�绰',
proAddress varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��ַ',
proFax varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '����',
createdBy bigint(20) DEFAULT NULL COMMENT '�����ߣ�userId��',
creationDate datetime DEFAULT NULL COMMENT '����ʱ��',
modifyDate datetime DEFAULT NULL COMMENT '����ʱ��',
modifyBy bigint(20) DEFAULT NULL COMMENT '�����ߣ�userId��',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into smbms_provider(id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyDate,modifyBy) values (1,'BJ_GYS001','������ľ����ó���޹�˾','���ں�����飬��Ӫ��Ʒ:ę́������Һ���ɾơ��ƹ�ơ������Ͻѡ���é�ơ�������Ƶ�','�Ź�ǿ','13566667777','�����з�̨������԰��·','010-58858787',1,'2013-03-21 16:52:07',NULL,NULL),(2,'HB_GYS001','ʯ��ׯ˧��ʳƷó�����޹�˾','���ں�����飬��Ӫ��Ʒ:���ϡ�ˮ���ϡ�ֲ�ﵰ�����ϡ�����ʳƷ����֭���ϡ��������ϵ�','����','13309094212','�ӱ�ʡʯ��ׯ�»���','0311-67738876',1,'2016-04-13 04:20:40',NULL,NULL),(3,'GZ_GYS001','������̩����ҵ���޹�˾','���κ�����飬��Ӫ��Ʒ�����ǽ�����,�������׵�','֣���','13402013312','�㶫ʡ�����и��������ϴ��6006�������','0755-67776212',1,'2014-03-21 16:56:07',NULL,NULL),(4,'GZ_GYS002','������ϲ������ó���޹�˾','���ں�����飬��Ӫ��Ʒ���������.�����۽�.��Ȼ����.Ӫ������.��ɫ��ʳ.����ʳƷ.��ζ��ʳ.�⸬��','����','18599897645','�㶫ʡ�����и�����ҵ��B2��3¥��','0755-67772341',1,'2013-03-22 16:52:07',NULL,NULL),(5,'JS_GYS001','�˻�������ζƷ��','���ں�����飬��Ӫ��Ʒ����Ȼ�����ϡ����������ϵ�ζ��','�����','13754444221','����ʡ�˻����ֺ���ҵ��','0523-21299098',1,'2015-11-22 16:52:07',NULL,NULL),(6,'BJ_GYS002','�����ɸ���ʳ�������޹�˾','���ں�����飬��Ӫ��Ʒ��ɽ���͡����͡������͡�����͵�','��ݺ','13422235678','�����г������齭�۾�1��¥','010-588634233',1,'2012-03-21 17:52:07',NULL,NULL),(7,'BJ_GYS003','��������ʳ�������޹�˾','���κ�����飬��Ӫ��Ʒ�������͡����͡�Сĥ�͵�','����','13344441135','�����������Ƶ꿪����','010-588134111',1,'2016-04-13 00:00:00',NULL,NULL),(8,'ZJ_GYS001','��Ϫ�й����ɫʳƷ��','���ں�����飬��Ӫ��Ʒ�����꽴���ƶ��������潴�������������ũ��Ʒ','Ѧʥ��','18099953223','�㽭ʡ�����д�Ϫ����С����','0574-34449090',1,'2013-11-21 06:02:07',NULL,NULL),(9,'GX_GYS001','�Ű���ó���޹�˾','���ں�����飬��Ӫ��Ʒ���ջ���Ʒ','������','13323566543','����������������42-1��','0771-98861134',1,'2013-03-21 19:52:07',NULL,NULL),(10,'JS_GYS002','�Ͼ���ͷ����Ϣ�������޹�˾','���ں�����飬��Ӫ��Ʒ������ֳ��ߵ�','��Ůʿ','13098992113','����ʡ�Ͼ����ֿ����ֿڴ��1���³��ܲ�����A��903��','025-86223345',1,'2013-03-25 16:52:07',NULL,NULL),(11,'GZ_GYS003','�����а��������������Ʒ��','���ں�����飬��Ӫ��Ʒ�����ല�桢���桢���桢������ͷ��ͷ���','����','13562276775','�����а���������̶����·20��','020-85542231',1,'2016-12-21 06:12:17',NULL,NULL),(12,'BJ_GYS004','����¡ʢ�ջ��Ƽ�','���ں�����飬��Ӫ��Ʒ���ջ�������ϴ�����Ҿ�ϴ��ר����ϴ����Ʒ����ǽ���ù����ǽ��ù���������','����','13689865678','�����д������ɹ�','010-35576786',1,'2014-11-21 12:51:11',NULL,NULL),(13,'SD_GYS001','ɽ�����˻������Ϸ�չ���޹�˾','���ں�����飬��Ӫ��Ʒ��ϴ����ϴ�·ۡ�ϴ��Һ��ϴ�ྫ����ɱ�ࡢ�����','���ת','13245468787','ɽ�������ñ���ҵ���ʺͽ�21��','0531-53362445',1,'2015-01-28 10:52:07',NULL,NULL),(14,'JS_GYS003','����ϲԴ������','���ں�����飬��Ӫ��Ʒ���ջ�Ʒ����','��һ��','18567674532','��������ʢ����·','0510-32274422',1,'2016-04-23 11:11:11',NULL,NULL),(15,'ZJ_GYS002','�ְ�����Ʒ��','���ں�����飬��Ӫ��Ʒ�������С��ߵ����ϱ��������ֿ�ˮ�����ܷⱭ�������ʱ������ʺУ�����汭����Ʒ��','������','13212331567','�㽭ʡ�����������嶫·','0579-34452321',1,'2016-08-22 10:01:30',NULL,NULL);

DROP TABLE IF EXISTS smbms_role;

CREATE TABLE smbms_role (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����ID',
roleCode varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��ɫ����',
roleName varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��ɫ����',
createdBy bigint(20) DEFAULT NULL COMMENT '������',
creationDate datetime DEFAULT NULL COMMENT '����ʱ��',
modifyBy bigint(20) DEFAULT NULL COMMENT '�޸���',
modifyDate datetime DEFAULT NULL COMMENT '�޸�ʱ��',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into smbms_role(id,roleCode,roleName,createdBy,creationDate,modifyBy,modifyDate) values (1,'SMBMS_ADMIN','ϵͳ����Ա',1,'2016-04-13 00:00:00',NULL,NULL),(2,'SMBMS_MANAGER','����',1,'2016-04-13 00:00:00',NULL,NULL),(3,'SMBMS_EMPLOYEE','��ͨԱ��',1,'2016-04-13 00:00:00',NULL,NULL);

DROP TABLE IF EXISTS smbms_user;

CREATE TABLE smbms_user (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����ID',
userCode varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '�û�����',
userName varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '�û�����',
userPassword varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '�û�����',
gender int(10) DEFAULT NULL COMMENT '�Ա�1:Ů�� 2:�У�',
birthday date DEFAULT NULL COMMENT '��������',
phone varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '�ֻ�',
address varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '��ַ',
userRole bigint(20) DEFAULT NULL COMMENT '�û���ɫ��ȡ�Խ�ɫ��-��ɫid��',
createdBy bigint(20) DEFAULT NULL COMMENT '�����ߣ�userId��',
creationDate datetime DEFAULT NULL COMMENT '����ʱ��',
modifyBy bigint(20) DEFAULT NULL COMMENT '�����ߣ�userId��',
modifyDate datetime DEFAULT NULL COMMENT '����ʱ��',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into smbms_user(id,userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate,modifyBy,modifyDate) values (1,'admin','ϵͳ����Ա','1234567',1,'1983-10-10','13688889999','�����к������ɸ�·207��',1,1,'2013-03-21 16:52:07',NULL,NULL),(2,'liming','����','0000000',2,'1983-12-10','13688884457','�����ж�����ǰ�Ŷ����9��',2,1,'2014-12-31 19:52:09',NULL,NULL),(5,'hanlubiao','��·��','0000000',2,'1984-06-05','18567542321','�����г�������������12��',2,1,'2014-12-31 19:52:09',NULL,NULL),(6,'zhanghua','�Ż�','0000000',1,'1983-06-15','13544561111','�����к�����ѧԺ·61��',3,1,'2013-02-11 10:51:17',NULL,NULL),(7,'wangyang','����','0000000',2,'1982-12-31','13444561124','�����к�����������Ի͹���16��',3,1,'2014-06-11 19:09:07',NULL,NULL),(8,'zhaoyan','����','0000000',1,'1986-03-07','18098764545','�����к�����������С��10��¥',3,1,'2016-04-21 13:54:07',NULL,NULL),(10,'sunlei','����','0000000',2,'1981-01-04','13387676765','�����г�������ׯ����С��12¥',3,1,'2015-05-06 10:52:07',NULL,NULL),(11,'sunxing','����','0000000',2,'1978-03-12','13367890900','�����г������������ϴ��10��',3,1,'2016-11-09 16:51:17',NULL,NULL),(12,'zhangchen','�ų�','0000000',1,'1986-03-28','18098765434','��������ׯ·�ڱ����ְ�������13��¥',3,1,'2016-08-09 05:52:37',1,'2016-04-14 14:15:36'),(13,'dengchao','�˳�','0000000',2,'1981-11-04','13689674534','�����к�������������Ժ10��¥',3,1,'2016-07-11 08:02:47',NULL,NULL),(14,'yangguo','���','0000000',2,'1980-01-01','13388886623','�����г�������Է��԰����԰20��¥',3,1,'2015-02-01 03:52:07',NULL,NULL),(15,'zhaomin','����','0000000',1,'1987-12-04','18099897657','�����в�ƽ����ͨԷ3��12��¥',2,1,'2015-09-12 12:02:12',NULL,NULL);