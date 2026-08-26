CREATE TABLE IF NOT EXISTS siscoescola.tb_link_internet (
    uuid_link_internet UUID PRIMARY KEY,
    tipo_link CHARACTER VARYING(30) NOT NULL,
    ip_publico CHARACTER VARYING(45) NOT NULL,
    mascara_rede CHARACTER VARYING(45) NOT NULL,
    gateway CHARACTER VARYING(45) NOT NULL,
    dns_primario CHARACTER VARYING(45) NOT NULL,
    dns_secundario CHARACTER VARYING(45),
    vlan_id INTEGER,
    uuid_contrato_net UUID NOT NULL,
    uuid_provedor UUID NOT NULL,
    uuid_escola UUID NOT NULL,
    is_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_cadastro TIMESTAMP WITH TIME ZONE NOT NULL,
    data_atualizacao TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_link_internet_contrato_net UNIQUE (uuid_contrato_net),
    CONSTRAINT uk_link_internet_ip_publico UNIQUE (ip_publico),
    CONSTRAINT fk_link_internet_contrato FOREIGN KEY (uuid_contrato_net)
        REFERENCES siscoescola.tb_contrato_net (uuid_contrato_net),
    CONSTRAINT fk_link_internet_provedor FOREIGN KEY (uuid_provedor)
        REFERENCES siscoescola.tb_provedor_net (uuid_provedor),
    CONSTRAINT fk_link_internet_escola FOREIGN KEY (uuid_escola)
        REFERENCES siscoescola.tb_escola (uuid_escola)
);

CREATE INDEX IF NOT EXISTS idx_link_internet_provedor_uuid
    ON siscoescola.tb_link_internet (uuid_provedor);

CREATE INDEX IF NOT EXISTS idx_link_internet_escola_uuid
    ON siscoescola.tb_link_internet (uuid_escola);
