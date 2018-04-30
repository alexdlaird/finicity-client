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

package com.github.alexdlaird.type.transaction;

import com.github.alexdlaird.component.rest.Body;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Transaction implements Body {
    @Element(required = false)
    private String accountId;

    @Element(required = false)
    private Double amount;

    @Element(required = false)
    private Double bonusAmount;

    @Element(required = false)
    private String checkNum;

    @Element(required = false)
    private Long createdDate;

    @Element(required = false)
    private String customerId;

    @Element(required = false)
    private String description;

    @Element(required = false)
    private Double escrowAmount;

    @Element(required = false)
    private Double feeAmount;

    @Element(required = false)
    private String id;

    @Element(required = false)
    private String institutionTransactionId;

    @Element(required = false)
    private Double interestAmount;

    @Element(required = false)
    private String memo;

    @Element(required = false)
    private Long postedDate;

    @Element(required = false)
    private Double principalAmount;

    @Element(required = false)
    private TransactionStatus status;

    @Element(required = false)
    private Subaccount subaccount;

    @Element(required = false)
    private Long transactionDate;

    @Element(required = false)
    private String type;

    @Element(required = false)
    private Double unitQuantity;

    @Element(required = false)
    private Double unitValue;

    @Element(required = false)
    private Categorization categorization;

    public Transaction() {
    }

    public Transaction(final Double amount, final String description, final Long postedDate,
                       final Long transactionDate) {
        this.amount = amount;
        this.description = description;
        this.postedDate = postedDate;
        this.transactionDate = transactionDate;
    }

    public Transaction(final String accountId, final Double amount, final Double bonusAmount, final String checkNum,
                       final Long createdDate, final String customerId, final String description,
                       final Double escrowAmount, final Double feeAmount, final String id,
                       final String institutionTransactionId, final Double interestAmount, final String memo,
                       final Long postedDate, final Double principalAmount, final TransactionStatus status,
                       final Subaccount subaccount, final Long transactionDate, final String type, Double unitQuantity,
                       final Double unitValue, final Categorization categorization) {
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

    public Double getAmount() {
        return amount;
    }

    public Double getBonusAmount() {
        return bonusAmount;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getDescription() {
        return description;
    }

    public Double getEscrowAmount() {
        return escrowAmount;
    }

    public Double getFeeAmount() {
        return feeAmount;
    }

    public String getId() {
        return id;
    }

    public String getInstitutionTransactionId() {
        return institutionTransactionId;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public String getMemo() {
        return memo;
    }

    public Long getPostedDate() {
        return postedDate;
    }

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Subaccount getSubaccount() {
        return subaccount;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public String getType() {
        return type;
    }

    public Double getUnitQuantity() {
        return unitQuantity;
    }

    public Double getUnitValue() {
        return unitValue;
    }

    public Categorization getCategorization() {
        return categorization;
    }
}
