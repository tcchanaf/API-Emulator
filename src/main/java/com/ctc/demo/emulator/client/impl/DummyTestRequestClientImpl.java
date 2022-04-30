package com.ctc.demo.emulator.client.impl;

import javax.annotation.Resource;
import javax.ws.rs.WebApplicationException;

import org.springframework.stereotype.Service;

import com.ctc.demo.emulator.client.DummyTestRequestClient;
import com.ctc.demo.emulator.client.api.TestRequestToClientApi;
import com.ctc.demo.emulator.model.APIRequest;
import com.ctc.demo.emulator.model.APIResponse;
import com.ctc.demo.emulator.util.CTCLogger;

@Service(value="testAPIClient")
public class DummyTestRequestClientImpl implements DummyTestRequestClient {

	@Resource
	private TestRequestToClientApi testRequestToClientApi;


	@SuppressWarnings("finally")
	@Override
	public APIResponse sendClientTestApi(APIRequest request) {
		APIResponse response = null;
		try {
			response = testRequestToClientApi.testClientApi(request);

		}catch (WebApplicationException e) {

			CTCLogger.error("Fail to invoke testClientApi, caused by {0}", e.getMessage());
		} finally {
			return response;
		}
	}




}