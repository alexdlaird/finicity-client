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

package com.finicityclient.operation;

import com.finicityclient.component.Token;
import com.finicityclient.component.rest.Response;
import com.finicityclient.component.rest.RestClient;
import com.finicityclient.type.Credentials;
import com.finicityclient.type.PartnerAccess;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link PartnerOperations}.
 */
public class DefaultPartnerOperations extends DefaultOperations implements PartnerOperations {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultPartnerOperations.class));

    /**
     * Finicity partnerId.
     */
    private final String partnerId;

    /**
     * Finicity partnerSecret.
     */
    private final String partnerSecret;

    /**
     * Construct a client for Partner operations.
     *
     * @param restClient    A REST client for operations.
     * @param appKey        Finicity appKey.
     * @param partnerId     Finicity partnerId.
     * @param partnerSecret Finicity partnerSecret.
     */
    public DefaultPartnerOperations(RestClient restClient, String appKey, String partnerId, String partnerSecret) {
        super(restClient, appKey, null);

        this.partnerId = partnerId;
        this.partnerSecret = partnerSecret;
    }

    /**
     * {@link PartnerOperations} does not use or implement the authentication {@link Token token}.
     *
     * @param token Unused.
     */
    @Override
    public void refreshToken(Token token) {
        throw new UnsupportedOperationException();
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

    @Override
    public void modifyPartnerSecret(Credentials credentials) {
        Response response = restClient.executePut("/v2/partners/authentication",
                credentials,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new PartnerOperations.PartnerOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }
}
