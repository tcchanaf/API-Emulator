package com.ctc.demo.emulator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ctc.demo.emulator.biz.DummyBizDelegate;
import com.ctc.demo.emulator.model.APIRequest;
import com.ctc.demo.emulator.model.APIResponse;
import com.ctc.demo.emulator.util.CTCLogger;
import com.ctc.demo.emulator.web.api.TestRequestToServerApi;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DummyController implements TestRequestToServerApi {
	private static int THREAD_ID = 0;

	@Resource
	private DummyBizDelegate bizDelegate;


	@RequestMapping(value = "/data-submission/upload/request",
			produces = { "application/json" },
			consumes = { "multipart/form-data" },
			method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<APIResponse> handleFileUpload(HttpServletRequest request) throws IOException {

		APIResponse response = new APIResponse();
		APIRequest message = null;
		OutputStream outStream = null;

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			for (Part p: multipartRequest.getParts()) {
				if ("application/json".equals(p.getContentType())) {
					InputStream inputStream = p.getInputStream();
					String json = IOUtils.toString(inputStream, "utf-8");
					message = new ObjectMapper().readValue(json, APIRequest.class);
				} else {
					// download application/octet-stream\
					InputStream inputStream = p.getInputStream();
					byte[] buffer = new byte[inputStream.available()];
					inputStream.read(buffer);

					File targetFile = new File("target\\downloadFolder\\downloadFile.meta");
					outStream = new FileOutputStream(targetFile);
					outStream.write(buffer);
					outStream.close();
				}
			}
		} catch(Exception ex) {
			if (outStream != null)
				outStream.close();
			ex.printStackTrace();
		}


		if (message != null) {
			final APIRequest requestMessage = message;
			CTCLogger.error("requestMessage: {0}", requestMessage);
			Timer timer = new Timer("CPMonitor-"+ (THREAD_ID++), true);
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					try {
						bizDelegate.receiveDataSubmissionFileCaptureResultResponse(requestMessage);

					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			}, 1 * 1000); //delay 1 seconds
		}
		response.setReturnDesc("The Message is received");

		return ResponseEntity.ok(response);
	}


	@Override
	public ResponseEntity performFileDownloadRequest(APIRequest request) {
		try {
			return bizDelegate.performFileDownloadRequest(request);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
