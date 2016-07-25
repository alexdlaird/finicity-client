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

package com.github.alexdlaird.fixture;

import com.github.alexdlaird.component.FinicityPersister;
import com.github.alexdlaird.component.Token;
import com.github.alexdlaird.component.rest.Parameter;
import com.github.alexdlaird.component.rest.Response;
import com.github.alexdlaird.component.rest.RestClient;
import com.github.alexdlaird.type.customer.Customer;
import com.github.alexdlaird.type.customer.CustomerType;
import com.github.alexdlaird.type.partner.Credentials;
import com.github.alexdlaird.type.partner.PartnerAccess;
import com.github.alexdlaird.type.transaction.Transaction;
import com.github.alexdlaird.type.transaction.Transactions;
import org.simpleframework.xml.Serializer;

import java.io.StringWriter;
import java.util.Collections;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FixtureHelper {

    private final static Serializer serializer = new FinicityPersister();

    public static RestClient createMockPartnerRestClient() throws Exception {
        RestClient mockRestClient = mock(RestClient.class);

        PartnerAccess partnerAccess = new PartnerAccess("TOKEN");
        StringWriter writer = new StringWriter();
        serializer.write(partnerAccess, writer);

        when(mockRestClient.executePost(
                eq("/v2/partners/authentication"), any(Credentials.class), anyListOf(Parameter.class), anyMapOf(String.class, String.class)))
                .thenReturn(
                        new Response(200, writer.toString(), null));
        when(mockRestClient.executePut(
                eq("/v2/partners/authentication"), any(Credentials.class), anyListOf(Parameter.class), anyMapOf(String.class, String.class)))
                .thenReturn(
                        new Response(204, "", null));

        return mockRestClient;
    }

    public static RestClient createMockAccountRestClient() {
        RestClient mockRestClient = mock(RestClient.class);

        // TODO: implement mocks

        return mockRestClient;
    }

    public static RestClient createMockCustomerRestClient() {
        RestClient mockRestClient = mock(RestClient.class);

        // TODO: implement mocks

        return mockRestClient;
    }

    public static RestClient createMockInstitutionRestClient() {
        RestClient mockRestClient = mock(RestClient.class);

        // TODO: implement mocks

        return mockRestClient;
    }

    public static RestClient createMockTransactionRestClient(String customerId) throws Exception {
        RestClient mockRestClient = mock(RestClient.class);

        Transactions transactions = new Transactions(Collections.singletonList(new Transaction(25.0, "description", System.currentTimeMillis(), System.currentTimeMillis())));
        StringWriter writer = new StringWriter();
        serializer.write(transactions, writer);

        when(mockRestClient.executeGet(
                eq("/v2/customers/" + customerId + "/transactions"), anyListOf(Parameter.class), anyMapOf(String.class, String.class)))
                .thenReturn(
                        new Response(200, writer.toString(), null));

        return mockRestClient;
    }

    public static RestClient createMockTxPushRestClient() {
        RestClient mockRestClient = mock(RestClient.class);

        // TODO: implement mocks

        return mockRestClient;
    }

    public static Token createToken() {
        return new Token("TOKEN", System.currentTimeMillis() + (90 * 60 * 1000));
    }

    public static Customer createCustomer() {
        return new Customer("id1234", "test-username", "First", "Last", CustomerType.TESTING, System.currentTimeMillis());
    }
}
