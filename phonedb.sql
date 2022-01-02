/***************************
*    초기화  
***************************/

-- 기존 테이블 삭제
drop table person;

-- 기존 SEQUENCE 삭제
drop sequence seq_person_id;

-- person 테이블 생성
create table person(
    person_id       number(5),
    name            varchar2(30) not null,
    hp              varchar2(20),
    company         varchar2(20),
    primary key(person_id));  

-- SEQUENCE 생성
create sequence seq_person_id
increment by 1
start with 1
nocache;

/*******************************/

-- INSERT 
insert into person
values(seq_person_id.nextval, '이효리', '010-1111-1111', '02-1111-1111');

insert into person
values(seq_person_id.nextval, '정우성', '010-2222-2222', '02-2222-2222');

insert into person
values(seq_person_id.nextval, '유재석', '010-3333-3333', '02-3333-3333');

insert into person
values(seq_person_id.nextval, '이정재', '010-4444-4444', '02-4444-4444');

insert into person
values(seq_person_id.nextval, '서장훈', '010-5555-5555', '02-5555-5555');

-- SELECT
select  person_id, name, hp, company
from    person;

-- UPDATE
update  person
set     name= '이정재',
        hp= '010-9999-9999',
        company= '02-9999-9999'
where   person_id= 4;       

/*
-- DELETE (서장훈 삭제)
delete from person
where       person_id= 5;
*/ 

-- SELECT
select * from person;

commit;