package structure

/**
 * JMA - 04/05/2022 00:26
 **/
interface Command {
    fun execute(action: () -> Unit)
    fun undo()
}
