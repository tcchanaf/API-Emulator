package com.ctc.demo.emulator.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ctc.demo.emulator.service.impl.DummyCsvReloadServiceImpl;
import com.ctc.demo.emulator.service.impl.DummyCsvReloadServiceImpl.MessageCsvEntry;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimulationUtils {
	private static final String DEFAULT = "default";

	public static Method getGetterByString(Class<?> modelClass, String methodStr) {
		Method[] methods = modelClass.getDeclaredMethods();
		Method resultGetter = null;
		for (Method m: methods) {
			if (StringUtils.equals(m.getName(), methodStr)) {
				resultGetter = m;
				break;
			}
		}

		if (resultGetter != null)
			return resultGetter;

		CTCLogger.debug("Model class [{0}] do not have defined getter: [{1}]",
				modelClass.getName(), modelClass, methodStr);

		return null;
	}

	public static Object callGetterByString(Object instance, String methodStr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = getGetterByString(instance.getClass(), methodStr);
		if (m != null)
			return m.invoke(instance);

		return null;
	}

	public static String getOutputMessagePath(Object request, String inputMessageType, String outputMessageType) {
		List<MessageCsvEntry> filteredMsg = DummyCsvReloadServiceImpl.DUMMY_MESSAGE
				.stream()
				.filter(item -> outputMessageType.equals(item.getOutMsgType()) && inputMessageType.equals(item.getInMsgType()))
				.collect(Collectors.toList());

		List<MessageCsvEntry> defaultMsgs = DummyCsvReloadServiceImpl.DUMMY_MESSAGE
				.stream()
				.filter(item -> outputMessageType.equals(item.getOutMsgType()) && DEFAULT.equals(item.getGetterMethods()))
				.collect(Collectors.toList());

		if (defaultMsgs.size() == 0)
			CTCLogger.error("No default message for output messageType: {0}", outputMessageType);

		for (MessageCsvEntry msg: filteredMsg) {
			Object predicateObj = request;
			String[] getterMethods = msg.getGetterMethods().split("_");
			try {
				for(String getterStr: getterMethods) {
					predicateObj = callGetterByString(predicateObj, getterStr);
				}
				if (predicateObj == null)
					predicateObj = "null";
			} catch (Exception e) {
				continue;
			}
			if (msg.getRegex() == null)
				continue;
			String predicateStr = predicateObj.toString();
			if (msg.getRegex().matcher(predicateStr).matches())

				return msg.getRespOrReqJsonFilePath();
		}

		return defaultMsgs.get(0).getRespOrReqJsonFilePath();
	}



	public static Object getMessageObjectFromJson(Class<?> messageClass, String jsonFilePath){
		Object message = null;

		try {
			message = new ObjectMapper().readValue(new File(jsonFilePath), messageClass);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return message;
	}
}
