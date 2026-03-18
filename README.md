# Eventorias

Application Android de gestion d’événements développée en **Kotlin** avec **Jetpack Compose**.  
Eventorias permet de **créer, organiser, consulter et partager des événements** au sein d’une interface moderne, pensée pour être simple d’utilisation et accessible. 

---

## Aperçu

Eventorias a été conçu dans le cadre d’un projet de formation Android avec pour objectif de produire une application :

- moderne,
- maintenable,
- structurée selon les bonnes pratiques Android,
- accessible au plus grand nombre. 

L’application propose notamment :
- une authentification utilisateur,
- une liste d’événements,
- un écran de création,
- la gestion du profil,
- une synchronisation avec Firebase.  
Les captures visibles dans la présentation montrent l’écran de connexion, la liste des événements et l’écran de création. 

---

## Fonctionnalités

- Création d’événements
- Consultation de la liste des événements
- Gestion des informations d’un événement : titre, description, date, heure, adresse
- Mise à jour et suppression d’événements
- Synchronisation avec Firebase
- Authentification utilisateur
- Prise en compte de l’accessibilité utilisateur 
---

## Stack technique

- **Langage** : Kotlin
- **UI** : Jetpack Compose
- **Architecture** : MVVM + Clean Architecture
- **Injection de dépendances** : Hilt
- **Base de données / backend** : Firebase
- **Réseau / services** : Web services
- **Tests** : JUnit 

---

## Architecture

L’application est construite autour d’une architecture **MVVM** associée à une **Clean Architecture**.  
Le schéma de la présentation montre clairement la séparation entre :

- **Model**
- **ViewModel**
- **View**
- **classes d’état UI** 

### Organisation globale

- **Model** : gestion des données métier et des sources de données
- **Repository** : abstraction de l’accès aux données
- **UseCases** : logique métier
- **ViewModel** : gestion de l’état et exposition des données à l’UI
- **View (Compose)** : affichage des écrans et interaction utilisateur :contentReference[oaicite:7]{index=7}

### Mappers

Des mappers sont utilisés pour transformer les données entre les différentes couches :

- `Document -> Event`
- `Event -> UiModel` 

### Exemples de composants visibles dans la présentation

**ViewModels :**
- `AddViewModel`
- `AuthViewModel`
- `DetailViewModel`
- `EventListViewModel`
- `UserProfileViewModel`

**Screens :**
- `AddScreen.kt`
- `AuthScreen.kt`
- `DetailScreen.kt`
- `EventListScreen.kt`
- `UserProfileScreen.kt`
- `HomeScreen.kt` :contentReference[oaicite:9]{index=9}

### Use Cases

La présentation montre également une organisation par cas d’usage, par exemple :

- `AddEventUseCase`
- `GetAllEventsUseCase`
- `GetEventByIdUseCase`
- plusieurs use cases liés à l’utilisateur et à l’authentification. 

---

## Points forts du projet

- Mise en place d’une architecture **MVVM / Clean Architecture**
- Utilisation de **composants réutilisables**
- Intégration de **Hilt**
- Respect des bonnes pratiques Android
- Réflexion autour de l’**accessibilité**
- Projet structuré avec séparation claire des responsabilités 

---

## Difficultés rencontrées

Au cours du développement, plusieurs points ont demandé une attention particulière :

- intégration de Firebase UI,
- gestion de la dépréciation de certaines classes,
- contrainte de temps de développement. 

### Solutions apportées

- appui sur la documentation Android,
- échanges avec le mentor,
- recherche sur GitHub,
- accompagnement OpenClassrooms. 

---

## Captures d’écran

Ajoute ici tes images pour rendre le repo plus fort visuellement :

### Écran de connexion
![Connexion](./screenshots/login.png)

### Liste des événements
![Liste des événements](./screenshots/event_list.png)

### Création d’un événement
![Création](./screenshots/create_event.png)



---

## Lancer le projet

### Prérequis

- Android Studio
- JDK 17 recommandé
- Compte Firebase configuré
- Fichier de configuration Google si nécessaire

### Installation

```bash
git clone https://github.com/HRDF88/eventorias.git
