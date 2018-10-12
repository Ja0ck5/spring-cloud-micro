package com.ja0ck5.cloud.model.serializer;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

import io.netty.channel.Channel;

/**
 * Kryo 对象池工厂
 * 
 * @author sysu
 * @version 2017/5/16 22:53
 */
public class KryoPoolFactory {

	private static volatile KryoPoolFactory poolFactory = null;

	private KryoPoolFactory() {
	}

	/**
	 * 初始化工厂方式
	 */
	private KryoFactory factory = () -> {
		Kryo kryo = new Kryo();
		kryo.setReferences(true);
		// 把已知的结构注册到Kryo注册器里面，提高序列化/反序列化效率
		kryo.register(Channel.class);
		kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
		return kryo;
	};

	private KryoPool pool = new KryoPool.Builder(factory).build();

	/**
	 * 单例
	 * 
	 * @return
	 */
	public static KryoPool getKryoPoolInstance() {
		if (poolFactory == null) {
			synchronized (KryoPoolFactory.class) {
				if (poolFactory == null) {
					poolFactory = new KryoPoolFactory();
				}
			}
		}
		return poolFactory.getPool();
	}

	public KryoPool getPool() {
		return pool;
	}
}
