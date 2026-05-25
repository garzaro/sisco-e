package com.sisco.escola.validacao;

import org.passay.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

/**para validação do tipo de senha*/

@Configuration
public class SenhaValidatorConfig {
    /**
     * EnglishCharacterData é um enum da biblioteca Passay
     * que define grupos de caracteres padrão com base no
     * alfabeto latino básico (sem acentos).
     */
    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator(Arrays.asList(
                new LengthRule(6, 32),
                /**CaracteresDadosCustomizados - personalizado*/
                new CharacterRule(CaracteresDadosCustomizados.UPPERCASE_LATINO_pt_BR, 1),
                new CharacterRule(CaracteresDadosCustomizados.LOWERCASE_LATINO_pt_BR, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule(),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                /**
                 * para validar repetições na senha como 111, aaa.
                 * RegraCaracteresRepetidos
                 * */
                new RegraCaracteresRepetidos(2)
        ));
    }
}