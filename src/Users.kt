object Users {

    val userSet = mutableSetOf<String>()

    fun addUser(nameInput: String): Boolean {
        if (userSet.contains(nameInput)) {
            println("failed to add user")
            return false
        } else {
            println("user added successfully")
            userSet.add(nameInput)
            return true
        }
    }

    fun removeUser(nameInput: String) {
        userSet.remove(nameInput)
    }

    fun findUser (testString : String) : String?{
        if(testString.isNotEmpty()){
            for (i in 1 until testString.length+1){
                println(testString.length+1)
                println("checking substring: ${testString.substring(0,i)}")
                val stringOrNull = checkSubString(testString.substring(0, i), testString)
                if(stringOrNull != null){
                    return stringOrNull
                }
            }
            return null         //string did not match any user
        }
        else return null        //string was empty
    }

    /*
    users: Leonardo, Lee

    try find Leo

    L - L(eonardo), L(ee)
    more than 1 match

    Le - Le(onardo), Le(e)
    more than 1 match

    Leo - Leo(nardo), Lee(not matching)
    1 match

    */
    fun checkSubString(subString: String, wholeString:String): String? { //returns user if only one user corresponds to parameter substring
        var matches = 0                             //otherwise returns null
        var userStorage : String? = null
        for (user in userSet) {
            if (wholeString == user) {
                return user
            }
        }

        for (user in userSet) {
            if (subString == user.substring(0, subString.length-1)) {
                userStorage = user
                matches++
            }
        }
        if (matches == 1) {
            return userStorage
        } else return null
    }
}



