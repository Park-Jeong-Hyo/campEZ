--멤버테이블생성
drop table members;
create table members(
    mname varchar2(20) not null,
    mid varchar2(20) primary key,
    pw varchar2(20) not null,
    phone varchar2(13) check(phone like '%-%-%') not null,
    email varchar2(50) not null unique,
    nickname varchar2(20)not null unique,
    maddress varchar2(50),
    mtype char(1) not null,
    companyname varchar2(20),
    businessnumber varchar2(20) check(businessnumber like '%-%-%')
);

--제약조건 변경 양식
alter table members add check (businessnumber like '%-%-%');
 ALTER TABLE members DROP CONSTRAINT SYS_C007329;

--제약조건
alter table members add check(length(pw) >= 8);
alter table members add check(mtype in('n','b'));
alter table members add check(regexp_like(pw,'[!-/]') or regexp_like(pw,'[:-@]')
or regexp_like(pw,'[{-~]') or regexp_like(pw,'[[-`]'));
alter table members add unique(businessnumber);

--캠핑테이블생성
drop table camping;
create table camping(
    cnumber number(4) primary key,
    mid varchar2(20) not null,
    cname varchar2(20),
    caddress varchar2(50) unique not null,
    camptel varchar2(20) check(camptel like '%-%'),
    ctype char(1) check(ctype in('g','a')),
    operdate varchar2(20),
    homepage  varchar2(50),
    ctitle varchar2(30),
    ctext clob,
    priceweekday number(6),
    priceweekend number(6),
    toilet char(1) check(toilet in('o','x')),
    mart char(1) check(mart in('o','x')),
    udate date DEFAULT sysdate,
    foreign key (mid) REFERENCES members(mid)
);

--캠프에이리어테이블생성
drop table camparea;
create table camparea(
    cnumber number(4),
    area number(2),
    capacitys number(2) not null,
    primary key(cnumber,area),
    foreign key (cnumber) REFERENCES camping(cnumber) ON DELETE CASCADE
);

--오더테이블생성
drop table orders;
create table orders(
    onumber number(4),
    cnumber number(4) not null,
    area number(2) not null,
    mid varchar2(20) not null,
    phone varchar2(13) check(phone like '%-%') not null,
    headcount number(2) not null ,
--    check(headcount < (select capacitys from camparea c,orders o
--    where c.cnumber = o.cnumber  and c.area = o.area)),
    checkin varchar2(15) not null,
    checkout varchar2(15) not null,
    primary key(onumber),
    foreign key (cnumber,area) REFERENCES camparea(cnumber,area),
    foreign key (mid) REFERENCES members(mid)
);

--포스트테이블생성
drop table post;
create table post(
    pnumber number(4) primary key,
    nickname varchar2(20) not null,
    ptitle varchar2(50) not null,
    ptext clob,
    ptype char(1) check(ptype in('n','f')) not null,
    udate date DEFAULT TO_DATE(sysdate,'yyyy-mm-dd hh24:mi:ss')
);

--코멘트테이블생성
drop table comments;
create table comments(
    conumber number(4) primary key,
    pnumber number(4) not null,
    nickname varchar2(20) not null,
    cotext varchar2(100),
    udate date DEFAULT TO_DATE(sysdate,'yyyy-mm-dd hh24:mi:ss'),
    foreign key (pnumber) REFERENCES post(pnumber) ON DELETE CASCADE
);

--업로드테이블생성
drop table uploadfile;
create table uploadfile(
    upnumber  number(4) primary key,
    code            varchar2(5) not null,
    rid             varchar2(10) not null,
    storename  varchar2(50) not null,
    uploadname varchar2(100) not null,
    fsize           varchar2(45) not null,
    ftype           varchar2(50) not null,
    cdate      date DEFAULT TO_DATE(sysdate,'yyyy-mm-dd hh24:mi:ss'),
    udate      date DEFAULT TO_DATE(sysdate,'yyyy-mm-dd hh24:mi:ss')
);

--시퀀스생성
drop sequence cnumber_seq;
drop sequence onumber_seq;
drop sequence pnumber_seq;
drop sequence conumber_seq;
drop sequence upnumber_seq;

create sequence cnumber_seq;
create sequence onumber_seq;
create sequence pnumber_seq;
create sequence conumber_seq;
create sequence upnumber_seq;

--member 생성--
insert into members(mname,mid,pw,phone,email,nickname,mtype,businessnumber)
     values('홍길동','test123', 'test[123899','010-1234-5678','test1@naver.com','별명1','n','010-1234-5678');

insert into members(mname,mid,pw,phone,email,nickname,mtype,businessnumber)
     values('홍길동','test124', 'test[123899','010-1234-5678','test2@naver.com','별명2','n',null);

insert into members(mname,mid,pw,phone,email,nickname,mtype,businessnumber)
     values('홍길동','test125', 'test[123899','010-1234-5678','test3@naver.com','별명3','n',null);

--camping 생성--
insert into CAMPING
(cnumber,mid,cname,caddress,camptel,ctype,operdate,homepage,ctitle,ctext,priceweekday,priceweekend,toilet,mart)
values (cnumber_seq.nextval,'test123','KH캠핑장','울산 남구 신정동','0000-1234-5678','a','09:00~21:00',
'https://www.khcamping.com','캠핑','testtesttest',30000,40000,'o','x');

--camparea 생성--
insert into CAMPAREA
(cnumber,area,capacitys)
values((select max(cnumber) from camping),2,3);

--orders 생성--
insert into ORDERS
(onumber,cnumber,area,mid,phone,headcount,checkin,checkout)
values(onumber_seq.nextval,1,2,'test123','010-1234-5678',3,
2023011110,2023011209);

--post 생성--
insert into POST
(pnumber,nickname,ptitle,ptext,ptype)
values(pnumber_seq.nextval,'별명1','게시글1','testtesttest','n');

--comments 생성--
insert into COMMENTs
(conumber,pnumber,nickname,cotext)
values(conumber_seq.nextval,1,'별명1','댓글입니다');

--uploadfile 생성--
INSERT INTO uploadfile (upnumber, code, rid, storename, uploadname, fsize, ftype)
VALUES (upnumber_seq.nextval, 'A01',1,'sdfs','sadf','100','image/png');

ALTER SESSION SET NLS_DATE_FORMAT='YYYY/MM/DD HH:MI:SS';

select  *  from members;
select  *  from camping;
select  *  from camparea;
select  *  from orders;
select  *  from post;
select  *  from comments;