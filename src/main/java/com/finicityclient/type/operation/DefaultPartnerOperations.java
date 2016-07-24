/**
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

package com.finicityclient.type.operation;

import com.finicityclient.type.component.FinicityPersister;
import com.finicityclient.type.component.rest.Body;
import com.finicityclient.type.component.rest.Response;
import com.finicityclient.type.component.rest.RestClient;
import com.finicityclient.type.type.PartnerAccess;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultPartnerOperations implements PartnerOperations {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultPartnerOperations.class));

    protected final Serializer serializer = new FinicityPersister();

    private final RestClient restClient;

    private final String appKey;

    private final String partnerId;

    private final String partnerSecret;

    public DefaultPartnerOperations(RestClient restClient, String appKey, String partnerId, String partnerSecret) {
        this.restClient = restClient;

        this.appKey = appKey;
        this.partnerId = partnerId;
        this.partnerSecret = partnerSecret;
    }

    @Override
    public PartnerAccess authentication() {
        Response response = restClient.executePost("/v2/partners/authentication",
                new Credentials(partnerId, partnerSecret),
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new PartnerOperations.PartnerOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(PartnerAccess.class, response.getBody());
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the authentication response.", ex);

            throw new PartnerOperations.PartnerOperationsException("An error occurred when parsing the authentication response.", ex);
        }
    }

    @Root
    public static class Credentials implements Body {
        @Element
        private String partnerId;

        @Element
        private String partnerSecret;

        public Credentials() {
        }

        public Credentials(String partnerId, String partnerSecret) {
            this.partnerId = partnerId;
            this.partnerSecret = partnerSecret;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public String getPartnerSecret() {
            return partnerSecret;
        }
    }
}
