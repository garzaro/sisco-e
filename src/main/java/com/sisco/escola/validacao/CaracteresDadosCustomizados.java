package com.sisco.escola.validacao;

import org.passay.CharacterData;

/** Enum criado para que possa aceitar nomes,
 *  senhas ou dados com acentuação na criação de senhas
 *  */

public enum CaracteresDadosCustomizados implements CharacterData {

    UPPERCASE_LATINO_pt_BR("INSUFFICIENT_UPPERCASE", "ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚÂÊÎÔÛÃÕÀÇ"),
    LOWERCASE_LATINO_pt_BR("INSUFFICIENT_LOWERCASE", "abcdefghijklmnopqrstuvwxyzáéíóúâêîôûãõàç");

    private final String errorCode;
    private final String characters;

    CaracteresDadosCustomizados(String code, String chars) {
        this.errorCode = code;
        this.characters = chars;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getCharacters() {
        return this.characters;
    }
}
