package io.sureshg

import io.rsocket.RSocketFactory
import io.rsocket.transport.netty.client.WebsocketClientTransport
import io.rsocket.util.DefaultPayload
import java.net.URI
import java.time.Duration


fun main() {

    val ws = WebsocketClientTransport.create(URI.create("wss://rsocket-demo.herokuapp.com/ws"))
    val client = RSocketFactory.connect().keepAlive().transport(ws).start().block()!!

    try {
        val s = client.requestStream(DefaultPayload.create("peace")).take(Duration.ofMinutes(2))
            .doOnEach { println(it.get()?.dataUtf8) }.blockLast()

    } finally {
        client.dispose()
    }
}
