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

package com.finicityclient.type.tx_push;

import com.finicityclient.component.rest.Body;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Subscription implements Body {
    @Element(required = false)
    private String id;

    @Element(required = false)
    private String accountId;

    @Element(required = false)
    private SubscriptionType type;

    @Element
    private String callbackUrl;

    @Element(required = false)
    private String signingKey;

    public Subscription() {
    }

    public Subscription(String id, String accountId, SubscriptionType type, String callbackUrl, String signingKey) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.callbackUrl = callbackUrl;
        this.signingKey = signingKey;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public SubscriptionType getType() {
        return type;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getSigningKey() {
        return signingKey;
    }
}
