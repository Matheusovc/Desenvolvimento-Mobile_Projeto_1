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

- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- ViewModel + StateFlow
- Kotlin Coroutines
- JUnit + kotlinx-coroutines-test (testes unitários)

Ainda não há Room, Retrofit ou injeção de dependência (Hilt) — serão adicionados quando o app realmente precisar (persistência local e API), evitando dependências desnecessárias nesta fase.

## Arquitetura

O projeto segue uma variação de MVVM com Repository Pattern:

```
UI (Composable) → ViewModel → Repository (interface) → Fonte de dados (mock hoje, API futuramente)
```

- As telas (Composables) não acessam dados diretamente; só observam `StateFlow` expostos pelos ViewModels.
- Os ViewModels dependem de **interfaces** de repositório (`TicketRepository`, `AuthRepository`), nunca de implementações concretas.
- As implementações mock (`MockTicketRepository`, `MockAuthRepository`) simulam uma fonte remota (com pequeno delay). Trocar por uma implementação baseada em Retrofit no futuro não deve exigir mudanças nas telas.
- Dependências são fornecidas por um `AppContainer` simples (DI manual), suficiente para o tamanho atual do projeto.

## Estrutura do projeto

```
com.fieldservice.app/
├── data/
│   ├── AppContainer.kt          # provedor manual das dependências
│   ├── mock/                    # dados de exemplo (MOCK)
│   └── repository/              # implementações mock dos repositórios
├── domain/
│   ├── model/                   # Ticket, TicketStatus, Priority, Technician
│   └── repository/              # interfaces TicketRepository, AuthRepository
├── presentation/
│   ├── navigation/               # rotas centralizadas + grafo de navegação
│   ├── login/ | home/ | tickets/ | ticketdetails/ | profile/
│   └── UiState.kt                # estado genérico (Loading/Success/Error/Empty)
├── ui/
│   ├── components/                # componentes reutilizáveis (botão, campo de texto,
│   │                               #   card de chamado, badges de prioridade/status, estados de UI)
│   └── theme/                     # cores, tipografia e tema Material 3 do FieldService
└── utils/                         # utilitários (ex.: formatação de data)
```

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

Implementado nesta etapa: login mock, navegação entre Home/Chamados/Perfil, listagem e detalhes de chamados, ação de aceitar chamado, tema visual próprio e testes unitários básicos (ViewModel e repositório).

Ainda não implementado (propositalmente, fora do escopo desta etapa): backend/API real, banco de dados local (Room), autenticação real, geolocalização, notificações, upload de evidências, funcionamento offline e sincronização.
