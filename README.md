# Sum_3rd_project

-- 회원
CREATE TABLE members (
	memId         VARCHAR2(30)  primary key,     
	memName       VARCHAR2(30)  NOT NULL,   
	memPassword   VARCHAR2(30)  NOT NULL,    
	email         VARCHAR2(60)  NOT NULL,     
	address       VARCHAR2(60)  NOT NULL,    
	hp            VARCHAR2(30)  NOT NULL   
 
);

insert into members values('alfos77','오미래','1234','alfos77@naver.com','경기도 시흥시','01077287934');


select * from members;



-- 회원
CREATE UNIQUE INDEX PK_members
	ON members ( -- 회원
		memid DESC -- 회원 아이디
	);



-- 상품

create table products (
    procd varchar2(60),
    pronm varchar2(60) NOT NULL,
    CONSTRAINT PK_PRO PRIMARY KEY(procd)
);


INSERT INTO products VALUES ('outer','겉옷');
INSERT INTO products VALUES ('top','상의');
INSERT INTO products VALUES ('bottom','하의');
INSERT INTO products VALUES ('dress','원피스');
INSERT INTO products VALUES ('shoesNacc','악세서리');
INSERT INTO products VALUES ('sale','세일품목');



-- 상품 내용
CREATE TABLE product(
	productNum   NUMBER,    
	productName  VARCHAR2(60),
        procd varchar2(60),     
	explan       VARCHAR2(500),    
	price        NUMBER DEFAULT 0,
        product_url  varchar2(500),
        CONSTRAINT PK_PRO2 PRIMARY KEY(productNum),
        CONSTRAINT FK_PRO2 FOREIGN KEY(procd) REFERENCES products(procd)
          
);




--color
create table colorsd (
	colors varchar2(30) primary key,
	colorname varchar2(30),
);

insert into colorsd values ('BLACK', 2);
insert into colorsd values ('RED', 3);
insert into colorsd values ('YELLOW', 4);
insert into colorsd values ('BLUE', 5);
insert into colorsd values ('CIELO', 6);
insert into colorsd values ('WHITE', 1);



--size
create table sizesize (
    sizename varchar2(30) not null,
    sizecd varchar2(30),
    sizenum varchar2(30),
    constraint PK_sizename PRIMARY KEY(sizename)
);


insert into sizesize values('XS', 'str_size', 'XS');
insert into sizesize values('S', 'str_size', 'S');
insert into sizesize values('M', 'str_size', 'M');
insert into sizesize values('L', 'str_size', 'L');
insert into sizesize values('XL', 'str_size', 'XL');
insert into sizesize values(215, 'num_size', 215);
insert into sizesize values(220, 'num_size', 220);
insert into sizesize values(225, 'num_size', 225);
insert into sizesize values(230, 'num_size', 230);
insert into sizesize values(235, 'num_size', 235);
insert into sizesize values(240, 'num_size', 240);
insert into sizesize values(245, 'num_size', 245);
insert into sizesize values(250, 'num_size', 250);
insert into sizesize values(255, 'num_size', 255);
insert into sizesize values(260, 'num_size', 260);
insert into sizesize values(265, 'num_size', 265);
insert into sizesize values(270, 'num_size', 270);



-- 장바구니
CREATE TABLE basket (
      basketNum   NUMBER,           
      memId       VARCHAR2(30),       
      productNum  NUMBER,     
      quantity    NUMBER,
      colors      VARCHAR2(30),
      sizename    VARCHAR2(30),
      CONSTRAINT PK_basket PRIMARY KEY(basketNum),
      CONSTRAINT FK_mem FOREIGN KEY(memId) REFERENCES members(memId),
      CONSTRAINT FK_product FOREIGN KEY(productNum) REFERENCES product(productNum),
      CONSTRAINT FK_col FOREIGN KEY(colors) REFERENCES colorsd(colors),
      CONSTRAINT FK_SIZENAME FOREIGN KEY(sizename) REFERENCES sizesize(sizename)
);

create sequence SEQ_basket start with 1 increment by 1;



-- 결제
CREATE TABLE payment(
	paymentNum          INT          primary key, 
	memId               VARCHAR2(30)  NOT NULL,     
	productNum          INT          NOT NULL,    
	numbers             INT          NOT NULL,     
	address             VARCHAR2(60)  NOT NULL,     
	hp                  VARCHAR2(30)  NOT NULL,     
	creditcardNum       VARCHAR2(30)  NOT NULL,     
	creditcardPassword  VARCHAR2(10)  NOT NULL    
);


-- 결제
CREATE UNIQUE INDEX PK_payment
	ON payment ( -- 결제
		paymentNum DESC -- 결제 번호
	);




-- 게시판 종류
CREATE TABLE shop_bbs(
    bbscd varchar2(60),
    bbsnm varchar2(60) NOT NULL,
    CONSTRAINT PK_BBS PRIMARY KEY(bbscd)
);
 

-- 게시글
CREATE TABLE bbsedit (
    bbseditno NUMBER,
    bbscd varchar2(20),
    title varchar2(100) NOT NULL,
    contents varchar2(600),
    memId varchar2(60),
    hit NUMBER,
    bbs_date DATE,
    CONSTRAINT PK_ARTICLE PRIMARY KEY(bbseditno),
    CONSTRAINT FK_ARTICLE FOREIGN KEY(bbscd) REFERENCES shop_bbs(bbscd)
);
 
-- 게시글 번호 생성기
CREATE SEQUENCE SEQ_bbsedit
INCREMENT BY 1
START WITH 1;

 
--게시판 데이터 입력
INSERT INTO shop_bbs VALUES ('review','리뷰게시판');
INSERT INTO shop_bbs VALUES ('qna','QnA게시판');
INSERT INTO shop_bbs VALUES ('notice','문의게시판');

commit;


insert into bbsedit values(seq_bbsedit.nextval, 'notice', 'test1', 'it', 'user1', 0, sysdate);
insert into bbsedit values(seq_bbsedit.nextval, 'notice', 'test2',, 'it', 'user2', 0, sysdate);
insert into bbsedit values(seq_bbsedit.nextval, 'notice', 'test3', 'it', 'user3', 0, sysdate);




-- 첨부파일 
create table attachfile (
 attachfileno number,
 filename varchar2(50) NOT NULL,
 filetype varchar2(30),
 filesize number,
 bbseditno number,
 productNum number.
 constraint PK_ATTACHFILE PRIMARY KEY(attachfileno)
);

-- 첨부파일 번호 생성기
create sequence SEQ_ATTACHFILE
increment by 1
start with 1;


-- 댓글
create table comments (
	commentno          number,
	bbseditno          number,     
	memId              varchar2(60),  
        memo               varchar2(4000),  
	bbs_date           date,
        constraint PK_COMMENTS PRIMARY KEY(commentno)
);

-- 댓글 번호 생성기

create sequence SEQ_COMMENTS
increment by 1
start with 1;







상품 타입

outer = T01
bottom  = T02
dress  = T03
shoes&acc  = T04
sale   = T05


상품 번호


outer = 1~100
bottom  = 101~200
dress  = 201~300
shoes&acc  = 301~400
sale   = 401~500
