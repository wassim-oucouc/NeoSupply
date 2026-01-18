NeoSupply – Documentation Technique (JWT)
1. Présentation du projet

NeoSupply est une application backend développée avec Spring Boot pour la gestion complète d’une chaîne logistique (Supply Chain).

Fonctionnalités principales :

Authentification sécurisée via JWT

Gestion des utilisateurs

Gestion des fournisseurs

Gestion des produits et inventaires

Gestion des entrepôts

Gestion des transporteurs

Gestion des commandes d’achat et de vente

Application conçue pour un déploiement Dockerisé.

2. Architecture

Architecture en couches :

Controller → Service → Repository → Database


Controller : exposition des API REST

Service : logique métier

DTO : objets de transfert (Request / Response)

Entity : entités JPA persistées

3. Authentification & Sécurité (JWT + Refresh Token)

NeoSupply utilise une authentification stateless basée sur JWT, avec un Refresh Token pour renouveler les accès sans redemander les identifiants.

3.1 Principe JWT + Refresh Token

L’utilisateur se connecte avec email et mot de passe

Le backend génère :

un Access Token (JWT) de courte durée

un Refresh Token stocké en base

Le client utilise l’Access Token pour accéder aux APIs protégées

À expiration, le client appelle /api/auth/refreshtoken avec le Refresh Token

Le serveur retourne un nouvel Access Token

Authorization: Bearer <ACCESS_TOKEN>

3.2 Endpoints d’authentification

Base URL : /api/auth

➤ Inscription

POST /api/auth/register

Public

Body : UsersDTO

Response : UserDtoResponse

➤ Connexion (Login)

POST /api/auth/login

Public

Body :

{
  "email": "user@mail.com",
  "password": "password123"
}


Response :

{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "c9f3b8c1-2d6f-4d2a-a0d9-xxxx"
}

➤ Rafraîchir le token

POST /api/auth/refreshtoken

Public

Body :

{
  "refreshToken": "c9f3b8c1-2d6f-4d2a-a0d9-xxxx"
}


Response :

{
  "accessToken": "new.jwt.token.here"
}

4. APIs protégées (JWT requis)

Toutes les APIs suivantes nécessitent un JWT valide.

4.1 Transporteurs (Carriers)

Base URL : /api/carriers

Méthode	Endpoint	Description
POST	/	Créer un transporteur
PUT	/{id}	Modifier un transporteur
GET	/	Liste de tous les transporteurs
GET	/{id}	Détails d’un transporteur
DELETE	/{id}	Supprimer un transporteur
POST	/{id}/activate	Activer un transporteur
POST	/{id}/deactivate	Désactiver un transporteur
POST	/{id}/assign-shipments	Assigner des livraisons

Tous les endpoints sont accessibles uniquement aux WAREHOUSE_MANAGER

4.2 Produits

Base URL : /product

Méthode	Endpoint	Description
POST	/create	Créer un produit
GET	/details/{id}	Détails d’un produit
PUT	/update/{id}	Modifier un produit
DELETE	/delete/{id}	Supprimer un produit
PATCH	/api/products/{sku}/desactive	Désactiver un produit

Seuls les ADMIN peuvent accéder à ces endpoints

4.3 Fournisseurs (Suppliers)

Base URL : /api/suppliers

Méthode	Endpoint	Description
GET	/	Liste de tous les fournisseurs
GET	/{id}	Détails d’un fournisseur
POST	/	Créer un fournisseur
PUT	/{id}	Modifier un fournisseur
DELETE	/{id}	Supprimer un fournisseur

Accès réservé aux WAREHOUSE_MANAGER

4.4 Entrepôts (Warehouses)

Base URL : /api/warehouses

Méthode	Endpoint	Description
POST	/	Créer un entrepôt
GET	/{id}	Détails d’un entrepôt
PUT	/{id}	Modifier un entrepôt
DELETE	/{id}	Supprimer un entrepôt
GET	/	Liste de tous les entrepôts

Accès réservé aux WAREHOUSE_MANAGER

4.5 Commandes d’achat (Purchase Orders)

Base URL : /purchaseorder

Méthode	Endpoint	Description
POST	/create	Créer une commande d’achat
PUT	/cancel/{purchaseId}	Annuler une commande
PUT	/approve/{purchaseId}/{warehouseId}	Approuver une commande

Accès réservé aux WAREHOUSE_MANAGER

4.6 Commandes de vente (Sales Orders)

Base URL : /api/sales-orders

Méthode	Endpoint	Description
POST	/	Créer une commande de vente
POST	/{id}/approve?carrierId=	Approuver une commande avec un transporteur
GET	/page?size=&page=	Pagination des commandes

Accès réservé aux WAREHOUSE_MANAGER

5. Bonnes pratiques & améliorations

Implémenter gestion complète des rôles : ADMIN, WAREHOUSE_MANAGER, CLIENT

OpenAPI / Swagger pour documentation dynamique

Centralisation des exceptions

Tests unitaires et de sécurité

Gestion avancée du Refresh Token
