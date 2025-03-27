set foreign_key_checks = 0;

delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from usuario;
delete from usuario_grupo;
delete from pagamento;
delete from matricula;
delete from pergunta_opcao;
delete from pergunta;
delete from questionario;
delete from questionario_usuario;
delete from curso;
delete from modulo;
delete from aula;

set foreign_key_checks = 1;

alter table usuario auto_increment = 1;
alter table curso auto_increment = 1;
alter table matricula auto_increment = 1;
alter table pagamento auto_increment = 1;
alter table questionario auto_increment = 1;
alter table questionario_usuario auto_increment = 1;
alter table pergunta auto_increment = 1;
alter table pergunta_opcao auto_increment = 1;
alter table modulo auto_increment = 1;
alter table aula auto_increment = 1;


insert into permissao (id, nome, descricao) values (1, 'COMPRAR_CURSOS', 'Permite que usuários comprem cursos');
insert into permissao (id, nome, descricao) values (2, 'ACESSAR_CURSOS_COMPRADOS', 'Permite acessar cursos comprados');
insert into permissao (id, nome, descricao) values (3, 'CRIAR_CURSOS', 'Permite criar novos cursos');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_CURSOS', 'Permite editar cursos criados');
insert into permissao (id, nome, descricao) values (5, 'EXCLUIR_CURSOS', 'Permite excluir cursos criados');
insert into permissao (id, nome, descricao) values (6, 'GERENCIAR_CONTEUDO_CURSO', 'Permite gerenciar conteúdo dos cursos');
insert into permissao (id, nome, descricao) values (7, 'GERENCIAR_TODOS_CURSOS', 'Gerente pode editar/excluir qualquer curso');
insert into permissao (id, nome, descricao) values (8, 'GERENCIAR_USUARIOS', 'Gerente pode gerenciar usuários');
insert into permissao (id, nome, descricao) values (9, 'GERENCIAR_PROFESSORES', 'Gerente pode gerenciar permissões de professores');
insert into permissao (id, nome, descricao) values (10, 'GERAR_RELATORIOS', 'Gerente pode gerar relatórios');


insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Professor'), (3, 'Usuario');

insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

insert into grupo_permissao (grupo_id, permissao_id)
select 2, id from permissao where nome in (
    'COMPRAR_CURSOS',
    'ACESSAR_CURSOS_COMPRADOS',
    'CRIAR_CURSOS',
    'EDITAR_CURSOS',
    'EXCLUIR_CURSOS',
    'GERENCIAR_CONTEUDO_CURSO'
);

insert into grupo_permissao (grupo_id, permissao_id)
select 3, id from permissao where nome in (
    'COMPRAR_CURSOS',
    'ACESSAR_CURSOS_COMPRADOS'
);

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@hotmail.com.br', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@hotmail.com.br', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@hotmail.com.br', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@hotmail.com.br', '123', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp),
(6, 'Débora Mendonça', 'email.teste.fb+debora@gmail.com', '123', utc_timestamp),
(7, 'Carlos Lima', 'email.teste.fb+carlos@gmail.com', '123', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (1, "Administração", "Curso voltado para a area...", 500.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (2, "Contabilidade", "Curso voltado para a area...", 500.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (3, "Gestão de Negócios", "Curso voltado para a area...", 500.00, 1, utc_timestamp);



