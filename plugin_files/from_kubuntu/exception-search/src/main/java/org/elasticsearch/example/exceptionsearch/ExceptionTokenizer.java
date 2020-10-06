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
import java.util.Arrays;

public final class ExceptionTokenizer extends Tokenizer {
    private final CharTermAttribute termAttribute = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute positionIncrementAttribute = addAttribute(PositionIncrementAttribute.class);

    @Override
    public boolean incrementToken() throws IOException {
        int previousIncrement = positionIncrementAttribute.getPositionIncrement();

        clearAttributes();

        int positionIncrement = previousIncrement;
        String line;
        do {
            line = readLine(input);

            if (line == null) {
                return false;
            }

            positionIncrement += line.length();
        } while (!line.startsWith("at "));

        positionIncrementAttribute.setPositionIncrement(positionIncrement);

        line = line.substring("at ".length());
        int openingParenth = line.indexOf('(');
        if(openingParenth != -1) {
            line = line.substring(0, openingParenth);
        }

        termAttribute.resizeBuffer(line.length());
        termAttribute.setEmpty().append(line);

        return true;
    }

    private String readLine(Reader input) throws IOException {
        char[] buffer = new char[8192];
        int bufferPos = 0;
        char charRead;
        do {
            int charInt = input.read();
            if(charInt == -1) {
                break;
            }

            charRead = (char) charInt;
            buffer[bufferPos++] = charRead;
        } while(charRead != '\n');

        if(bufferPos == 0) {
            // Nothing was read, return null
            return null;
        }

        char[] result = Arrays.copyOfRange(buffer, 0, bufferPos);
        return new String(result);
    }
}
