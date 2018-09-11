import java.io.InputStream
import java.io.PrintStream
import java.util.*
import kotlin.math.min

class CommandInterpreter(input : InputStream, val printStream : PrintStream) : Runnable, Observer {

    private val scanner: Scanner = Scanner(input)
    //val printStream: PrintStream = PrintStream(output)

    var quit = false
    var userName: String? = null

    init {
        ChatHistory.registerObserver(this)
    }

    override fun update(message: ChatMessage) {

        if(message.user != userName) {
            printStream.println(message.toString())
            println("update()")
        }
    }

    override fun run() {

        printStream.println("Welcome to Chat!")
        Users.userSet.add("SERVER")
        Users.userSet.add("Leeb")

        while (!quit) {

            var userEntry = scanner.nextLine()

            //handling commands to methods

            //USERNAME NOT SET
            if (userName == null) {
                if (userEntry != null) {
                    if (    userEntry.substring(0, min(6, userEntry.length)) == ":user "       //check for :user
                        &&  userEntry.removePrefix(":user ") != ""                     ) {  //check there is something after :user

                        if (Users.addUser(userEntry.removePrefix(":user "))) {
                            userName = userEntry.removePrefix(":user ")
                            printStream.println("Welcome $userName!")
                            printStream.println("User name successfully set!")
                        } else printStream.println("Username already exists! Failed to set user")
                    }
                    else if(userEntry.substring(0, min(5, userEntry.length)) == ":help"){
                        help()
                    }
                    else printStream.println("Please enter username first with :user")
                }
            }

            //USERNAME IS ALREADY SET
            else {
                if (userEntry.isNotEmpty() && userEntry != null) {
                    if (userEntry[0] == ':') {
                        if (userEntry.substring(0, min(5, userEntry.length)) == ":quit") {
                            Users.removeUser(userName.toString())
                            quit = true
                            printStream.println("Quitting..")
                            scanner.close()
                        }

                        else if (userEntry.substring(0, min(6, userEntry.length)) == ":users") {
                            val userList = Users.userSet.toList()

                            for (users in userList) {
                                printStream.println(users)
                            }
                        }

                        else if (userEntry.substring(0, min(9, userEntry.length)) == ":messages") {
                            val input = userEntry.removePrefix(":messages ").toIntOrNull()
                            if (input != null){
                                ChatHistory.printMessages(input, this)
                            }
                            else {
                                ChatHistory.printMessages(10, this)
                            }
                        }

                        else if (    userEntry.substring(0, min(8, userEntry.length)) == ":private"
                                  || userEntry.substring(0, min(3, userEntry.length)) == ":pm"        ) {
                            var msgRecipient = userEntry.removePrefix(":private ")                           //remove private prefix
                            msgRecipient = msgRecipient.removePrefix(":pm ")                                 //remove pm prefix
                            val message = msgRecipient.substringAfter(" ")
                            msgRecipient = msgRecipient.substringBefore(" ")
                            println("looking for user: $msgRecipient")

                            //findUser
                            val doesRecipientExist = Users.findUser(msgRecipient)
                            if (doesRecipientExist != null){
                                ChatHistory.addMessage(message, userName.toString(),java.time.LocalDateTime.now(), doesRecipientExist  )
                            }
                            else (printStream.println("Specified user recipient was either not found or not specific enough!"))
                        }

                        else if(userEntry.substring(0, min(5, userEntry.length)) == ":help"){
                            help()
                        }

                        else {
                            printStream.println("not a valid command, type :help to see list of commands")
                        }
                    }
                    else {
                        ChatHistory.addMessage(userEntry, userName.toString(), java.time.LocalDateTime.now(), null)
                    }
                }
            }
        }
    }

    private fun help(){
        printStream.println("List of commands:")
        printStream.println(":help -- displays list of commands")
        printStream.println(":user  -- Sets the username for this session")
        printStream.println(":users -- displays all currently active users.")
        printStream.println(":messages -- will show last 10 messages. can be followed by number to show specific number of messages")
        printStream.println(":private or :pm -- must be followed by valid user name and then message. sends a private message to the user")
        printStream.println(":quit -- logs you out of this session")
    }
}