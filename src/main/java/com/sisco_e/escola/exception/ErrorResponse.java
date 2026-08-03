package com.sisco_e.escola.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**estruturar a mensagem de erro**/

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
	private String message;

}
