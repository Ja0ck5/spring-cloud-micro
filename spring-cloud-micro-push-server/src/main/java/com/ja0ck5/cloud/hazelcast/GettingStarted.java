package com.ja0ck5.cloud.hazelcast;

import com.hazelcast.core.*;
import com.hazelcast.config.*;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.Map;
import java.util.Queue;

public class GettingStarted {

	public static void main(String[] args) {
		Config cfg = new Config();
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);

		Map<Integer,Channel> mapCustomers = instance.getMap("customers");
		Channel channel = new Channel() {
			@Override
			public boolean isOpen() {
				return true;
			}

			@Override
			public void close() throws IOException {

			}
		};
		System.out.println("channel:" + channel);
		mapCustomers.put(1, channel);// serialization error
		mapCustomers.put(2, channel);
		mapCustomers.put(3, channel);

		System.out.println("Customer with key 1: " + mapCustomers.get(1));
		System.out.println("Map Size:" + mapCustomers.size());

		Queue<String> queueCustomers = instance.getQueue("customers");
		queueCustomers.offer("Tom");
		queueCustomers.offer("Mary");
		queueCustomers.offer("Jane");
		System.out.println("First customer: " + queueCustomers.poll());
		System.out.println("Second customer: " + queueCustomers.peek());
		System.out.println("Queue size: " + queueCustomers.size());
	}
}