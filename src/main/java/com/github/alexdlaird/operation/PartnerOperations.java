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

import com.github.alexdlaird.FinicityClient;
import com.github.alexdlaird.exception.FinicityException;
import com.github.alexdlaird.type.partner.PartnerAccess;

/**
 * Interface for Partner operations, the API for which can be found <a href="https://developer.finicity.com/admin/docs/#/partners">here</a>.
 */
public interface PartnerOperations {
    /**
     * Validate the partner’s credentials (Finicity-App-Key, Partner ID, and Partner Secret) and return a temporary
     * access token. The token must be passed in the HTTP header Finicity-App-Token on all subsequent API requests (see
     * <a href="https://finicity.zendesk.com/hc/en-us/articles/202460715-Accessing-the-API">Accessing the API</a>). This
     * token is valid for two hours, after which the partner must call Partner Authentication again to obtain a new
     * token. <p> Ten unsuccessful attempts will cause the partner’s account to be locked. To unlock the account, send
     * an email to <a href="mailto:support@finicity.com"></a>Finicity Support. <p> Success: HTTP 200 (OK) <p> Though
     * this function is documented in the interface and publically accessible, token management should be done by the
     * {@link FinicityClient} and Operations classes to ensure this method is called as infrequently as possible.
     * Retrieving a token too often or having too many active tokens at the same may cause you to fall into ill favor
     * with Finicity.
     *
     * @return An object with a temporary access token
     * @throws FinicityException An error occurred when interaction with the API
     */
    PartnerAccess authentication();

    /**
     * Change the partner secret that is used to authenticate this partner. The secret does not expire, but can be
     * changed by calling Modify Partner Secret. A valid partner secret MUST include at least one number and at least
     * one letter, and its length MUST be between 12 and 255 characters. It MAY contain upper- and lowercase characters,
     * numbers, and the characters with !,@,#,$,%,&#x26;,*,_,-,+. <p> Success: HTTP 204 (No Content)
     *
     * @param newPartnerSecret The new secret to be set for the partner.
     * @throws FinicityException An error occurred when interaction with the API
     */
    void modifyPartnerSecret(String newPartnerSecret);

    /**
     * An error has occurred when processing an operation in {@link PartnerOperations}.
     */
    class PartnerOperationsException extends FinicityException {
        public PartnerOperationsException(String msg, Exception cause) {
            super(msg, cause);
        }

        public PartnerOperationsException(String msg) {
            super(msg);
        }
    }
}
