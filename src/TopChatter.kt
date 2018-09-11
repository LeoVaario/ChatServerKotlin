object TopChatter : Observer{
    var userAndMsgMap = mutableMapOf<String, Int>()

    override fun update(message: ChatMessage) {
        if(userAndMsgMap.containsKey(message.user)){
            val value = userAndMsgMap.get(message.user)
            if (value != null){
                userAndMsgMap.set(message.user)
            }
        }
        else {
            userAndMsgMap.put(message.user, 1)

        }
    }
}