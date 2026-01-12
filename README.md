# NeoSupply – Documentation Technique (JWT)

## 1. Présentation du projet

**NeoSupply** est une application backend développée avec **Spring Boot** pour la gestion d’une chaîne logistique (Supply Chain).

Fonctionnalités principales :

* Authentification sécurisée par JWT
* Gestion des utilisateurs
* Gestion des fournisseurs
* Gestion des produits et inventaires
* Gestion des entrepôts
* Gestion des transporteurs
* Gestion des commandes d’achat et de vente

Application conçue pour un déploiement **Dockerisé**.

---

## 2. Architecture

Architecture en couches :

```
Controller → Service → Repository → Database
```

* **Controller** : exposition API REST
* **Service** : logique métier
* **DTO** : Request / Response
* **Entity** : JPA

---

## 3. Authentification & Sécurité (JWT + Refresh Token)

NeoSupply utilise une authentification **stateless basée sur JWT** avec un **Refresh Token** pour renouveler les accès sans redemander les identifiants.

---

### 3.1 Principe JWT + Refresh Token

1. L’utilisateur se connecte avec email et mot de passe
2. Le backend génère :

   * un **Access Token (JWT)** de courte durée
   * un **Refresh Token** stocké en base
3. Le client utilise l’Access Token pour accéder aux APIs
4. À expiration, le client appelle `/refreshtoken` avec le Refresh Token
5. Un nouvel Access Token est généré

```
Authorization: Bearer <ACCESS_TOKEN>
```

---

### 3.2 Endpoints d’authentification

Base URL : `/api/auth`

#### ➤ Inscription

* **POST** `/api/auth/register`
* **Public**

---

#### ➤ Connexion (Login)

* **POST** `/api/auth/login`
* **Public**

**Body** :

```json
{
  "email": "user@mail.com",
  "password": "password123"
}
```

**Response** :

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "c9f3b8c1-2d6f-4d2a-a0d9-xxxx"
}
```

---

#### ➤ Rafraîchir le token

* **POST** `/api/auth/refreshtoken`
* **Public**

**Body** :

```json
{
  "refreshToken": "c9f3b8c1-2d6f-4d2a-a0d9-xxxx"
}
```

**Response** :

```json
{
  "accessToken": "new.jwt.token.here"
}
```

---

### 3.2 Endpoints d’authentification

#### ➤ Inscription

* **POST** `/user/register`
* **Public**

**Body** : `UsersDTO`

---

#### ➤ Connexion (Login)

* **POST** `/user/login`
* **Public**

**Body** :

```json
{
  "email": "user@mail.com",
  "password": "password123"
}
```

**Response** :

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

---

## 4. APIs protégées

Tous les endpoints suivants nécessitent un JWT valide.

### 4.1 Carriers

Base URL : `/api/carriers`

| Méthode | Endpoint                 | Description           |
| ------- | ------------------------ | --------------------- |
| POST    | `/`                      | Créer un transporteur |
| PUT     | `/{id}`                  | Modifier              |
| GET     | `/`                      | Liste                 |
| GET     | `/{id}`                  | Détails               |
| DELETE  | `/{id}`                  | Supprimer             |
| POST    | `/{id}/activate`         | Activer               |
| POST    | `/{id}/deactivate`       | Désactiver            |
| POST    | `/{id}/assign-shipments` | Assigner livraisons   |

---

### 4.2 Produits

| Méthode | Endpoint                        | Description |
| ------- | ------------------------------- | ----------- |
| POST    | `/product/create`               | Créer       |
| GET     | `/product/details/{id}`         | Détails     |
| PUT     | `/product/update/{id}`          | Modifier    |
| DELETE  | `/product/delete/{id}`          | Supprimer   |
| PATCH   | `/api/products/{sku}/desactive` | Désactiver  |

---

### 4.3 Suppliers

| Méthode | Endpoint                 |
| ------- | ------------------------ |
| GET     | `/supplier/all`          |
| GET     | `/supplier/details/{id}` |
| POST    | `/supplier/add`          |
| PUT     | `/supplier/update/{id}`  |
| DELETE  | `/supplier/delete/{id}`  |

---

### 4.4 Warehouses

| Méthode | Endpoint                  |
| ------- | ------------------------- |
| POST    | `/warehouse/create`       |
| GET     | `/warehouse/details/{id}` |
| PUT     | `/warehouse/update/{id}`  |
| DELETE  | `/warehouse/delete/{id}`  |
| GET     | `/warehouse/all`          |

---

### 4.5 Purchase Orders

| Méthode | Endpoint                                            |
| ------- | --------------------------------------------------- |
| POST    | `/purchaseorder/create`                             |
| PUT     | `/purchaseorder/cancel/{purchaseId}`                |
| PUT     | `/purchaseorder/approve/{purchaseId}/{warehouseId}` |

---

### 4.6 Sales Orders

Base URL : `/api/sales-orders`

| Méthode | Endpoint                   |
| ------- | -------------------------- |
| POST    | `/`                        |
| POST    | `/{id}/approve?carrierId=` |

---



#

## 7. Bonnes pratiques & améliorations

* Ajouter **Refresh Token**
* Gestion des rôles (ADMIN, USER)
* OpenAPI / Swagger
* Centralisation des exceptions
* Tests unitaires & sécurité
