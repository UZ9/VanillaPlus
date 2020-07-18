package com.yerti.vanillaplus.machines

import javax.xml.stream.Location

abstract class Machine(private var location: Location) {

    abstract fun save()

    fun getLocation() : Location {
        return location
    }
}