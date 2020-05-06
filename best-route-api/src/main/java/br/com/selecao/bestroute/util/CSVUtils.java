package br.com.selecao.bestroute.util;

import java.util.Collections;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Column;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSVUtils {

	public static <T> List<T> loadObjectList(final Class<T> type, final List<Column> cs, final MultipartFile file) {
		
		try {
			final CsvSchema schema = CsvSchema
					.builder()
					.addColumns(cs)
					.build()
					.withColumnSeparator(',')
					.withoutHeader()
					.withSkipFirstDataRow(false);
			final CsvMapper mapper = new CsvMapper();
			
			final MappingIterator<T> values = mapper
					.readerFor(type)
					.with(schema)
					.readValues(file.getInputStream());
			return values.readAll();
			
		} catch (Exception e) {
			log.error("Error occurred while loading object list from file " + file.getOriginalFilename(), e.getMessage());
		}
		
		return Collections.emptyList();
	}

}
