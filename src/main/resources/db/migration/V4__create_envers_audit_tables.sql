CREATE SEQUENCE IF NOT EXISTS siscoescola.revinfo_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS siscoescola.revinfo (
    rev INTEGER NOT NULL,
    revtstmp BIGINT,
    CONSTRAINT pk_revinfo PRIMARY KEY (rev)
);

CREATE TABLE IF NOT EXISTS siscoescola.tb_escola_aud (
    uuid_escola UUID NOT NULL,
    rev INTEGER NOT NULL,
    revtype SMALLINT,
    nome_escola CHARACTER VARYING(120),
    codigo_escola CHARACTER VARYING(120),
    municipio CHARACTER VARYING(120),
    estado CHARACTER VARYING(2),
    cep CHARACTER VARYING(8),
    logradouro CHARACTER VARYING(255),
    bairro CHARACTER VARYING(255),
    tipo_escola CHARACTER VARYING(30),
    is_ativo BOOLEAN,
    data_cadastro TIMESTAMP WITH TIME ZONE,
    data_atualizacao TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_tb_escola_aud PRIMARY KEY (uuid_escola, rev),
    CONSTRAINT fk_tb_escola_aud_revinfo FOREIGN KEY (rev)
        REFERENCES siscoescola.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_tb_escola_aud_rev ON siscoescola.tb_escola_aud (rev);

CREATE TABLE IF NOT EXISTS siscoescola.tb_usuario_aud (
    uuid_usuario UUID NOT NULL,
    rev INTEGER NOT NULL,
    revtype SMALLINT,
    nome_completo CHARACTER VARYING(120),
    username CHARACTER VARYING(120),
    cpf CHARACTER VARYING(11),
    email CHARACTER VARYING(120),
    password CHARACTER VARYING(255),
    data_cadastro TIMESTAMP WITH TIME ZONE,
    data_atualizacao TIMESTAMP WITH TIME ZONE,
    is_ativo BOOLEAN,
    CONSTRAINT pk_tb_usuario_aud PRIMARY KEY (uuid_usuario, rev),
    CONSTRAINT fk_tb_usuario_aud_revinfo FOREIGN KEY (rev)
        REFERENCES siscoescola.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_tb_usuario_aud_rev ON siscoescola.tb_usuario_aud (rev);

CREATE TABLE IF NOT EXISTS siscoescola.tb_diretor_aud (
    uuid_diretor UUID NOT NULL,
    rev INTEGER NOT NULL,
    revtype SMALLINT,
    nome_diretor CHARACTER VARYING(120),
    cpf CHARACTER VARYING(11),
    matricula_funcional CHARACTER VARYING(20),
    email CHARACTER VARYING(120),
    data_posse TIMESTAMP WITH TIME ZONE,
    data_fim_mandato TIMESTAMP WITH TIME ZONE,
    is_ativo BOOLEAN,
    data_cadastro TIMESTAMP WITH TIME ZONE,
    data_atualizacao TIMESTAMP WITH TIME ZONE,
    escola_uuid UUID,
    CONSTRAINT pk_tb_diretor_aud PRIMARY KEY (uuid_diretor, rev),
    CONSTRAINT fk_tb_diretor_aud_revinfo FOREIGN KEY (rev)
        REFERENCES siscoescola.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_tb_diretor_aud_rev ON siscoescola.tb_diretor_aud (rev);
