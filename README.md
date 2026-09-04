# FieldService

Aplicativo Android para técnicos de campo acompanharem e executarem chamados de assistência técnica (Field Service Management). Este repositório contém a **primeira versão** do app: a base do cliente Android, com dados mockados e arquitetura preparada para receber uma API real no futuro.

## Objetivo

Ajudar empresas com técnicos externos a organizar e acompanhar chamados de assistência técnica. Nesta etapa, o foco é oferecer uma base sólida — arquitetura, navegação e telas principais — para o técnico visualizar seus chamados e iniciar o atendimento.

## Principais funcionalidades

- Login (mock, sem backend)
- Home do técnico com resumo do dia (pendentes / em atendimento / concluídos) e chamados prioritários
- Lista de chamados com filtros (Todos / Pendentes / Em atendimento / Concluídos)
- Detalhes do chamado, com ação de aceitar um chamado atribuído
- Perfil do técnico com logout

Funcionalidades como deslocamento, chegada, diagnóstico, evidências, peças utilizadas e sincronização com backend **ainda não foram implementadas** — fazem parte dos próximos ciclos do projeto.

## Tecnologias

- Java
- Android SDK (AndroidX)
- XML Layouts + Material Components (Material 3)
- View Binding
- RecyclerView
- ViewModel + LiveData
- Repository Pattern
- JUnit (testes unitários)

Ainda não há Room, Retrofit ou injeção de dependência (Hilt) — serão adicionados quando o app realmente precisar (persistência local e API), evitando dependências desnecessárias nesta fase.

## Arquitetura

O projeto segue uma variação de MVVM com Repository Pattern:

```
UI (Activity + XML) → ViewModel → Repository (interface) → Fonte de dados (mock hoje, API futuramente)
```

- As telas (Activities) não acessam dados diretamente; só observam `LiveData` exposto pelos ViewModels.
- Os ViewModels dependem de **interfaces** de repositório (`TicketRepository`, `AuthRepository`), nunca de implementações concretas.
- As implementações mock (`MockTicketRepository`, `MockAuthRepository`) simulam a fonte de dados. Trocar por uma implementação baseada em Retrofit no futuro não deve exigir mudanças nas telas.
- Dependências são fornecidas por um `AppContainer` simples (DI manual), suficiente para o tamanho atual do projeto.

## Estrutura do projeto

```
com.fieldservice.app/
├── data/
│   ├── AppContainer.java        # provedor manual das dependências
│   ├── mock/                    # dados de exemplo (MOCK)
│   └── repository/              # implementações mock dos repositórios
├── domain/
│   ├── model/                   # Ticket, TicketStatus, Priority, Technician
│   └── repository/              # interfaces TicketRepository, AuthRepository
├── presentation/
│   ├── login/ | home/ | tickets/ | ticketdetails/ | profile/
│   └── UiState.java             # estado genérico (Loading/Success/Error/Empty)
├── ui/
│   └── components/              # componentes reutilizáveis (adapter, badges de prioridade/status)
└── utils/                       # utilitários (ex.: formatação de data)
```

O código Kotlin/Compose da fase anterior do projeto foi preservado em `legacy-kotlin/` (fora do módulo compilado) apenas como referência histórica; ele não faz parte do build.

## Identidade visual

O app tem uma única identidade visual **dark mode**, aplicada de forma consistente independentemente do tema do sistema. As cores ficam centralizadas em `res/values/colors.xml` (e replicadas em `res/values-night/colors.xml`), nunca embutidas nos layouts ou nas classes Java.

## Como executar

1. Abra a pasta do projeto no Android Studio.
2. Aguarde a sincronização do Gradle (**File → Sync Project with Gradle Files**).
3. Crie ou selecione um emulador (**Device Manager**) ou conecte um dispositivo físico com depuração USB.
4. Rode o módulo `app` (▶️ ou `Shift+F10`).
5. Na tela de login, use as credenciais mock:
   - **E-mail:** `tecnico@fieldservice.com`
   - **Senha:** `123456`

## Status do desenvolvimento

🚧 Em desenvolvimento — primeira versão.

Implementado nesta etapa: login mock, navegação Home → Chamados → Detalhes → Perfil → Logout, listagem e detalhes de chamados, ação de aceitar chamado, tema visual próprio (dark) e testes unitários básicos (ViewModel, repositório e filtros).

Ainda não implementado (propositalmente, fora do escopo desta etapa): backend/API real, banco de dados local (Room), autenticação real, geolocalização, notificações, upload de evidências, funcionamento offline e sincronização.
