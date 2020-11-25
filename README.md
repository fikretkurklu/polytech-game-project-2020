
# GROUPE 1 - Idée de projet

  

## L'histoire

Le joueur a été choisi pour aller récupérer une idole volée par X qui s’est réfugié dans son donjon. Pour y accéder, il va devoir réussir un certain nombre de donjon avant de trouver X (Idée de base un donjon et s’il nous reste assez de temps complétez).

  

## But du jeu

Récupérer l'idole. Pour cela, il va devoir réussir différents donjon avant de trouver l’idole. A chacun, il va passer par différents essais dans un donjon très compliqué où il est obligé de revenir au village pour améliorer son équipement afin de réussir le donjon.

Le donjon a une difficulté de base très élevée. Les salles et les niveaux sont générés aléatoirement mais respectant les “codes” du donjon. C’est à dire que le donjon sera globalement toujours le même (l'archétype des salles ainsi que le nombre de monstres et de pièges), les salles ne seront jamais les même. Il pourra quitter le donjon en milieu de partie afin d’améliorer ses capacités au village cela oblige le joueur à perdre sa progression dans le donjon actuel (il peut revenir à la salle où il s’est arrêté moyennant de l’argent).

  

Une fois le premier donjon, le même schéma se répète pour le second, jusqu'à arriver au dernier donjon.

  
  

## La vue

  

**vue de côté / gestion de gravité**

  

Exemple de jeu :

  

![alt text](https://www.micromania.fr/on/demandware.static/-/Sites-Micromania-Library/default/dw5f36c6d2/fanzone/dossier/ori/ori-genial_Header.jpg "Ori and the blind forest")

>Ori and the blind forest, Jeu-Vidéo, 2015

  

![alt text](https://www.mobygames.com/images/promo/original/1481294587-3633937912.jpg "Rogue Legacy")

>Rogue Legacy, Jeu-Vidéo, 2013

  
  
  

## Trois maps de bases

  

* Le village (crafting, stuff, magasin, etc…) -> Le village est une fenêtre contenant les différentes options disponibles au joueur dans le village (via un menu sur la droite). Ici, le joueur peut vendre et acheter de l'équipement (arme & magic), se reposer (soins), gérer son personnage, “craft” des équipements et des potion, et partir à l’aventure.

  

![alt text](https://images-eu.ssl-images-amazon.com/images/I/A1iJN9BobVL.jpg "Shakes & Fidget")

>Shakes & Fidget, Jeu-Vidéo, 2009, Exemple de village au sein d'un jeu

  

* Donjon : Ici, le joueur doit atteindre la sortie en affrontant des monstres, et en sautant sur des plateformes. Il peut revenir à tout moment au village avec les équipements et l’argent récupérés dans le donjon. Il faut donc que le joueur choisisse le moment idéal pour revenir avec un maximum de ressources pour améliorer son équipement et ainsi réussir la nouvelle tentative du donjon. -> le donjon serait composé d’un nombre de salles aléatoires (le joueur (basées sur des archétypes : le placement des pièges et monstres placés aléatoirement mais position des plateformes fixes). Chaque salle serait plus grande que le viewport du joueur. Lorsque le joueur arrive dans une salle du donjon, il doit récupérer une clé qui est cachée sur l’un des monstres et s’en servir pour ouvrir la porte qui mène à la salle suivante (il ne peut pas faire marche arrière dès qu’il est entré dans une nouvelle salle, il est donc important que le joueur récupère un maximum d’argent dans la salle où il est avant de passer à la salle suivante). L’argent est récupéré en tuant des monstres. Moyennant une certaine somme d’argent, le joueur pourra également revenir à certains points clés de sa progression pour éviter qu’il ait à refaire les salles les plus faciles lorsque ses statistiques seront bien augmentées.

Le personnage pourra aussi récupérer de manière plus rare des fragments de clé du boss qui lui permettront une fois tous récupérer d’aller affronter le boss. Attention tout de même, les fragments de clé sont enchantés et de ce fait ceux-ci ne peuvent pas sortir du donjon, le retour au village du personnage entraîne donc la perte de tous les fragments de clé du joueur.

  
  

* Monde de la seconde chance : Ce monde sera accessible à la mort du joueur et offrira à celui-ci un défi pour essayer de récupérer les fragments de son âme afin de revenir à la vie. Dans ce mode, le joueur incarne une âme errante poursuivie par les fantômes des créatures tuées dans le monde normal (à chaque fois qu’une créature est tuée, un fantôme est généré dans le monde de la seconde chance). Ce monde est en vu de dessus ou le personnage cherche les fragments de son âme éparpillés sur la map avec des ennemis qui fonce vers le joueur quand celui-ci s’approche d’eux.

  
  

## Equipements

  

Dans le jeux, le joueur aura accès différents type d’objets:

  

1. Arcs : Long ou court. Les arcs long sont très puissants mais la vitesse d’attaque est lente. Pour les arcs cours c’est l’inverse.

2. Équipements : Armures, heaume, gant, jambières : augmente les résistances du joueur. Un mauvais équipement réduira sa vitesse de déplacement et d’attaque.

3. Consommables : Potions de soin, améliorations de stats.

  

## Le personnage

  

### Stats :

1. Résistance : Attaque Physique/Magique

2. Vie

3. Vitesse d'attaque (nb attaque/secondes)

4. Force

  

### Automates: A compléter

### Dans le donjon: A compléter

  

### Personnage:

- Déplacement: q et d concerne le déplacement à gauche et à droite

- Egg : tirer une flèche avec son arc (click & release du clic gauche de la souris)

- Pop : sauter (z)

- Wizz : ramasser un objet / activer un levier / manipuler des objets (clic droit)

  

### Ennemis:

- Déplacement horizontal des ennemis sur les plateformes (certains ennemis se dirigeront vers le joueur en volant par exemple)

- Pop : courir vers le joueur (pour certains ennemis)

- Wizz : attaquer/tirer une boule de feu, etc… (pour certains ennemis)

  

### Dans le monde de la seconde chance :

  

#### Personnage :

- Déplacement : z,q,s,d dans les 4 directions

- Egg: crée un leurre qui attire les fantômes vers lui et ainsi permet au joueur de se créer un ouverture pour récupérer des fragments d’âme.

- Pop: dash du joueur dans la direction vers laquelle il s’oriente

- Wizz: canalisation d’un fragment d’âme pour qu’il rejoigne son corps

  

#### Ennemis :

- Déplacement : déplacement aléatoire

- Pop : suivre le joueur quand il s’approche trop près

  

## Difficulté

  

- Gestion de la gravité

- Génération aléatoire des niveaus (gestion des objets etc...)

- Gestion du viewport

- Gestion de l'IA des ennemis

  

## Contrat

  

### Les différentes tâches à accomplir :

  

### Liste des tâches:

  

- Génération des salles aléatoire: Il y aura des “models” de salles mais le contenus sera aléatoire.  
	Solution: création de la classe Salle avec les différents archétypes de salle qui étendent cette classe (2 ou 3 au début, on aimerait en faire une dizaine si on a le temps)
-   Gestion du personnage avec gestion de la gravité
        Solution : Définir les deux stunts du personnage (un pour le donjon avec la gravité et un pour le monde de la seconde chance) et leurs automates associé
- Création de l’interface “village” avec les différents features: “Weapon Shop”, “Magic Shop”, “Infirmary”, “Aventure”. Dans les boutiques, il pourra payer pour reroll l'équipement proposé
 Solution : Interface avec des boutons qui sont gérés par des automates
 -   Génération des équipements:
 Chaque équipement aura une base (design, prix, stat) mais les bonus qu’il donne seront tirés aléatoirement (un bonus important aura moins de chance d'apparaître qu’une bonus plus faible) Le prix augmentera en fonction de la qualité des bonus. On trouvera Arc Long, Arc Court, Armure, Jambière, Heaume, Botte, Ceinture, gants.
    Solution : Classe équipement avec des sous-classes pour chaque type d’équipement (ici leurs automates ne font rien, les équipements augmentent juste les stats du joueur)
-   Gestion de la musique : la musique change lorsque le joueur est dans le village, dans le donjon, dans le monde de la seconde chance ou lorsqu’il combat un boss.
-   Gestion des consommables:
	Potion de soin, potion d’augmentation de stats, flèches magiques (étourdissante, perforantes)
	Solution : Classe Consommable avec des sous-classe par type de consommable et ajout d’un inventaire au joueur (sous forme de tableau potentiellement). Les consommables auront des automates basiques (un état non consommé puis un état de destruction)
-   Génération des ennemis interagissant avec le joueur. La puissance des ennemis sera croissante en fonction de l’avancement dans le donjon. Il y aura différent types d’ennemis :
ceux qui peuvent attaquer à distance
ceux qui attaquent au corps à corps.
  Solution : Classe Ennemi avec des sous-classes par type d’ennemis et automates avec pop (ennemi corps à corps fonce vers le joueur/Se déplacer pour les ennemis à distance) et egg (créer un projectile pour les ennemis à distance)
-   Construction des automates à partir de l’ast (soit par lecture de champs de l’arbre soit par utilisation des fonctions de Visitor)
- Génération du monde de la seconde chance :
 Remplissage du monde au fur et à mesure que le joueur tue des ennemis dans le donjon (les ennemis doivent apparaître aléatoirement dans l’autre monde et se déplace de manière aléatoire tant qu’ils n’ont pas repéré le joueur. Ceux-ci foncent sur lui s’il se rapproche trop). Pour éviter une surcharge du monde (à partir d’un certain nombre, les fantômes “fusionnent” et ainsi génèrent des fantômes plus fort (plus rapide et ayant de nouvelles attaques) (donc le nombres d’entités reste constant à partir d’un certain cap mais les ennemis sont plus fort).
Solution : Action egg dans le monde de la seconde chance qui crée un fantôme et automate associés à chaque fantôme avec pop (déplacement aléatoire des fantômes) et wizz (les fantômes foncent sur le joueur quand celui-ci se rapproche d’eux) et egg (les fantômes plus fort créent un projectile)
 -   Génération des fragments d’âmes qui apparaissent les uns après les autres sur la map de manière aléatoire.
    
-   Gestion des “leurres”: le joueur peut créer un leur qu’il peut envoyer la ou il veut afin d’attirer les âmes ennemis. Il en aura 3 en réserve, chaque leurre va rester 2 sec et se recharge sur 7-10 secondes.
-   Génération de l’interface graphique avec les sprites des personnages et des ennemis ainsi que les visuels des cartes des différents monde
    
-   Salle du boss : salle spécial avec le boss.

### Importance des tâches :

#### Priorité :

-   Génération des salles aléatoire
   
-   Construction des automates à partir de l’ast (soit par lecture de champs de l’arbre soit par utilisation des fonctions de Visitor) 

  

#### Nécessaire pour avoir une base fonctionnelle :

-   Gestion du personnage avec gestion de la gravité
    
-   Génération des ennemis interagissant avec le joueur
    
-   Génération du monde de la seconde chance
    

-   Création de l’interface “village”
    
-   Génération des équipements et argent
    

#### Important mais secondaire :

-   Salle du boss
    
-   Gestion de la musique

#### Le diagramme de gantt
![alt text](https://i.imgur.com/N5GZQdQ.jpg "Diagramme de Gantt")

>Diagramme de Gantt, Projet Groupe 1

## Automates

``` 
Player Donjon(Init){

* (Init):

| Key(q) ? Move(W) : (Init)

| Key(d) ? Move(E) : (Init)

| Key(s) ? Wizz : (Init)  //activer un levier ou ramasser un objet

| Key(z) ? 50% Pop / 50% Jump: (Init)  //saut du joueur

| Key(SPACE) ? Egg : (Init)  //tir du joueur

| Cell(H,A) ? Power : (Init)  //Collision et perte de vie

| ! GotPower() ? Explode () : ()  //Mort du joueur

}

Player Esprit(Init){

*(Init):

| Key(z) ? Move(N) : (Diag_N)

| Key(s) ? Move(S) : (Diag_S)

| Key(q) ? Move(W) : (Diag_W)

| Key(d) ? Move(E) : (Diag_E)

| Key(SPACE) ? Egg : (Init)

| Key(a) ? Pop : (Init)  //Dash dans la direction de la souris

| Key(e) ? Wizz : (Init)  //Canalisation de l’âme

| Cell(H,A) ? Power : (Init)  //Collision et perte de vie

*(Diag_N):

| Key(q) ? Move(W) : (Init)

| Key(d) ? Move(E) : (Init)

| True ? (Init)

*(Diag_S):

| Key(q) ? Move(W) : (Init)

| Key(d) ? Move(E) : (Init)

| True ? (Init)

*(Diag_W):

| Key(z) ? Move (N) : (Init)

| Key(s) ? Move(S) : (Init)

| True ? (Init)

*(Diag_E):

| Key(z) ? Move(N) : (Init)

| Key(s) ? Move(S) : (Init)

| True ? (Init)

}

Block (Inactif) {

*(Inactif) :

| True ? (Inactif)

}

  

Flèche (Init) {

*(Init)

| Cell(H,V) ? Move : (Init)

| Cell(H,_) ? Explode : ( )

}

  

Monstre(Init) {

* (Init):

| Closest(L, A) ? Pop(L) : (Init)  //Courir vers le joueur ou tirer en fonction du monstre

| Closest(R, A) ? Pop(R) : (Init)

| Closest(L, O) ? Rotate(R) : (Init) //Si on atteint le bord de la plateforme, on bouge dans l’autre sens (on crée des obstacles invisibles au bout des plateformes qui sont des obstacles uniquement pour les monstres ) on l’utilise pour détecter le bord d’une plateforme et éviter au monstre de tomber

| Closest(R, O) ? Rotate(L) : (Init)

| Cell(H, M) ? Power : (Init)  //Collision et perte de vie

| ! GotPower() ? Explode() : ()

| True ? Move : (Init)

}

  
  
  

Fantôme(State1) {

*(State1):

| Closest(N, A) ? Pop(N) : (secondMove) // Pop désigne l’action pour suivre le personnage principal (Move)

| Closest(W, A) ? Pop(W) : (secondMove)

| Closest(S, A) ? Pop(S) : (secondMove)

| Closest(E, A) ? Pop(E) : (secondMove)

  

*(secondMove)

| Closest(N, A)? Pop(N) : (Init) //Cet état gère les déplacements en diagonale

| Closest(W, A) ? Pop(W) : (Init)

| Closest(S, A)? Pop(S) : (Init)

| Closest(E, A) ? Pop(E) : (init)

}

  
  

Boss (Init) {

* (Init):

| Closest(L, A) ? Rotate(L) : (Action)

| Closest(R, A) ? Rotate(R) : (Action)

  

*(Action):

| True ? 3% Pop / 90% Move / 2% Egg :(Init) //Choix des attaques et mouvements du Boss

| Cell(H, M) ? Power : (Init)  //Collision avec les flèches du joueur

| ! GotPower() ? Explode () : ()

*():

}

```

