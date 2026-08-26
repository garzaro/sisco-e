CREATE TABLE IF NOT EXISTS siscoescola.tb_diretor (
    uuid_diretor UUID PRIMARY KEY,
    nome_diretor CHARACTER VARYING(120) NOT NULL,
    cpf CHARACTER VARYING(11) NOT NULL,
    matricula_funcional CHARACTER VARYING(20) NOT NULL,
    email CHARACTER VARYING(120) NOT NULL,
    data_posse TIMESTAMP WITH TIME ZONE NOT NULL,
    data_fim_mandato TIMESTAMP WITH TIME ZONE,
    is_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_cadastro TIMESTAMP WITH TIME ZONE NOT NULL,
    data_atualizacao TIMESTAMP WITH TIME ZONE NOT NULL,
    escola_uuid UUID NOT NULL,
    CONSTRAINT uk_tb_diretor_cpf UNIQUE (cpf),
    CONSTRAINT uk_tb_diretor_email UNIQUE (email),
    CONSTRAINT uk_tb_diretor_matricula_funcional UNIQUE (matricula_funcional),
    CONSTRAINT uk_tb_diretor_escola_uuid UNIQUE (escola_uuid),
    CONSTRAINT fk_tb_diretor_escola FOREIGN KEY (escola_uuid)
        REFERENCES siscoescola.tb_escola (uuid_escola)
);
