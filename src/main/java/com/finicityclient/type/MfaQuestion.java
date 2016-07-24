/*
 * Copyright (c) 2016 Alex Laird
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.finicityclient.type;

import com.finicityclient.component.rest.Body;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.Map;

@Root(name = "question")
public class MfaQuestion implements Body {
    @Element
    private String text;

    @Element(required = false)
    private String image;

    @ElementMap(entry = "choice", key = "value", required = false, attribute = true, inline = true)
    private Map<String, String> choices;

    @ElementMap(entry = "imageChoice", key = "value", required = false, attribute = true, inline = true)
    private Map<String, String> imageChoices;

    @Element(required = false)
    private String answer;

    public MfaQuestion() {
    }

    public MfaQuestion(String text, String image, Map<String, String> choices, Map<String, String> imageChoices) {
        this.text = text;
        this.image = image;
        this.choices = choices;
        this.imageChoices = imageChoices;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public Map<String, String> getChoices() {
        return choices;
    }

    public Map<String, String> getImageChoices() {
        return imageChoices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
