package br.com.gpc.util;

public abstract class MensagensUtil {

    public static final String TEMA_JA_EXISTE = "Já existe uma pauta com esse tema!";
    public static final String NENHUM_USUARIO_INFORMADO = "E necessário informar pelo menos um usuário para realizar a associação!";
    public static final String PAUTA_NAO_ENCOTRADA_OU_VOTACAO_INICIADA = "A pauta informada não foi encontrada ou já foi iniciada a votação!";
    public static final String NENHUM_USUARIO_ASSOCIADO = "A pauta não possui nenhum usuário associado para iniciar a votação!";
    public static final String VOTACAO_FECHADA_OU_INEXISTENTE = "A pauta informada encontra-se fechada para votação ou não existe!";
    public static final String VOTACAO_ENCERRADA = "A votação já foi encerrada para a pauta informada!";
    public static final String PAUTA_NAO_ENCONTRADA = "A pauta informada não foi encontrada!";
    public static final String PAUTA_SEM_VOTACAO = "A pauta não possui nenhuma votação!";
    public static final String USUARIO_JA_CADASTRADO = "Usuário já cadastrado!";
    public static final String CPF_INVALIDO = "Não foi possível cadastrar o usuário, pois o cpf informado e invalido!";
    public static final String PAUTA_SEM_VOTO_PARA_APURAR = "A pauta informada não possui votos para serem apurados!";
    public static final String PAUTA_REPROVADA = "A pauta não foi aprovada pois a quantidade de votos negativos foi superior aos positivos.";
    public static final String PAUTA_APROVADA = "A pauta foi aprovada pois a quantidade de votos positivos foi superior aos negativos.";
    public static final String PAUTA_EMPATADA = "Votação da pauta empatada.";
    public static final String USUARIO_COM_VOTO_REALIZADO = "O usuário não pode realizar mais de um voto em uma única pauta!";
    public static final String PAUTA_COM_VOTACAO_ABERTA = "A pauta informada ainda não terminou a sessão de votação!";
}
