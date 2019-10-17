INSERT INTO HOURLY_WAGE VALUES('�ְ�','6000');
INSERT INTO HOURLY_WAGE VALUES('�߰�','12000');

INSERT INTO POSITION VALUES('1','����');
INSERT INTO POSITION VALUES('2','�޴���');
INSERT INTO POSITION VALUES('3','�Ϲ�����');

INSERT INTO BRANCH VALUES('1000001','����ø�����','����Ư���� ���빮�� ����ø��� ���������','02-1234-5678','2');
INSERT INTO BRANCH VALUES('1000002','������','����Ư���� ���빮�� ����','02-1234-5679','3');
INSERT INTO BRANCH VALUES('1000003','�ְ浿��','����Ư���� ���빮�� �ְ浿','02-1234-5680','4');

INSERT INTO EMPLOYEE VALUES ('100000119201','1','1000001','�赵��','010-2450-9884','�츮����','1002-543-646874','2014-04-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000119202','2','1000001','������','010-2401-9884','��ȯ����','1002-543-646873','2014-03-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000115603','3','1000001','�ڿ���','010-1405-9884','�츮����','1002-543-646872','2014-02-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000119204','1','1000002','�����','010-4405-9884','��������','1002-543-646874','2013-08-01',NULL,'Y');

INSERT INTO WORKING_RECORD (EMPLOYEE_NO, WORK_DATE, WORK_START_TIME, WORK_END_TIME, DAILY_WAGE) VALUES ('100000119201', '140601', '1200', '1300', '5000');
INSERT INTO WORKING_RECORD (EMPLOYEE_NO, WORK_DATE, WORK_START_TIME, WORK_END_TIME, DAILY_WAGE) VALUES ('100000119201', '140602', '1200', '1300', '5000');
INSERT INTO WORKING_RECORD (EMPLOYEE_NO, WORK_DATE, WORK_START_TIME, WORK_END_TIME, DAILY_WAGE) VALUES ('100000119202', '140530', '2000', '2100', '6000');

INSERT INTO SALARY (EMPLOYEE_NO, MONTH_NO, SALARY, SALARY_PAY_STATE) VALUES ('100000119201', '1406', '10000', 'N');
INSERT INTO SALARY (EMPLOYEE_NO, MONTH_NO, SALARY, SALARY_PAY_STATE) VALUES ('100000119202', '1405', '6000', 'N');

INSERT INTO LOGIN_INFORMATION (EMPLOYEE_NO, LOGIN_PASSWORD, BRANCH_NO) VALUES (100000119201, 'abc111', 1000001);
INSERT INTO LOGIN_INFORMATION (EMPLOYEE_NO, LOGIN_PASSWORD, BRANCH_NO) VALUES (100000119202, 'abc222', 1000001);
INSERT INTO LOGIN_INFORMATION (EMPLOYEE_NO, LOGIN_PASSWORD, BRANCH_NO) VALUES (100000115603, 'abc333', 1000001);
INSERT INTO LOGIN_INFORMATION (EMPLOYEE_NO, LOGIN_PASSWORD, BRANCH_NO) VALUES (100000119204, 'abc444', 1000002);

INSERT INTO ITEM VALUES('10000000','�ſ�����','����','1500','0',NULL);
INSERT INTO ITEM VALUES('10000001','�ſ�����','����','1500','0',NULL);
INSERT INTO ITEM VALUES('10000002','�����ǻ�','����','1500','0',NULL);
INSERT INTO ITEM VALUES('10000003','������ ����Ĩ','����','3000','0',NULL);
INSERT INTO ITEM VALUES('10000004','Ģ��','����','3000','0',NULL);
INSERT INTO ITEM VALUES('10000005','������','����','1000','0',NULL);
INSERT INTO ITEM VALUES('10000006','�Ŷ��','�������','1000','0',NULL);
INSERT INTO ITEM VALUES('10000007','��������','�������','1000','0',NULL);
INSERT INTO ITEM VALUES('10000008','���ĸ�����','�������','1050','0',NULL);
INSERT INTO ITEM VALUES('10000009','�Ҵߺ�����','�������','1050','0',NULL);
INSERT INTO ITEM VALUES('10000010','���������','�ַ�','1500','0',NULL);
INSERT INTO ITEM VALUES('10000011','���̽�(Fresh)','�ַ�','1300','0',NULL);
INSERT INTO ITEM VALUES('10000012','���̽�(Original)','�ַ�','1300','0',NULL);
INSERT INTO ITEM VALUES('10000013','���������','�ַ�','2500','0',NULL);
INSERT INTO ITEM VALUES('10000014','���� ����','�߰���','2000','0',NULL);
INSERT INTO ITEM VALUES('10000015','�ſ� ����','������ǰ','4000','0',NULL);
INSERT INTO ITEM VALUES('10000016','���ִ� ����','������ǰ','4000','0',NULL);
INSERT INTO ITEM VALUES('10000017','�ľ�� ���','����','1000','0',NULL);
INSERT INTO ITEM VALUES('10000018','�ٳ���','����','1500','0',NULL);
INSERT INTO ITEM VALUES('10000019','���� ũ������','������','4000','0',NULL);
INSERT INTO ITEM VALUES('10000020','��ũ����','������','1000','0',NULL);

INSERT INTO CUSTOMER VALUES (111,'��¯��',5,'����','010-0000-0000','1000');
INSERT INTO CUSTOMER VALUES (442,'����ȣ',42,'����','010-0000-0001','3000');
INSERT INTO CUSTOMER VALUES (123,'������',28,'����','010-0000-0002','2000');
INSERT INTO CUSTOMER VALUES (554,'������',29,'����','010-0000-0003','1000');
INSERT INTO CUSTOMER VALUES (245,'�豤��',27,'����','010-0000-0004','1234');
INSERT INTO CUSTOMER VALUES (116,'�ѱ���',28,'����','010-0000-0005','1212');
INSERT INTO CUSTOMER VALUES (557,'��ȿ��',27,'����','010-0000-0006','1215');
INSERT INTO CUSTOMER VALUES (123,'�����',28,'����','010-0000-0007','1098');
INSERT INTO CUSTOMER VALUES (542,'�����',29,'����','010-0000-0008','4966');
INSERT INTO CUSTOMER VALUES (128,'������',26,'����','010-0000-0009','11942');
INSERT INTO CUSTOMER VALUES (597,'������',23,'����','010-0000-0010','39');

INSERT INTO HOURLY_WAGE VALUES ('D', 5000);
INSERT INTO HOURLY_WAGE VALUES ('Y', 6000);

INSERT INTO INVENTORY (BRANCH_NO, ITEM_NO, ITEM_CODE, STORE_DATE, SHELF_LIFE, STORED_AMOUNT) VALUES ('1000001', '1000000120140101', '10000001', '20140201', '20150101', '11');
INSERT INTO INVENTORY (BRANCH_NO, ITEM_NO, ITEM_CODE, STORE_DATE, SHELF_LIFE, STORED_AMOUNT) VALUES ('1000001', '1000000220140102', '10000002', '20140202', '20150102', '22');
INSERT INTO INVENTORY (BRANCH_NO, ITEM_NO, ITEM_CODE, STORE_DATE, SHELF_LIFE, STORED_AMOUNT) VALUES ('1000001', '1000000320140103', '10000003', '20140203', '20150103', '33');
INSERT INTO INVENTORY (BRANCH_NO, ITEM_NO, ITEM_CODE, STORE_DATE, SHELF_LIFE, STORED_AMOUNT) VALUES ('1000001', '1000000420140104', '10000004', '20140204', '20150104', '44');
INSERT INTO INVENTORY (BRANCH_NO, ITEM_NO, ITEM_CODE, STORE_DATE, SHELF_LIFE, STORED_AMOUNT) VALUES ('1000001', '1000000520140105', '10000005', '20140205', '20150105', '55');
INSERT INTO INVENTORY (BRANCH_NO, ITEM_NO, ITEM_CODE, STORE_DATE, SHELF_LIFE, STORED_AMOUNT) VALUES ('1000002', '1000000520140105', '10000005', '20140205', '20150105', '55');
 
INSERT INTO SALE (SALE_NO, BRANCH_NO, CUSTOMER_NO, SALES_AMOUNT, SALE_TIME, SALE_VALIDATION) VALUES (2014050300000005, 1000001, 123, 50000, 140503150005, 'Y'); 
INSERT INTO SALE (SALE_NO, BRANCH_NO, CUSTOMER_NO, SALES_AMOUNT, SALE_TIME, SALE_VALIDATION) VALUES (2014060300000001, 1000001, 111, 1500, 140603110001, 'Y'); 
INSERT INTO SALE (SALE_NO, BRANCH_NO, CUSTOMER_NO, SALES_AMOUNT, SALE_TIME, SALE_VALIDATION) VALUES (2014060300000002, 1000001, NULL, 6000, 140603120002, 'Y'); 
INSERT INTO SALE (SALE_NO, BRANCH_NO, CUSTOMER_NO, SALES_AMOUNT, SALE_TIME, SALE_VALIDATION) VALUES (2014060300000003, 1000001, 116, 18000, 140603130003, 'Y'); 
INSERT INTO SALE (SALE_NO, BRANCH_NO, CUSTOMER_NO, SALES_AMOUNT, SALE_TIME, SALE_VALIDATION) VALUES (2014060300000004, 1000001, NULL, 36000, 140603140004, 'Y');

INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000001, 1000000120140101, 1000001, 1500, 1, 1500);
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000002, 1000000120140101, 1000001, 1500, 2, 3000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000002, 1000000220140102, 1000001, 1500, 2, 3000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000003, 1000000120140101, 1000001, 1500, 3, 4500); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000003, 1000000220140102, 1000001, 1500, 3, 4500); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000003, 1000000320140103, 1000001, 3000, 3, 9000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000004, 1000000120140101, 1000001, 1500, 4, 6000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000004, 1000000220140102, 1000001, 1500, 4, 6000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000004, 1000000320140103, 1000001, 3000, 4, 12000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000004, 1000000420140104, 1000001, 3000, 4, 12000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000005, 1000000120140101, 1000001, 1500, 5, 7500); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000005, 1000000220140102, 1000001, 1500, 5, 7500); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000005, 1000000320140103, 1000001, 3000, 5, 15000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000005, 1000000420140104, 1000001, 3000, 5, 15000); 
INSERT INTO SALE_LIST (SALE_NO, ITEM_NO, BRANCH_NO, ITEM_UNIT_PRICE, SALE_QUANTITY, SALE_SUM) VALUES (2014060100000005, 1000000520140105, 1000001, 1000, 5, 5000); 

INSERT INTO RETURN_LIST (RETURN_NO, BRANCH_NO, ITEM_NO, RETURN_AMOUNT, RETURN_DATE, RETURN_REASON, RETURN_STATE, INVENTORY_APPLY_STATE) VALUES ('2014020100001', '1000001', '1000000120140101', '10', '20140201', '��ǰ �ļ�', '��ǰ �Ϸ�', 'Y')
INSERT INTO RETURN_LIST (RETURN_NO, BRANCH_NO, ITEM_NO, RETURN_AMOUNT, RETURN_DATE, RETURN_REASON, RETURN_STATE, INVENTORY_APPLY_STATE) VALUES ('2014020100002', '1000001', '1000000220140102', '20', '20140201', '���翡�� ȸ�� ��û', '��ǰ �Ϸ�', 'Y')
