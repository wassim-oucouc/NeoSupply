# NeoSupply – Backend Supply Chain

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-20.10-blue)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

---

## Table des matières

- [Présentation](#présentation)  
- [Fonctionnalités](#fonctionnalités)  
- [Technologies](#technologies)  
- [Architecture](#architecture)  
- [Installation & Lancement](#installation--lancement)  
- [Authentification JWT](#authentification-jwt)  
- [API Endpoints](#api-endpoints)  
- [Bonnes pratiques & améliorations](#bonnes-pratiques--améliorations)  
- [Licence](#licence)  

---

## Présentation

**NeoSupply** est une application backend développée avec **Spring Boot** pour la gestion complète d’une chaîne logistique (Supply Chain).  
Elle gère les utilisateurs, fournisseurs, produits, entrepôts, transporteurs et commandes d’achat/vente, avec une **authentification sécurisée via JWT**.  

---

## Fonctionnalités

- Authentification sécurisée avec **JWT + Refresh Token**  
- Gestion des utilisateurs et rôles (ADMIN, WAREHOUSE_MANAGER, CLIENT)  
- Gestion des fournisseurs et transporteurs  
- Gestion des produits et inventaires  
- Gestion des entrepôts  
- Gestion des commandes d’achat et de vente  
- Déploiement **Dockerisé**  

---

## Technologies

- **Java 17**  
- **Spring Boot 3.x**  
- **Spring Security (JWT)**  
- **Spring Data JPA / Hibernate**  
- **H2 / MySQL**  
- **Docker**  

---

## Architecture

Architecture en couches :

Controller → Service → Repository → Database

yaml
Copier le code

- **Controller** : expose les APIs REST  
- **Service** : logique métier  
- **DTO** : objets de transfert Request / Response  
- **Entity** : entités JPA persistées  

---

## Installation & Lancement

### Prérequis

- Java 17  
- Maven ou Gradle  
- Docker (optionnel)  

### Lancer localement

```bash
git clone https://github.com/ton-utilisateur/neosupply.git
cd neosupply
./mvnw clean install
./mvnw spring-boot:run
L’API sera accessible sur : http://localhost:8080

Lancer avec Docker
bash
Copier le code
docker build -t neosupply .
docker run -p 8080:8080 neosupply
Authentification JWT
NeoSupply utilise JWT pour sécuriser les endpoints.

Access Token : courte durée, utilisé pour authentifier les requêtes

Refresh Token : longue durée, permet de générer un nouvel Access Token

Endpoints d’authentification
Méthode	Endpoint	Description
POST	/api/auth/register	Créer un nouvel utilisateur
POST	/api/auth/login	Connexion (retourne access + refresh token)
POST	/api/auth/refreshtoken	Rafraîchir un token JWT

Exemple login :

bash
Copier le code
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"user@mail.com","password":"password123"}'
Réponse :

json
Copier le code
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "c9f3b8c1-2d6f-4d2a-a0d9-xxxx"
}
API Endpoints
Tous les endpoints protégés nécessitent un JWT valide

Transporteurs (Carriers)
Base URL : /api/carriers

Méthode	Endpoint	Description
POST	/	Créer un transporteur
PUT	/{id}	Modifier un transporteur
GET	/	Liste des transporteurs
GET	/{id}	Détails d’un transporteur
DELETE	/{id}	Supprimer un transporteur
POST	/{id}/activate	Activer un transporteur
POST	/{id}/deactivate	Désactiver un transporteur
POST	/{id}/assign-shipments	Assigner des livraisons

Accès : WAREHOUSE_MANAGER

Produits (Products)
Base URL : /product

Méthode	Endpoint	Description
POST	/create	Créer un produit
GET	/details/{id}	Détails d’un produit
PUT	/update/{id}	Modifier un produit
DELETE	/delete/{id}	Supprimer un produit
PATCH	/api/products/{sku}/desactive	Désactiver un produit

Accès : ADMIN

Fournisseurs (Suppliers)
Base URL : /api/suppliers

Méthode	Endpoint	Description
GET	/	Liste de tous les fournisseurs
GET	/{id}	Détails d’un fournisseur
POST	/	Créer un fournisseur
PUT	/{id}	Modifier un fournisseur
DELETE	/{id}	Supprimer un fournisseur

Accès : WAREHOUSE_MANAGER

Entrepôts (Warehouses)
Base URL : /api/warehouses

Méthode	Endpoint	Description
POST	/	Créer un entrepôt
GET	/{id}	Détails d’un entrepôt
PUT	/{id}	Modifier un entrepôt
DELETE	/{id}	Supprimer un entrepôt
GET	/	Liste de tous les entrepôts

Accès : WAREHOUSE_MANAGER

Commandes d’achat (Purchase Orders)
Base URL : /purchaseorder

Méthode	Endpoint	Description
POST	/create	Créer une commande d’achat
PUT	/cancel/{purchaseId}	Annuler une commande
PUT	/approve/{purchaseId}/{warehouseId}	Approuver une commande

Accès : WAREHOUSE_MANAGER

Commandes de vente (Sales Orders)
Base URL : /api/sales-orders

Méthode	Endpoint	Description
POST	/	Créer une commande de vente
POST	/{id}/approve?carrierId=	Approuver une commande avec un transporteur
GET	/page?size=&page=	Pagination des commandes

Accès : WAREHOUSE_MANAGER
