# HamburgueriaZ

Projeto desenvolvido em aula prática da disciplina de Desenvolvimento Mobile

Instituição: Ampli by Anhanguera

Este repositório contém um aplicativo Android simples criado como atividade de
sala de aula. O objetivo do projeto é implementar a interface de um app para
uma hamburgueria permitindo que clientes façam pedidos diretamente pelo app.

## Objetivo

O propósito deste projeto é servir como exercício prático para a disciplina de
Desenvolvimento Mobile, onde os alunos aprendem a criar interfaces modernas com
componentes do Material Design, manipular `Views`, calcular valores e usar
`Intents` para integrar com outros apps do dispositivo (por exemplo, enviar
pedidos por e-mail).

## O que foi implementado (fase atual)

- Interface com campo para o nome do cliente.
- Lista de adicionais (checkboxes) — queijo, bacon, onion rings.
- Controle de quantidade com botões + / - e exibição da quantidade.
- Cálculo do preço total com base no preço base e adicionais.
- Exibição do resumo do pedido na própria tela.
- Ação de "Enviar pedido" que abre o app de e-mail com assunto e corpo preenchidos
  usando um `Intent` do tipo `ACTION_SENDTO`.
- Banner no topo da tela com a logo da hamburgueria.

> Observação: o app atualmente implementa apenas a interface e a lógica local de
> cálculo/geração de resumo; funcionalidades de backend/persistência podem ser
> adicionadas em etapas futuras.

## Como compilar e rodar

1. Abra o projeto no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Conecte um dispositivo Android ou inicie um emulador.
4. Execute o app (`Run` > `app`).

Ou via terminal (PowerShell):

```powershell
cd "C:\Users\rodri\AndroidStudioProjects\HamburgueriaZ"
.\gradlew assembleDebug
.\gradlew installDebug
```

## Licença / Observações

Este projeto foi criado para fins didáticos em sala de aula. Não há licença
especificada; se desejar adicionar uma, informe qual preferir.
