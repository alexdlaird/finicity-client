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

import com.github.alexdlaird.component.rest.Parameter;
import com.github.alexdlaird.component.rest.RestClient;
import com.github.alexdlaird.fixture.FixtureHelper;
import com.github.alexdlaird.type.partner.Credentials;
import com.github.alexdlaird.type.partner.PartnerAccess;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

public class DefaultPartnerOperationsTest {

    private final RestClient mockRestClient;

    private final DefaultPartnerOperations defaultPartnerOperations;

    public DefaultPartnerOperationsTest() {
        mockRestClient = FixtureHelper.createMockPartnerRestClient();

        defaultPartnerOperations = new DefaultPartnerOperations(mockRestClient, "APP_KEY", "PARTNER_ID", "PARTNER_SECRET");
    }

    @Test
    public void testAuthentication() throws Exception {
        PartnerAccess authentication = defaultPartnerOperations.authentication();

        assertEquals("TOKEN", authentication.getToken());
    }

    @Test
    public void testModifyPartnerSecret() throws Exception {
        defaultPartnerOperations.modifyPartnerSecret("NEW_PARTNER_SECRET");

        ArgumentCaptor<Credentials> argumentCaptor = ArgumentCaptor.forClass(Credentials.class);
        verify(mockRestClient).executePut(anyString(), argumentCaptor.capture(), Matchers.<List<Parameter>>eq(null), Matchers.<Map<String, String>>eq(null));

        assertEquals("NEW_PARTNER_SECRET", argumentCaptor.getValue().getNewPartnerSecret());
    }
}