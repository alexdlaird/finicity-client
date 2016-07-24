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

package com.finicityclient.type;

import com.finicityclient.type.component.rest.RestClient;
import com.finicityclient.type.operation.*;

/**
 * A client for interacting with the <a href="https://developer.finicity.com/admin/docs">Finicity API</a>.
 *
 * An implementation of this interace must handle token management, documented <a href="https://finicity.zendesk.com/hc/en-us/articles/201702709-Partners">here</a>).
 * A token should only be requested every 90 minutes, so the retrieved token should be stored for resuse on subsequent
 * calls until expiration.
 *
 * Any Operations classes may throw a {@link RestClient.RestClientTokenExpiredException}. Check for and catch this
 * exception with each API call. If seen, simply refresh the token and retry. To refresh the token, call
 * {@link #refreshToken()}, which will handle retrieving a new token and updating it accordingly implementations.
 */
public interface FinicityClient {
    /**
     * Refresh the Finicity-App-Token, which is valid for 90 minutes.
     */
    void refreshToken();

    /**
     * Retrieve API client for "Partner" operations.
     *
     * @return The Partner API client.
     */
    PartnerOperations getPartnerOperations();

    /**
     * Retrieve API client for "Institution" operations.
     *
     * @return The Institution API client.
     */
    InstitutionOperations getInstitutionOperations();

    /**
     * Retrieve API client for "Customer" operations.
     *
     * @return The Customer API client.
     */
    CustomerOperations getCustomerOperations();

    /**
     * Retrieve API client for "Account" operations.
     *
     * @return The Account API client.
     */
    AccountOperations getAccountOperations();

    /**
     * Retrieve API client for "Transaction" operations.
     *
     * @return The Transaction API client.
     */
    TransactionOperations getTransactionOperations();
}
