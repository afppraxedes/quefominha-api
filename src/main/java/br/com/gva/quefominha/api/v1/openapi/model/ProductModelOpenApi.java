package br.com.gva.quefominha.api.v1.openapi.model;

import java.math.BigDecimal;

//import javax.validation.constraints.NotBlank;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import jakarta.validation.constraints.NotNull;
import lombok.Data;

//@ApiModel("UsuarioResponse")
@Data
public class ProductModelOpenApi {

//	@ApiModelProperty(example = "Alex dos Santos", position = 1)
	private String name;
	
//	@ApiModelProperty(example = "dos Santos", position = 2)
	private String description;
	
//	@ApiModelProperty(example = "15117439000176", position = 3)
	private BigDecimal unitValue;
	
//	@ApiModelProperty(example = "21998332109", position = 4)
	private Boolean active;
	
//	@ApiModelProperty(example = "Rio de Janeiro", position = 5)
//	@NotBlank
	private String imagePath;
	
//	@ApiModelProperty(example = "Rio de Janeiro", position = 5)
//	@NotBlank
	private String howMuchServe;

}
