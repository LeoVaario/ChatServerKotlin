import java.time.LocalDateTime
import java.util.*

fun main(args: Array<String>){

    CommandInterpreter(System.`in`, System.out).run()

    /*
    var firstThread = CommandInterpreter(System.`in`, System.out)
    var secondThread = CommandInterpreter(System.`in`, System.out)

    firstThread.userName = "Leo"
    secondThread.userName = "NotLeo"


    Users.addUser("Leo")
    Users.addUser("LeoTwo")
    Users.addUser("NotLeo")

    println(Users.userSet)

    val message = "Hello! how are you"
    val doesRecipientExist = Users.findUser("Not")

    ChatHistory.addMessage(message, "Leo", LocalDateTime.now(), doesRecipientExist)

    println(Users.userSet)

    println("Done")

*/
}