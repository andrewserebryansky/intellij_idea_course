package org.elasticsearch.example.exceptionsearch;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

public class ExceptionAnalyzerProvider extends AbstractIndexAnalyzerProvider<Analyzer> {

    /**
     * Constructs a new analyzer component, with the index name and its settings and the analyzer name.
     *
     * @param indexSettings the settings and the name of the index
     * @param name          The analyzer name
     * @param settings      Settings
     */
    public ExceptionAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
    }

    @Override
    public Analyzer get() {
       return new ExceptionAnalyzer();
    }

    private static class ExceptionAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            return new TokenStreamComponents(new ExceptionTokenizer());
        }
    }
}
