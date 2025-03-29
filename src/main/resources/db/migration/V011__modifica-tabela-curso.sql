alter table curso add column questionario_id bigint;

alter table curso add constraint fk_curso_questionario foreign key (questionario_id) references questionario(id);