# Representação Externa de Dados em Java

Este projeto demonstra a serialização e desserialização de objetos Java (POJOs) utilizando formatos de representação externa de dados modernos e binários: **Protocol Buffers (Protobuf)**, **Apache Avro** e **MessagePack**. O projeto é uma continuação do tutorial de Representação Externa de Dados.

## 🚀 Tutorial: Como utilizar e rodar no Eclipse com Maven

Para facilitar a gestão das bibliotecas (sem precisar baixar dezenas de arquivos `.jar` soltos na internet), o projeto foi convertido para o formato **Maven**.

### Passo 1: Importando ou Convertendo o Projeto no Eclipse
1. Abra o Eclipse e selecione seu Workspace.
2. Clique com o botão direito no seu projeto atual (`external-representation`) no *Project Explorer*.
3. Vá em **Configure** > **Convert to Maven Project**.
   - *Nota:* Como já existe um arquivo `pom.xml` que foi criado neste diretório, o Eclipse vai ler as dependências imediatamente.
4. Aguarde alguns segundos. O Eclipse fará o download automático das bibliotecas do Avro, Protobuf (Protostuff) e MessagePack, bem como de todas as suas dependências internas (Jackson, Slf4j, etc).

### Passo 2: Estrutura dos Pacotes Implementados
Foram criados os pacotes solicitados, cada um contendo as classes: `Writer`, `Reader` e `Main`.
* `br.edu.ifsuldeminas.mch.sd.protobuf`
* `br.edu.ifsuldeminas.mch.sd.avro`
* `br.edu.ifsuldeminas.mch.sd.messagepack`

**Nota Técnica de Implementação:** 
Para obedecer o requisito de *utilizar os mesmos POJOs originais* sem a complexidade de rodar compiladores externos, foram usadas abordagens dinâmicas:
- **Protobuf:** Usou-se a biblioteca *Protostuff*, que utiliza reflexão para serializar classes Java para o formato binário do Protobuf perfeitamente sem exigir a compilação de arquivos `.proto`.
- **Avro:** Usou-se o recurso `ReflectData`, que constrói o schema (`.avsc`) implicitamente em tempo de execução.
- **MessagePack:** Foi utilizado através do ecossistema Jackson (`jackson-dataformat-msgpack`), que age de forma idêntica a leitores JSON.

### Passo 3: Execução
Para testar qualquer tecnologia:
1. Navegue até o pacote desejado (ex: `...sd.protobuf`).
2. Clique com o botão direito na classe `Main.java`.
3. Escolha **Run As** > **Java Application**.
4. Verifique o console mostrando a mensagem de sucesso e o objeto sendo recuperado do disco, e note o arquivo criado na raiz do projeto (`person.protobuf`, `person.avro`, etc).

---

## 📝 Respostas do Questionário

### 1. Quais vantagens e desvantagens cada tecnologia apresenta?
* **Protocol Buffers (Protobuf)**
  * **Vantagens:** Serialização extremamente rápida e arquivos muito compactos. É o formato padrão para gRPC. Suporta evolução de schema sem quebrar sistemas antigos.
  * **Desvantagens:** Os dados são em formato binário não autodescritivo. Geralmente exige a criação de arquivos `.proto` e geração de classes (embora tenhamos usado Protostuff para simplificar aqui).
* **Apache Avro**
  * **Vantagens:** É autodescritivo (o schema JSON é armazenado junto no próprio arquivo ou em um Registry). Permite ler os dados genéricos sem nem ter a classe Java compilada. Tipagem rica perfeita para Data Lakes e ecossistemas Hadoop.
  * **Desvantagens:** Quando serializamos *apenas um único objeto pequeno*, o arquivo fica grande, pois ele anexa a "receita" (schema) inteira dentro do arquivo. Formato mais complexo para casos simples.
* **MessagePack**
  * **Vantagens:** É virtualmente um "JSON Binário". Simples, não requer definição de esquemas (schema-less). É muito fácil migrar de um sistema JSON para MessagePack e ganhar performance e compactação imediatamente.
  * **Desvantagens:** Por não ter schemas rigorosos (contratos), não oferece a mesma segurança entre microserviços diferentes como o Protobuf/Avro proporcionam.

### 2. Em quais tipos de sistemas cada formato costuma ser utilizado?
* **Protobuf:** Microserviços de alta performance de comunicação síncrona (via **gRPC**), aplicativos móveis onde banda é limitada e jogos.
* **Apache Avro:** Ecossistema de Big Data (Hadoop, Spark), Data Lakes e comunicação assíncrona por mensageria (ex: **Apache Kafka** utiliza fortemente o Avro e o Schema Registry).
* **MessagePack:** Sistemas de Cache (como o Redis), substituto direto de JSON em APIs REST para reduzir payload sem alterar a lógica de negócio, e aplicações IoT simples.

### 3. Qual formato gerou o menor arquivo?
**Protobuf** e **MessagePack** geram, em empate técnico, os menores arquivos. Como ambos não anexam os nomes dos campos (`schema`) e compactam tipos numéricos fortemente, eles superam com folga JSON e XML. 
Já o **Avro** gera um arquivo *bem maior* nesse caso de teste específico (às vezes até maior que JSON e XML). Isso acontece porque o Avro insere o Schema inteiro em formato de texto no cabeçalho do arquivo `.avro` recém-criado para ele ser autodescritivo. O Avro só brilha em tamanho quando enviamos milhares de `Person` de uma vez em um único arquivo (lote), pois o cabeçalho é salvo apenas 1 vez para todo o lote.

### 4. Qual formato foi mais simples de implementar?
Considerando o uso dinâmico por trás de bibliotecas que fazem reflexão, todos foram simples, mas o **MessagePack** (via Jackson) leva vantagem na facilidade e proximidade com as APIs JSON. Com apenas 2 linhas e instanciando um `ObjectMapper`, já foi possível serializar dados binários, de forma muito idêntica à biblioteca `json-io` utilizada na primeira etapa do tutorial.

### 5. Qual formato parece mais adequado para Sistemas Distribuídos modernos?
Depende do tipo do sistema moderno, mas hoje há dois grandes dominantes:
- **Protocol Buffers (Protobuf)** é o mais adequado e utilizado na atualidade para a comunicação entre **microserviços** (chamadas síncronas usando gRPC), devido aos seus contratos de interface (os `.proto` files) garantirem tipagem estrita entre as APIs.
- Já o **Apache Avro** é o mais adequado para **processamento de streaming de eventos**, arquiteturas *Event-Driven* e integração massiva de dados assíncronos (ex: barramento Apache Kafka).
