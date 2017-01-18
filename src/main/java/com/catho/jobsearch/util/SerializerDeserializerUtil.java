package com.catho.jobsearch.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class SerializerDeserializerUtil implements Serializer, Deserializer {

	private final Gson gson;
	private static final SerializerDeserializerUtil instance = new SerializerDeserializerUtil();

	private SerializerDeserializerUtil() {
		this.gson = new GsonBuilder().create();
	}

	public static SerializerDeserializerUtil getInstance() {
		return instance;
	}

	@Override
	public <T> T fromJson(String s, Class<T> clazz) {
		return gson.fromJson(s, clazz);
	}

	@Override
	public <T> T fromJson(InputStream is, Class<T> clazz) {
		return gson.fromJson(new JsonReader(new InputStreamReader(is)), clazz);
	}

	@Override
	public String toJson(Object o) {
		return gson.toJson(o);
	}
	
	@Override
	public byte[] serialize(Serializable o) {
		if (o == null) {
			throw new IllegalArgumentException();
		}
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); //
				ObjectOutputStream os = new ObjectOutputStream(bos)) {
			os.writeObject(o);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] in) {
		if (in == null) {
			throw new IllegalArgumentException();
		}
		try (ByteArrayInputStream bis = new ByteArrayInputStream(in); //
				ObjectInputStream is = new ObjectInputStream(bis)) {
			return (T) is.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
