package br.com.consumer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

public final class AppUtils {

	public final static JsonObject deserialize(byte[] content) throws IOException {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(content);
				JsonReader reader = Json.createReader(bais)) {
			return reader.readObject();
		}
	}

	public final static JsonObject toJsonObject(String message) {
		JsonObject obj = Json.createObjectBuilder()
				   .add("message", message)
				   .build();
		return obj;
	}
	
	public final static byte[] serialize(String message) throws IOException {
		return serialize(toJsonObject(message));
	}

	public final static byte[] serialize(JsonObject object) throws IOException {
		try (ByteArrayOutputStream oos = new ByteArrayOutputStream(); JsonWriter writer = Json.createWriter(oos)) {
			writer.writeObject(object);
			writer.close();
			oos.flush();
			return oos.toByteArray();
		}
	}
	
	public final static String getStringFromArray(String[] strings){
	    if (strings == null || strings.length < 1)
	        return "";
	    return joinStrings(strings, " ");
	}

	public final static String joinStrings(String[] strings, String delimiter) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuilder words = new StringBuilder(strings[0]);
	    for (int i = 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	}
	
	public final static boolean stringIsNullOrEmpty(String s){
		return s==null || s.compareTo("")==0;
	}
	
	public final static boolean stringIsNullOrEmptyOrSpaces(String s){
		return s==null || s.compareTo("")==0 || s.trim().compareTo("")==0;
	}
	
	public final static String reduceStringAt(String text, int size){
		String reducedText = text;
		if(!stringIsNullOrEmptyOrSpaces(text) && text.length() > size)
			reducedText = text.substring(0, size);
		return reducedText;
	}

	public final static void doSleepWorkByDots(String taskDelayInDots) {
		doSleepWorkByDots(taskDelayInDots, null);
	}
	public final static void doSleepWorkByDots(String task, Long millis) {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				try {
					Thread.sleep(millis == null ? 1000 : millis);
				} catch (InterruptedException _ignored) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}
