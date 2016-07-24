/**
 * Copyright (c) 2016 Alex Laird
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.finicityclient;

import com.finicityclient.component.Token;
import com.finicityclient.component.rest.DefaultRestClient;
import com.finicityclient.component.rest.RestClient;
import com.finicityclient.operation.*;
import com.finicityclient.type.PartnerAccess;
import com.finicityclient.type.PartnerAccess;

/**
 * The default implementation of a {@link FinicityClient}, which instantiates default Operations clients and implements
 * token refreshing.
 *
 * This implementation is a singleton pattern, and an instance of the client can be retrieved by calling {@link #getInstance(String, String, String)}.
 * This is to ensure an authentication token is not retrieved too often and the proper refresh pattern is followed.
 */
public class DefaultFinicityClient implements FinicityClient {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String DEFAULT_CONTENT_TYPE = "application/xml";

    private static final long DEFAULT_TOKEN_EXPIRATION = 90 * 60 * 1000;

    private static DefaultFinicityClient instance;

    private final String appKey;

    private final String partnerId;

    private final String partnerSecret;

    private final DefaultRestClient restClient;

    private final DefaultPartnerOperations partnerOperations;

    private final DefaultAccountOperations accountOperations;

    private final DefaultCustomerOperations customerOperations;

    private final DefaultInstitutionOperations institutionOperations;

    private final DefaultTransactionOperations transactionOperations;

    private Token token;

    /**
     * Private to ensure only one instance of this class is instantiated. To retrieve an instance, call {@link #getInstance(String, String, String)}.
     *
     * @param appKey        The Finicity developer appKey.
     * @param partnerId     The Finicity developer partnerId.
     * @param partnerSecret The Finicity developer partnerSecret.
     */
    private DefaultFinicityClient(String appKey, String partnerId, String partnerSecret) {
        this.appKey = appKey;
        this.partnerId = partnerId;
        this.partnerSecret = partnerSecret;

        this.restClient = new DefaultRestClient(appKey, DEFAULT_ENCODING, DEFAULT_CONTENT_TYPE);

        partnerOperations = new DefaultPartnerOperations(restClient, appKey, partnerId, partnerSecret);
        PartnerAccess partnerAccess = partnerOperations.authentication();
        token = new Token(partnerAccess.getToken(), System.currentTimeMillis() + DEFAULT_TOKEN_EXPIRATION);
        restClient.refreshToken(token);

        accountOperations = new DefaultAccountOperations(restClient, appKey, token);
        customerOperations = new DefaultCustomerOperations(restClient, appKey, token);
        institutionOperations = new DefaultInstitutionOperations(restClient, appKey, token);
        transactionOperations = new DefaultTransactionOperations(restClient, appKey, token);
    }

    /**
     * Retrieve the singleton instance of the default Finicity client.
     *
     * Note that if an instance already exists and getInstance is called with a different set of Finicity credentials,
     * a new instance of the singleton will be instantiated and returned.
     *
     * @param appKey        The Finicity developer appKey.
     * @param partnerId     The Finicity developer partnerId.
     * @param partnerSecret The Finicity developer partnerSecret.
     * @return An instance of a {@link DefaultFinicityClient} with a valid authentication token.
     */
    public static DefaultFinicityClient getInstance(String appKey, String partnerId, String partnerSecret) {
        assert appKey != null;
        assert partnerId != null;
        assert partnerSecret != null;

        if (instance == null ||
                !instance.getAppKey().equals(appKey) ||
                !instance.getPartnerId().equals(partnerId) ||
                !instance.getPartnerSecret().equals(partnerSecret)) {
            instance = new DefaultFinicityClient(appKey, partnerId, partnerSecret);
        }

        return instance;
    }

    public static void main(String... args) {
        System.out.println("Nothing to see here. Just a library!");
    }

    private String getAppKey() {
        return appKey;
    }

    private String getPartnerId() {
        return partnerId;
    }

    private String getPartnerSecret() {
        return partnerSecret;
    }

    private void ensureTokenIsValid() {
        if (token != null && System.currentTimeMillis() > token.getExpiration()) {
            refreshToken();
        }
    }

    /**
     * Refresh the Finicity-App-Token, expiring 90 minutes from its renewal, and pass the updated token to the
     * {@link DefaultRestClient} and each of the Operations classes.
     */
    @Override
    public void refreshToken() {
        PartnerAccess partnerAccess = partnerOperations.authentication();
        token = new Token(partnerAccess.getToken(), System.currentTimeMillis() + DEFAULT_TOKEN_EXPIRATION);

        restClient.refreshToken(token);
        accountOperations.refreshToken(token);
        customerOperations.refreshToken(token);
        institutionOperations.refreshToken(token);
        transactionOperations.refreshToken(token);
    }

    @Override
    public PartnerOperations getPartnerOperations() {
        ensureTokenIsValid();

        return this.partnerOperations;
    }

    /**
     * {@inheritDoc}
     *
     * With each use, use the static reference to obtain the {@link DefaultFinicityClient}'s singleton, then retrieve
     * this Operations client to ensure a fresh and valid authentication token is alwasy used. If you are seeing
     * {@link RestClient.RestClientTokenExpiredException}, this is likely the cause.
     */
    @Override
    public InstitutionOperations getInstitutionOperations() {
        ensureTokenIsValid();

        return this.institutionOperations;
    }

    /**
     * {@inheritDoc}
     *
     * With each use, use the static reference to obtain the {@link DefaultFinicityClient}'s singleton, then retrieve
     * this Operations client to ensure a fresh and valid authentication token is alwasy used. If you are seeing
     * {@link RestClient.RestClientTokenExpiredException}, this is likely the cause.
     */
    @Override
    public CustomerOperations getCustomerOperations() {
        ensureTokenIsValid();

        return this.customerOperations;
    }

    /**
     * {@inheritDoc}
     *
     * With each use, use the static reference to obtain the {@link DefaultFinicityClient}'s singleton, then retrieve
     * this Operations client to ensure a fresh and valid authentication token is alwasy used. If you are seeing
     * {@link RestClient.RestClientTokenExpiredException}, this is likely the cause.
     */
    @Override
    public AccountOperations getAccountOperations() {
        ensureTokenIsValid();

        return this.accountOperations;
    }

    /**
     * {@inheritDoc}
     *
     * With each use, use the static reference to obtain the {@link DefaultFinicityClient}'s singleton, then retrieve
     * this Operations client to ensure a fresh and valid authentication token is alwasy used. If you are seeing
     * {@link RestClient.RestClientTokenExpiredException}, this is likely the cause.
     */
    @Override
    public TransactionOperations getTransactionOperations() {
        ensureTokenIsValid();

        return this.transactionOperations;
    }
}