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
delete from oauth2_registered_client;

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
insert into permissao (id, nome, descricao) values (2, 'CONSULTAR_CURSOS_COMPRADOS', 'Permite acessar cursos comprados');
insert into permissao (id, nome, descricao) values (3, 'CRIAR_CURSOS', 'Permite criar novos cursos');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_CURSOS', 'Permite editar cursos');
insert into permissao (id, nome, descricao) values (5, 'CONSULTAR_MATRICULAS', 'Permite consultar matrículas');
insert into permissao (id, nome, descricao) values (6, 'CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar matrículas');
insert into permissao (id, nome, descricao) values (7, 'CONSULTAR_PAGAMENTOS', 'Permite consultar pagamentos');


insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Professor'), (3, 'Usuario');

insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

insert into grupo_permissao (grupo_id, permissao_id)
select 2, id from permissao where nome in (
    'EDITAR_CURSOS',
    'CRIAR_CURSOS',
    'CONSULTAR_MATRICULAS'
);

insert into grupo_permissao (grupo_id, permissao_id)
select 3, id from permissao where nome in (
    'COMPRAR_CURSOS',
    'ACESSAR_CURSOS_COMPRADOS',
    'CONSULTAR_MATRICULAS'
);

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@hotmail.com.br', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@hotmail.com.br', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', utc_timestamp),
(3, 'José Souza', 'jose.aux@hotmail.com.br', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@hotmail.com.br', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', utc_timestamp),
(6, 'Débora Mendonça', 'email.teste.fb+debora@gmail.com', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', utc_timestamp),
(7, 'Carlos Lima', 'email.teste.fb+carlos@gmail.com', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 3);

insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (1, "Administração", "Curso voltado para a area...", 500.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (2, "Contabilidade", "Curso voltado para a area...", 500.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (3, "Gestão de Negócios", "Curso voltado para a area...", 500.00, 1, utc_timestamp);

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES('1', 'plataforma-backend', '2026-10-19 22:18:57', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', NULL, 'Plataforma Backend', 'client_secret_basic', 'client_credentials', '', NULL, 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES('2', 'plataformaweb', '2026-10-19 22:18:58', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', NULL, 'Plataforma Web', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized', NULL, 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES('3', 'plataformaanalytics', '2026-10-19 22:18:58', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', NULL, 'Plataforma Analytics', 'client_secret_basic', 'authorization_code', 'http://www.plataformaanalytics.local:8082', NULL, 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

insert into matricula (id, data_matricula, status_matricula, curso_id, usuario_id) 
values (1, utc_timestamp, 'PAGAMENTO_CONFIRMADO', 1, 1);
