# Sistema de Notas

Sistema para gerenciar notas de alunos com backend Java (API REST) e frontend Flutter.

## Instruções para Executar o Sistema

### Pré-requisitos:
- JDK 8 ou superior instalado
- Flutter SDK (para frontend)

### Backend Java:

1. **Compilar:**
```bash
cd backend/src/main/java
"C:\Program Files\Java\jdk1.8.0_341\bin\javac.exe" com\sistema\*.java
```

2. **Executar servidor HTTP:**
```bash
cd backend/src/main/java
java com.sistema.ServidorNotas
```
Servidor roda em: `http://localhost:8080`

3. **Executar versão linha de comando:**
```bash
cd backend/src/main/java
java com.sistema.SistemaNotas < ../../exemplo_entrada.txt
```

### Frontend Flutter:

```bash
cd frontend
flutter run
```

### API Endpoints:
- **POST** `/api/alunos` - Adicionar aluno (formato: Nome,Nota1,Nota2,Nota3,Nota4,Nota5,Frequencia)
- **GET** `/api/resultados` - Obter resultados (JSON)

## Premissas Assumidas

- Cada aluno possui exatamente 5 notas
- Frequência é expressa em porcentagem (0-100)
- Frequência mínima aceitável é 75%
- Entrada de dados no formato: `Nome Nota1 Nota2 Nota3 Nota4 Nota5 Frequencia`
- Sistema suporta múltiplos alunos por sessão

## Decisões de Projeto

### Arquitetura:
- **Backend**: Java puro com HttpServer nativo para simplicidade
- **Frontend**: Flutter para interface multiplataforma
- **Comunicação**: API REST com JSON

### Estrutura de Classes:
- `Aluno`: Encapsula dados e cálculo de média individual
- `SistemaNotas`: Gerencia coleção de alunos e cálculos da turma
- `ServidorNotas`: Servidor HTTP com endpoints REST

### Funcionalidades Implementadas:
- Média individual de cada aluno
- Média da turma por disciplina
- Lista de alunos acima da média geral
- Lista de alunos com frequência < 75%

## Informações Importantes

### Compatibilidade:
- Java 8+ (testado com JDK 1.8.0_341)
- CORS habilitado para acesso do Flutter
- Servidor HTTP simples sem dependências externas

### Limitações:
- Dados não são persistidos (apenas em memória)
- Servidor single-threaded
- Validação básica de entrada

### Exemplo de Uso da API:

**Adicionar aluno:**
```bash
curl -X POST http://localhost:8080/api/alunos -d "João,7,8,6,9,7,85"
```

**Obter resultados:**
```bash
curl http://localhost:8080/api/resultados
```

### Estrutura do Projeto:
```
backend/
├── src/main/java/com/sistema/
│   ├── Aluno.java
│   ├── SistemaNotas.java
│   └── ServidorNotas.java
├── exemplo_entrada.txt
└── README.md
```

### Amazon Q:
- Foi utilizado o Amazon Q para auxiliar no desenvolvimento do sistema