package by.heap.komodo.samples

import by.heap.komodo.args.DefaultArgs
import by.heap.komodo.command.Command
import by.heap.komodo.command.CommandArguments
import by.heap.komodo.command.CommandResult
import by.heap.komodo.command.DefaultCommandExecutor
import by.heap.komodo.command.SuccessResult
import by.heap.komodo.config.DefaultKomodoConfiguration
import by.heap.komodo.config.configModule
import by.heap.komodo.di.moduleOf
import by.heap.komodo.kmd

// http://pholser.github.io/jopt-simple/
// http://commons.apache.org/proper/commons-cli/

// http://picocontainer.com/injection.html
// https://salomonbrys.github.io/Kodein/
// http://docs.spring.io/spring-framework/docs/5.0.0.M5/spring-framework-reference/htmlsingle/#spring-introduction
// http://pholser.github.io/jopt-simple/
// http://www.jcommander.org/
// https://github.com/fusesource/jansi

// https://github.com/airlift/airline
// http://www.jcommander.org/#_overview
// http://docs.spring.io/spring-boot/docs/1.3.5.RELEASE/reference/html/configuration-metadata.html#configuration-metadata-annotation-processor
fun main(args: Array<String>) {
    kmd {
        module(testModule)
        module(configModule)
        args(args)
        run()
        command {
            println("config: " + it.getBean(DefaultKomodoConfiguration::class).getConfig(SimpleConfig::class))
        }
    }
}

class Bean1 {
    fun init() {
        println("Bean1 Constructed")
    }

    fun foo() {
        println("foo")
    }
}

interface Bean2 {
    fun init()
}

class DefaultBean2(val bean1: Bean1) : Bean2 {
    override fun init() {
        println("Default Bean2 Constructed")
        bean1.foo()
    }
}

class TestBean2(val bean1: Bean1) : Bean2 {
    override fun init() {
        println("Test Bean2 Constructed")
        bean1.foo()
    }
}

class CustomBean2(val bean1: Bean1) : Bean2 {
    override fun init() {
        println("Custom Bean2 Constructed")
        bean1.foo()
    }
}

val testModule = moduleOf {
    registerBean(Bean1::class)
    registerBean(DefaultBean2::class)
    registerBean(CustomBean2::class)
    registerBean(TestBean2::class)
    registerBean(ExampleCommand::class)
    registerBean(DefaultArgs::class)
    registerBean(DefaultCommandExecutor::class)
}

class ExampleCommand : Command {
    override val name: String = "example"

    override fun run(arguments: CommandArguments): CommandResult {
        println("!!!!!Running Example command.")
        return SuccessResult("Successful run example command.")
    }
}

data class SimpleConfig(val profile: String, val password: ByteArray)