package br.com.gva.quefominha.api.v1.openapi.controllers;

//import javax.validation.Valid;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;

//import com.afpsistemas.peacemaker.api.exceptionhandler.Problem;
//import com.afpsistemas.peacemaker.api.v1.model.UsuarioModel;
//import com.afpsistemas.peacemaker.api.v1.model.input.UsuarioInput;
//import com.afpsistemas.peacemaker.api.v1.model.input.UsuarioInputComSenha;
//import com.afpsistemas.peacemaker.domain.dto.UsuarioDTO;
//import com.afpsistemas.peacemaker.domain.model.Usuario;
//import com.afpsistemas.peacemaker.infrastructure.repository.filter.UsuarioFilter;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;

//@Api(tags = "Usuários")
public interface ProductControllerOpenApi {

	// ANTERIOR:
//	@ApiOperation("Lista os usuários")
//	CollectionModel<UsuarioModel> listar();
	
//	@HideApiDocumentation
//	@ApiOperation("Lista os usuários (excluir)")
//	public Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable);

//	@HideApiDocumentation
//	@ApiOperation("Busca um usuário pelo código - está tudo com \"ResponseEntity\"!")
//	@ApiResponses({
//		@ApiResponse(code = 401, message = "Usuário não autorizado", response = Problem.class),
//		@ApiResponse(code = 400, message = "Código do usuário inválido", response = Problem.class),
//		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
//	})
//	ResponseEntity<Usuario> buscarPeloCodigo(@ApiParam(value = "Código do usuário", example = "1", required = true) Long codigo);

//	@HideApiDocumentation
//	@ApiOperation("Busca um usuário pelo e-mail (este método está muito ruim! - está tudo com \"ResponseEntity\"!")
//	@ApiResponses({
//		@ApiResponse(code = 401, message = "Usuário não autorizado", response = Problem.class),
//		@ApiResponse(code = 400, message = "Código do usuário inválido", response = Problem.class),
//		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
//	})
//	ResponseEntity<Usuario> buscarPeloEmail(String email);
	
//	@ApiOperation("Busca um usuário pelo e-mail, retornando \"true\" caso encontre ou \"false\" caso não encontre")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Ok")
//	})
//	Boolean verificarEmailCadastrado(String email);
	
//	@HideApiDocumentation
//	@ApiOperation("Cadastra um Usuário")
//	@ApiResponses({
//		@ApiResponse(code = 201, message = "Usuário cadastrado"),
//	})
//	public UsuarioModel adicionar(@Valid @RequestBody UsuarioInputComSenha usuarioInput);
	
//	UsuarioResponse adicionar(
//			@ApiParam(name = "corpo", value = "Representação de um novo Usuário", required = true)
//			UsuarioRequest usuarioRequest);
			// ANTERIOR:
//			UsuarioResponse adicionar(
//			@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
//			UsuarioComSenhaInput usuarioInput);
	
//	@HideApiDocumentation
//	@ApiOperation("Atualiza um usuário pelo código")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Usuário atualizado"),
//		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
//	})
//	public UsuarioModel atualizar(@Valid @RequestBody UsuarioInput usuarioInput, @PathVariable Long id);
	
//	UsuarioResponse atualizar(
//			@ApiParam(value = "ID do Usuário", example = "1", required = true)
//			Long usuarioId,
//			
//			@ApiParam(name = "corpo", value = "Representação de um Usuário com os novos dados",
//				required = true)
//			UsuarioRequest usuarioRequest);
	
//	@HideApiDocumentation
//	@ApiOperation("Atualiza o perfil do usuário")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Usuário atualizado"),
//		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
//	})
//	ResponseEntity<Usuario> perfilEditar(@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) 
//		UsuarioDTO usuario);
	
//	@HideApiDocumentation
//	@ApiOperation("Recupera a senha do usuário pelo email")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Usuário atualizado"),
//		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
//	})
//	void recuperarSenha(@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true)
//		String email);
	
//	@HideApiDocumentation
//	@ApiOperation("Exclui um usuário pelo código")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Usuário atualizado"),
//		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
//	})
//	void excluir (@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) Long codigo);

//	@HideApiDocumentation
//	@ApiOperation("Ativa/desativa um usuário do sistema pelo código")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Usuário atualizado"),
//		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
//	})
//	public void mudarStatus(
//			@ApiParam(name = "codigo", value = "Código do usuário", required = true) Long codigo, 
//			@ApiParam(name = "ativo", value = "valor para ativação/desativação do usuário", required = true) Boolean ativo);
	
//	@ApiOperation("Atualiza a senha de um usuário")
//	@ApiResponses({
//		@ApiResponse(code = 204, message = "Senha alterada com sucesso"),
//		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
//	})
//	void alterarSenha(
//			@ApiParam(value = "ID do usuário", example = "1", required = true)
//			Long usuarioId,
//			
//			@ApiParam(name = "corpo", value = "Representação de uma nova senha", 
//				required = true)
//			SenhaInput senha);

}


