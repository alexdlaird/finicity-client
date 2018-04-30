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

package com.github.alexdlaird.type.institution;

import com.github.alexdlaird.component.rest.Body;

import org.simpleframework.xml.Element;

public class LoginField implements Body {
    @Element
    private String id;

    @Element
    private String name;

    @Element(required = false)
    private String value;

    @Element(required = false)
    private Integer displayOrder;

    @Element(required = false)
    private Boolean mask;

    @Element(required = false)
    private String description;

    @Element(required = false)
    private String instructions;

    @Element(required = false)
    private Integer valueLengthMin;

    @Element(required = false)
    private Integer valueLengthMax;

    public LoginField() {
    }

    public LoginField(final String id, final String name, final String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public LoginField(final String id, final String name, final String value, final Integer displayOrder,
                      final Boolean mask, final String description, final String instructions,
                      final Integer valueLengthMin, final Integer valueLengthMax) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.displayOrder = displayOrder;
        this.mask = mask;
        this.description = description;
        this.instructions = instructions;
        this.valueLengthMin = valueLengthMin;
        this.valueLengthMax = valueLengthMax;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public Boolean isMask() {
        return mask;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public Integer getValueLengthMin() {
        return valueLengthMin;
    }

    public Integer getValueLengthMax() {
        return valueLengthMax;
    }
}
