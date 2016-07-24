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

package com.finicityclient.type.account;

import com.finicityclient.component.rest.Body;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Account implements AccountResponse, Body {
    @Element
    private String id;

    @Element
    private String number;

    @Element
    private String name;

    @Element
    private AccountType type;

    @Element
    private AccountStatus status;

    @Element
    private double balance;

    @Element(required = false)
    private int aggregationStatusCode;

    @Element(required = false)
    private String customerId;

    @Element(required = false)
    private String institutionId;

    @Element(required = false)
    private long balanceDate;

    @Element(required = false)
    private long aggregationSuccessDate;

    @Element(required = false)
    private long aggregationAttemptDate;

    @Element(required = false)
    private long createdDate;

    @Element(required = false)
    private long lastUpdatedDate;

    @Element(required = false)
    private String detail;

    public Account() {
    }

    public Account(String id, String number, String name, AccountType type, AccountStatus status, double balance, int aggregationStatusCode, String customerId, String institutionId, long balanceDate, long aggregationSuccessDate, long aggregationAttemptDate, long createdDate, long lastUpdatedDate, String detail) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.type = type;
        this.status = status;
        this.balance = balance;
        this.aggregationStatusCode = aggregationStatusCode;
        this.customerId = customerId;
        this.institutionId = institutionId;
        this.balanceDate = balanceDate;
        this.aggregationSuccessDate = aggregationSuccessDate;
        this.aggregationAttemptDate = aggregationAttemptDate;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public double getBalance() {
        return balance;
    }

    public int getAggregationStatusCode() {
        return aggregationStatusCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public long getBalanceDate() {
        return balanceDate;
    }

    public long getAggregationSuccessDate() {
        return aggregationSuccessDate;
    }

    public long getAggregationAttemptDate() {
        return aggregationAttemptDate;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public long getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public String getDetail() {
        return detail;
    }
}
