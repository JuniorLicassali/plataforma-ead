alter table questionario_usuario 
add constraint unique_matricula_questionario unique (matricula_id, questionario_id);