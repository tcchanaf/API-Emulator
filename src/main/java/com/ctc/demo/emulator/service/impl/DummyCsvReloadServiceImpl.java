package com.ctc.demo.emulator.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ctc.demo.emulator.service.DummyCsvReloadService;
import com.ctc.demo.emulator.util.CTCLogger;
import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVEntryParser;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import lombok.Data;

@Component
public class DummyCsvReloadServiceImpl implements DummyCsvReloadService {

	protected static final String MESSAGE_CLASSPATH_MAPPING = "target/classes/dummy_message_map.csv";

	public static final List<MessageCsvEntry> DUMMY_MESSAGE = new ArrayList<MessageCsvEntry>();

	Timer mapReloadTimmer = new Timer(this.getClass().getName() + ".timer", true);


	@PostConstruct
	public void init() {
		reloadCsvEntries();
		mapReloadTimmer.schedule(new TimerTask() {
			@Override
			public void run() {
				reloadCsvEntries();
			}
		}, 1 * 15 * 1000, 1 * 20 * 1000);

	}

	public void destroy() {
		if (mapReloadTimmer != null) {
			mapReloadTimmer.cancel();
		}
	}


	protected void reloadCsvEntries() {
		CTCLogger.info("reloadCsvEntries");
		InputStream inStream = null;
		byte[] csvData = null;
		try {
				inStream = FileUtils.openInputStream(new File(MESSAGE_CLASSPATH_MAPPING));
				if (inStream == null)
					return;
				csvData = IOUtils.toByteArray(inStream);

			CSVReader<MessageCsvEntry> reader = new CSVReaderBuilder<MessageCsvEntry>(
					new InputStreamReader(new ByteArrayInputStream(csvData)))
							.entryParser(new AcctCsvEntryParser())
							.strategy(new CSVStrategy(',', CSVStrategy.UK_DEFAULT.getQuoteCharacter(),
									CSVStrategy.UK_DEFAULT.getCommentIndicator(), true, true))
							.build();
			List<MessageCsvEntry> entries = reader.readAll();
			DUMMY_MESSAGE.clear();
			if (entries != null && !entries.isEmpty()) {
				for (MessageCsvEntry entry : entries) {
					if (entry == null)
						continue;

					DUMMY_MESSAGE.add(entry);
				}
			}

			CTCLogger.info("Reload CSV Dummy Account, {0} entries", DUMMY_MESSAGE.size());

//				for(MessageCsvEntry e : DUMMY_MESSAGE) {
//					CTCLogger.info("{0}",e);
//				}

		} catch (Exception e) {
			CTCLogger.error("Failed to read dummy message mapping csv file, cause by:{0}", new String[] { e.getMessage() }, e);
		} finally {
			IOUtils.closeQuietly(inStream);
		}
	}

	@Data
	public static class MessageCsvEntry {
		private String inMsgType;
		private String outMsgType;
		private String getterMethods;
		private Pattern regex;
		private String respOrReqJsonFilePath;
	}

	protected static class AcctCsvEntryParser implements CSVEntryParser<MessageCsvEntry> {
		@Override
		public MessageCsvEntry parseEntry(String... data) {
			MessageCsvEntry entry = new MessageCsvEntry();

			if (data == null || data.length == 0 || data.length < 4)
				return null;

			//parse account data first
			for (int i = 0; i < data.length; i++) {
				String field = StringUtils.trimToNull(data[i]);
				if (field == null)
					continue;

				switch (i) {
				case 0:
					entry.setInMsgType(field);
					break;
				case 1:
					entry.setOutMsgType(field);
					break;
				case 2:
					entry.setGetterMethods(field);
					break;
				case 3:
					entry.setRegex(Pattern.compile(field));
					break;
				case 4:
					entry.setRespOrReqJsonFilePath(field);
					break;

				}
			}

			return entry;
		}
	}
}
