import java.io.PrintStream
import java.net.ServerSocket

fun main(args: Array<String>) {
    try {
        //val serverSocket = ServerSocket(30002, 3)

        val serverSocket = ServerSocket(0)
        println("We have port number: " + serverSocket.localPort)

        while(true) {
            println("Step 1")
            val socket = serverSocket.accept()

            println("Step 2")

            val printer = PrintStream(socket.getOutputStream(), true)
            val thread = Thread(CommandInterpreter(socket.getInputStream(), printer))
            thread.start()
        }

    } catch (e: Exception) {
        println("something went wrong ${e.message}")
    }
}
