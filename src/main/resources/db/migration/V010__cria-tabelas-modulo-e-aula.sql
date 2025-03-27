create table modulo (
	id bigint not null auto_increment,
	nome varchar(255) not null,
	descricao text not null,
	ordem int,
	curso_id bigint,

	primary key (id),

	constraint fk_modulo_curso foreign key (curso_id) references curso(id)
) engine=InnoDB default charset=utf8;

create table aula (
	id bigint not null auto_increment,
	titulo varchar(255) not null,
	descricao text not null,
	ordem int,
	url_video varchar(255),
	modulo_id bigint not null,

	primary key (id),

	constraint fk_aula_modulo foreign key (modulo_id) references modulo(id)
) engine=InnoDB default charset=utf8;