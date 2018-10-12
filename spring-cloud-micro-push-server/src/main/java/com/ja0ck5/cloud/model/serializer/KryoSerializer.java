package com.ja0ck5.cloud.model.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * Kryo 序列化
 * 
 * @author sysu
 * @version 2017/5/16 14:12
 */
public class KryoSerializer<T> {
	private KryoPool pool;

	public KryoSerializer(KryoPool pool) {
		this.pool = pool;
	}

	public byte[] toBytes(T obj) {
		Kryo kryo = pool.borrow();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Output output = new Output(stream);
		kryo.writeClassAndObject(output, obj);
		output.close(); // Also calls output.flush()
		pool.release(kryo);
		return stream.toByteArray(); // Serialization done, get bytes
	}

	@SuppressWarnings("unchecked")
	public T fromBytes(byte[] bytes) throws Exception {
		Kryo kryo = pool.borrow();
		Input input = new Input(new ByteArrayInputStream(bytes));
		T result;
		try {
			result = (T) kryo.readClassAndObject(new Input(new ByteArrayInputStream(bytes)));
		} catch (Exception e) {
			throw new Exception();
		}
		input.close();
		pool.release(kryo);
		return result;
	}
}
