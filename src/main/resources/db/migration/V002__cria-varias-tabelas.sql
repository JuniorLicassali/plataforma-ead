create table curso (
	id bigint not null auto_increment,
    descricao varchar(255) not null,
    preco decimal(10,2) not null,
    ativo tinyint(1) not null,
    data_criacao datetime not null,
    data_atualizacao datetime,
    
    primary key (id)
) engine=InnoDB default charset=utf8;

create table matricula (
	id bigint not null auto_increment,
    data_matricula datetime not null,
    status_matricula varchar(50) not null,
    usuario_id bigint not null,
    curso_id bigint not null,
    
    primary key (id),
    
    constraint fk_matricula_usuario foreign key (usuario_id) references usuario(id),
    constraint fk_matricula_curso foreign key (curso_id) references curso(id)
) engine=InnoDB default charset=utf8;

create table pagamento (
	id bigint not null auto_increment,
    matricula_id bigint not null,
    data_criacao datetime not null,
    preco decimal(10,2) not null,
    status_pagamento varchar(50) not null,
    metodo_pagamento varchar(50) not null,
    
    primary key (id),
    
    constraint fk_pagamento_matricula foreign key (matricula_id) references matricula(id)
) engine=InnoDB default charset=utf8;

create table questionario (
	id bigint not null auto_increment,
    descricao varchar(255) not null,
    ativo tinyint(1) not null,
    data_abertura datetime not null,
    data_fechamento datetime,
    curso_id bigint not null,
    
    primary key (id),
    
    constraint fk_questionario_curso foreign key (curso_id) references curso(id)
) engine=InnoDB default charset=utf8;

create table pergunta (
	id bigint not null auto_increment,
    enunciado text not null,
    resposta_correta text not null,
    questionario_id bigint not null,
    
    primary key (id),
    
	constraint fk_pergunta_questionario foreign key (questionario_id) references questionario(id)
) engine=InnoDB default charset=utf8;

create table pergunta_opcoes (
	pergunta_id bigint not null,
    opcoes text,
    
    constraint fk_pergunta_opcoes_pergunta foreign key (pergunta_id) references pergunta(id)
) engine=InnoDB default charset=utf8;

