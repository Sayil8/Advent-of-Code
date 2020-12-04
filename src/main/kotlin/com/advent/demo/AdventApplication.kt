package com.advent.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AdventApplication

fun main(args: Array<String>) {
	runApplication<AdventApplication>(*args)
}
