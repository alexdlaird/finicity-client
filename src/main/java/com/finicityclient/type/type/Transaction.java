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

package com.finicityclient.type.type;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Transaction {
    @Element
    private String accountId;

    @Element
    private double amount;

    @Element
    private double bonusAmount;

    @Element(required = false)
    private String checkNum;

    @Element(required = false)
    private long createdDate;

    @Element
    private String customerId;

    @Element(required = false)
    private String description;

    @Element(required = false)
    private double escrowAmount;

    @Element(required = false)
    private double feeAmount;

    @Element
    private String id;

    @Element
    private String institutionTransactionId;

    @Element(required = false)
    private double interestAmount;

    @Element(required = false)
    private String memo;

    @Element(required = false)
    private long postedDate;

    @Element(required = false)
    private double principalAmount;

    @Element
    private TransactionStatus status;

    @Element(required = false)
    private Subaccount subaccount;

    @Element(required = false)
    private String transactionDate;

    @Element(required = false)
    private String type;

    @Element(required = false)
    private double unitQuantity;

    @Element(required = false)
    private double unitValue;

    @Element(required = false)
    private Categorization categorization;

    public Transaction() {
    }

    public Transaction(String accountId, double amount, double bonusAmount, String checkNum, long createdDate, String customerId, String description, double escrowAmount, double feeAmount, String id, String institutionTransactionId, double interestAmount, String memo, long postedDate, double principalAmount, TransactionStatus status, Subaccount subaccount, String transactionDate, String type, double unitQuantity, double unitValue, Categorization categorization) {
        this.accountId = accountId;
        this.amount = amount;
        this.bonusAmount = bonusAmount;
        this.checkNum = checkNum;
        this.createdDate = createdDate;
        this.customerId = customerId;
        this.description = description;
        this.escrowAmount = escrowAmount;
        this.feeAmount = feeAmount;
        this.id = id;
        this.institutionTransactionId = institutionTransactionId;
        this.interestAmount = interestAmount;
        this.memo = memo;
        this.postedDate = postedDate;
        this.principalAmount = principalAmount;
        this.status = status;
        this.subaccount = subaccount;
        this.transactionDate = transactionDate;
        this.type = type;
        this.unitQuantity = unitQuantity;
        this.unitValue = unitValue;
        this.categorization = categorization;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public double getBonusAmount() {
        return bonusAmount;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getDescription() {
        return description;
    }

    public double getEscrowAmount() {
        return escrowAmount;
    }

    public double getFeeAmount() {
        return feeAmount;
    }

    public String getId() {
        return id;
    }

    public String getInstitutionTransactionId() {
        return institutionTransactionId;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public String getMemo() {
        return memo;
    }

    public long getPostedDate() {
        return postedDate;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Subaccount getSubaccount() {
        return subaccount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getType() {
        return type;
    }

    public double getUnitQuantity() {
        return unitQuantity;
    }

    public double getUnitValue() {
        return unitValue;
    }

    public Categorization getCategorization() {
        return categorization;
    }
}
