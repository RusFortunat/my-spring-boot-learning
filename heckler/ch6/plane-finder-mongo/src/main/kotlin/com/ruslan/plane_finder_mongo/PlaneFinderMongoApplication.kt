package com.ruslan.plane_finder_mongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlaneFinderMongoApplication

fun main(args: Array<String>) {
	runApplication<PlaneFinderMongoApplication>(*args)
}
