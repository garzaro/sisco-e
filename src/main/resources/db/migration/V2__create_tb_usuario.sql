CREATE TABLE IF NOT EXISTS siscoescola.tb_usuario (
    id_usuario UUID PRIMARY KEY,
    nome_completo CHARACTER VARYING(120) NOT NULL,
    username CHARACTER VARYING(120) NOT NULL,
    cpf CHARACTER VARYING(11),
    email CHARACTER VARYING(120) NOT NULL,
    password CHARACTER VARYING(255) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    is_ativo BOOLEAN,
    CONSTRAINT uk_tb_usuario_cpf UNIQUE (cpf),
    CONSTRAINT uk_tb_usuario_email UNIQUE (email)
);
