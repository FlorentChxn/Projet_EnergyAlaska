# Commandes HEM, BCM4Java et Javassist

Ce document rassemble les principales commandes necessaires pour compiler, inspecter et executer le projet ALASCA etape 1. Les commandes sont separees en trois parties :

1. HEM : application de gestion de l'energie ;
2. BCM4Java : framework de composants et deploiement distribue ;
3. Javassist : inspection et modification dynamique de classes Java.

## 0. Prerequis communs

Le projet utilise directement `javac` et `java`. Il n'utilise pas Maven, Gradle ou Ant dans `ALASCA-etape-1`.

### Verifier Java

```powershell
java -version
javac -version
jar --version
javap -version
```

`javac` appartient au JDK. Un JRE seul ne permet pas de compiler.

### Definir les chemins sous PowerShell

Executer depuis le dossier `Semestre3` :

```powershell
$ROOT = (Resolve-Path "ALASCA\ALASCA-etape-1\ALASCA-etape-1").Path
$HEM = Join-Path $ROOT "HEM-2026-etape1-21092026-src"
$BCM = Join-Path $ROOT "BCM4Java-21092026-src"
$OUT = Join-Path $ROOT "build\classes"

$CP = @(
    (Join-Path $ROOT "BCM4Java-21092026.jar")
    (Join-Path $ROOT "BCM4JavaCyPhy-21092026.jar")
    (Join-Path $ROOT "javassist.jar")
    (Join-Path $ROOT "jing.jar")
    (Join-Path $ROOT "commons-math3-3.6.1.jar")
) -join ";"

New-Item -ItemType Directory -Force $OUT | Out-Null
```

Sous Windows, le separateur du classpath est `;`. Sous Linux et macOS, il est `:`.

---

# 1. Commandes HEM

## 1.1 Compiler les sources HEM

La compilation comprend les deux modules Java du HEM : `hem-2026-base` et `hem-2026-e1`.

```powershell
$SOURCES = @(
    Get-ChildItem (Join-Path $HEM "hem-2026-base") -Recurse -Filter *.java
    Get-ChildItem (Join-Path $HEM "hem-2026-e1") -Recurse -Filter *.java
).FullName

javac -encoding UTF-8 -cp $CP -d $OUT $SOURCES
```

Signification des options :

```text
-encoding UTF-8  encodage des fichiers Java
-cp              bibliotheques necessaires a la compilation
-d               dossier de sortie des fichiers .class
```

## 1.2 Recompiler proprement

```powershell
Remove-Item (Join-Path $ROOT "build") -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force $OUT | Out-Null

$SOURCES = @(
    Get-ChildItem (Join-Path $HEM "hem-2026-base") -Recurse -Filter *.java
    Get-ChildItem (Join-Path $HEM "hem-2026-e1") -Recurse -Filter *.java
).FullName

javac -encoding UTF-8 -cp $CP -d $OUT $SOURCES
```

Equivalent Unix/Linux :

```bash
rm -rf build/classes
mkdir -p build/classes
find HEM-2026-etape1-21092026-src/hem-2026-base \
     HEM-2026-etape1-21092026-src/hem-2026-e1 \
     -name '*.java' > sources.txt
javac -encoding UTF-8 -cp "$CP" -d build/classes @sources.txt
```

## 1.3 Lancer le test d'integration HEM

```powershell
java -ea -cp "$OUT;$CP" `
    fr.sorbonne_u.components.hem2026e1.CVMIntegrationTest
```

La classe cree et lance :

```text
ClocksServer
ElectricMeter
Batteries
SolarPanel
Generator
WeatherStation
HairDryer
HairDryerTester
Heater
HeaterUnitTester
HEM
```

`-ea` active les assertions Java utilisees pour les preconditions, postconditions et invariants.

## 1.4 Lancer les tests unitaires des equipements

```powershell
java -ea -cp "$OUT;$CP" fr.sorbonne_u.components.hem2026e1.equipments.batteries.CVMUnitTest
java -ea -cp "$OUT;$CP" fr.sorbonne_u.components.hem2026e1.equipments.generator.CVMUnitTest
java -ea -cp "$OUT;$CP" fr.sorbonne_u.components.hem2026e1.equipments.meter.CVMUnitTest
java -ea -cp "$OUT;$CP" fr.sorbonne_u.components.hem2026e1.equipments.solar_panel.CVMUnitTest
java -ea -cp "$OUT;$CP" fr.sorbonne_u.components.hem2026e1.equipments.weather_station.CVMUnitTest
java -ea -cp "$OUT;$CP" fr.sorbonne_u.components.hem2026e1.equipments.heater.CVMUnitTest
java -ea -cp "$OUT;$CP" fr.sorbonne_u.components.hem2026e1.equipments.hairdryer.CVMUnitTest
```

## 1.5 Inspecter les classes HEM compilees

Lister les classes generees :

```powershell
Get-ChildItem $OUT -Recurse -Filter *.class
```

Rechercher une classe precise :

```powershell
Get-ChildItem $OUT -Recurse -Filter HEM.class
```

Afficher l'API publique d'une classe :

```powershell
javap -classpath "$OUT;$CP" -public `
    fr.sorbonne_u.components.hem2026e1.equipments.hem.HEM
```

Afficher le bytecode :

```powershell
javap -classpath "$OUT;$CP" -c `
    fr.sorbonne_u.components.hem2026e1.equipments.hem.HEM
```

## 1.6 Creer un JAR du HEM compile

```powershell
$HEM_JAR = Join-Path $ROOT "hem-etape1.jar"
jar cf $HEM_JAR -C $OUT .
```

Verifier son contenu :

```powershell
jar tf (Join-Path $ROOT "hem-etape1.jar")
```

Lancer une classe depuis ce JAR :

```powershell
java -ea -cp "$ROOT\hem-etape1.jar;$CP" `
    fr.sorbonne_u.components.hem2026e1.CVMIntegrationTest
```

---

# 2. Commandes BCM4Java

BCM4Java fournit le runtime de composants, les ports, les connecteurs, les CVM, le registre global et la synchronisation distribuee.

## 2.1 Structure d'un composant

Syntaxe minimale :

```java
public class MyComponent extends AbstractComponent {
    protected MyComponent() throws Exception {
        super(1, 1);
    }

    @Override
    public synchronized void start() throws ComponentStartException {
        super.start();
    }

    @Override
    public synchronized void execute() throws Exception {
        // Activite du composant.
    }

    @Override
    public synchronized void finalise() throws Exception {
        super.finalise();
    }

    @Override
    public synchronized void shutdown() throws ComponentShutdownException {
        super.shutdown();
    }
}
```

## 2.2 Creer un composant dans une CVM

```java
AbstractComponent.createComponent(
    MyComponent.class.getCanonicalName(),
    new Object[]{});
```

Avec des parametres :

```java
AbstractComponent.createComponent(
    MyComponent.class.getCanonicalName(),
    new Object[]{10, true});
```

## 2.3 Declarer une interface offerte

```java
public interface MyOfferedCI extends OfferedCI {
    public void doSomething() throws Exception;
}
```

```java
@OfferedInterfaces(offered = {MyOfferedCI.class})
public class MyComponent extends AbstractComponent {
}
```

## 2.4 Declarer une interface requise

```java
public interface MyRequiredCI extends RequiredCI {
    public String getValue() throws Exception;
}
```

```java
@RequiredInterfaces(required = {MyRequiredCI.class})
public class ClientComponent extends AbstractComponent {
}
```

## 2.5 Publier un port entrant

```java
protected MyInboundPort inboundPort;

this.inboundPort = new MyInboundPort("my-inbound-port", this);
this.inboundPort.publishPort();
```

Retirer le port lors de l'arret :

```java
this.inboundPort.unpublishPort();
```

## 2.6 Publier un port sortant

```java
protected MyOutboundPort outboundPort;

this.outboundPort = new MyOutboundPort(this);
this.outboundPort.publishPort();
```

## 2.7 Connecter et deconnecter des ports

```java
this.doPortConnection(
    this.outboundPort.getPortURI(),
    "my-inbound-port",
    MyConnector.class.getCanonicalName());
```

Deconnexion :

```java
this.doPortDisconnection(this.outboundPort.getPortURI());
```

Tester l'etat d'une connexion :

```java
if (this.outboundPort.connected()) {
    // Le port est connecte.
}
```

## 2.8 Cycle de vie d'une CVM

```java
public class MyCVM extends AbstractCVM {
    @Override
    public void deploy() throws Exception {
        AbstractComponent.createComponent(
            MyComponent.class.getCanonicalName(),
            new Object[]{});
        super.deploy();
    }

    public static void main(String[] args) {
        try {
            MyCVM cvm = new MyCVM();
            cvm.startStandardLifeCycle(5000L);
            Thread.sleep(10000L);
            System.exit(0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
```

## 2.9 Compiler une classe BCM seule

Exemple avec les JAR situes dans le dossier courant :

```powershell
javac -cp "$ROOT\BCM4Java-21092026.jar;$ROOT\BCM4JavaCyPhy-21092026.jar" `
    -d $OUT MyComponent.java
```

Compiler un exemple BCM :

```powershell
javac -cp "$ROOT\BCM4Java-21092026.jar" `
    -d $OUT `
    (Join-Path $BCM "examples\fr\sorbonne_u\components\examples\basic_cs\CVM.java")
```

## 2.10 Lancer le registre global

Commande Unix fournie dans `bash-scripts/start-gregistry` :

```bash
java -ea -Xms2m -cp 'jars/*' \
     -Djava.security.manager \
     -Djava.security.policy=dcvm.policy \
     fr.sorbonne_u.components.registry.GlobalRegistry config.xml
```

Equivalent PowerShell :

```powershell
java -ea -Xms2m -cp ".\jars\*" `
     -Djava.security.policy=dcvm.policy `
     fr.sorbonne_u.components.registry.GlobalRegistry config.xml
```

## 2.11 Lancer la barriere cyclique

Commande Unix fournie dans `bash-scripts/start-cyclicbarrier` :

```bash
java -ea -Xms2m -cp 'jars/*' \
     -Djava.security.manager \
     -Djava.security.policy=dcvm.policy \
     fr.sorbonne_u.components.cvm.utils.DCVMCyclicBarrier config.xml
```

Equivalent PowerShell :

```powershell
java -ea -Xms2m -cp ".\jars\*" `
     -Djava.security.policy=dcvm.policy `
     fr.sorbonne_u.components.cvm.utils.DCVMCyclicBarrier config.xml
```

## 2.12 Lancer le distributeur DCVM

Commande Unix fournie dans `bash-scripts/launch` :

```bash
java -ea -Xms2m -cp 'jars/*' \
     -Djava.security.manager \
     -Djava.security.policy=dcvm.policy \
     fr.sorbonne_u.components.cvm.utils.DCVM_Launcher config.xml
```

Equivalent PowerShell :

```powershell
java -ea -Xms2m -cp ".\jars\*" `
     -Djava.security.policy=dcvm.policy `
     fr.sorbonne_u.components.cvm.utils.DCVM_Launcher config.xml
```

## 2.13 Lancer une JVM de test distribuee

Le script `start-dcvm` attend deux parametres :

```text
start-dcvm testNumber jvmName
```

Commande Unix :

```bash
./start-dcvm 1 player1jvm
```

La commande execute en interne :

```bash
java -ea -cp 'jars/*' \
     -Djava.security.manager \
     -Djava.security.policy=dcvm.policy \
     fr.sorbonne_u.components.testdistributed.DCVM1 player1jvm config.xml
```

Equivalent PowerShell :

```powershell
java -ea -cp ".\jars\*" `
     -Djava.security.policy=dcvm.policy `
     fr.sorbonne_u.components.testdistributed.DCVM1 player1jvm config.xml
```

`testNumber` devient le suffixe de la classe `DCVM`, par exemple `1` produit `DCVM1`.

## 2.14 Lancer les exemples BCM

Exemples de classes principales :

```powershell
java -ea -cp "$OUT;$ROOT\BCM4Java-21092026.jar" `
    fr.sorbonne_u.components.examples.basic_cs.CVM
```

Exemple distribue :

```powershell
java -ea -cp "$OUT;$ROOT\BCM4Java-21092026.jar" `
    fr.sorbonne_u.components.examples.basic_cs.DistributedCVM `
    player1jvm config.xml
```

Exemples disponibles :

```text
basic_cs
pingpong
reflection
cps
ddeployment_cs
```

## 2.15 Commandes d'inspection BCM

Lister les classes du JAR BCM :

```powershell
jar tf (Join-Path $ROOT "BCM4Java-21092026.jar") | Select-String "components"
```

Rechercher une classe :

```powershell
jar tf (Join-Path $ROOT "BCM4Java-21092026.jar") | Select-String "AbstractComponent"
```

Afficher l'API de `AbstractComponent` :

```powershell
javap -classpath (Join-Path $ROOT "BCM4Java-21092026.jar") `
    -public fr.sorbonne_u.components.AbstractComponent
```

Afficher son bytecode :

```powershell
javap -classpath (Join-Path $ROOT "BCM4Java-21092026.jar") `
    -c fr.sorbonne_u.components.AbstractComponent
```

---

# 3. Commandes Javassist

Javassist est une bibliotheque Java utilisee par BCM4Java pour inspecter et modifier des classes a l'execution. Le fichier fourni est `javassist.jar`.

## 3.1 Inspecter le JAR Javassist

```powershell
jar tf (Join-Path $ROOT "javassist.jar")
```

Rechercher les classes principales :

```powershell
jar tf (Join-Path $ROOT "javassist.jar") | Select-String "ClassPool|CtClass|CtMethod|HotSwapAgent"
```

Afficher la version Maven embarquee :

```powershell
jar xf (Join-Path $ROOT "javassist.jar") `
    META-INF/maven/org.javassist/javassist/pom.properties
Get-Content "META-INF\maven\org.javassist\javassist\pom.properties"
```

Afficher l'API de `ClassPool` :

```powershell
javap -classpath (Join-Path $ROOT "javassist.jar") `
    -public javassist.ClassPool
```

Afficher l'API de `CtClass` :

```powershell
javap -classpath (Join-Path $ROOT "javassist.jar") `
    -public javassist.CtClass
```

## 3.2 Compiler une classe utilisant Javassist

```powershell
javac -cp (Join-Path $ROOT "javassist.jar") `
    -d $OUT MyJavassistProgram.java
```

Avec BCM4Java et Javassist :

```powershell
javac -cp "$ROOT\BCM4Java-21092026.jar;$ROOT\javassist.jar" `
    -d $OUT MyJavassistProgram.java
```

## 3.3 Lancer une classe utilisant Javassist

```powershell
java -cp "$OUT;$ROOT\javassist.jar" MyJavassistProgram
```

Avec toutes les dependances du projet :

```powershell
java -cp "$OUT;$CP" MyJavassistProgram
```

## 3.4 Syntaxe Javassist : charger une classe

```java
import javassist.ClassPool;
import javassist.CtClass;

ClassPool pool = ClassPool.getDefault();
CtClass clazz = pool.get(
    "fr.sorbonne_u.components.hem2026e1.equipments.heater.Heater");
```

Ajouter un chemin de classes :

```java
pool.appendClassPath("build/classes");
pool.appendClassPath("BCM4Java-21092026.jar");
```

## 3.5 Syntaxe Javassist : lire une methode

```java
import javassist.CtMethod;

CtMethod method = clazz.getDeclaredMethod("execute");
System.out.println(method.getName());
System.out.println(method.getReturnType());
```

Rechercher une methode avec des parametres :

```java
CtClass[] parameters = new CtClass[]{
    pool.get("java.lang.String")
};
CtMethod method = clazz.getDeclaredMethod("myMethod", parameters);
```

## 3.6 Syntaxe Javassist : modifier une methode

```java
method.insertBefore("System.out.println(\"debut\");");
method.insertAfter("System.out.println(\"fin\");");
```

Remplacer tout le corps :

```java
method.setBody("{ System.out.println(\"nouveau corps\"); }");
```

Ajouter une methode :

```java
CtMethod added = CtMethod.make(
    "public void hello() { System.out.println(\"hello\"); }",
    clazz);
clazz.addMethod(added);
```

## 3.7 Syntaxe Javassist : produire une classe

Ecrire la classe modifiee sur disque :

```java
clazz.writeFile("build/generated-classes");
```

Charger la classe dans la JVM :

```java
Class<?> generatedClass = clazz.toClass();
```

Une classe deja chargee peut etre protegee contre les modifications. Dans ce cas, utiliser `defrost()` si la classe Javassist a ete gelee :

```java
if (clazz.isFrozen()) {
    clazz.defrost();
}
```

## 3.8 Exemple complet Javassist

```java
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class ModifyClass {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath("build/classes");

        CtClass clazz = pool.get(
            "fr.sorbonne_u.components.hem2026e1.equipments.heater.Heater");
        CtMethod method = clazz.getDeclaredMethod("execute");

        method.insertBefore(
            "System.out.println(\"execute commence\");");
        clazz.writeFile("build/generated-classes");

        clazz.detach();
    }
}
```

Compiler et executer cet exemple :

```powershell
javac -cp "$OUT;$ROOT\BCM4Java-21092026.jar;$ROOT\javassist.jar" `
    -d $OUT ModifyClass.java

java -cp "$OUT;$CP" ModifyClass
```

## 3.9 Exemple BCM reflection avec agent

L'exemple BCM de reflexion peut utiliser un agent HotSwap. Le script fourni contient cette forme :

```bash
java -ea -javaagent:hotswap.jar -cp 'jars/*' \
     fr.sorbonne_u.components.examples.reflection.DistributedCVM
```

Sous PowerShell :

```powershell
java -ea -javaagent:hotswap.jar `
    -cp ".\jars\*" `
    fr.sorbonne_u.components.examples.reflection.DistributedCVM
```

Le fichier `hotswap.jar` doit exister dans le dossier de lancement. Il peut etre genere par l'exemple `CreateHotswap.java` :

```powershell
javac -cp "$ROOT\BCM4Java-21092026.jar;$ROOT\javassist.jar" `
    -d $OUT `
    (Join-Path $BCM "examples\fr\sorbonne_u\components\examples\reflection\CreateHotswap.java")

java -cp "$OUT;$ROOT\BCM4Java-21092026.jar;$ROOT\javassist.jar" `
    fr.sorbonne_u.components.examples.reflection.CreateHotswap
```

---

# 4. Depannage rapide

## Classe introuvable

Erreur :

```text
Could not find or load main class
```

Verifier :

```powershell
Test-Path $ROOT
Test-Path $OUT
Test-Path (Join-Path $ROOT "BCM4Java-21092026.jar")
$CP
```

La classe doit etre indiquee avec son nom complet, par exemple :

```text
fr.sorbonne_u.components.hem2026e1.CVMIntegrationTest
```

## Package introuvable a la compilation

Erreur :

```text
package fr.sorbonne_u.components does not exist
```

Ajouter le JAR BCM au classpath :

```powershell
javac -cp "$ROOT\BCM4Java-21092026.jar;$ROOT\BCM4JavaCyPhy-21092026.jar" ...
```

## Javassist introuvable

Erreur :

```text
package javassist does not exist
```

Ajouter `javassist.jar` :

```powershell
javac -cp "$ROOT\javassist.jar;$ROOT\BCM4Java-21092026.jar" ...
```

## Port deja utilise

Le registre global, la barriere et les registres RMI utilisent des ports definis dans `config.xml`. Si un port est deja utilise :

```powershell
Get-NetTCPConnection -State Listen | Where-Object LocalPort -in 55252,55253,55999
```

Arreter le processus concerne :

```powershell
Stop-Process -Id <PID>
```

## Security Manager

Les scripts historiques utilisent parfois :

```text
-Djava.security.manager
-Djava.security.policy=dcvm.policy
```

Les versions recentes de Java ne supportent plus ces options de la meme maniere. Pour un premier lancement local, utiliser la commande sans `-Djava.security.manager` :

```powershell
java -ea -cp "$OUT;$CP" `
    fr.sorbonne_u.components.hem2026e1.CVMIntegrationTest
```

## Difference entre Windows et Unix

| Action | Windows PowerShell | Unix/Linux |
|---|---|---|
| Separateur classpath | `;` | `:` |
| Suite de ligne | backtick `` ` `` | backslash `\` |
| Lister un dossier | `Get-ChildItem` | `ls` |
| Supprimer | `Remove-Item` | `rm` |
| Creer un dossier | `New-Item -ItemType Directory` | `mkdir -p` |
| Executer un script | `script.bat` ou commande Java directe | `./script` |
