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

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.io.Reader;

public class ExceptionTokenizer extends Tokenizer {
    private final CharTermAttribute termAttribute = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute positionIncrementAttribute = addAttribute(PositionIncrementAttribute.class);
    private final String AT_PREFIX = "at ";

    @Override
    public final boolean incrementToken() throws IOException {
        int positionIncrement = positionIncrementAttribute.getPositionIncrement();

        clearAttributes();

        String trimmedLine;
        do {
            String line = readLine(input);
            if (line == null) {
                return false;
            }

            trimmedLine = line.replaceAll("^\\s+", "");

            if(!trimmedLine.startsWith(AT_PREFIX)) {
                positionIncrement += line.length();
            } else {
                positionIncrement += line.length() - trimmedLine.length();
            }
        } while (!trimmedLine.startsWith(AT_PREFIX));

        positionIncrementAttribute.setPositionIncrement(positionIncrement);

        trimmedLine = trimmedLine.substring(AT_PREFIX.length());
        int openingParenthIndex = trimmedLine.indexOf('(');
        if(openingParenthIndex != -1) {
            trimmedLine = trimmedLine.substring(0, openingParenthIndex);
        }

        termAttribute.resizeBuffer(trimmedLine.length());
        termAttribute.setEmpty().append(trimmedLine);

        return true;
    }

    private String readLine(Reader input) throws IOException {
        StringBuilder lineBuilder = new StringBuilder();

        char charRead;
        do {
            int charInt = input.read();
            if (charInt == -1) {
                break;
            }
            charRead = (char) charInt;
            lineBuilder.append(charRead);
        } while (charRead != '\n');

        return lineBuilder.length() == 0 ? null : lineBuilder.toString();

    }
}
