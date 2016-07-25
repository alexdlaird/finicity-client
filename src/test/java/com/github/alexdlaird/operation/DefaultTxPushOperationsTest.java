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

package com.github.alexdlaird.operation;

import com.github.alexdlaird.component.Token;
import com.github.alexdlaird.component.rest.RestClient;
import com.github.alexdlaird.fixture.FixtureHelper;
import org.junit.Test;

public class DefaultTxPushOperationsTest {

    private final RestClient mockRestClient;

    private final DefaultTxPushOperations defaultTxPushOperations;

    public DefaultTxPushOperationsTest() {
        mockRestClient = FixtureHelper.createMockTxPushRestClient();
        Token token = FixtureHelper.createToken();

        defaultTxPushOperations = new DefaultTxPushOperations(mockRestClient, "APP_KEY", token);
    }

    @Test
    public void testEnableTxPushNotifications() throws Exception {
        // TODO: implement test
    }

    @Test
    public void testDisableTxPushNotifications() throws Exception {
        // TODO: implement test
    }

    @Test
    public void testDeleteTxPushSubscription() throws Exception {
        // TODO: implement test
    }

    @Test
    public void testAddTransactionForTestingAccount() throws Exception {
        // TODO: implement test
    }
}