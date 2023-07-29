package im.getmansky

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class MyRunner : CommandLineRunner {

    override fun run(vararg args: String?) {

        println("Ok!")
    }
}
