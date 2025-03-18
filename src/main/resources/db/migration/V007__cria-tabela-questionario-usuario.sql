create table questionario_usuario (
	id bigint not null auto_increment,
	matricula_id bigint not null,
	questionario_id bigint not null,
	data_abertura datetime,
    data_fechamento datetime,
	finalizado tinyint(1) not null,

	primary key (id),
	
	constraint fk_questionario_usuario_matricula foreign key (matricula_id) references matricula(id),
	constraint fk_questionario_usuario_questionario foreign key (questionario_id) references questionario(id)
) engine=InnoDB default charset=utf8;