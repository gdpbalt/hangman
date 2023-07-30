package im.getmansky

import im.getmansky.service.HangmanService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class MyRunner(
    private val hangmanService: HangmanService,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Начало игры \"Виселица\"")
        hangmanService.run()
        println("Конец игры")
    }
}