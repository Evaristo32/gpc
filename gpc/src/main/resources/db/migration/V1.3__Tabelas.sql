create table tb_usuario
(
    id  bigint      not null
        constraint tb_usuario_pkey
            primary key,
    cpf varchar(11) not null
);

-- alter table tb_usuario
--     owner to fnwskuow;

create table tb_pauta
(
    id                bigint       not null
        constraint tb_pauta_pkey
            primary key,
    data_hora_votacao timestamp,
    descricao         varchar(255) not null,
    resultado_enviado boolean,
    tema              varchar(100) not null
);
--
-- alter table tb_pauta
--     owner to fnwskuow;

create table pauta_usuario
(
    id_pauta   bigint not null
        constraint fk_pauta_usuario
            references tb_pauta,
    usuario_id bigint not null
        constraint fk_usuario_pauta
            references tb_usuario,
    constraint pauta_usuario_pkey
        primary key (id_pauta, usuario_id)
);


create table tb_voto
(
    id         bigint     not null
        constraint tb_voto_pkey
            primary key,
    voto       varchar(3) not null,
    id_pauta   bigint     not null
        constraint fk_pauta_voto
            references tb_pauta,
    id_usuario bigint     not null
        constraint fk_usuario_voto
            references tb_usuario
);
--
-- alter table tb_voto
--     owner to fnwskuow;

create table tb_resultado_votacao
(
    id             bigint         not null
        constraint tb_resultado_votacao_pkey
            primary key,
    mensagem       varchar(100)   not null,
    total_voto     numeric(19, 2) not null,
    voto_favoravel numeric(19, 2) not null,
    voto_oposto    numeric(19, 2) not null,
    id_pauta       bigint         not null
        constraint fk_pauta_resultado_votacao
            references tb_pauta
);
--
-- alter table tb_resultado_votacao
--     owner to fnwskuow;
