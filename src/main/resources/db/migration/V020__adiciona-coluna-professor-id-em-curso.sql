alter table curso add column professor_id bigint;

alter table curso add constraint fk_curso_professor
foreign key (professor_id) references usuario (id);