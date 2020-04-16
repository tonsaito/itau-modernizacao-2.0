-- Sample users
insert into user(id, nome, cpf, email, cadastro) values(10001, 'João Machado', '123.456.789-11', 'joao.machado@email.com', sysdate());
insert into user(id, nome, cpf, email, cadastro) values(10002, 'Maria Diniz', '123.456.789-12', 'maria.diniz@email.com', sysdate());
insert into user(id, nome, cpf, email, cadastro) values(10003, 'Marcelo Silva', '123.456.789-13', 'marcelo.silva@email.com', sysdate());

-- Sample logtime
insert into workingtime(id, user_id, log_date, log_type) values(100011, 10001, '2020-04-11T09:00:00.000+00', 'ENTRADA');
insert into workingtime(id, user_id, log_date, log_type) values(100012, 10001, '2020-04-11T12:00:00.000+00', 'SAIDA');
insert into workingtime(id, user_id, log_date, log_type) values(100013, 10001, '2020-04-11T13:00:00.000+00', 'ENTRADA');
insert into workingtime(id, user_id, log_date, log_type) values(100014, 10001, '2020-04-11T18:00:00.000+00', 'SAIDA');

insert into workingtime(id, user_id, log_date, log_type) values(100021, 10002, '2020-04-11T09:30:20.000+00', 'ENTRADA');
insert into workingtime(id, user_id, log_date, log_type) values(100022, 10002, '2020-04-11T12:20:35.000+00', 'SAIDA');
insert into workingtime(id, user_id, log_date, log_type) values(100023, 10002, '2020-04-11T13:10:40.000+00', 'ENTRADA');
insert into workingtime(id, user_id, log_date, log_type) values(100024, 10002, '2020-04-11T18:05:15.000+00', 'SAIDA');

insert into workingtime(id, user_id, log_date, log_type) values(100031, 10003, '2020-04-11T11:30:20.000+00', 'ENTRADA');
insert into workingtime(id, user_id, log_date, log_type) values(100032, 10003, '2020-04-11T15:22:32.000+00', 'SAIDA');