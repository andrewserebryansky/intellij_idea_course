/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
     * @param settings      Elastic Search settings
     */
    public ExceptionAnalyzerProvider(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
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
