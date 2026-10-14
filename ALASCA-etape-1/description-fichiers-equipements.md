# Description des fichiers du dossier `equipments`

Ce document decrit l'utilite des fichiers presents dans :

```text
HEM-2026-etape1-21092026-src/
└── hem-2026-e1/
    └── fr/sorbonne_u/components/hem2026e1/equipments/
```

Les packages representent les equipements domestiques, le gestionnaire HEM et les classes BCM necessaires pour les connecter.

## Organisation generale

```text
equipments/
├── batteries/
├── generator/
├── hairdryer/
├── heater/
├── hem/
├── meter/
├── solar_panel/
└── weather_station/
```

## Vocabulaire des fichiers

| Type de fichier | Utilite |
|---|---|
| `<Equipment>.java` | Implementation concrete du composant BCM et de son etat interne. |
| `<Equipment>CI.java` | Interface de composant : contrat des services visibles par les autres composants. |
| `<Equipment>ImplementationI.java` | Interface des operations que l'implementation concrete doit fournir. |
| `<Equipment>ConfigurationI.java` | Constantes et unites de mesure propres a l'equipement. |
| `InboundPort.java` | Port entrant qui expose les services du composant serveur. |
| `OutboundPort.java` | Port sortant utilise par un composant client pour appeler le serveur. |
| `Connector.java` | Objet BCM qui relie le port sortant au port entrant. |
| `UnitTester.java` ou `Tester.java` | Composant qui execute les tests de l'equipement via son port sortant. |
| `CVMUnitTest.java` | CVM qui cree l'equipement et son testeur pour un test isole. |
| `package.html` | Documentation Javadoc du package. |

## Relations entre les fichiers

```text
ConfigurationI
      |
      v
ImplementationI <-------- Equipment.java
      |                         |
      |                         +-- InboundPort
      v                         |
EquipmentCI <-------------------+
      ^
      |
OutboundPort <-- Connector <-- InboundPort
      ^
      |
HEM ou UnitTester
```

Le chemin d'un appel est donc :

```text
Client
  -> OutboundPort
  -> Connector
  -> InboundPort
  -> Equipement.java
  -> ImplementationI et etat interne
```

---

# 1. Package `batteries`

Chemin : `hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/`

Le package represente un stockage d'energie. Il gere la capacite, la charge, la decharge et l'etat des batteries.

## Fichiers principaux

| Fichier | Utilite |
|---|---|
| [Batteries.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/Batteries.java) | Composant BCM principal. Il contient l'etat courant, la capacite nominale, la puissance de charge et la puissance maximale de sortie. Il publie `BatteriesInboundPort`. |
| [BatteriesCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/BatteriesCI.java) | Contrat public des batteries : capacite nominale, capacite courante, charge, decharge et niveau de charge. |
| [BatteriesImplementationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/BatteriesImplementationI.java) | Contrat interne de l'implementation. Il reprend les operations de `BatteriesCI` et verifie notamment qu'une batterie ne charge et ne decharge pas simultanement. |
| [BatteriesConfigurationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/BatteriesConfigurationI.java) | Definit les unites de capacite, tension et puissance utilisees par les batteries. |
| [BatteriesUnitTester.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/BatteriesUnitTester.java) | Composant de test qui appelle les batteries via `BatteriesOutboundPort` et verifie les capacites et etats. |
| [CVMUnitTest.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/CVMUnitTest.java) | Lance une batterie et son testeur dans une CVM isolee. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/package.html) | Documentation Javadoc du composant batteries. |

## Fichiers `batteries/connections`

| Fichier | Utilite |
|---|---|
| [BatteriesInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/connections/BatteriesInboundPort.java) | Recoit les appels `BatteriesCI` et les delegue au composant `Batteries`. |
| [BatteriesOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/connections/BatteriesOutboundPort.java) | Permet au HEM ou au testeur d'appeler les services du composant batteries. |
| [BatteriesConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/connections/BatteriesConnector.java) | Traduit les appels du port sortant vers le port entrant des batteries. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/batteries/connections/package.html) | Documentation Javadoc du package de connexion des batteries. |

---

# 2. Package `generator`

Chemin : `hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/`

Le package represente un generateur electrique avec reservoir de carburant et puissance de production.

## Fichiers principaux

| Fichier | Utilite |
|---|---|
| [Generator.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/Generator.java) | Composant BCM principal. Il gere l'etat, le reservoir, la puissance produite et la consommation de carburant. |
| [GeneratorCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/GeneratorCI.java) | Contrat public du generateur : etat, reservoir, tension, puissance et consommation. |
| [GeneratorImplementationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/GeneratorImplementationI.java) | Contrat interne des operations du generateur. |
| [GeneratorConfigurationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/GeneratorConfigurationI.java) | Definit les unites watts, volts, litres et litres par heure. |
| [GeneratorState.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/GeneratorState.java) | Enumeration des etats `OFF`, `TANK_EMPTY`, `IDLE` et `PRODUCING`. |
| [GeneratorUnitTester.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/GeneratorUnitTester.java) | Teste le generateur via son port sortant et son connecteur. |
| [CVMUnitTest.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/CVMUnitTest.java) | Cree le generateur et son testeur pour un test independant. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/package.html) | Documentation Javadoc du generateur. |

## Fichiers `generator/connections`

| Fichier | Utilite |
|---|---|
| [GeneratorInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/connections/GeneratorInboundPort.java) | Expose `GeneratorCI` et transmet les appels au generateur. |
| [GeneratorOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/connections/GeneratorOutboundPort.java) | Permet au HEM ou au testeur d'appeler le generateur. |
| [GeneratorConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/connections/GeneratorConnector.java) | Transporte les appels entre outbound port et inbound port. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/generator/connections/package.html) | Documentation du package de connexion du generateur. |

---

# 3. Package `meter`

Chemin : `hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/`

Le package represente le compteur electrique du logement.

| Fichier | Utilite |
|---|---|
| [ElectricMeter.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/ElectricMeter.java) | Composant principal du compteur. Il conserve la tension et expose les mesures de consommation et de production. |
| [ElectricMeterCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/ElectricMeterCI.java) | Contrat public pour lire la tension, la consommation et la production. |
| [ElectricMeterImplementationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/ElectricMeterImplementationI.java) | Contrat interne des operations du compteur. |
| [ElectricMeterConfigurationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/ElectricMeterConfigurationI.java) | Definit les unites amperes et volts du compteur. |
| [ElectricMeterUnitTester.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/ElectricMeterUnitTester.java) | Verifie les mesures fournies par le compteur. |
| [CVMUnitTest.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/CVMUnitTest.java) | Lance le compteur et son testeur dans une CVM de test. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/package.html) | Documentation Javadoc du compteur. |

## Fichiers `meter/connections`

| Fichier | Utilite |
|---|---|
| [ElectricMeterInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/connections/ElectricMeterInboundPort.java) | Recoit les appels `ElectricMeterCI` et les transmet au compteur. |
| [ElectricMeterOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026e1/equipments/meter/connections/ElectricMeterOutboundPort.java) | Utilise par le HEM ou le testeur pour appeler le compteur. |
| [ElectricMeterConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026e1/equipments/meter/connections/ElectricMeterConnector.java) | Relie le port sortant du client au port entrant du compteur. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/meter/connections/package.html) | Documentation du package de connexion du compteur. |

---

# 4. Package `solar_panel`

Chemin : `hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/`

Le package represente un panneau solaire et sa puissance de production.

| Fichier | Utilite |
|---|---|
| [SolarPanel.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/SolarPanel.java) | Composant principal du panneau solaire. Il gere la surface, la capacite nominale et la production courante. |
| [SolarPanelCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/SolarPanelCI.java) | Contrat public pour lire la surface et la puissance produite. |
| [SolarPanelImplementationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/SolarPanelImplementationI.java) | Contrat interne des operations du panneau solaire. |
| [SolarPanelConfigurationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/SolarPanelConfigurationI.java) | Definit l'unite watts et les constantes de configuration solaire. |
| [SolarPanelUnitTester.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/SolarPanelUnitTester.java) | Teste le nombre de metres carres et la production du panneau. |
| [CVMUnitTest.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/CVMUnitTest.java) | Cree un panneau d'une surface donnee et son testeur. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/package.html) | Documentation Javadoc du panneau solaire. |

## Fichiers `solar_panel/connections`

| Fichier | Utilite |
|---|---|
| [SolarPanelInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/connections/SolarPanelInboundPort.java) | Expose `SolarPanelCI` sur le composant solaire. |
| [SolarPanelOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/connections/SolarPanelOutboundPort.java) | Permet au HEM ou au testeur de lire la production solaire. |
| [SolarPanelConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/connections/SolarPanelConnector.java) | Relie les ports de communication du panneau solaire. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/solar_panel/connections/package.html) | Documentation du package de connexion solaire. |

---

# 5. Package `weather_station`

Chemin : `hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/`

Le package fournit les temperatures interieure et exterieure du logement.

| Fichier | Utilite |
|---|---|
| [WeatherStation.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/WeatherStation.java) | Composant BCM principal de la station meteorologique. Il conserve les mesures de temperature et publie son port entrant. |
| [WeatherStationCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/WeatherStationCI.java) | Contrat public pour lire les temperatures interieure et exterieure. |
| [WeatherStationImplementationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/WeatherStationImplementationI.java) | Contrat interne des operations de la station. |
| [WeatherStationConfigurationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/WeatherStationConfigurationI.java) | Definit l'unite Celsius et la configuration des temperatures. |
| [WeatherStationUnitTester.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/WeatherStationUnitTester.java) | Teste les temperatures fournies par la station. |
| [CVMUnitTest.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/CVMUnitTest.java) | Cree la station et son testeur dans une CVM isolee. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/package.html) | Documentation Javadoc de la station meteorologique. |

## Fichiers `weather_station/connections`

| Fichier | Utilite |
|---|---|
| [WeatherStationInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/connections/WeatherStationInboundPort.java) | Expose les services de la station. |
| [WeatherStationOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/connections/WeatherStationOutboundPort.java) | Permet au HEM ou au testeur de lire les temperatures. |
| [WeatherStationConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/connections/WeatherStationConnector.java) | Transporte les appels entre client et station. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/weather_station/connections/package.html) | Documentation du package de connexion meteorologique. |

---

# 6. Package `hairdryer`

Chemin : `hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/`

Le seche-cheveux represente un equipement utilisateur non ajustable par le HEM dans cette etape.

| Fichier | Utilite |
|---|---|
| [HairDryer.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/HairDryer.java) | Composant principal du seche-cheveux. Il conserve l'etat `ON/OFF` et le mode `LOW/HIGH`. |
| [HairDryerUserCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/HairDryerUserCI.java) | Contrat des commandes utilisateur du seche-cheveux. |
| [HairDryerImplementationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/HairDryerImplementationI.java) | Contrat interne de l'implementation du seche-cheveux. |
| [HairDryerConfigurationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/HairDryerConfigurationI.java) | Definit les puissances, la tension et les valeurs initiales. |
| [HairDryerState.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/HairDryerState.java) | Enumeration des etats du seche-cheveux. |
| [HairDryerMode.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/HairDryerMode.java) | Enumeration des modes de puissance du seche-cheveux. |
| [HairDryerTester.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/HairDryerTester.java) | Testeur et simulateur des actions utilisateur. Il utilise aussi une horloge pour planifier les actions. |
| [CVMUnitTest.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/CVMUnitTest.java) | Lance le seche-cheveux et son testeur. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/package.html) | Documentation Javadoc du seche-cheveux. |

## Fichiers `hairdryer/connections`

| Fichier | Utilite |
|---|---|
| [HairDryerInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/connections/HairDryerInboundPort.java) | Expose les commandes utilisateur du seche-cheveux. |
| [HairDryerOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/connections/HairDryerOutboundPort.java) | Permet au testeur d'appeler les commandes utilisateur. |
| [HairDryerConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/connections/HairDryerConnector.java) | Relie le testeur au seche-cheveux. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hairdryer/connections/package.html) | Documentation du package de connexion du seche-cheveux. |

---

# 7. Package `heater`

Chemin : `hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/`

Le chauffage est l'equipement le plus riche du projet. Il expose trois familles de services : utilisateur, controle interne et controle externe.

## Fichiers principaux du chauffage

| Fichier | Utilite |
|---|---|
| [Heater.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/Heater.java) | Composant principal. Il gere l'etat `OFF`, `ON`, `HEATING`, la temperature cible, la temperature courante et la puissance. |
| [HeaterConfigurationI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterConfigurationI.java) | Definit les unites, temperatures limites et puissance maximale du chauffage. |
| [HeaterStateI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterStateI.java) | Contrat interne qui decrit les informations d'etat du chauffage. |
| [HeaterUserCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterUserCI.java) | Interface offerte aux utilisateurs : marche, arret, temperature cible et consultation de l'etat. |
| [HeaterUserI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterUserI.java) | Interface d'implementation des services utilisateur. |
| [HeaterUserJava4CI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterUserJava4CI.java) | Variante Java 4 de l'interface utilisateur, utile pour la generation dynamique de connecteurs. |
| [HeaterInternalControlCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterInternalControlCI.java) | Contrat des informations et commandes de controle interne. |
| [HeaterInternalControlI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterInternalControlI.java) | Interface d'implementation du controle interne. |
| [HeaterExternalControlCI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterExternalControlCI.java) | Contrat du controle externe : puissance, suspension et etat du chauffage. |
| [HeaterExternalControlI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterExternalControlI.java) | Interface d'implementation du controle externe. |
| [HeaterExternalControlJava4CI.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterExternalControlJava4CI.java) | Variante compatible Java 4 utilisee par le connecteur personnalise et l'adaptation dynamique. |
| [HeaterUnitTester.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/HeaterUnitTester.java) | Execute les scenarios du chauffage : modes, suspension, reprise et urgence. |
| [CVMUnitTest.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/CVMUnitTest.java) | Cree le chauffage et son testeur dans une CVM isolee. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/package.html) | Documentation Javadoc du chauffage. |

## Fichiers `heater/connections`

| Fichier | Utilite |
|---|---|
| [HeaterUserInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterUserInboundPort.java) | Port entrant standard des commandes utilisateur. |
| [HeaterUserJava4InboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterUserJava4InboundPort.java) | Port entrant utilisateur compatible avec les contraintes Java 4 de la generation dynamique. |
| [HeaterUserOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterUserOutboundPort.java) | Port sortant utilise par un client utilisateur. |
| [HeaterUserConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterUserConnector.java) | Relie un client utilisateur au port entrant utilisateur. |
| [HeaterInternalControlInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterInternalControlInboundPort.java) | Expose les services de controle interne. |
| [HeaterInternalControlOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterInternalControlOutboundPort.java) | Permet a un composant de demander les informations internes du chauffage. |
| [HeaterInternalControlConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterInternalControlConnector.java) | Transporte les appels de controle interne. |
| [HeaterExternalControlInboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterExternalControlInboundPort.java) | Expose l'interface de controle externe du chauffage. |
| [HeaterExternalControlJava4InboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterExternalControlJava4InboundPort.java) | Version Java 4 du port entrant de controle externe. |
| [HeaterExternalControlOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterExternalControlOutboundPort.java) | Port sortant pour appeler les operations de controle externe. |
| [HeaterExternalControlConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/HeaterExternalControlConnector.java) | Relie un client aux services de controle externe. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/heater/connections/package.html) | Documentation des connexions du chauffage. |

---

# 8. Package `hem`

Chemin : `hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hem/`

Ce package contient le gestionnaire d'energie domestique et les classes qui lui permettent de controler les equipements.

| Fichier | Utilite |
|---|---|
| [HEM.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hem/HEM.java) | Composant central. Il cree des ports sortants, se connecte au compteur, aux batteries, au panneau solaire, au generateur, a la station meteo et au chauffage. Il execute aussi les tests d'integration. |
| [AdjustableOutboundPort.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hem/AdjustableOutboundPort.java) | Port sortant generique base sur `AdjustableCI`. Le HEM l'utilise pour controler un equipement ajustable. |
| [AdjustableConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hem/AdjustableConnector.java) | Connecteur generique pour le cas ou l'equipement offre directement `AdjustableCI`. Le code indique qu'il sert principalement au debug. |
| [HeaterConnector.java](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hem/HeaterConnector.java) | Connecteur personnalise entre `AdjustableCI` et `HeaterExternalControlJava4CI`. Il traduit les modes generiques en niveaux de puissance du chauffage. |
| [package.html](ALASCA/ALASCA-etape-1/ALASCA-etape-1/HEM-2026-etape1-21092026-src/hem-2026-e1/fr/sorbonne_u/components/hem2026e1/equipments/hem/package.html) | Documentation Javadoc du package HEM. |

Le fichier `HEM.java` depend directement des fichiers suivants :

```text
HEM.java
├── AdjustableCI
├── AdjustableOutboundPort
├── HeaterConnector
├── ElectricMeterOutboundPort et Connector
├── BatteriesOutboundPort et Connector
├── SolarPanelOutboundPort et Connector
├── GeneratorOutboundPort et Connector
├── WeatherStationOutboundPort et Connector
├── ClocksServerOutboundPort et Connector
├── CVMIntegrationTest pour CLOCK_URI
└── UnitTester des equipements
```

La derniere dependance est importante : dans cette etape, le HEM contient encore du code de test et importe `CVMIntegrationTest`. Dans une version plus independante, la constante d'horloge devrait etre fournie par une configuration ou une interface dediee.

---

# 9. Lecture par equipement

## Equipements simples

Les equipements suivants suivent presque le meme modele :

```text
Composant principal
    + CI
    + ImplementationI
    + ConfigurationI
    + InboundPort
    + OutboundPort
    + Connector
    + UnitTester
    + CVMUnitTest
```

Cela concerne :

- batteries ;
- generateur ;
- compteur ;
- panneau solaire ;
- station meteorologique.

## Equipement utilisateur

Le seche-cheveux ajoute :

- un etat ;
- un mode ;
- une interface utilisateur ;
- un testeur d'actions utilisateur.

Il n'est pas controle par `HEM` comme un equipement ajustable.

## Equipement ajustable

Le chauffage ajoute plusieurs contrats et plusieurs familles de connexions :

```text
Utilisateur
    -> HeaterUserCI

Controle interne
    -> HeaterInternalControlCI

HEM
    -> AdjustableCI
    -> HeaterExternalControlCI
```

C'est pourquoi le package `heater` contient davantage de fichiers que les autres packages.

## Regle de lecture d'un fichier

Pour comprendre un equipement, lire dans cet ordre :

1. `<Equipment>ConfigurationI.java` pour connaitre les unites et constantes ;
2. `<Equipment>CI.java` pour connaitre les services publics ;
3. `<Equipment>ImplementationI.java` pour connaitre le contrat interne ;
4. `<Equipment>.java` pour voir l'etat et l'implementation ;
5. `connections/*` pour comprendre la communication BCM ;
6. `*UnitTester.java` pour voir les scenarios de test ;
7. `CVMUnitTest.java` pour voir comment le composant est lance.

## Resume

| Package | Fonction principale | Particularite |
|---|---|---|
| `batteries` | Stockage d'energie | Charge, decharge et capacite |
| `generator` | Production electrique | Reservoir et consommation de carburant |
| `meter` | Mesure electrique | Tension, consommation et production |
| `solar_panel` | Production solaire | Surface et puissance produite |
| `weather_station` | Mesures meteorologiques | Temperatures interieure et exterieure |
| `hairdryer` | Equipement utilisateur | Non ajustable par le HEM |
| `heater` | Chauffage controlable | Trois familles d'interfaces |
| `hem` | Gestionnaire d'energie | Orchestre les equipements et les tests |
