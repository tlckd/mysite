insert into board
values(1,'테스트타이틀1','테스트내용',1,now(),1,1,1,1);

insert into board values(null,'테스트타이틀1','테스트내용',0,now(),(select ifnull(max(g_no),0)+1 from board t) ,1,1,1);

select ifnull(max(g_no),0)+1 from board; 

select no,title, g_no, o_no, depth from board where no=1;

update board set o_no=o_no+1 where g_no=2 and o_no>=2;

SELECT * FROM webdb.board;

select a.no,title,contents,hit,date_format(reg_date, '%Y-%m-%d %H:%i:%s'),g_no,o_no,depth,a.user_no,b.name 
from board a, user b 
where a.user_no = b.no 
order by g_no desc , o_no asc;

select a.no,title,contents,hit,date_format(reg_date, '%Y-%m-%d %H:%i:%s'),g_no,o_no,depth,a.user_no,b.name from board a, user b where a.user_no = b.no order by g_no desc , o_no asc;

select no,title,contents from board where no=4;

delete from board where no=1;

update board set hit=?+1 where g_no=1