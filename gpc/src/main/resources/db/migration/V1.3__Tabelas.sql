create table gpc.tb_usuario
(
    id  bigint      not null
        constraint tb_usuario_pkey
            primary key,
    cpf varchar(11) not null
);

alter table gpc.tb_usuario
    owner to usr_gpc;

create table gpc.tb_pauta
(
    id                bigint       not null
        constraint tb_pauta_pkey
            primary key,
    data_hora_votacao timestamp,
    descricao         varchar(255) not null,
    resultado_enviado boolean,
    tema              varchar(100) not null
);

alter table gpc.tb_pauta
    owner to usr_gpc;

create table gpc.pauta_usuario
(
    id_pauta   bigint not null
        constraint fk_pauta_usuario
            references gpc.tb_pauta,
    usuario_id bigint not null
        constraint fk_usuario_pauta
            references gpc.tb_usuario,
    constraint pauta_usuario_pkey
        primary key (id_pauta, usuario_id)
);

alter table gpc.pauta_usuario
    owner to usr_gpc;

create table gpc.tb_voto
(
    id         bigint     not null
        constraint tb_voto_pkey
            primary key,
    voto       varchar(3) not null,
    id_pauta   bigint     not null
        constraint fk_pauta_voto
            references gpc.tb_pauta,
    id_usuario bigint     not null
        constraint fk_usuario_voto
            references gpc.tb_usuario
);

alter table gpc.tb_voto
    owner to usr_gpc;

create table gpc.tb_resultado_votacao
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
            references gpc.tb_pauta
);

alter table gpc.tb_resultado_votacao
    owner to usr_gpc;
