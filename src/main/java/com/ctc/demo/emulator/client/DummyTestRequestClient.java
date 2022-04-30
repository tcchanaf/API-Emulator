package com.ctc.demo.emulator.client;

import org.hibernate.service.spi.ServiceException;

import com.ctc.demo.emulator.model.APIRequest;
import com.ctc.demo.emulator.model.APIResponse;

public interface DummyTestRequestClient {

	public APIResponse sendClientTestApi(APIRequest request) throws ServiceException;


}
