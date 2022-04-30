package com.ctc.demo.emulator.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.hibernate.service.spi.ServiceException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ctc.demo.emulator.client.DummyTestRequestClient;
import com.ctc.demo.emulator.model.APIRequest;
import com.ctc.demo.emulator.model.APIResponse;
import com.ctc.demo.emulator.util.SimulationUtils;

@Component
public class DummyBizDelegate {

	@Resource
	private DummyTestRequestClient testAPIClient;

	@SuppressWarnings("finally")
	public APIResponse receiveDataSubmissionFileCaptureResultResponse(APIRequest requestMessage) throws ServiceException {
		APIResponse response = null;
		String inputMessageType = "APIRequest";
		String outputMessageType = "APIResponse";
		try {
			String requestFilePath = SimulationUtils.getOutputMessagePath(requestMessage, inputMessageType, outputMessageType);
			APIRequest request = (APIRequest) SimulationUtils.getMessageObjectFromJson(APIRequest.class, requestFilePath);

			response = testAPIClient.sendClientTestApi(request);

		} finally {
			return response;
		}
	}

	public ResponseEntity performFileDownloadRequest (APIRequest body) throws FileNotFoundException {
		String inputMessageType = "APIRequest";
		String outputMessageType = "file_content";

		try {
			// determine the response is json or file object(Octet-stream)
			String requestFilePath = SimulationUtils.getOutputMessagePath(body, inputMessageType, outputMessageType);
			if (requestFilePath.endsWith("json")) {
				APIResponse cmnResp = (APIResponse) SimulationUtils.getMessageObjectFromJson(APIResponse.class, requestFilePath);

				return ResponseEntity.ok(cmnResp);
			} else {
				File file2Upload = new File(requestFilePath);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				InputStreamResource i = new InputStreamResource(new FileInputStream(file2Upload));

				return ResponseEntity.ok().headers(headers).contentLength(file2Upload.length())
						.contentType(MediaType.parseMediaType("application/octet-stream"))
						.body(i);
			}
		} finally {}
	}
}
