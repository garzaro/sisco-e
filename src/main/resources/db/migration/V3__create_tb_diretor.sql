CREATE TABLE IF NOT EXISTS siscoescola.tb_diretor (
    uuid_diretor UUID PRIMARY KEY,
    nome_diretor CHARACTER VARYING(120) NOT NULL,
    cpf CHARACTER VARYING(11) NOT NULL,
    email CHARACTER VARYING(120) NOT NULL,
    is_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_cadastro TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    uuid_escola UUID NOT NULL,
    CONSTRAINT uk_tb_diretor_cpf UNIQUE (cpf),
    CONSTRAINT uk_tb_diretor_email UNIQUE (email),
    CONSTRAINT uk_tb_diretor_uuid_escola UNIQUE (uuid_escola),
    CONSTRAINT fk_tb_diretor_escola FOREIGN KEY (uuid_escola)
        REFERENCES siscoescola.tb_escola (uuid_escola)
);
