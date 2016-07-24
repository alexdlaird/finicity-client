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
