package app.command

/**
 * JMA - 04/05/2022 00:26
 **/
interface Command {
    fun execute(action: Command)
    fun undos()
}
