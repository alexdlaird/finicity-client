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

package com.github.alexdlaird.type.partner;

import com.github.alexdlaird.component.rest.Body;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Credentials implements Body {
    @Element
    private String partnerId;

    @Element
    private String partnerSecret;

    @Element(required = false)
    private String newPartnerSecret;

    public Credentials() {
    }

    public Credentials(String partnerId, String partnerSecret) {
        this.partnerId = partnerId;
        this.partnerSecret = partnerSecret;
    }

    public Credentials(String partnerId, String partnerSecret, String newPartnerSecret) {
        this.partnerId = partnerId;
        this.partnerSecret = partnerSecret;
        this.newPartnerSecret = newPartnerSecret;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getPartnerSecret() {
        return partnerSecret;
    }

    public String getNewPartnerSecret() {
        return newPartnerSecret;
    }
}
