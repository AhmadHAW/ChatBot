package client.listenerComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import client.timeOutComponent.TimeOutComponentInterface;

@Service
public class StreamListener {
	@Autowired
	TimeOutComponentInterface toci;

	StreamListener() {

	}

	@Async
	public void startListen() {
		while (!Thread.interrupted()) {
			
		}
	}

}
