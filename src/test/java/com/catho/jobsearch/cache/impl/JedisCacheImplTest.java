package com.catho.jobsearch.cache.impl;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.catho.jobsearch.util.Deserializer;
import com.catho.jobsearch.util.RedisUtil;
import com.catho.jobsearch.util.Serializer;
import com.catho.jobsearch.util.SerializerDeserializerUtil;

import redis.clients.jedis.Jedis;

@RunWith(MockitoJUnitRunner.class)
public class JedisCacheImplTest {

	private static final String DEFAULT_KEY = "key";

	@Mock
	private RedisUtil redisUtil;

	@Mock
	private Jedis jedis;

	private Serializer serializer = SerializerDeserializerUtil.getInstance();

	private Deserializer deserializer = SerializerDeserializerUtil.getInstance();

	private JedisCacheImpl impl;

	@Before
	public void setup() {
		impl = new JedisCacheImpl(redisUtil, serializer, deserializer);
	}

	@Test
	public void shouldGetNullWhenHasNoJedisObject() {
		Mockito.when(redisUtil.getJedisRead()).thenReturn(null);

		Serializable serializable = impl.get(DEFAULT_KEY);

		Assert.assertNull(serializable);

		Mockito.verify(jedis, Mockito.never()).get(Mockito.any(byte[].class));
		Mockito.verify(jedis, Mockito.never()).close();
	}

	@Test
	public void shouldGetNullWhenHasNoObjectInRedis() {
		Mockito.when(redisUtil.getJedisRead()).thenReturn(jedis);
		Mockito.when(jedis.get(Mockito.any(byte[].class))).thenReturn(null);

		Serializable serializable = impl.get(DEFAULT_KEY);

		Assert.assertNull(serializable);

		Mockito.verify(jedis).get(Mockito.any(byte[].class));
		Mockito.verify(jedis).close();
	}

	@Test
	public void shouldReturnNullWhenJedisThrowAnExceptionTryingGetObjectFromRedis() {
		Mockito.when(redisUtil.getJedisWrite()).thenReturn(jedis);
		Mockito.doThrow(new RuntimeException()).when(jedis).get(Mockito.any(byte[].class));

		Serializable serializable = impl.get(DEFAULT_KEY);

		Assert.assertNull(serializable);
		Mockito.verify(jedis, Mockito.never()).get(Mockito.any(byte[].class));
		Mockito.verify(jedis, Mockito.never()).close();
	}

	@Test
	public void shouldGetObjectFromRedis() {
		Mockito.when(redisUtil.getJedisRead()).thenReturn(jedis);
		Mockito.when(jedis.get(Mockito.any(byte[].class))).thenReturn(serializer.serialize("YO"));

		Serializable serializable = impl.get(DEFAULT_KEY);

		Assert.assertNotNull(serializable);

		Mockito.verify(jedis).get(Mockito.any(byte[].class));
		Mockito.verify(jedis).close();
	}

	@Test
	public void shouldDoNothingWhenTryingPutObjectAndHasNoJedisObject() {
		Mockito.when(redisUtil.getJedisWrite()).thenReturn(null);

		impl.put(DEFAULT_KEY, new String("123"), 1);

		Mockito.verify(jedis, Mockito.never()).setex(Mockito.any(byte[].class), Mockito.anyInt(),
				Mockito.any(byte[].class));
	}

	@Test
	public void shouldDoNothingWhenJedisThrowAnExceptionTryingPutObjectInRedis() {
		Mockito.when(redisUtil.getJedisWrite()).thenReturn(jedis);
		Mockito.doThrow(new RuntimeException()).when(jedis).setex(Mockito.any(byte[].class), Mockito.anyInt(),
				Mockito.any(byte[].class));

		impl.put(DEFAULT_KEY, new String("123"), 1);

		Mockito.verify(jedis).setex(Mockito.any(byte[].class), Mockito.anyInt(),
				Mockito.any(byte[].class));
		Mockito.verify(jedis).close();
	}

	@Test
	public void shouldPutObjectInRedis() {
		Mockito.when(redisUtil.getJedisWrite()).thenReturn(jedis);
		Mockito.when(jedis.setex(Mockito.any(byte[].class), Mockito.anyInt(),
				Mockito.any(byte[].class))).thenReturn("OK");

		impl.put(DEFAULT_KEY, new String("123"), 1);

		Mockito.verify(jedis).setex(Mockito.any(byte[].class), Mockito.anyInt(),
				Mockito.any(byte[].class));
		Mockito.verify(jedis).close();
	}

}
