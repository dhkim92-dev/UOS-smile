INSERT INTO HOURLY_WAGE VALUES('주간','6000');
INSERT INTO HOURLY_WAGE VALUES('야간','12000');

INSERT INTO POSITION VALUES('1','점장');
INSERT INTO POSITION VALUES('2','임시직원');
INSERT INTO POSITION VALUES('3','정직원');

INSERT INTO BRANCH VALUES('1000001','서울시립대점','서울특별시 동대문구 서울시립대 정보기술관','02-1234-5678','2');
INSERT INTO BRANCH VALUES('1000002','전농점','서울특별시 동대문구 전농동','02-1234-5679','3');
INSERT INTO BRANCH VALUES('1000003','휘경동점','서울특별시 동대문구 휘경동','02-1234-5680','4');

INSERT INTO EMPLOYEE VALUES ('100000119200','1','1000001','김도훈','010-2450-9884','우리은행','1002-543-646874','2014-04-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000119204','2','1000001','성예담','010-2401-9884','외환은행','1002-543-646873','2014-03-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000115601','2','1000001','박요한','010-1405-9884','우리은행','1002-543-646872','2014-02-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000119204','3','1000001','김가나','010-1234-5678','국민은행','1002-543-646876','2014-01-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000115601','3','1000001','김다라','010-3465-9504','외환은행','1002-543-646875','2013-09-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000119200','1','1000002','이춘식','010-4405-9884','국민은행','1002-543-646874','2013-08-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000119204','2','1000002','김삿갓','010-5405-9844','우리은행','1002-543-646873','2013-07-01',NULL,'N');
INSERT INTO EMPLOYEE VALUES ('100000115601','2','1000002','사오정','010-6405-9824','우리은행','1002-543-646872','2013-06-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000119204','3','1000002','저팔계','010-7405-9814','외환은행','1002-543-646873','2014-05-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000115601','3','1000002','손오공','010-8405-9684','우리은행','1002-543-646872','2014-04-01',NULL,'N');
INSERT INTO EMPLOYEE VALUES ('100000119200','1','1000003','왕진지','010-9405-9822','국민은행','1002-543-646874','2014-03-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000119204','2','1000003','윤고딕','010-1205-9284','우리은행','1002-543-646873','2014-02-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000115601','2','1000003','네이버','010-2465-6884','외환은행','1002-543-646872','2014-01-01',NULL,'N');
INSERT INTO EMPLOYEE VALUES ('100000119204','3','1000003','정기관','010-2446-9004','국민은행','1002-543-646873','2013-09-01',NULL,'Y');
INSERT INTO EMPLOYEE VALUES ('100000115601','3','1000003','시립대','010-5605-9800','우리은행','1002-543-646872','2013-08-01',NULL,'Y');

INSERT INTO ITEM VALUES('10000000','매운새우깡','과자','1500','0',NULL);
INSERT INTO ITEM VALUES('10000001','매운새우깡','과자','1500','0',NULL);
INSERT INTO ITEM VALUES('10000002','옛날건빵','과자','1500','0',NULL);
INSERT INTO ITEM VALUES('10000003','촉촉한 초코칩','과자','3000','0',NULL);
INSERT INTO ITEM VALUES('10000004','칙촉','과자','3000','0',NULL);
INSERT INTO ITEM VALUES('10000005','오감자','과자','1000','0',NULL);
INSERT INTO ITEM VALUES('10000006','신라면','유탕면류','1000','0',NULL);
INSERT INTO ITEM VALUES('10000007','새우탕면','유탕면류','1000','0',NULL);
INSERT INTO ITEM VALUES('10000008','무파마탕면','유탕면류','1050','0',NULL);
INSERT INTO ITEM VALUES('10000009','불닭볶음면','유탕면류','1050','0',NULL);
INSERT INTO ITEM VALUES('10000010','버드와이저','주류','1500','0',NULL);
INSERT INTO ITEM VALUES('10000011','참이슬(Fresh)','주류','1300','0',NULL);
INSERT INTO ITEM VALUES('10000012','참이슬(Original)','주류','1300','0',NULL);
INSERT INTO ITEM VALUES('10000013','버드와이저','주류','2500','0',NULL);
INSERT INTO ITEM VALUES('10000014','안주 땅콩','견과류','2000','0',NULL);
INSERT INTO ITEM VALUES('10000015','매운 육포','건조식품','4000','0',NULL);
INSERT INTO ITEM VALUES('10000016','맛있는 육포','건조식품','4000','0',NULL);
INSERT INTO ITEM VALUES('10000017','씻어나온 사과','과일','1000','0',NULL);
INSERT INTO ITEM VALUES('10000018','바나나','과일','1500','0',NULL);
INSERT INTO ITEM VALUES('10000019','구구 크러스터','빙과류','4000','0',NULL);
INSERT INTO ITEM VALUES('10000020','스크류바','빙과류','1000','0',NULL);

INSERT INTO CUSTOMER VALUES (111,'신짱구',5,'남성','010-0000-0000','1000');
INSERT INTO CUSTOMER VALUES (442,'박찬호',42,'남성','010-0000-0001','3000');
INSERT INTO CUSTOMER VALUES (123,'류현진',28,'남성','010-0000-0002','2000');
INSERT INTO CUSTOMER VALUES (554,'윤석민',29,'남성','010-0000-0003','1000');
INSERT INTO CUSTOMER VALUES (245,'김광현',27,'남성','010-0000-0004','1234');
INSERT INTO CUSTOMER VALUES (116,'한기주',28,'남성','010-0000-0005','1212');
INSERT INTO CUSTOMER VALUES (557,'한효주',27,'여성','010-0000-0006','1215');
INSERT INTO CUSTOMER VALUES (123,'김범수',28,'남성','010-0000-0007','1098');
INSERT INTO CUSTOMER VALUES (542,'임재범',29,'남성','010-0000-0008','4966');
INSERT INTO CUSTOMER VALUES (128,'박정현',26,'여성','010-0000-0009','11942');
INSERT INTO CUSTOMER VALUES (597,'이지은',23,'여성','010-0000-0010','39');

INSERT INTO HOURLY_WAGE VALUES ('D', 5000);
INSERT INTO HOURLY_WAGE VALUES ('Y', 6000);

