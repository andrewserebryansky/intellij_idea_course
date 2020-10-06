 # IntelliJ IDEA Basics

 ## Things to consider
 How many participants? 
 Where, some place or remote or both?

 ## Description German
 IntelliJ IDEA ist einer der meist genutzten und mächstigsten IDEs der Welt. Die wird von Millionen von 
 Softwareentwicklern täglich benutzt und das gilt auch für MaibornWolff. Viele Features liegen dann auf 
 Oberfläche oder werden von IDE aktiv vorgeschlagen (z.B. Quck Fixes, Code Completion oder Code Analysis), 
 aber es gibt mehrere anderen, die tief in den Menus oder hinter Shortcuts versteckt sind, die das Leben
 eines Softwareentwicklers erleichtern könnten. Solche Features werden wir im Rahmen von diesem Workshop kennenlernen und ausprobieren.
 
 Folgendes erwartet euch in der Schulung: 
 - Wie baue, starte und debugge ich mein Projekt am schnellsten
 - Wie navigiere ich durch Codebase
    - Wie kann ich Sachen in einem Projekt finden
    - Wie navigiere ich innerhalb einer Datei
    - Wie analysiere ich Abhängigkeiten im Projekt, Data(Werte)flow und Exception Stacktraces
 - Wie schreibe ich Code am schnellsten mit Code Completion, Templates usw.
 - Wie finde ich Fehler oder Code Smells und repariere die
 - Wie kann ich mein Code sicher Refactoren
 
 Damit auch was nutzliches beim Lernen entsteht, werden wir einen Analyzer Plugin für ElasticSearch schreiben,
 der die Exceptions in Logs analysiert und die Suche nach ähnlichen Exceptions ermöglicht.
 
 Die Schulung eignet sich für Softwareentwickler, die im Alltag IntelliJ IDEA nutzen und möchten mehr von der IDE
 bekommen und schneller bei der Arbeit werden. 
 
 ## Description English
 
 ## Prerequisites
- OpenJDK 11
- IntelliJ IDEA Community Edition. Herunterladen unter https://www.jetbrains.com/de-de/idea/download/#section=linux
    - xms und xmx in bin/idea64.vmopions auf 2048 und 4096 entsprechend konfigurieren
- ElasticSearch Projekt aufsetzen (https://github.com/elastic/elasticsearch)
    - Projekt lokal klonen und 7.9 Release Branch auschecken: 
        > git clone https://github.com/elastic/elasticsearch.git elasticsearch
        > cd elasticsearch/
        > git checkout --track origin/7.9
    - Mit IntelliJ IDEA Öffnen: File > Open, "elasticsearch" folder aussuchen
    - Warten bis das Projekt importiert und indexiert ist, ggf. Gradle Build importieren
    - Sicherstellen, dass das richtige SDK (Java 11) fürs Projekt gesetzt ist: Navigate -> Search Everywhere, Project Structure suchen, 
    Project Settings -> Project, Project SDK Feld prüfen 
- ElasticSearch starten und debuggen
    - gradle/run.gradle öffnen, security ausschalten: setting 'xpack.security.enabled' von 'true' auf 'false' setzen 
    - "Debug ElasticSearch" Run Configuration in IntelliJ IDEA mit Debug starten
    - Start ElasticSearch: > ./gradlew run --debug-jvm (immer erst "Debug ElasticSearch" starten und dann ElasticSearch selber, 
    sonst funktioniert das Debuggen nicht)
    - Sicherstellen, dass ElasticSearch antwortet: > curl http://localhost:9200/_cat/indices?v=true soll mit leeren Liste antworten.


 ## Geschichte und Architektur
 Founded in 2000... IntelliJ Renamer

 Component Model: Shell-level Components, Project-level Components
 Project Model: Project interface, VirtualFile und VirtualFileSystem
 Lexer: Lexer, LookAheadLexer, JavaLexer
 PSI: JavaParser, ASTNode, PsiElement, PsiFile, PsiReference, resolve
 Code Analysis: Daemon, IntentionAction

 
 ## Steps
 **Step 1: Build and run your application**
 - Run -> Run.../Debug... or Alt+Shift+F10/Alt+Shift+F9
 - Set shortcut to 'Execute Gradle Task'
 - Demonstrate what can be done with Breakpoints, printing something. See RestIndicesAction.doCatRequest
 - Demonstrate switching between Tool Windows with Alt+... shortcuts
 - Demonstrate stopping processes with Ctrl+2 (F2)
 - Demonstrate debugging Streams
 
 **Step 2: Explain task and create project structure for ES Plugin**
 - We're making an ElasticSearch plugin that allows searching for similar exceptions and scoring them
    https://www.elastic.co/guide/en/elasticsearch/reference/current/analyzer.html
    Character filters, Tokenizer, Token Filters
 - Add build.gradle by copying from custom-suggester and renaming things, refresh gradle structure
 - Demonstrate creating multiple directories at once: create folder exception-search and then create folder
 src/main/java and src/main/resources
 - Add plugin-descriptor.properties file under resources, explain its purpose
 - 
 **Step 3: exploring**
 - Explore structure of ElasticSearch project, Alt+1 Show/hide Project tool window
 - Explore plugins, navigate to AnalysisUkrainianPlugin
 - Search for implementations of AnalysisPlugin, open in Find Usages window (shortcuts, Ctrl+Alt+Click)
 - Iterate through implementations with Ctrl+Alt+Down/Up
 - See Recent Locations
 - See Recent Files
 - Return back to AnalysisUkrainianPlugin (Go To Class), Navigate to Plugin (we want to see
 how ElasticSearch plugins are loaded)
 - Search for usages of Plugin, navigate to PluginsService
 - Demonstrate 'Semantic Highlighting'
 - Return to Plugin, Demonstrate Analyse -> Data Flow To... on client parameter of createComponents
 - Return to Node, Demonstrate Analyse -> Data Flow From Here... on settings in Node constructor
 - Demonstrate Analyse -> Dependencies on Node class
 
 
 **Step 4: programming**
 - Create ExceptionSearchPlugin class
 - Write 'extends Plugin' and 'implements AnalysisPlugin', then demonstrate Smart Completion and the difference with Basic Completion
 - Implement 'getAnalyzers' method. Show different ways of implementing methods:
    - via Code Completion
    - via Alt+Insert
    - Demonstrate generating constructor, 'equals and hashCode()', 'toString()' via Alt+Insert
 - Create ExceptionAnalyzerProvider extends AbstractIndexAnalyzerProvider<Analyzer>, demonstrate Quick-Fixes
 - Demonstrating jumping out of a method to write a new one with Ctrl+Shift+Enter. Demonstrate Shift+Enter and Ctrl+Alt+Enter to jump out of statement.
 - Create ExceptionAnalyzer inner class and ExceptionTokenizer with Quick Fix
 - In ExceptionAnalyzerProvider get() method demonstrate postfix completion:
    - .new, .return, .var, .if, .try, .for
 - Explain the purpose of ExceptionTokenizer, implement  readLine method
 - Demonstrate Live Templates, add do while live template, explain variables etc.
 - Implement incrementToken method, before that add CharTermAttribute and PositionIncrementAttribute, explain their purpose, demonstrate Smart Completion on .class parameter to addAttribute
 - Demonstrate Douple Smart Completion for int positionIncrement = positionIncrementAttribute.getPositionIncrement()
 - in while (!line.startsWith("at ")) demonstrate .not postfix completion
 - in line = line.substring("at ".length()))) extract field with postfix completion, tweak options with Alt
 - Demonstrate split declaration Quick-Fix and Moving Declarations
 - Demonstrate generating getters and setters with completion
 - Write tokenization fully, then update plugin loading in Environment class

**Step 5: testing**
 - Start Elastic Search under debugger and make sure plugin gets loaded
 - Create test index and assign exception_analyzer to exception field: curl -X PUT http://localhost:9200/test -H "Content-Type: application/json" -d '{ "mappings": { "properties": { "exception": { "type": "text", "analyzer": "exception_analyzer" } } } }'
 - Index exception and see what happens
 - Search for the same exception, then search for exception with missing data in stack trace and show that scoring changes accordingly
 
 **Step 6: refactoring**
 - Demonstrate Refactor This menu on ExceptionAnalyzerProvider class
 - Demonstrate most common refactorings:
    - Rename
    - Change Signature
    - Extract/Introduce -> Method, Variable, Field, Parameter etc
    - Extract Superclass -> Pull members up/Push members down
    - Inline Method/Variable
    
 **Step 7: questions**

 org.elasticsearch.bootstrap.StartupException: java.lang.IllegalArgumentException: Test Exception
    at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:174) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.bootstrap.Elasticsearch.execute(Elasticsearch.java:161) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.cli.EnvironmentAwareCommand.execute(EnvironmentAwareCommand.java:86) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.cli.Command.mainWithoutErrorHandling(Command.java:127) ~[elasticsearch-cli-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.cli.Command.main(Command.java:90) ~[elasticsearch-cli-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:126) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:92) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
 Caused by: java.lang.IllegalArgumentException: Test Exception
    at org.elasticsearch.plugins.PluginsService.logPluginInfo(PluginsService.java:200) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.plugins.PluginsService.<init>(PluginsService.java:186) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.node.Node.<init>(Node.java:313) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.node.Node.<init>(Node.java:262) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.bootstrap.Bootstrap$5.<init>(Bootstrap.java:225) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.bootstrap.Bootstrap.setup(Bootstrap.java:225) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.bootstrap.Bootstrap.init(Bootstrap.java:387) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:170) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]
    ... 6 more