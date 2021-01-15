# gpc
    Gerenciador de pauta para cooperativas.

#Implantação 

    As seguintes instruções devem ser executadas para rodar o projeto.

#Pré-requisito:

 	E nescessario ter instalado na maquina as seguintes ferramentas:
    • maven 3.
    • Jdk 8
	
#Build:
	
Acessar a pasta  onde se encontra o arquivo “pom.xml”  e executar os seguintes comandos:

    • mvn clean install
    • mvn spring-boot:run


#Utilização dos Endpoints 


#Buscar pautas 
    Api swagger:

        Executar a chamada.
  
    Terminal:

        curl -X GET "http://localhost:8080/api/v1/pauta" -H "accept: application/json"


#Criar Pauta 

    Necessario informar apenas os seguintes valores:

    Api swagger:

        {
          "descricao": "string",
          tema": "string"
        }

    Terminal:

        curl -X POST "http://localhost:8080/api/v1/pauta" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"descricao\": \"string\", tema\": \"string\"}"


#Abrir Votação da Pauta

    Api swagger:

	    Informar apenas o id da pauta.

    Terminal:

        curl -X PUT "http://localhost:8080/api/v1/pauta/abrir-votacao/1" -H "accept: application/json"


#Associar usuários em pauta.

    Api swagger:


        {
            "id": 3,
            "tema": "Teste 3",
            "descricao": "Teste 3",
            "usuarios": [

	          {
	            "id": 1,
	            "cpf": "55133279027"
	          }

            ],
            "dataHoraVotacao": null,
            "resultadoEnviado": false
        }


    Terminal:

        curl -X PUT "http://localhost:8080/api/v1/pauta/associar" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"id\": 3, \"tema\": \"Teste 3\", \"descricao\": \"Teste 3\", \"usuarios\": [\t {\t \"id\": 1,\t \"cpf\": \"55133279027\"\t } ], \"dataHoraVotacao\": null, \"resultadoEnviado\": false }"


#Buscar Usuários 

    Api swagger:

    	Executar a chamada.

    Terminal:

        curl -X GET "http://localhost:8080/api/v1/usuario" -H "accept: application/json"


#Criar Usuário

    Api swagger:

        {
          "cpf": "14116288020"
        }

    Terminal:

        curl -X POST "http://localhost:8080/api/v1/usuario" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"cpf\": \"14116288020\"}"


#Computar Voto

    E necessário informar o id da pauta e o id do usuário associado a pauta.

    Api swagger:

        voto = SIM:
            {

              "pauta": {
                "id": 3
              },
              "usuario": {
                "id": 1
              },
              "voto": "SIM"
            }


        voto = NAO:
            {

              "pauta": {
                "id": 3
              },
              "usuario": {
                "id": 4
              },
              "voto": "NAO"
            }

    Terminal:

        voto = SIM:

            curl -X POST "http://localhost:8080/api/v1/voto" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"pauta\": { \"id\": 3 }, \"usuario\": { \"id\": 1}, \"voto\": \"SIM\"}"


        voto = NAO:

            curl -X POST "http://localhost:8080/api/v1/voto" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"pauta\": { \"id\": 3 }, \"usuario\": { \"id\": 4 }, \"voto\": \"NAO\"}"


#Buscar Resultado Votação da Pauta

    Api swagger:

    	Informar apenas o id da pauta.

    Terminal:

        curl -X GET "http://localhost:8080/api/v1/resultado-votacao/3" -H "accept: application/json"


#Explicações	

#Feign Client

    Usei o Feign client pois sua implementação  é muito simples de trabalhar criando interfaces entre aplicações e outros serviços, especificando caminhos e métodos de forma muito clara via anotações. Outra facilidade do mesmo e a forma de tratarmos erros e os demais retornos através de Decoders.


#Forma de Utilização no Projeto 

Adicionado no pom.xml: 

<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
Adicionar no arquivo de inicialização “GpcApplication.class” a anotação “@EnableFeignClients“:

import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GpcApplication {
   public static void main(String[] args) {
      SpringApplication.run(GpcApplication.class, args);
   }
}

#RabbitMQ

Usei o  rabbitMq pois ele um mediador de mensagens leve, confiavel e portatil que se baseia nas especificações do spring AMQP utilizando o protocolo de comunicação AMQP.
 
#Forma de Utilização no Projeto 

configurações da fila se encontra no “package br.com.gpc.config“ arquivo “RabbitMqConfig”, e as configurações para conexão ao rabbitMq se encontram no arquivo “application.yml” na pasta “src/main/resources” podendo ser utilizadas de duas formas:

spring: 
	rabbitmq: 
		host: "localhost" 
		port: 5672 
		username: "admin" 
		password: "secret"

ou da seguinte forma:

spring: 
	rabbitmq: 
		addresses: "amqp://admin:secret@localhost"	



#Scheduled

Usei o scheduled para disparar uma rotina pela simplicidade na forma de utilização e por fazer para do pacote do spring:

#Forma de Utilização no Projeto 

    Adicionado a anotação @EnableScheduling na class principal do projeto para habilitar o uso do Scheduled:

        @EnableScheduling
        @SpringBootApplication
        public class GpcApplication {
           public static void main(String[] args) {
              SpringApplication.run(GpcApplication.class, args);
           }
        }
    Adicionado a anotação @Scheduled na class “PautaJob” que se encontra no “package br.com.gpc.job”:


        @Scheduled(cron = "${schedule.resultado-pauta}")
        public void dispararResultadoPauta() {
            this.logger.info("Method dispararResultadoPauta.");
            this.logger.info("Iniciando emissão dos resultados de pautas encerradas -" + LocalDateTime.now());
            List<Pauta> pautasParaEmissao = this.pautaService.buscarPautasParaEmitirResultado(LocalDateTime.now());
            for (Pauta pauta : pautasParaEmissao) {
                ResultaVotacaoDTO resultaVotacaoDTO = this.votoService.contabilizarVotacaoDaPauta(pauta.getId());
                this.rabbitMqService.enviarMensagemFilaMq(this.parseObjectToJson(resultaVotacaoDTO));
                this.pautaService.tornaPautasEmitidas(pauta);
            }
            this.logger.info("Finalizando emissão dos resultados de pautas encerradas -" + LocalDateTime.now());
        }

    Obs. A expressão “${schedule.resultado-pauta}” busca o valor da cron do arquivo “application.yml”


#Bean Validation

    Usei o Bean validation pois ele nós permite validar objetos de uma maneira muito simples com facilidade em diferentes camadas evitando assim o uso de ifs exagerados pelo código pois suas validações são feitas através de anotações e se precisarmos criar uma anotação customizada ele também nós permite isso.

#Forma de Utilização no Projeto 

    Adicionado no pom.xml: 

        <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

    Adicionar nas class que são utilizadas na camada rest para comunicação com os clientes que se encontram no package “br.com.gpc.dto”:


        import javax.validation.constraints.NotEmpty;
        import javax.validation.constraints.Size;

        public class PautaDTO {
            private Long id;
            @Size(max = 100, message = MensagensBeanValidationUtil.TEMA_TAMANHO_MAXIMO)
            @NotEmpty(message = MensagensBeanValidationUtil.TEMA_OBRIGATORIO)
            private String tema;
            @Size(max = 255, message = MensagensBeanValidationUtil.DESCRICAO_TAMANHO_MAXIMO)
            @NotEmpty(message = MensagensBeanValidationUtil.DESCRICAO_OBRIGATORIO)
            private String descricao;
            private Set<UsuarioDTO> usuarios;
            private LocalDateTime dataHoraVotacao;
            private Boolean resultadoEnviado;
        }


#Lombok

    Usei o lombok com o objetivo de evitar duplicidade de código e economizar na escrita de algumas linhas de código .

#Forma de Utilização no Projeto 

    Adicionado no pom.xml:
        <dependencies>

        <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>

           <optional>true</optional>
        </dependency>
        </dependencies>


        <build>
           <plugins>
              
              <plugin>
                 <groupId>org.apache.maven.plugins</groupId>
                 <artifactId>maven-compiler-plugin</artifactId>
                 <version>${maven.version}</version>
                 <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <annotationProcessorPaths>
                       
                       <path>
                          <groupId>org.projectlombok</groupId>
                          <artifactId>lombok</artifactId>
                          <version>${lombok.version}</version>
                       </path>
                       <path>
                          <groupId>org.projectlombok</groupId>
                          <artifactId>lombok-mapstruct-binding</artifactId>
                          <version>${lombok-binding.version}</version>
                       </path>
                    </annotationProcessorPaths>
                    </configuration>
              </plugin>
           </plugins>
        </build>

#MapStruct

     Usei o mapstruct pois em comparação com outras estruturas de mapeamento, o MapStruct gera mapeamentos de bean em tempo de compilação, o que garante um alto desempenho, permite feedback rápido do desenvolvedor e verificação completa de erros.

#Forma de Utilização no Projeto 

    Adicionado no pom.xml:
        <dependency>
           <groupId>org.mapstruct</groupId>
           <artifactId>mapstruct</artifactId>
        </dependency>


        <build>
           <plugins>
              <plugin>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-maven-plugin</artifactId>
              </plugin>
              <plugin>
                 <groupId>org.apache.maven.plugins</groupId>
                 <artifactId>maven-compiler-plugin</artifactId>
                 <version>${maven.version}</version>
                 <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <annotationProcessorPaths>
                       <path>
                          <groupId>org.projectlombok</groupId>
                          <artifactId>lombok-mapstruct-binding</artifactId>
                          <version>${lombok-binding.version}</version>
                       </path>
                    </annotationProcessorPaths>
                    <showWarnings>true</showWarnings>
                    <compilerArgs>
                       <compilerArg>-Amapstruct.suppressGeneratorTimestamp=true</compilerArg>
                       <compilerArg>-Amapstruct.suppressGeneratorVersionInfoComment=true</compilerArg>
                       <compilerArg>-Amapstruct.verbose=true</compilerArg>
                       <compilerArg>-Amapstruct.defaultComponentModel=spring</compilerArg>
                    </compilerArgs>
                 </configuration>
              </plugin>
           </plugins>
        </build>

    Adicionado @Mapper nas interfaces que se encontram no package br.com.gpc.mapper:

        import org.mapstruct.Mapper;

        @Mapper(uses = {UsuarioMapper.class})
        public interface PautaMapper extends EntityMapper<PautaDTO, Pauta> {
        }

#Mockito

    Usei o mockito pela facilidade que ele nós traz na hora de criar testes unitários.  Nos ajudando simular o comportamento de objetos reais complexos, principalmente quando estes objetos são difíceis de serem incorporados nos testes de unidade.

#Junit

    Usei o junit pois ele nos ajuda a verificar se cada unidade de código esta funcionado da forma esperada, facilita a criação e execução dos teste automatizados e é orientado a objeto.

#Forma de Utilização no Projeto 

    Adicionar no pom.xml:

        <dependency>
           <groupId>org.junit.platform</groupId>
           <artifactId>junit-platform-runner</artifactId>
           <version>1.2.0</version>
           <scope>test</scope>
        </dependency>
        <dependency>
           <groupId>org.junit.vintage</groupId>
           <artifactId>junit-vintage-engine</artifactId>
           <scope>test</scope>
        </dependency>

    Adicionar nas classes de testes que se encontram na pasta “src/test/java/br/com/gpc/service/impl”:

        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.junit.jupiter.MockitoExtension;


        @ExtendWith(MockitoExtension.class)
        public class PautaServiceImplTest extends Assertions {
            @InjectMocks
            private PautaServiceImpl pautaServiceImpl;
            @Mock
            private PautaRepository pautaRepository;
            @Mock
            private PautaMapper pautaMapper;
        @Test
        public void cadastrarPautaTest() {
            PautaDTO pautaDTO = PautaDTO.builder().tema("teste").descricao("teste").build();
            when(this.pautaRepository.buscarPautaPorTema(any())).thenReturn(Optional.empty());
            when(this.pautaMapper.toDto((Pauta) any())).thenReturn(pautaDTO);
            Pauta pauta = Pauta.builder().id(1l).build();
            when(this.pautaMapper.toEntity((PautaDTO) any())).thenReturn(pauta);
            when(this.pautaRepository.save(any())).thenReturn(pauta);
            assertNotNull(this.pautaServiceImpl.cadastrarPauta(pautaDTO));
        }
        }


#Flyway

    Usei o Flyway pois a mesma nós ajuda a organizar os scripts SQL que são executados no banco de dados,  fazendo para nós o controle de versão do banco de dados.


#Forma de Utilização no Projeto 

    Adicionado no pom.xml:

        <dependency>
           <groupId>org.flywaydb</groupId>
           <artifactId>flyway-core</artifactId>
        </dependency>

    Adicionado no  application.yml

        flyway:
          enabled: true
          check-location: false
          locations: db/migration
          url: jdbc:postgresql://ec2-18-232-254-253.compute-1.amazonaws.com:5432/dbk96134uvcs69
          username: kpmzujmcfildus
          password: 67a831212cd98fd0172566754866656ff92be80447bd04bd20f1376e865790ef
          baseline-on-migrate: true

    Obs. o atributo “locations”  deve ser informado a pasta onde os scripts vão ser salvos caminho relativo da pasta no projeto “src/main/resources/db/migration”.
