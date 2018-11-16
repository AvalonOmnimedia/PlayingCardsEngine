package com.avalonomnimedia.war.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi

fun main(args: Array<String>) = App().main(args)

class App : CliktCommand() {
    override fun run() {
        val game = Game { message -> TermUi.echo(message) }
        game.play()
    }
}