package com.plataforma.plataforma_ead.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.plataforma.plataforma_ead.domain.model.FotoUsuario;
import com.plataforma.plataforma_ead.domain.model.Usuario;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQueries  {
	
	Optional<Usuario> findByEmail(String email);
	
	@Query("select f from FotoUsuario f join f.usuario u where u.id = :usuarioId")
	Optional<FotoUsuario> findFotoById(Long usuarioId);
	
////	talvez nao esteja sendo usado
//	@Query("SELECT u FROM Usuario u JOIN u.matriculas m WHERE m.id = :matriculaId")
//	Optional<Usuario> findByMatriculaId(Long matriculaId);
//	
////	talvez nao esteja sendo usado
//	@Query("SELECT m FROM Usuario u JOIN u.matriculas m")
//    List<Matricula> findAllMatriculas();
	
//	@Query("SELECT m FROM Matricula m WHERE m.curso = :curso AND m.usuario = :usuario")
//    Matricula findByCursoAndUsuario(@Param("curso") Curso curso, @Param("usuario") Usuario usuario);
	
//	@Query("SELECT m.questionarioUsuario FROM Usuario u JOIN u.matriculas m WHERE m.id = :matriculaId")
//    QuestionarioUsuario findQuestionarioUsuarioByMatriculaId(@Param("matriculaId") Long matriculaId);

//    @Query("SELECT m FROM Usuario u JOIN u.matriculas m WHERE m.id = :matriculaId")
//    Matricula findMatriculaById(@Param("matriculaId") Long matriculaId);
	
}