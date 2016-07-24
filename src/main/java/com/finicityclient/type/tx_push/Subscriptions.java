package com.finicityclient.type.tx_push;

import com.finicityclient.component.rest.Body;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class Subscriptions implements Body {
    @ElementList(inline = true, required = false, empty = false)
    private List<Subscription> subscriptions;

    public Subscriptions() {
    }

    public Subscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}
