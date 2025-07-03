# API REST - Gestion d'équipe de football

## Fonctionnalités
- Ajouter une équipe (avec ou sans joueurs)
- Récupérer une liste paginée des équipes avec tri
- Ajouter un joueur associé à une équipe
- Récupérer une liste paginée des joueurs avec tri

## Tech Stack
- Java 17
- Spring Boot 3
- Hibernate + PostgreSQL
- MapStruct
- Lombok

## Installation
1. Installer PostgreSQL et créer une base `gestion_foot`
2. Modifier les identifiants dans `src/main/resources/application.properties`
3. Lancer l’application :`./mvnw spring-boot:run`

## Endpoints principaux
`http://localhost:8080/equipes`
`http://localhost:8080/joueurs`

## Exemple GET - Lister les équipe

`GET http://localhost:8080/equipes?page=0&size=5&sortBy=name`

## Exemple POST - Créer une équipe
`POST http://localhost:8080/equipes`

`{
    "name": "Paris",
    "acronym": "PRS",
    "budget": 500,
    "joueursDTOList": [{
        "name": "Player One",
        "position": "Attaquant",
        "equipeId": null
    }]
}`

## Exemple GET - Lister les joueurs

`GET http://localhost:8080/joueurs?page=0&size=5&sortBy=equipe`

## Exemple POST - Créer un joueur
`POST http://localhost:8080/joueurs`
`{
    "name": "Alice Smith",
    "position": "Milieu",
    "equipeId": 1
}`


```bash