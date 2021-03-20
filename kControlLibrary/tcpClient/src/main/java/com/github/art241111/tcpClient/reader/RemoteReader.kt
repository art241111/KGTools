package com.github.art241111.tcpClient.reader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.lang.Exception
import java.net.Socket

/**
 * This class creates a Reader that will listen to the
 * incoming stream and process the data that the server sends.
 * @author Artem Gerasimov.
 */
class RemoteReader : RemoteReaderImp {
    private val incomingTextPrivate: MutableStateFlow<String> = MutableStateFlow("")
    val incomingText: StateFlow<String> = incomingTextPrivate

    // A variable that displays whether our reader is working.
    private var isReading = false

    // Reader for receiving and reading incoming data from the server
    private lateinit var reader: BufferedReader

    /**
     * Stop reading track.
     */
    override fun destroyReader() {
        isReading = false

        if (::reader.isInitialized && isReading) {
            GlobalScope.launch(Dispatchers.IO) {
                reader.close()
            }
        }
    }

    /**
     * Start reading from InputStream
     * @param socket - the connection that you want to listen to.
     */
    override fun createReader(socket: Socket) {
        reader = socket.getInputStream().bufferedReader()
        isReading = true

        GlobalScope.launch {
            startTrackingInputString(reader)
        }
    }

    private fun startTrackingInputString(reader: BufferedReader) {
        while (isReading) {
            try {
                if (reader.ready()) {
                    val line = reader.readLine()
                    incomingTextPrivate.value = line
                }
            } catch (e: NullPointerException) {
//                     Log.e("reader", "No elements come", e)
            } catch (e: Exception) {
//                    Log.e("reader", "Unknown error", e)
            }
        }
    }
}
