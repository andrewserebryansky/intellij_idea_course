# Original Exception
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
    
# JSON-Ready exception
org.elasticsearch.bootstrap.StartupException: java.lang.IllegalArgumentException: Test Exception\n    at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:174) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.execute(Elasticsearch.java:161) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.EnvironmentAwareCommand.execute(EnvironmentAwareCommand.java:86) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.Command.mainWithoutErrorHandling(Command.java:127) ~[elasticsearch-cli-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.Command.main(Command.java:90) ~[elasticsearch-cli-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:126) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:92) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n Caused by: java.lang.IllegalArgumentException: Test Exception\n    at org.elasticsearch.plugins.PluginsService.logPluginInfo(PluginsService.java:200) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.plugins.PluginsService.<init>(PluginsService.java:186) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.node.Node.<init>(Node.java:313) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.node.Node.<init>(Node.java:262) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap$5.<init>(Bootstrap.java:225) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap.setup(Bootstrap.java:225) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap.init(Bootstrap.java:387) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:170) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    ... 6 more

# Delete test index:
curl --location --request DELETE 'http://localhost:9200/test'

# Create test index with analyzer mapping
curl --location --request PUT 'http://localhost:9200/test/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "mappings": {
        "properties": {
            "exception": {
                "type": "text",
                "analyzer": "exception_analyzer",
                "search_analyzer": "exception_analyzer"
            }
        }
    }
}'

# Index exception
curl --location --request POST 'http://localhost:9200/test/_doc' \
--header 'Content-Type: application/json' \
--data-raw '{
    "exception": "org.elasticsearch.bootstrap.StartupException: java.lang.IllegalArgumentException: Test Exception\n    at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:174) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.execute(Elasticsearch.java:161) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.EnvironmentAwareCommand.execute(EnvironmentAwareCommand.java:86) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.Command.mainWithoutErrorHandling(Command.java:127) ~[elasticsearch-cli-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.Command.main(Command.java:90) ~[elasticsearch-cli-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:126) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:92) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n Caused by: java.lang.IllegalArgumentException: Test Exception\n    at org.elasticsearch.plugins.PluginsService.logPluginInfo(PluginsService.java:200) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.plugins.PluginsService.<init>(PluginsService.java:186) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.node.Node.<init>(Node.java:313) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.node.Node.<init>(Node.java:262) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap$5.<init>(Bootstrap.java:225) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap.setup(Bootstrap.java:225) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap.init(Bootstrap.java:387) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:170) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    ... 6 more"
}'


# Search
curl --location --request GET 'http://localhost:9200/test/_search' \
--header 'Content-Type: application/json' \
--data-raw '{
    "query": {
        "match": {
            "exception": {
                "query": "org.elasticsearch.bootstrap.StartupException: java.lang.IllegalArgumentException: Test Exception\n    at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:174) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.execute(Elasticsearch.java:161) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.EnvironmentAwareCommand.execute(EnvironmentAwareCommand.java:86) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.Command.mainWithoutErrorHandling(Command.java:127) ~[elasticsearch-cli-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.cli.Command.main(Command.java:90) ~[elasticsearch-cli-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:126) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:92) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n Caused by: java.lang.IllegalArgumentException: Test Exception\n    at org.elasticsearch.plugins.PluginsService.logPluginInfo(PluginsService.java:200) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.plugins.PluginsService.<init>(PluginsService.java:186) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.node.Node.<init>(Node.java:313) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.node.Node.<init>(Node.java:262) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap$5.<init>(Bootstrap.java:225) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap.setup(Bootstrap.java:225) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Bootstrap.init(Bootstrap.java:387) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:170) ~[elasticsearch-8.0.0-SNAPSHOT.jar:8.0.0-SNAPSHOT]\n    ... 6 more"
            }
        }
    }
}'

