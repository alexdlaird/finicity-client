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
import com.finicityclient.component.rest.RestClient;
import com.finicityclient.fixture.FixtureHelper;
import org.junit.Test;

public class DefaultCustomerOperationsTest {

    private final RestClient mockRestClient;

    private final DefaultCustomerOperations defaultCustomerOperations;

    public DefaultCustomerOperationsTest() {
        mockRestClient = FixtureHelper.createMockCustomerRestClient();
        Token token = FixtureHelper.createToken();

        defaultCustomerOperations = new DefaultCustomerOperations(mockRestClient, "APP_KEY", token);
    }

    @Test
    public void testGetCustomers() throws Exception {
        // TODO: implement test
    }

    @Test
    public void testGetCustomer() throws Exception {
        // TODO: implement test
    }

    @Test
    public void testAddTestingCustomer() throws Exception {
        // TODO: implement test
    }

    @Test
    public void testAddCustomer() throws Exception {
        // TODO: implement test
    }

    @Test
    public void testModifyCustomer() throws Exception {
        // TODO: implement test
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        // TODO: implement test
    }
}