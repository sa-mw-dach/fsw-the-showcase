/*
 * 2012-3 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.overlord.rtgov.quickstarts.demos.orders;

public class OrderAck {
    
    private String _orderId;
    private boolean _accepted;
    private String _status;
    private String _customer;
    private double _total=0;
    
    public String getOrderId() {
        return _orderId;
    }
    
    public boolean isAccepted() {
        return _accepted;
    }
    
    public String getStatus() {
        return _status;
    }
    
    public OrderAck setOrderId(String orderId) {
        _orderId = orderId;
        return this;
    }
    
    public OrderAck setStatus(String status) {
        _status = status;
        return this;
    }

    public OrderAck setAccepted(boolean accepted) {
        _accepted = accepted;
        return this;
    }
    
    public double getTotal() {
        return _total;
    }

    public void setTotal(double total) {
        this._total = total;
    }
    
    public OrderAck setCustomer(String customer) {
        this._customer = customer;
        return this;
    }
    
    public String getCustomer() {
        return _customer;
    }

    @Override
    public String toString() {
        if (_accepted) {
            return "Order " + _orderId + " Accepted.";
        } else {
            return "Order " + _orderId + " Not Accepted: " + _status;
        }
    }
}
