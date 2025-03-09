set foreign_key_checks = 0;

drop table usuario;
drop table pagamento;
drop table matricula;
drop table pergunta_opcoes;
drop table pergunta;
drop table questionario;
drop table curso;

set foreign_key_checks = 1;

alter table usuario auto_increment = 1;
alter table curso auto_increment = 1;
alter table matricula auto_increment = 1;
alter table pagamento auto_increment = 1;
alter table questionario auto_increment = 1;
alter table pergunta auto_increment = 1;
alter table pergunta_opcoes auto_increment = 1;