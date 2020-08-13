package io.sureshg

import io.rsocket.core.RSocketConnector
import io.rsocket.metadata.WellKnownMimeType
import io.rsocket.transport.netty.client.WebsocketClientTransport
import io.rsocket.util.DefaultPayload
import java.net.URI
import java.time.Duration


fun main() {

    val ws: WebsocketClientTransport =
        WebsocketClientTransport.create(URI.create("wss://rsocket-demo.herokuapp.com/rsocket"))

    val clientRSocket = RSocketConnector.create()
        .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.string)
        .dataMimeType(WellKnownMimeType.APPLICATION_JSON.string)
        .connect(ws)
        .block()!!

    try {
        clientRSocket.requestStream(DefaultPayload.create("peace"))
            .take(Duration.ofMinutes(2))
            .doOnEach { println(it.get()?.dataUtf8) }
            .blockLast()

    } finally {
        clientRSocket.dispose()
    }
}
