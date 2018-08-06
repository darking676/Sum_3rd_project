# Sum_3rd_project

#회원
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



#상품

create table products (<br/>
    procd varchar2(60),<br/>
    pronm varchar2(60) NOT NULL,<br/>
    CONSTRAINT PK_PRO PRIMARY KEY(procd)<br/>
);
<br/>
INSERT INTO products VALUES ('outer','겉옷');<br/>
INSERT INTO products VALUES ('top','상의');<br/>
INSERT INTO products VALUES ('bottom','하의');<br/>
INSERT INTO products VALUES ('dress','원피스');<br/>
INSERT INTO products VALUES ('shoesNacc','악세서리');<br/>
INSERT INTO products VALUES ('sale','세일품목');



#상품 내용
CREATE TABLE product(<br/>
	productNum   NUMBER,<br/>    
	productName  VARCHAR2(60),<br/>
        procd varchar2(60),<br/>     
	explan       VARCHAR2(500),<br/>    
	price        NUMBER DEFAULT 0,<br/>
        product_url  varchar2(500),<br/>
        CONSTRAINT PK_PRO2 PRIMARY KEY(productNum),<br/>
        CONSTRAINT FK_PRO2 FOREIGN KEY(procd) REFERENCES products(procd)<br/>     
);




#color
create table colorsd (<br/>
	colors varchar2(30) primary key,<br/>
	colorname varchar2(30),<br/>
);
<br/>
insert into colorsd values ('BLACK', 2);<br/>
insert into colorsd values ('RED', 3);<br/>
insert into colorsd values ('YELLOW', 4);<br/>
insert into colorsd values ('BLUE', 5);<br/>
insert into colorsd values ('CIELO', 6);<br/>
insert into colorsd values ('WHITE', 1);



#size
create table sizesize (<br/>
    sizename varchar2(30) not null,<br/>
    sizecd varchar2(30),<br/>
    sizenum varchar2(30),<br/>
    constraint PK_sizename PRIMARY KEY(sizename)<br/>
);
<br/>
insert into sizesize values('XS', 'str_size', 'XS');<br/>
insert into sizesize values('S', 'str_size', 'S');<br/>
insert into sizesize values('M', 'str_size', 'M');<br/>
insert into sizesize values('L', 'str_size', 'L');<br/>
insert into sizesize values('XL', 'str_size', 'XL');<br/>
insert into sizesize values(215, 'num_size', 215);<br/>
insert into sizesize values(220, 'num_size', 220);<br/>
insert into sizesize values(225, 'num_size', 225);<br/>
insert into sizesize values(230, 'num_size', 230);<br/>
insert into sizesize values(235, 'num_size', 235);<br/>
insert into sizesize values(240, 'num_size', 240);<br/>
insert into sizesize values(245, 'num_size', 245);<br/>
insert into sizesize values(250, 'num_size', 250);<br/>
insert into sizesize values(255, 'num_size', 255);<br/>
insert into sizesize values(260, 'num_size', 260);<br/>
insert into sizesize values(265, 'num_size', 265);<br/>
insert into sizesize values(270, 'num_size', 270);



#장바구니
CREATE TABLE basket (<br/>
      basketNum   NUMBER,  <br/>         
      memId       VARCHAR2(30),  <br/>     
      productNum  NUMBER,     <br/>
      quantity    NUMBER,<br/>
      colors      VARCHAR2(30),<br/>
      sizename    VARCHAR2(30),<br/>
      CONSTRAINT PK_basket PRIMARY KEY(basketNum),<br/>
      CONSTRAINT FK_mem FOREIGN KEY(memId) REFERENCES members(memId),<br/>
      CONSTRAINT FK_product FOREIGN KEY(productNum) REFERENCES product(productNum),<br/>
      CONSTRAINT FK_col FOREIGN KEY(colors) REFERENCES colorsd(colors),<br/>
      CONSTRAINT FK_SIZENAME FOREIGN KEY(sizename) REFERENCES sizesize(sizename)<br/>
);<br/>

create sequence SEQ_basket start with 1 increment by 1;



#결제
CREATE TABLE payment(<br/>
	paymentNum          INT          primary key, <br/>
	memId               VARCHAR2(30)  NOT NULL,    <br/> 
	productNum          INT          NOT NULL,    <br/>
	numbers             INT          NOT NULL,    <br/> 
	address             VARCHAR2(60)  NOT NULL,   <br/>  
	hp                  VARCHAR2(30)  NOT NULL,   <br/>  
	creditcardNum       VARCHAR2(30)  NOT NULL,  <br/>   
	creditcardPassword  VARCHAR2(10)  NOT NULL   <br/> 
);

<br/>
-- 결제
CREATE UNIQUE INDEX PK_payment<br/>
	ON payment (<br/> -- 결제
		paymentNum DESC<br/> -- 결제 번호
	);




#게시판 종류
CREATE TABLE shop_bbs(<br/>
    bbscd varchar2(60),<br/>
    bbsnm varchar2(60) NOT NULL,<br/>
    CONSTRAINT PK_BBS PRIMARY KEY(bbscd)<br/>
);
 

#게시글
CREATE TABLE bbsedit (<br/>
    bbseditno NUMBER,<br/>
    bbscd varchar2(20),<br/>
    title varchar2(100) NOT NULL,<br/>
    contents varchar2(600),<br/>
    memId varchar2(60),<br/>
    hit NUMBER,<br/>
    bbs_date DATE,<br/>
    CONSTRAINT PK_ARTICLE PRIMARY KEY(bbseditno),<br/>
    CONSTRAINT FK_ARTICLE FOREIGN KEY(bbscd) REFERENCES shop_bbs(bbscd)<br/>
);
 <br/>
-- 게시글 번호 생성기
CREATE SEQUENCE SEQ_bbsedit<br/>
INCREMENT BY 1<br/>
START WITH 1;<br/>

 
--게시판 데이터 입력
INSERT INTO shop_bbs VALUES ('review','리뷰게시판');<br/>
INSERT INTO shop_bbs VALUES ('qna','QnA게시판');<br/>
INSERT INTO shop_bbs VALUES ('notice','문의게시판');<br/>

commit;

<br/>
insert into bbsedit values(seq_bbsedit.nextval, 'notice', 'test1', 'it', 'user1', 0, sysdate);<br/>
insert into bbsedit values(seq_bbsedit.nextval, 'notice', 'test2',, 'it', 'user2', 0, sysdate);<br/>
insert into bbsedit values(seq_bbsedit.nextval, 'notice', 'test3', 'it', 'user3', 0, sysdate);




#첨부파일 
create table attachfile (<br/>
 attachfileno number,<br/>
 filename varchar2(50) NOT NULL,<br/>
 filetype varchar2(30),<br/>
 filesize number,<br/>
 bbseditno number,<br/>
 productNum number.<br/>
 constraint PK_ATTACHFILE PRIMARY KEY(attachfileno)<br/>
);<br/>

-- 첨부파일 번호 생성기
create sequence SEQ_ATTACHFILE<br/>
increment by 1<br/>
start with 1;<br/>


#댓글
create table comments (<br/>
	commentno          number,<br/>
	bbseditno          number,    <br/> 
	memId              varchar2(60),  <br/>
        memo               varchar2(4000),  <br/>
	bbs_date           date,<br/>
        constraint PK_COMMENTS PRIMARY KEY(commentno)<br/>
);
<br/>
-- 댓글 번호 생성기

create sequence SEQ_COMMENTS<br/>
increment by 1<br/>
start with 1;

#상품 타입

outer = T01<br/>
bottom  = T02<br/>
dress  = T03<br/>
shoes&acc  = T04<br/>
sale   = T05


#상품 번호

outer = 1~100<br/>
bottom  = 101~200<br/>
dress  = 201~300<br/>
shoes&acc  = 301~400<br/>
sale   = 401~500
