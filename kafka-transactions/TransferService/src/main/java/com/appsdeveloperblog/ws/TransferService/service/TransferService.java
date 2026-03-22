package com.appsdeveloperblog.ws.TransferService.service;

import com.appsdeveloperblog.ws.TransferService.model.TransferRestModel;

public interface TransferService {
    public boolean transfer(TransferRestModel productPaymentRestModel);
}
