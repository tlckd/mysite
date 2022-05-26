-- UserRepository 

desc user;

insert into user values(null, '관리자','admin@mysite.com','1234','female',now());

select * from user;

-- login 
select no,name from user where email='ss' and password='ss';

-- findByNo 
select no,name,email,gender from user where no=2;
select no,name,email,gender from user where no=2;

-- update
update user set name='asas', email='asas@asas.com', gender='female',password='asas' where no=2;