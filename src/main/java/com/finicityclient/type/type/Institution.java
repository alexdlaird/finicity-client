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
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Institution {
    @Element
    private Integer id;

    @Element
    private String name;

    @Element(required = false)
    private String accountTypeDescription;

    @Element(required = false)
    private String urlHomeApp;

    @Element(required = false)
    private String urlLogonApp;

    @Element(required = false)
    private String phone;

    @Element(required = false)
    private String currency;

    @Element(required = false)
    private String email;

    @Element(required = false)
    private String specialText;

    @Path("address")
    @Element(name = "addressLine1", required = false)
    private String addressLine1;

    @Path("address")
    @Element(required = false)
    private String addressLine2;

    @Path("address")
    @Element(required = false)
    private String city;

    @Path("address")
    @Element(required = false)
    private String state;

    @Path("address")
    @Element(required = false)
    private String postalCode;

    @Path("address")
    @Element(required = false)
    private String country;

    public Institution() {
    }

    public Institution(Integer id, String name, String accountTypeDescription, String urlHomeApp, String urlLogonApp, String phone, String currency, String email, String specialText, String addressLine1, String addressLine2, String city, String state, String postalCode, String country) {
        this.id = id;
        this.name = name;
        this.accountTypeDescription = accountTypeDescription;
        this.urlHomeApp = urlHomeApp;
        this.urlLogonApp = urlLogonApp;
        this.phone = phone;
        this.currency = currency;
        this.email = email;
        this.specialText = specialText;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccountTypeDescription() {
        return accountTypeDescription;
    }

    public String getUrlHomeApp() {
        return urlHomeApp;
    }

    public String getUrlLogonApp() {
        return urlLogonApp;
    }

    public String getPhone() {
        return phone;
    }

    public String getCurrency() {
        return currency;
    }

    public String getEmail() {
        return email;
    }

    public String getSpecialText() {
        return specialText;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }
}
