-- UserRepository 

desc user;

insert into user values(null, '관리자','admin@mysite.com','1234','female',now());

select * from user;

-- login 
select no,name from user where email='asf' and password='asf';

-- findByNo 
select no,name,email,gender from user where email='asf' and password='asf';