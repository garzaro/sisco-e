package com.sisco.escola.model.entity;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class Mensagens {
	private final MessageSourceAccessor accessor;
	
	public Mensagens(@NonNull MessageSource messaSource) {
		this.accessor = new MessageSourceAccessor(messaSource);		
	}
	public String pegar(@NonNull String code, Object... args) {
		return accessor.getMessage(code, args); //pega locale automaticamente		
	}	
}
