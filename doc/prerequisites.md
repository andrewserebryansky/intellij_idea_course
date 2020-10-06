# Prerequisites
- OpenJDK 11
- IntelliJ IDEA Community Edition. Download from https://www.jetbrains.com/de-de/idea/download/#section=linux
    - Configure xms and xmx in bin/idea64.vmopions to 2048 and 4096 respectively
- ElasticSearch Projekt aufsetzen (https://github.com/elastic/elasticsearch)
    - Projekt lokal klonen und 7.9 Release Branch auschecken: 
        > git clone https://github.com/elastic/elasticsearch.git elasticsearch
        > cd elasticsearch/
        > git checkout --track origin/7.8
    - Mit IntelliJ IDEA Öffnen: File > Open, "elasticsearch" folder aussuchen
    - Warten bis das Projekt importiert und indexiert ist
    - Sicherstellen, dass das richtige SDK fürs Projekt gesetzt ist: Navigate -> Search Everywhere, Project Structure suchen, 
    Project Settings -> Project, Project SDK Feld prüfen 
- ElasticSearch starten und debuggen
    - gradle/run.gradle öffnen, security ausschalten: setting 'xpack.security.enabled', 'true' -> setting 'xpack.security.enabled', 'false' 
    - "Debug ElasticSearch" Run Configuration in IntelliJ IDEA mit Debug starten
    - Start ElasticSearch: > ./gradlew run --debug-jvm (immer erst Debug ElasticSearch starten und dann ElasticSearch selber, 
    sonst funktioniert das Debuggen nicht)
    - Sicherstellen, dass ElasticSearch antwortet. > curl http://localhost:9200/_cat/indices?v=true soll mit leeren Liste antworten.
