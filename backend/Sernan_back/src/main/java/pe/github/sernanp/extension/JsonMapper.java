package pe.github.sernanp.extension;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class JsonMapper extends ObjectMapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonMapper() {
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		this.registerModule(new Jdk8Module());
		//this.registerModule(new JodaModule());
		this.setSerializationInclusion(Include.ALWAYS);
		this.setDateFormat(new ISO8601DateFormat());
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.setDateFormat(df);
	}
}
