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

insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (1, "Administração", "Curso voltado para a area...", 450.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (2, "Contabilidade", "Curso voltado para a area...", 480.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (3, "Gestão de Negócios", "Curso voltado para a area...", 520.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (4, "Marketing Digital", "Curso voltado para a area...", 350.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (5, "Recursos Humanos", "Curso voltado para a area...", 390.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (6, "Logística", "Curso voltado para a area...", 420.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (7, "Direito Civil", "Curso voltado para a area...", 650.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (8, "Psicologia Organizacional", "Curso voltado para a area...", 580.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (9, "Sistemas de Informação", "Curso voltado para a area...", 720.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (10, "Engenharia de Produção", "Curso voltado para a area...", 850.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (11, "Ciência de Dados", "Curso voltado para a area...", 950.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (12, "Gestão Financeira", "Curso voltado para a area...", 540.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (13, "Comunicação Social", "Curso voltado para a area...", 460.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (14, "Design Gráfico", "Curso voltado para a area...", 490.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (15, "Gestão Pública", "Curso voltado para a area...", 510.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (16, "Economia", "Curso voltado para a area...", 620.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (17, "Relações Internacionais", "Curso voltado para a area...", 680.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (18, "Arquitetura de Software", "Curso voltado para a area...", 890.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (19, "Turismo e Hotelaria", "Curso voltado para a area...", 380.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (20, "Publicidade e Propaganda", "Curso voltado para a area...", 470.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (21, "Gestão Ambiental", "Curso voltado para a area...", 430.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (22, "Segurança da Informação", "Curso voltado para a area...", 820.00, 1, utc_timestamp);
insert into curso (id, nome, descricao, preco, ativo, data_criacao) values (23, "Inteligência Artificial", "Curso voltado para a area...", 980.00, 1, utc_timestamp);

insert into modulo (id, nome, descricao, ordem, curso_id) values 
(1, 'Módulo 01: Configuração e Setup', 'Preparando o ambiente de desenvolvimento', 1, 1),
(2, 'Módulo 02: REST e Controllers', 'Criação de endpoints e verbos HTTP', 2, 1);

insert into aula (id, titulo, descricao, ordem, url_video, modulo_id) values 
(101, 'Instalação do JDK e Maven', 'Configurando variáveis de ambiente', 1, 'https://res.cloudinary.com/demo/video/upload/v1631526437/dog.mp4', 1),
(102, 'Spring Initializr', 'Criando o projeto base', 2, 'https://res.cloudinary.com/demo/video/upload/v1/samples/sea-turtle.mp4', 1);

insert into aula (id, titulo, descricao, ordem, url_video, modulo_id) values 
(201, 'Criando @RestController', 'Mapeando as primeiras rotas', 1, 'https://res.cloudinary.com/demo/video/upload/v1/samples/elephants.mp4', 2),
(202, 'Request Body e Response Entity', 'Manipulando entradas e saídas', 2, 'https://res.cloudinary.com/demo/video/upload/v1631526437/dog.mp4', 2);

insert into oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
values('1', 'plataforma-backend', '2026-10-19 22:18:57', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', NULL, 'Plataforma Backend', 'client_secret_basic', 'client_credentials', '', NULL, 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

insert into oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
values('2', 'plataformaweb', '2026-10-19 22:18:58', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', NULL, 'Plataforma Web', 'client_secret_basic', 'refresh_token,authorization_code', 'http://localhost:8080/swagger-ui/oauth2-redirect.html,http://local-plataforma.com:4200/authorized,http://127.0.0.1:8080/authorized', 'http://local-plataforma.com:4200', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

insert into oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
values('3', 'plataformaanalytics', '2026-10-19 22:18:58', '$2a$12$n.U7eDGz8sazK/hJESJ5Ue6aLxNgeWmWVoGHsuaj7weW0DA8gDw4K', NULL, 'Plataforma Analytics', 'client_secret_basic', 'authorization_code', 'http://www.plataformaanalytics.local:8082', NULL, 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

insert into matricula (id, data_matricula, status_matricula, curso_id, usuario_id) 
values (1, utc_timestamp, 'PAGAMENTO_CONFIRMADO', 1, 1);
