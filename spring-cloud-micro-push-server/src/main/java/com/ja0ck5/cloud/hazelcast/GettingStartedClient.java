package com.ja0ck5.cloud.hazelcast;


import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.nio.channels.Channel;

public class GettingStartedClient {
    public static void main( String[] args ) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getGroupConfig().setName("dev");
        clientConfig.getNetworkConfig().addAddress("172.16.11.159:5701","172.16.11.159:5702");
        HazelcastInstance client = HazelcastClient.newHazelcastClient( clientConfig );
        IMap<Integer,Channel> map = client.getMap( "customers" );
        System.out.println( "Map Size:" + map.size() );
        System.out.println( "Map Channel:" + map.get(1) );
        client.shutdown();
    }
}
