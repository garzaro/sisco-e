package com.sisco.escola.validacao;

import org.passay.*;
import java.util.Collections;

/**
 * para validar repetições na senha como 111, aaa.
 * */
public class RegraCaracteresRepetidos implements Rule {
    private final int maxRepeat;

    public RegraCaracteresRepetidos(int maxRepeat) {
        this.maxRepeat = maxRepeat;
    }

    @Override
    public RuleResult validate(PasswordData passwordData) {
        String senha = passwordData.getPassword();
        int repetidos = 1;

        for (int i = 1; i < senha.length(); i++) {
            if (senha.charAt(i) == senha.charAt(i - 1)) {
                repetidos++;
                if (repetidos > maxRepeat) {
                    RuleResult result = new RuleResult();
                    result.setValid(false);
                    /**a lib passay ja traz essa mensagem*/
                    //result.getDetails().add(new RuleResultDetail("TOO_MANY_REPEAT_CHARS", Collections.emptyMap()));
                    return result;
                }
            } else {
                repetidos = 1;
            }
        }

        return new RuleResult(true);
    }
}
