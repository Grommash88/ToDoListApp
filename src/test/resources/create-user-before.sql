delete
from user_role;
delete
from usr;

insert into usr(id, password, username)
values (1, '$2a$10$TOgizEKUGKBYCGk88D4FxuhXtWMOUR382I.kKP5Ep6PwCnUtlofYW', 'имячко'),
       (2, '$2a$10$TOgizEKUGKBYCGk88D4FxuhXtWMOUR382I.kKP5Ep6PwCnUtlofYW', 'grommash');

insert into user_role(user_id, roles)
values (1, 'USER'),
       (2, 'USER');