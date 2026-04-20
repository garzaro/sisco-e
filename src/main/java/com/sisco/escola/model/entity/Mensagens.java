package com.sisco.escola.model.entity;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Mensagens {
	private final MessageSourceAccessor accessor;
	
	public Mensagens(MessageSource messaSource) {
		this.accessor = new MessageSourceAccessor(messaSource);		
	}
	public String pegar(String code, Object... args) {
		return accessor.getMessage(code, args); //pega locale automaticamente		
	}	
}
